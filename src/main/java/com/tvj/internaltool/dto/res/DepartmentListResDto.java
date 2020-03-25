package com.tvj.internaltool.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentListResDto {

    private List<DepartmentResDto> departmentResDtoList;

}
