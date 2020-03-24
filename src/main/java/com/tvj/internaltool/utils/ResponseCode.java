package com.tvj.internaltool.utils;

public class ResponseCode {

    // Error code
    public static final String COMMON_ERROR = "999";
    public static final String FORBIDDEN = "ERR001";
    public static final String UNAUTHORIZED = "ERR002";
    public static final String SEND_MAIL_FAILED = "ERR003";
    public static final String TOKEN_HAS_EXPIRED = "ERR004";
    public static final String UPDATE_PASSWORD_FAILED = "ERR005";
    public static final String UPDATE_AVATAR_FAILED = "ERR006";
    public static final String USER_IS_LOCKED = "ERR007";
    public static final String UPDATE_USER_SETTING_FAILED = "ERR008";
    public static final String GET_USER_SETTING_FAILED = "ERR009";
    public static final String REMOVE_AVATAR_FAILED = "ERR010";
    public static final String SAVE_LAST_LOGOUT_FAILED = "ERR011";
    public static final String ADD_NEW_MEMBER_FAILED = "ERR012";
    public static final String UPDATE_MEMBER_FAILED = "ERR013";

    // Success code
    public static final String SEND_MAIL_SUCCESS = "SUC001";
    public static final String UPDATE_PASSWORD_SUCCESS = "SUC002";
    public static final String REMOVE_AVATAR_SUCCESS = "SUC003";
    public static final String TOKEN_IS_VALID = "SUC004";
    public static final String SAVE_LAST_LOGOUT_SUCCESS = "SUC005";
    public static final String ADD_NEW_MEMBER_SUCCESS = "SUC006";
    public static final String UPDATE_MEMBER_SUCCESS = "SUC007";

}
