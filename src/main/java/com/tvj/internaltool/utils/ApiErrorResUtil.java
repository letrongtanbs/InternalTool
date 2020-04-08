package com.tvj.internaltool.utils;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiErrorResUtil {
    private String code;
    private String message;
    private List<String> errors;

    public ApiErrorResUtil(String code, String message, List<String> errors) {
        super();
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResUtil(String code, String message, String error) {
        super();
        this.code = code;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
