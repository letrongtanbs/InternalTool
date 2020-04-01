package com.tvj.internaltool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvj.internaltool.dto.res.CountryListResDto;
import com.tvj.internaltool.service.CountryService;

@RestController
@RequestMapping("/country")
@Validated // Enable validation for both request parameters and path variables
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllCountry() {
        CountryListResDto countryListResDto = countryService.getAllCountry();
        return new ResponseEntity<>(countryListResDto, HttpStatus.OK);
    }

}
