package com.atguigu.pojo.dto;

import lombok.Data;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 9/21/24 12:56
 */
@Data
public class PortalInput {
    private String keyWords;
    private int type = 0;
    private int pageNum = 1;
    private int pageSize = 10;
}
