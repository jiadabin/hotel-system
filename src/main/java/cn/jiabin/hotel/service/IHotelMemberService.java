package cn.jiabin.hotel.service;

import cn.jiabin.hotel.domain.HotelMember;
import cn.jiabin.hotel.service.dto.MemberQueryCriteria;
import org.springframework.data.domain.Pageable;

public interface IHotelMemberService {
    Object getList(MemberQueryCriteria queryCriteria, Pageable pageable);

    HotelMember getById(Long id);

    void recharge(HotelMember member);

    void deleteById(Long id);
}
