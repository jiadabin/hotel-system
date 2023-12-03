package cn.jiabin.hotel.service;

import cn.jiabin.hotel.domain.HotelRoom;
import cn.jiabin.hotel.service.dto.RoomQueryCriteria;
import org.springframework.data.domain.Pageable;

public interface IHotelRoomService {
    Object getList(RoomQueryCriteria queryCriteria, Pageable pageable);

    void addRoom(HotelRoom hotelRoom);

    HotelRoom getById(Long id);

    void editRoom(HotelRoom hotelRoom);

    void deleteById(Long id);
}
