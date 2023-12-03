package cn.jiabin.hotel.domain;

import cn.jiabin.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "hotel_member")
@org.hibernate.annotations.Table(appliesTo = "hotel_member",comment = "酒店会员信息表")
public class HotelMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * 会员昵称
     */
    @Column(name = "m_nickname")
    private String nickname;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 会员手机号
     */
    @Column(name = "m_phone",nullable = false)
    private String phone;

    /**
     * 会员名称
     */
    @Column(name = "email")
    private String email;

    /**
     * 会员登录密码
     */
    @Column(name = "m_password",nullable = false)
    private String password;

    /**
     * 会员登录次数
     */
    @Column(name = "login_times")
    private Integer loginTimes;

    /**
     * 会员状态
     */
    @Column(name = "status",nullable = false)
    private Integer status;

    /**
     * 会员头像
     */
    @Column(name = "member_icon")
    private String memberIcon;

    /**
     * 余额
     */
    @Column(name = "balance")
    private Integer balance;
}
