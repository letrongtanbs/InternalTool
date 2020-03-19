package com.tvj.internaltool.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tvj.internaltool.entity.RolePermissionEntity;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.ErrorResUtils;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;

// Must declare Filter as @Bean in WebSecurityConfig to join FilterChain
public class UserRoleFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            final String url = request.getRequestURI();

            String username = authentication.getName();
            UserEntity user = userService.getUserByUsername(username);
            Set<RolePermissionEntity> rolePermissionSet = user.getRole().getRolePermission();

            String[] urlParts = url.split("/");

            boolean isAccessible = false;

            for (RolePermissionEntity rolePermission : rolePermissionSet) {
                String[] rolePermissionSetParts = rolePermission.getPermission().getPermissionUrl().split("/");
                boolean isAllPartsMatch = false;

                // If number of path is different, bypass current loop
                if (rolePermissionSetParts.length != urlParts.length) {
                    continue;
                }

                // Compare each path of url
                for (int i = 0; i < rolePermissionSetParts.length; i++) {

                    // If current part is path variable, bypass current loop
                    if (rolePermissionSetParts[i].startsWith("{")) {
                        continue;
                    }

                    // If any part is mismatch, stop loop
                    if (!rolePermissionSetParts[i].equals(urlParts[i])) {
                        isAllPartsMatch = false;
                        break;
                    }

                    isAllPartsMatch = true;
                }

                // If match at least 1 url, stop loop then grant access
                if (isAllPartsMatch) {
                    isAccessible = true;
                    break;
                }
            }

            if (isAccessible) {
                // Continue next process after filter
                chain.doFilter(request, response);
            } else {
                ErrorResUtils errorResUtils = new ErrorResUtils();
                errorResUtils.responseError(ResponseCode.FORBIDDEN, ResponseMessage.FORBIDDEN, response);
            }

        } else {
            // Continue next process after filter
            chain.doFilter(request, response);
        }
    }
}