package cn.jiabin.hotel.service;

import cn.jiabin.hotel.domain.HotelCheckin;
import cn.jiabin.hotel.domain.HotelReserve;
import cn.jiabin.hotel.service.dto.ReserveQueryCriteria;
import org.springframework.data.domain.Pageable;

public interface IHotelReserveService {
    Object getList(ReserveQueryCriteria queryCriteria, Pageable pageable);

    HotelReserve getById(Long id);

    void unsubscribe(Long id);

    void payment(Long id);

    void checkin(HotelCheckin hotelCHeckin);
}
