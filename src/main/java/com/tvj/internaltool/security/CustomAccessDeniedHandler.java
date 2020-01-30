package com.tvj.internaltool.security;

import com.tvj.internaltool.utils.ErrorCode;
import com.tvj.internaltool.utils.ErrorMessage;
import com.tvj.internaltool.utils.ErrorResUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // define response for invalid authorization
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        ErrorResUtils errorResUtils = new ErrorResUtils();
        errorResUtils.responseError(ErrorCode.FORBIDDEN, ErrorMessage.FORBIDDEN, response);
    }
}