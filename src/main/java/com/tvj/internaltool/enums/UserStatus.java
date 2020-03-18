package com.tvj.internaltool.enums;

public enum UserStatus {

    OFFLINE("0"), AVAILABLE("1"), AWAY("2"), BUSY("3"), DO_NOT_DISTURB("4");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
