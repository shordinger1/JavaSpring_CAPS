package com.team4.caps.domain.param;

import lombok.Data;

@Data
public class PageParam {

    private Integer pageSize = 2;

    private Integer pageNumber = 1;

}
