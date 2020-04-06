package com.tvj.internaltool.dto.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberSearchReqDto {

    @Size(min = 0, max = 40, message = "size of name must between 0 and 40!!")
    private String name;

    @Size(min = 0, max = 20, message = "size of username must between 0 and 20!!")
    private String username;

    @Size(min = 0, max = 40, message = "size of title id must between 0 and 40!!")
    private String titleId;

    private Boolean isActivated;

    @Min(value = 0, message = "minimum value of offset is 0!!")
    private int offset;

    @Min(value = 1, message = "minimum value of limit is 1!!")
    private int limit;

}
