package cn.jiabin.hotel.repository;

import cn.jiabin.hotel.domain.HotelFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelFloorRepository extends JpaRepository<HotelFloor, Long>, JpaSpecificationExecutor<HotelFloor> {
    HotelFloor findByFloorNo(Integer floorNo);
}
