package com.tvj.internaltool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import com.tvj.internaltool.exception.FileStorageException;
import com.tvj.internaltool.properties.FileStorageProperties;

@RunWith(MockitoJUnitRunner.class)
public class FileStorageServiceImplTest {

    private final static String currentUsername = "admin1";

    private final static String dateTimePatternSticky = "yyyyMMddhhmmss";

    private final static String avatarUploadDir = "F:\\TVJ\\file_upload\\test\\";

    private final static String avatarTestFileName = "test.jpg";

    private final static String defaultAvatarTestFileName = "default_avatar.jpg";

    private FileStorageProperties fileStorageProperties = new FileStorageProperties(avatarUploadDir);

    @InjectMocks
    private FileStorageServiceImpl fileStorageService = new FileStorageServiceImpl(fileStorageProperties); // cannot
                                                                                                           // InjectMocks
                                                                                                           // interface

    @Before
    public void setUp() {
        // Initialize value for @Value parameter
        ReflectionTestUtils.setField(fileStorageService, "dateTimePatternSticky", dateTimePatternSticky);
        ReflectionTestUtils.setField(fileStorageService, "defaultAvatarFileName", defaultAvatarTestFileName);

        // Mocking Spring Security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(currentUsername);
    }

    // ---------- storeFile START ---------

    @Test
    public void storeFile_success() {

        // Value from client
        MockMultipartFile image = new MockMultipartFile("file", "cv.jpg", "image/jpeg",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.jpg\"}".getBytes());

        String outputFileName = fileStorageService.storeFile(image);
        String[] outputFileNameParts = outputFileName.split("_");

        assertEquals(outputFileNameParts[0], currentUsername);
        assertEquals(outputFileNameParts[2], image.getOriginalFilename());
    }

    @Test(expected = FileStorageException.class) // expected throws exception
    public void storeFile_failure() {
        // Value from client
        MockMultipartFile image = new MockMultipartFile("file", "../cv.jpg", "image/jpeg",
                "{\"image\": \"F:\\TVJ\\file_upload\\test\\cv.jpg\"}".getBytes());

        fileStorageService.storeFile(image);
    }

    // ---------- storeFile END ---------

    // ---------- convertAvatarToBase64 START ---------

    @Test
    public void convertAvatarToBase64_loadUserAvatar_success() {
        String outputFileName = fileStorageService.convertAvatarToBase64(avatarTestFileName);
        assertEquals(outputFileName.substring(outputFileName.length() - 1), "=");
    }

    @Test
    public void convertAvatarToBase64_loadDefaultAvatar_success() {
        String outputFileName = fileStorageService.convertAvatarToBase64("/not_exists.jpg");
        assertEquals(outputFileName.substring(outputFileName.length() - 1), "=");
    }

    @Test
    public void convertAvatarToBase64_loadDefaultAvatarFailure() {
        String absolutePath1 = avatarUploadDir + defaultAvatarTestFileName;
        String absolutePath2 = avatarUploadDir + "xxx_" + defaultAvatarTestFileName;

        // Change default avatar name
        File defaultAvatar1 = new File(absolutePath1);
        File defaultAvatar2 = new File(absolutePath2);
        defaultAvatar1.renameTo(defaultAvatar2);

        String outputFileName = fileStorageService.convertAvatarToBase64("/not_exists.jpg");
        assertNull(outputFileName);

        // Rollback default avatar name
        File defaultAvatar3 = new File(absolutePath2);
        defaultAvatar3.renameTo(defaultAvatar1);
    }

    // ---------- convertAvatarToBase64 END ---------

}
