package com.tvj.internaltool.dto.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberSearchReqDto {

    @Size(max = 40, message = "maximum size of name is 40!!")
    private String name;

    @Size(max = 20, message = "maximum size of username is 20!!")
    private String username;

    @Size(max = 40, message = "maximum size of title id is 40!!")
    private String titleId;

    private Boolean isActivated;

    @Min(value = 0, message = "minimum value of offset is 0!!")
    private int offset;

    @Min(value = 1, message = "minimum value of limit is 1!!")
    private int limit;

}
