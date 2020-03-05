package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.TitleResDto;

public class TitleResDtoDataDummy {

    public TitleResDto getTitle1() {
        TitleResDto titleResDto = new TitleResDto();
        titleResDto.setTitleId("1");
        titleResDto.setTitleCode("DEV001");
        titleResDto.setTitleName("Developer level 1");
        return titleResDto;
    }

    public TitleResDto getTitle2() {
        TitleResDto titleResDto = new TitleResDto();
        titleResDto.setTitleId("2");
        titleResDto.setTitleCode("DEV002");
        titleResDto.setTitleName("Developer level 2");
        return titleResDto;
    }

}
