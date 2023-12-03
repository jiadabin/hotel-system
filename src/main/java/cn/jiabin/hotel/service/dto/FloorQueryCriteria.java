package cn.jiabin.hotel.service.dto;

import cn.jiabin.annotation.EnableXuedenQuery;
import lombok.Data;

@Data
public class FloorQueryCriteria {

    /**
     * 根据楼层名称、楼层模糊查询
     */
    @EnableXuedenQuery(blurry = "floorNo,floorName")
    private String searchValue;
}
