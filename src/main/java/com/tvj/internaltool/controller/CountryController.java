package com.tvj.internaltool.controller;

import com.tvj.internaltool.dto.res.CountryListResDto;
import com.tvj.internaltool.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
@CrossOrigin
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