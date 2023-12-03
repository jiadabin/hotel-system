package cn.jiabin.hotel.controller;

import cn.jiabin.annotation.EnableSysLog;
import cn.jiabin.base.BaseResult;
import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelFloor;
import cn.jiabin.hotel.service.IHotelFloorService;
import cn.jiabin.hotel.service.dto.FloorQueryCriteria;
import cn.jiabin.utils.PageVo;
import cn.jiabin.utils.ResultVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("floor")
public class HotelFloorController {

    private final IHotelFloorService hotelFloorService;

    public HotelFloorController(IHotelFloorService hotelFloorService) {
        this.hotelFloorService = hotelFloorService;
    }

    @EnableSysLog("【后台】获取楼层列表数据")
    @GetMapping
    public ResponseEntity<Object> getList(FloorQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id" );
        return new ResponseEntity<>(hotelFloorService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    @EnableSysLog("【后台】添加楼层信息")
    @PostMapping
    public BaseResult addFloor(@RequestBody HotelFloor hotelFloor){
        hotelFloorService.addFloor(hotelFloor);
        return BaseResult.success("添加成功");
    }

    @EnableSysLog("【后台】根据ID获取详情信息")
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("获取信息失败");
        }
        HotelFloor dbHotelFloor = hotelFloorService.getById(id);
        return BaseResult.success(dbHotelFloor);
    }

    @EnableSysLog("【后台】更新楼层信息")
    @PutMapping
    public BaseResult editFloor(@RequestBody HotelFloor hotelFloor){
        hotelFloorService.editFloor(hotelFloor);
        return BaseResult.success("更新成功");
    }

    @EnableSysLog("【后台】删除楼层信息")
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("删除信息失败");
        }else {
            hotelFloorService.deleteById(id);
            return BaseResult.success("删除成功");
        }
    }

    @EnableSysLog("【后台】获取首页楼层")
    @GetMapping("/all")
    public BaseResult all(){
        List<HotelFloor> list = hotelFloorService.getAll();
        List<ResultVo> resultVoList = list.stream().map(temp-> {
            ResultVo obj = new ResultVo();
            obj.setName(temp.getFloorName());
            obj.setId(temp.getId());
            return obj;
        }).collect(Collectors.toList());
        return BaseResult.success(resultVoList);
    }
}
