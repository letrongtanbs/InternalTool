package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.LanguageResDto;

public class LanguageResDtoDataDummy {

    public LanguageResDto getLanguage1() {
        LanguageResDto languageResDto = new LanguageResDto();
        languageResDto.setLanguageId("1");
        languageResDto.setLanguageName("VietNamese");
        return languageResDto;
    }

    public LanguageResDto getLanguage2() {
        LanguageResDto languageResDto = new LanguageResDto();
        languageResDto.setLanguageId("2");
        languageResDto.setLanguageName("Japanese");
        return languageResDto;
    }

}
