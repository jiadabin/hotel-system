package cn.jiabin.hotel.repository;

import cn.jiabin.hotel.domain.HotelCheckin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HotelCheckinRepository  extends JpaRepository<HotelCheckin, Long>, JpaSpecificationExecutor<HotelCheckin> {

    /**
     * 退房
     * @param id
     */
    @Query(value = "update HotelCheckin set status =1 where id=?1")
    @Modifying
    void updateStatusById(Long id);
}
