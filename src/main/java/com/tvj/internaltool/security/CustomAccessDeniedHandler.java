package com.tvj.internaltool.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.tvj.internaltool.utils.ErrorResUtils;
import com.tvj.internaltool.utils.ResponseCode;
import com.tvj.internaltool.utils.ResponseMessage;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // define response for invalid authorization
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        ErrorResUtils errorResUtils = new ErrorResUtils();
        errorResUtils.responseError(ResponseCode.FORBIDDEN, ResponseMessage.FORBIDDEN, response);
    }
}