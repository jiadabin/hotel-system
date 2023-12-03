package cn.jiabin.hotel.controller;

import cn.jiabin.annotation.EnableSysLog;
import cn.jiabin.base.BaseResult;
import cn.jiabin.hotel.service.IHotelCheckinService;
import cn.jiabin.hotel.service.dto.CheckinQueryCriteria;
import cn.jiabin.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("checkin")
public class HotelCheckinController {

    private final IHotelCheckinService hotelCheckinService;

    public HotelCheckinController(IHotelCheckinService hotelCheckinService) {
        this.hotelCheckinService = hotelCheckinService;
    }

    @EnableSysLog("【后台】获取入住列表数据")
    @GetMapping
    public ResponseEntity<Object> getList(CheckinQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelCheckinService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    @EnableSysLog("【后台】退房")
    @PutMapping("/checkout/{id}")
    public BaseResult checkout(@PathVariable Long id){
        if(null==id){
            return BaseResult.fail("退房失败！");
        }else {
            hotelCheckinService.checkout(id);
            return BaseResult.success("退房成功");
        }

    }
}
