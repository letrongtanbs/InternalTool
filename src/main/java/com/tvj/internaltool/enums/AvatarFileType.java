package com.tvj.internaltool.enums;

public enum AvatarFileType {

    JPG("image/jpeg"), PNG("image/png");

    private String fileType;

    AvatarFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

}
