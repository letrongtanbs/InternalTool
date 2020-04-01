package com.tvj.internaltool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvj.internaltool.dto.res.LanguageListResDto;
import com.tvj.internaltool.service.LanguageService;

@RestController
@RequestMapping("/language")
@Validated // Enable validation for both request parameters and path variables
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllLanguage() {
        LanguageListResDto languageListResDto = languageService.getAllLanguage();
        return new ResponseEntity<>(languageListResDto, HttpStatus.OK);
    }

}
