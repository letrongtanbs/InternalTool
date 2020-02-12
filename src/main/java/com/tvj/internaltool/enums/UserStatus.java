package com.tvj.internaltool.enums;

public enum UserStatus {

    AVAILABLE(0), BUSY(1), DO_NOT_DISTURB(2);

    private int status;

    UserStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
