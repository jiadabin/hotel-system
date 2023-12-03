package cn.jiabin.hotel.service.dto;

import cn.jiabin.annotation.EnableXuedenQuery;
import lombok.Data;

@Data
public class CheckinQueryCriteria {

    /**
     * 根据姓名、订单号、手机号、身份证模糊查询
     */
    @EnableXuedenQuery(blurry = "orderNumber,memberName,phone,idCard")
    private String searchValue;

    /**
     * 根据订单状态
     */
    @EnableXuedenQuery
    private Integer status;
}
