package cn.jiabin.hotel.controller;

import cn.jiabin.annotation.EnableSysLog;
import cn.jiabin.base.BaseResult;
import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelMember;
import cn.jiabin.hotel.service.IHotelMemberService;
import cn.jiabin.hotel.service.dto.MemberQueryCriteria;
import cn.jiabin.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("member")
public class HotelMemberController {

    private final IHotelMemberService hotelMemberService;

    public HotelMemberController(IHotelMemberService hotelMemberService) {
        this.hotelMemberService = hotelMemberService;
    }

    @EnableSysLog("【后台】获取会员列表数据")
    @GetMapping
    public ResponseEntity<Object> getList(MemberQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelMemberService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    @EnableSysLog("【后台】根据ID获取会员详情信息")
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if (null==id){
            throw new BadRequestException("获取信息失败！");
        }else {
            HotelMember dbHotelMember =  hotelMemberService.getById(id);
            return BaseResult.success(dbHotelMember);
        }
    }

    @EnableSysLog("会员充值")
    @PutMapping("recharge")
    public BaseResult recharge(@RequestBody HotelMember member){
        hotelMemberService.recharge(member);
        return BaseResult.success("充值成功");
    }

    @EnableSysLog("【后台】删除会员信息")
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null==id){
            return BaseResult.fail("删除信息失败");
        }else {
            hotelMemberService.deleteById(id);
            return BaseResult.success("删除成功");
        }

    }
}
