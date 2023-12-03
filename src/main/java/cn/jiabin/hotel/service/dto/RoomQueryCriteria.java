package cn.jiabin.hotel.service.dto;

import cn.jiabin.annotation.EnableXuedenQuery;
import lombok.Data;

@Data
public class RoomQueryCriteria {

    /**
     * 根据房间号、房间名称模糊查询
     */
    @EnableXuedenQuery(blurry = "roomNumber,roomName")
    private String searchValue;

    /**
     * 根据楼层ID查询
     */
    @EnableXuedenQuery
    private Long floorId;

    /**
     * 根据房间类型ID查询
     */
    @EnableXuedenQuery
    private Long roomTypeId;
}
