package cn.jiabin.hotel.domain;

import cn.jiabin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "hotel_checkin")
@org.hibernate.annotations.Table(appliesTo = "hotel_checkin",comment = "入住登记信息表")
public class HotelCheckin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_number",nullable = false)
    private String orderNumber;

    /**
     * 房间ID
     */
    @Column(name = "room_id",nullable = false)
    private Long roomId;

    /**
     * 会员姓名
     */
    @Column(name = "member_name",nullable = false)
    private String memberName;

    /**
     * 身份证
     */
    @Column(name = "id_card",nullable = false)
    private String idCard;

    /**
     * 入住人数
     */
    @Column(name = "member_number",nullable = false)
    private Integer memberNumber;

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    private String birthday;

    /**
     * 性别
     */
    @Column(name = "gender")
    private String gender;

    /**
     * 省份
     */
    @Column(name = "province")
    private String province;

    /**
     * 城市
     */
    @Column(name = "city")
    private String city;

    /**
     * 家庭住址
     */
    @Column(name = "address")
    private String address;

    /**
     * 手机号
     */
    @Column(name = "phone",nullable = false)
    private String phone;

    /**
     * 入住状态 0表示已入住未退房，1表示已退房
     */
    @Column(name = "status",nullable = false)
    private Integer status;

    /**
     * 入住时间
     */
    @Column(name = "checkin_date",nullable = false)
    private LocalDate checinDate;

    /**
     * 退房时间
     */
    @Column(name = "checkout_date")
    private LocalDate checkoutDate;

    /**
     * 金额
     */
    @Column(name = "amount_money",nullable = false)
    private Integer amountMoney;

    /**
     * 酒店房间
     */
    @Transient
    private HotelRoom hotelRoom;
}
