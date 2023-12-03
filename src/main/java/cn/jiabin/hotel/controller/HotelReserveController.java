package cn.jiabin.hotel.controller;

import cn.jiabin.annotation.EnableSysLog;
import cn.jiabin.base.BaseResult;
import cn.jiabin.hotel.domain.HotelCheckin;
import cn.jiabin.hotel.domain.HotelReserve;
import cn.jiabin.hotel.service.IHotelReserveService;
import cn.jiabin.hotel.service.dto.ReserveQueryCriteria;
import cn.jiabin.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reserve")
public class HotelReserveController {

    private final IHotelReserveService hotelReserveService;

    public HotelReserveController(IHotelReserveService hotelReserveService) {
        this.hotelReserveService = hotelReserveService;
    }

    @EnableSysLog("【后台】获取预订列表数据")
    @GetMapping
    public ResponseEntity<Object> getList(ReserveQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelReserveService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    @EnableSysLog("【后台】根据ID获取详情信息")
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null==id){
            return BaseResult.fail("获取信息失败！");
        }else {
            HotelReserve dbHotelReserve = hotelReserveService.getById(id);
            return BaseResult.success(dbHotelReserve);
        }
    }

    @EnableSysLog("【后台】退订房间")
    @PutMapping("unsubscribe/{id}")
    public BaseResult unsubscribe(@PathVariable Long id){
        if(null==id){
            return BaseResult.fail("退订失败！");
        }else {
            hotelReserveService.unsubscribe(id);
            return BaseResult.success("退订成功");
        }
    }

    @EnableSysLog("【后台】确认收款")
    @PutMapping("payment/{id}")
    public BaseResult payment(@PathVariable Long id){
        if (null==id){
            return BaseResult.fail("收款失败！");
        }else {
            hotelReserveService.payment(id);
            return BaseResult.success("收款成功!");
        }
    }

    @EnableSysLog("【后台】登记入住酒店")
    @PostMapping("checkin")
    public BaseResult checkin(@RequestBody HotelCheckin hotelCHeckin){
        hotelReserveService.checkin(hotelCHeckin);
        return BaseResult.success("登记成功");
    }
}
