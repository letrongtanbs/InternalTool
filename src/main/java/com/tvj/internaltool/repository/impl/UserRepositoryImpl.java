package com.tvj.internaltool.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import com.tvj.internaltool.dto.req.MemberSearchReqDto;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.CustomUserRepository;

public class UserRepositoryImpl implements CustomUserRepository {

    private EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<UserEntity> searchMember(MemberSearchReqDto memberSearchReqDto) {
        int offset = memberSearchReqDto.getOffset();
        int limit = memberSearchReqDto.getLimit();

        StringBuilder queryStr = new StringBuilder();
        queryStr.append(" SELECT u FROM UserEntity u ");
        queryStr.append(" WHERE u.deletedDate IS NULL ");

        if (StringUtils.isNotBlank(memberSearchReqDto.getName())) {
            queryStr.append(" AND ((LOWER(u.firstName) LIKE LOWER(CONCAT('%',:name,'%'))) OR (LOWER(u.lastName) LIKE LOWER(CONCAT('%',:name,'%')))) ");
        }

        if (StringUtils.isNotBlank(memberSearchReqDto.getUsername())) {
            queryStr.append(" AND LOWER(u.username) LIKE LOWER(CONCAT('%',:username,'%')) ");
        }

        if (StringUtils.isNotBlank(memberSearchReqDto.getTitleId())) {
            queryStr.append(" AND u.titleId = :titleId ");
        }

        if (memberSearchReqDto.getIsActivated() != null) {
            queryStr.append(" AND u.isActivated = :isActivated ");
        }

        queryStr.append(" ORDER BY u.username ASC ");

        TypedQuery<UserEntity> query = entityManager.createQuery(queryStr.toString(), UserEntity.class);

        if (StringUtils.isNotBlank(memberSearchReqDto.getName())) {
            query.setParameter("name", memberSearchReqDto.getName());
        }

        if (StringUtils.isNotBlank(memberSearchReqDto.getUsername())) {
            query.setParameter("username", memberSearchReqDto.getUsername());
        }

        if (StringUtils.isNotBlank(memberSearchReqDto.getTitleId())) {
            query.setParameter("titleId", memberSearchReqDto.getTitleId());
        }

        if (memberSearchReqDto.getIsActivated() != null) {
            query.setParameter("isActivated", memberSearchReqDto.getIsActivated());
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public long searchMemberTotal(MemberSearchReqDto memberSearchReqDto) {
        StringBuilder queryStr = new StringBuilder();
        queryStr.append(" SELECT COUNT(u) FROM UserEntity u ");
        queryStr.append(" WHERE u.deletedDate IS NULL ");

        if (StringUtils.isNotBlank(memberSearchReqDto.getName())) {
            queryStr.append(" AND ((LOWER(u.firstName) LIKE LOWER(CONCAT('%',:name,'%'))) OR (LOWER(u.lastName) LIKE LOWER(CONCAT('%',:name,'%')))) ");
        }

        if (StringUtils.isNotBlank(memberSearchReqDto.getUsername())) {
            queryStr.append(" AND LOWER(u.username) LIKE LOWER(CONCAT('%',:username,'%')) ");
        }

        if (StringUtils.isNotBlank(memberSearchReqDto.getTitleId())) {
            queryStr.append(" AND u.titleId = :titleId ");
        }

        if (memberSearchReqDto.getIsActivated() != null) {
            queryStr.append(" AND u.isActivated = :isActivated ");
        }

        TypedQuery<Long> query = entityManager.createQuery(queryStr.toString(), Long.class);

        if (StringUtils.isNotBlank(memberSearchReqDto.getName())) {
            query.setParameter("name", memberSearchReqDto.getName());
        }

        if (StringUtils.isNotBlank(memberSearchReqDto.getUsername())) {
            query.setParameter("username", memberSearchReqDto.getUsername());
        }

        if (StringUtils.isNotBlank(memberSearchReqDto.getTitleId())) {
            query.setParameter("titleId", memberSearchReqDto.getTitleId());
        }

        if (memberSearchReqDto.getIsActivated() != null) {
            query.setParameter("isActivated", memberSearchReqDto.getIsActivated());
        }

        return query.getSingleResult();
    }

}
