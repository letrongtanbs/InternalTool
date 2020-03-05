package com.tvj.internaltool.service.impl;

import com.tvj.internaltool.exception.FileStorageException;
import com.tvj.internaltool.properties.FileStorageProperties;
import com.tvj.internaltool.service.FileStorageService;
import com.tvj.internaltool.utils.UserUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    @Value("${date-time-pattern.sticky}")
    private String dateTimePatternSticky;

    private final Path filePath;

    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.filePath = Paths.get(fileStorageProperties.getAvatarUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.filePath);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile multipartFile) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String newFileName = UserUtils.getCurrentUsername() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePatternSticky)) + "_" + fileName;

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.filePath.resolve(newFileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public String convertImageToBase64(String fileName) {
        if (fileName != null) {
            byte[] fileContent;
            String absoluteFilePath = filePath.toString() + "\\" + fileName;
            try {
                fileContent = FileUtils.readFileToByteArray(new File(absoluteFilePath));
            } catch (IOException ex) {
                logger.error(ex.getMessage());
                throw new FileStorageException("Could not convert file " + fileName + " to Base64 string. Please try again!", ex);
            }
            return Base64.getEncoder().encodeToString(fileContent);
        }
        return null;
    }

}
