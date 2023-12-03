package cn.jiabin.hotel.service.dto;

import cn.jiabin.annotation.EnableXuedenQuery;
import lombok.Data;

@Data
public class MemberQueryCriteria {

    /**
     * 根据会员邮箱、手机号、真实姓名、昵称模糊查询
     */
    @EnableXuedenQuery(blurry = "email,phone,realName,nickname")
    private String searchValue;
}
