package cn.jiabin.hotel.repository;

import cn.jiabin.hotel.domain.HotelMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HotelMemberRepository extends JpaRepository<HotelMember, Long>, JpaSpecificationExecutor<HotelMember> {
}
