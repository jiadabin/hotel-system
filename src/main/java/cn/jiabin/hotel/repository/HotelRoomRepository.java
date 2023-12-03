package cn.jiabin.hotel.repository;

import cn.jiabin.hotel.domain.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long>, JpaSpecificationExecutor<HotelRoom> {
    HotelRoom findByRoomNumber(String roomNumber);
}
