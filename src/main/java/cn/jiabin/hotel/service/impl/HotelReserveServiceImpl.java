package cn.jiabin.hotel.service.impl;

import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelCheckin;
import cn.jiabin.hotel.domain.HotelMember;
import cn.jiabin.hotel.domain.HotelReserve;
import cn.jiabin.hotel.domain.HotelRoom;
import cn.jiabin.hotel.repository.HotelCheckinRepository;
import cn.jiabin.hotel.repository.HotelMemberRepository;
import cn.jiabin.hotel.repository.HotelReserveRepository;
import cn.jiabin.hotel.repository.HotelRoomRepository;
import cn.jiabin.hotel.service.IHotelReserveService;
import cn.jiabin.hotel.service.dto.ReserveQueryCriteria;
import cn.jiabin.utils.PageUtil;
import cn.jiabin.utils.QueryHelp;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HotelReserveServiceImpl implements IHotelReserveService {

    private final HotelReserveRepository hotelReserveRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final HotelMemberRepository hotelMemberRepository;
    private final HotelCheckinRepository hotelCheckinRepository;

    public HotelReserveServiceImpl(HotelReserveRepository hotelReserveRepository, HotelRoomRepository hotelRoomRepository, HotelMemberRepository hotelMemberRepository, HotelCheckinRepository hotelCheckinRepository) {
        this.hotelReserveRepository = hotelReserveRepository;
        this.hotelRoomRepository = hotelRoomRepository;
        this.hotelMemberRepository = hotelMemberRepository;
        this.hotelCheckinRepository = hotelCheckinRepository;
    }

    @Override
    public Object getList(ReserveQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelReserve> page = hotelReserveRepository.findAll((root, query, criteriaBuilder)-> QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        setRoomToReserve(page.stream().toList());
        return PageUtil.toPage(page);
    }

    @Override
    public HotelReserve getById(Long id) {
        return hotelReserveRepository.findById(id).orElseGet(HotelReserve::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unsubscribe(Long id) {
        // 根据ID获取信息
        HotelReserve dbHotelReserve = hotelReserveRepository.findById(id).orElseGet(HotelReserve::new);
        if(dbHotelReserve.getStatus()!=0&&dbHotelReserve.getStatus()!=1){
            throw new BadRequestException("该订单已经完成，请勿重复提交");
        }else {
            // 订单状态0表示已预订待支付，1表示已付款待入住，2表示已入住带退房，3表示已退订4表示已完成
            dbHotelReserve.setStatus(3);
            hotelReserveRepository.save(dbHotelReserve);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payment(Long id) {
        // 根据id获取订单信息
        HotelReserve dbHotelReserve = hotelReserveRepository.getReferenceById(id);
        // 根据创建者ID获取会员信息
        HotelMember dbHotelMember = hotelMemberRepository.getReferenceById(dbHotelReserve.getCreateBy());
        // 判断会员余额是否足够付款
        if (dbHotelMember.getBalance()>=dbHotelReserve.getAmountMoney()){
            dbHotelMember.setBalance(dbHotelMember.getBalance()- dbHotelReserve.getAmountMoney());
            dbHotelReserve.setStatus(1);
            // 保存会员信息
            hotelMemberRepository.save(dbHotelMember);
            // 保存订单信息
            hotelReserveRepository.save(dbHotelReserve);
        }else {
            throw new BadRequestException("付款失败，余额不足，请先充值！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkin(HotelCheckin hotelCHeckin) {
        // 根据订单号查找订单信息
        HotelReserve dbHotelReserve = hotelReserveRepository.findByOrderNumber(hotelCHeckin.getOrderNumber());
        if(null==dbHotelReserve){
            throw new BadRequestException("订单不存在");
        }else {
            // 2表示已入住，待退房
            dbHotelReserve.setStatus(2);
            hotelReserveRepository.save(dbHotelReserve);
            // 保存到入住信息表
            hotelCHeckin.setRoomId(dbHotelReserve.getRoomId());
            hotelCHeckin.setStatus(0);
            hotelCHeckin.setAmountMoney(dbHotelReserve.getAmountMoney());
            hotelCHeckin.setChecinDate(LocalDate.now());
            hotelCheckinRepository.save(hotelCHeckin);
        }
    }

    private List<HotelReserve> setRoomToReserve(List<HotelReserve> hotelReserves) {
        for (HotelReserve r:hotelReserves){
            // 获取房间信息
            if(r.getRoomId()!=null&&r.getRoomId()!=0){
                HotelRoom hotelRoom = hotelRoomRepository.findById(r.getRoomId()).orElseGet(HotelRoom::new);
                if(StringUtils.isNotBlank(hotelRoom.getRoomName())){
                    r.setHotelRoom(hotelRoom);
                }
            }

            // 会员信息
            if(r.getCreateBy()!=null &&r.getCreateBy()!=0){
                HotelMember hotelMember = hotelMemberRepository.findById(r.getCreateBy()).orElseGet(HotelMember::new);
                if(StringUtils.isNotBlank(hotelMember.getNickname())){
                    r.setHotelMember(hotelMember);
                }
            }

        }
        return hotelReserves;
    }
}
