package com.tvj.internaltool.security;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.ErrorResDto;
import com.tvj.internaltool.utils.ErrorCode;
import com.tvj.internaltool.utils.ErrorMessage;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // define response for invalid authorization
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        ErrorResDto errorResDto = new ErrorResDto(ErrorCode.FORBIDDEN, ErrorMessage.FORBIDDEN);
        Gson gson = new Gson();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(gson.toJson(errorResDto));
    }
}