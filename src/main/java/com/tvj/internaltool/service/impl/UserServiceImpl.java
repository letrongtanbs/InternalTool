package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.repository.UserRepository;
import com.tvj.internaltool.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserDetails processLogin(String username, String password) throws UsernameNotFoundException, DataAccessException {
        UserEntity user = userRepository.findByUsername(username);

        // Check if user exists
        if (user == null) {
            return null;
        }

        // Compare password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // Get role of current login user
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        // By this way, UserEntity does not need to implement UserDetails
        return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @Override
    public UserDetails getUserDetails(String username) {
        UserEntity user = userRepository.findByUsername(username);

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // Get role of current login user
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        // By this way, UserEntity does not need to implement UserDetails
        return new User(username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
