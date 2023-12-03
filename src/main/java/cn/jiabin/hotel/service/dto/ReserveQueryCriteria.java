package cn.jiabin.hotel.service.dto;

import cn.jiabin.annotation.EnableXuedenQuery;
import lombok.Data;

@Data
public class ReserveQueryCriteria {

    /**
     * 根据订单号模糊查询
     */
    @EnableXuedenQuery(blurry = "orderNumber")
    private String searchValue;

    /**
     * 根据订单状态查询
     */
    @EnableXuedenQuery
    private Integer status;
}
