package cn.jiabin.hotel.repository;

import cn.jiabin.hotel.domain.HotelCheckin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelCheckinRepository  extends JpaRepository<HotelCheckin, Long>, JpaSpecificationExecutor<HotelCheckin> {
}
