package com.tvj.internaltool.dummy.dto.res;

import com.tvj.internaltool.dto.res.MemberResDto;

public class MemberResDtoDataDummy {

    public MemberResDto getMember1() {
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setFirstName("Ad");
        memberResDto.setLastName("Min1");
        memberResDto.setUsername("admin1");
        memberResDto.setTitleName("Developer 1");
        memberResDto.setActivated(true);
        memberResDto.setEmail("ngocdc@tinhvan.com");
        return memberResDto;
    }

    public MemberResDto getMember2() {
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setFirstName("Ad");
        memberResDto.setLastName("Min2");
        memberResDto.setUsername("admin2");
        memberResDto.setTitleName("Developer 2");
        memberResDto.setActivated(true);
        memberResDto.setEmail("ngocdc@tinhvan.com");
        return memberResDto;
    }

}
