package cn.jiabin.hotel.domain;

import cn.jiabin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "hotel_room_type")
@org.hibernate.annotations.Table(appliesTo = "hotel_room_type",comment = "房间类型信息表")
public class HotelRoomType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 房间类型名称
     */
    @Column(name = "type_name",nullable = false)
    private String typeName;

    /**
     * 类型排序
     */
    @Column(name = "type_sort",nullable = false)
    private Integer typeSort;
}
