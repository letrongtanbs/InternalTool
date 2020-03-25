package com.tvj.internaltool.dto.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberSearchReqDto {

    private String name;

    @Size(max = 20)
    private String username;

    private String titleId;

    private Boolean isActivated;

    @Min(0)
    private int offset;

    @Min(0)
    private int limit;

}
