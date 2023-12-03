package cn.jiabin.hotel.service.impl;

import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelMember;
import cn.jiabin.hotel.domain.HotelRechargeRecord;
import cn.jiabin.hotel.repository.HotelMemberRepository;
import cn.jiabin.hotel.repository.HotelRechargeRecordRepository;
import cn.jiabin.hotel.service.IHotelMemberService;
import cn.jiabin.hotel.service.dto.MemberQueryCriteria;
import cn.jiabin.utils.PageUtil;
import cn.jiabin.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HotelMemberServiceImpl implements IHotelMemberService {
    private final HotelMemberRepository hotelMemberRepository;
    private final HotelRechargeRecordRepository hotelRechargeRecordRepository;

    public HotelMemberServiceImpl(HotelMemberRepository hotelMemberRepository, HotelRechargeRecordRepository hotelRechargeRecordRepository) {
        this.hotelMemberRepository = hotelMemberRepository;
        this.hotelRechargeRecordRepository = hotelRechargeRecordRepository;
    }

    @Override
    public Object getList(MemberQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelMember> page = hotelMemberRepository.findAll((root, query, criteriaBuilder)-> QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    @Override
    public HotelMember getById(Long id) {
        return hotelMemberRepository.findById(id).orElseGet(HotelMember::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recharge(HotelMember member) {
        // 先获取会员信息
        HotelMember dbMember = hotelMemberRepository.getReferenceById(member.getId());
        if(null==dbMember){
            throw new BadRequestException("充值失败");
        }else {
            dbMember.setBalance(dbMember.getBalance()==null?member.getBalance():dbMember.getBalance()+member.getBalance());
            hotelMemberRepository.save(dbMember);
            // 保存到充值记录表
            HotelRechargeRecord temHotelRechargeRecord = new HotelRechargeRecord();
            temHotelRechargeRecord.setMoney(member.getBalance());
            temHotelRechargeRecord.setMemberId(member.getId());
            temHotelRechargeRecord.setRemarks(member.getRemarks());
            hotelRechargeRecordRepository.save(temHotelRechargeRecord);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        hotelMemberRepository.deleteById(id);
    }
}
