package cn.jiabin.hotel.domain;

import cn.jiabin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "hotel_floor")
@org.hibernate.annotations.Table(appliesTo = "hotel_floor",comment = "酒店楼层信息表")
public class HotelFloor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 楼层
     */
    @Column(name = "floor_no",nullable = false)
    private Integer floorNo;

    /**
     * 楼层名称
     */
    @Column(name = "floor_name")
    private String floorName;
}
