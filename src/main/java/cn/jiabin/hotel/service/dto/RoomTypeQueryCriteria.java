package cn.jiabin.hotel.service.dto;

import cn.jiabin.annotation.EnableXuedenQuery;
import lombok.Data;

@Data
public class RoomTypeQueryCriteria {

    /**
     * 根据房间类型名称模糊查询
     */
    @EnableXuedenQuery(blurry = "typeName")
    private String searchValue;
}
