package com.tvj.internaltool.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private String avatarUploadDir;

}
