package cn.jiabin.hotel.repository;

import cn.jiabin.hotel.domain.HotelReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelReserveRepository extends JpaRepository<HotelReserve, Long>, JpaSpecificationExecutor<HotelReserve> {

    /**
     * 根据订单号查询信息
     * @param orderNumber
     * @return
     */
    HotelReserve findByOrderNumber(String orderNumber);
}
