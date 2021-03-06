package com.tvj.internaltool.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile multipartFile);

    String convertAvatarToBase64(String fileName);

}
