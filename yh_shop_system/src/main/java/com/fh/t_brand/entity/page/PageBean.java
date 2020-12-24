package com.fh.t_brand.entity.page;/*--------
|姓名:
-------------
|作用:        |
--------------*/

import lombok.Data;

@Data
public class PageBean {

    private Integer page;

    private Integer limit;

    private Long count;

    private Integer code;

    private Object data;

    private Integer startIndex;


}
