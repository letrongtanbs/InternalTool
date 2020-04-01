package com.tvj.internaltool.dto.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberSearchReqDto {

    @Size(min = 0, max = 40)
    private String name;

    @Size(min = 0, max = 20)
    private String username;

    @Size(min = 0, max = 40)
    private String titleId;

    private Boolean isActivated;

    @Min(0)
    private int offset;

    @Min(1)
    private int limit;

}
