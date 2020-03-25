package com.tvj.internaltool.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvj.internaltool.dto.res.TitleListResDto;
import com.tvj.internaltool.service.TitleService;

@RestController
@RequestMapping("/title")
public class TitleController {

    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> getAllTitle() {
        TitleListResDto titleListResDto = titleService.getAllTitle();
        return new ResponseEntity<>(titleListResDto, HttpStatus.OK);
    }

}
