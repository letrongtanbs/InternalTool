package com.tvj.internaltool.security;

import com.tvj.internaltool.entity.RolePermissionEntity;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.ErrorCode;
import com.tvj.internaltool.utils.ErrorMessage;
import com.tvj.internaltool.utils.ErrorResUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class UserRoleFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            String url = request.getRequestURI();

            UserEntity user = userService.getUserByUserName(username);

            Set<RolePermissionEntity> rolePermissionSet = user.getRole().getRolePermission();

            boolean isAccessible = false;

            for (RolePermissionEntity rolePermission : rolePermissionSet) {
                if (rolePermission.getPermission().getPermissionUrl().equals(url)) {
                    // Continue next process after filter
                    isAccessible = true;
                    break;
                }
            }

            if (isAccessible) {
                chain.doFilter(request, response);
            } else {
                ErrorResUtils errorResUtils = new ErrorResUtils();
                errorResUtils.responseError(ErrorCode.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED, response);
            }

        } else {
            // Continue next process after filter
            chain.doFilter(request, response);
        }
    }
}