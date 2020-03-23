package com.tvj.internaltool.service.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tvj.internaltool.dto.req.MemberAddReqDto;
import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.dto.res.MemberListResDto;
import com.tvj.internaltool.dto.res.MemberResDto;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.entity.UserSettingEntity;
import com.tvj.internaltool.enums.UserStatus;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.repository.UserSettingRepository;
import com.tvj.internaltool.service.EmailService;
import com.tvj.internaltool.service.MemberManagementService;
import com.tvj.internaltool.utils.ModelMapperUtils;
import com.tvj.internaltool.utils.StaticValueUtils;
import com.tvj.internaltool.utils.UserUtils;

@Service
public class MemberManagementServiceImpl implements MemberManagementService {

    private static final Logger logger = LoggerFactory.getLogger(MemberManagementServiceImpl.class);

    @Value("${password-for-new-user.mail-subject}")
    private String passwordForNewUserMailSubject;

    @Value("${password-for-new-user.mail-template}")
    private String passwordForNewUserMailTemplate;

    private final UserRepository userRepository;
    private final UserSettingRepository userSettingRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public MemberManagementServiceImpl(UserRepository userRepository, UserSettingRepository userSettingRepository,
            PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.userSettingRepository = userSettingRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public MemberListResDto searchMember(MemberSearchReqDto memberSearchReqDto) {
        List<UserEntity> userEntityList = userRepository.searchMember(memberSearchReqDto);
        long userEntityListTotal = userRepository.searchMemberTotal(memberSearchReqDto);
        List<MemberResDto> memberResDtoList = ModelMapperUtils.mapAll(userEntityList, MemberResDto.class);
        return new MemberListResDto(userEntityListTotal, memberResDtoList);
    }

    @Transactional
    @Override
    public boolean addMember(MemberAddReqDto memberAddReqDto) {

        // check old member exists
        UserEntity oldUser = userRepository.findByUsername(memberAddReqDto.getUsername());
        if (oldUser != null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        // Save initial user setting
        UserSettingEntity userSettingEntity = new UserSettingEntity();
        userSettingEntity.setUserSettingId(UUID.randomUUID().toString());
        userSettingEntity.setCountryId(StaticValueUtils.COUNTRY_ID_VIETNAM);
        userSettingEntity.setLanguageId(StaticValueUtils.LANGUAGE_ID_ENGLISH);
        userSettingEntity.setStatusId(UserStatus.AVAILABLE.getStatus());
        userSettingEntity.setCreatedBy(UserUtils.getCurrentUsername());
        userSettingEntity.setCreatedDate(now);
        userSettingRepository.save(userSettingEntity);

        // Save user info
        UserEntity newUser = new UserEntity();
        newUser.setUserId(UUID.randomUUID().toString());
        newUser.setUserSettingId(userSettingEntity.getUserSettingId());
        newUser.setRoleId(StaticValueUtils.ROLE_ID_USER);
        newUser.setUsername(memberAddReqDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(memberAddReqDto.getPassword()));
        newUser.setFirstName(memberAddReqDto.getFirstName());
        newUser.setLastName(memberAddReqDto.getLastName());
        newUser.setTitleId(memberAddReqDto.getTitleId());
        newUser.setEmail(memberAddReqDto.getEmail());
        newUser.setActivated(true);
        newUser.setFirstTimeLogin(true);
        newUser.setLoginFailCount(0);
        newUser.setCreatedBy(UserUtils.getCurrentUsername());
        newUser.setCreatedDate(now);
        userRepository.save(newUser);

        // Send mail including password to new user
        try {
            emailService.sendSimpleMessage(memberAddReqDto.getEmail(),
                    MessageFormat.format(passwordForNewUserMailSubject, memberAddReqDto.getUsername()),
                    MessageFormat.format(passwordForNewUserMailTemplate, memberAddReqDto.getPassword()));
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }

}
