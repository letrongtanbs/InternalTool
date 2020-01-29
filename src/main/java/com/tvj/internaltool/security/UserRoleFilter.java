package com.tvj.internaltool.security;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.ErrorResDto;
import com.tvj.internaltool.entity.UserEntity;
import com.tvj.internaltool.service.UserService;
import com.tvj.internaltool.utils.ErrorCode;
import com.tvj.internaltool.utils.ErrorMessage;
import com.tvj.internaltool.utils.UserUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserRoleFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            String url = request.getRequestURI();

            UserEntity userEntity = userService.getUserByUserName(username);

            System.out.println("username is ->>: " + username);
            System.out.println("url is ->>: " + url);

            if (url.equals("/user/url-for-root") && userEntity.getRole().getRoleName().equals("ROOT")) {
                // Continue next process after filter
                chain.doFilter(request, response);
            } else {
                ErrorResDto errorResDto = new ErrorResDto(ErrorCode.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED);
                Gson gson = new Gson();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(gson.toJson(errorResDto));
            }
        } else {
            // Continue next process after filter
            chain.doFilter(request, response);
        }
    }
}