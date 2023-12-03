package cn.jiabin.hotel.service;

import cn.jiabin.hotel.domain.HotelRoomType;
import cn.jiabin.hotel.service.dto.RoomTypeQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHotelRoomTypeService {
    Object getList(RoomTypeQueryCriteria queryCriteria, Pageable pageable);

    void addRoomType(HotelRoomType hotelRoomType);


    HotelRoomType getById(Long id);

    void editRoomType(HotelRoomType hotelRoomType);

    void deleteById(Long id);

    List<HotelRoomType> getAll();
}
