package com.tvj.internaltool.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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
