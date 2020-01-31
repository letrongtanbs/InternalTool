package com.tvj.internaltool.utils;

import com.google.gson.Gson;
import com.tvj.internaltool.dto.res.MessageResDto;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ErrorResUtils {
    public void responseError(String errorCode, String errorMessage, HttpServletResponse response) throws IOException {
        MessageResDto messageResDto = new MessageResDto(errorCode, errorMessage);
        Gson gson = new Gson();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(gson.toJson(messageResDto));
    }
}
