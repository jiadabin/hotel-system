package cn.jiabin.hotel.service;

import cn.jiabin.hotel.domain.HotelFloor;
import cn.jiabin.hotel.service.dto.FloorQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHotelFloorService {
    Object getList(FloorQueryCriteria queryCriteria, Pageable pageable);

    void addFloor(HotelFloor hotelFloor);

    HotelFloor getById(Long id);

    void editFloor(HotelFloor hotelFloor);

    void deleteById(Long id);

    List<HotelFloor> getAll();
}
