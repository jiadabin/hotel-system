package cn.jiabin.hotel.service;

import cn.jiabin.hotel.service.dto.CheckinQueryCriteria;
import org.springframework.data.domain.Pageable;

public interface IHotelCheckinService {
    Object getList(CheckinQueryCriteria queryCriteria, Pageable pageable);

    void checkout(Long id);
}
