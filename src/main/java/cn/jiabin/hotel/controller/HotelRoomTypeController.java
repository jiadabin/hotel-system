package cn.jiabin.hotel.controller;

import cn.jiabin.annotation.EnableSysLog;
import cn.jiabin.base.BaseResult;
import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelRoomType;
import cn.jiabin.hotel.service.IHotelRoomTypeService;
import cn.jiabin.hotel.service.dto.RoomTypeQueryCriteria;
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
@RequestMapping("roomType")
public class HotelRoomTypeController {

    private final IHotelRoomTypeService hotelRoomTypeService;

    public HotelRoomTypeController(IHotelRoomTypeService hotelRoomTypeService) {
        this.hotelRoomTypeService = hotelRoomTypeService;
    }

    @EnableSysLog("获取房间类型列表数据")
    @GetMapping
    public ResponseEntity<Object> getList(RoomTypeQueryCriteria queryCriteria,
                                          PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelRoomTypeService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    @EnableSysLog("新增房间类型信息")
    @PostMapping
    public BaseResult addRoomType(@RequestBody HotelRoomType hotelRoomType){
        hotelRoomTypeService.addRoomType(hotelRoomType);
        return BaseResult.success("添加成功");
    }

    @EnableSysLog("根据ID获取房间类型的详情信息")
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("获取信息失败");
        }
        return BaseResult.success(hotelRoomTypeService.getById(id));
    }

    @EnableSysLog("更新房间类型信息")
    @PutMapping
    public BaseResult editRoomType(@RequestBody HotelRoomType hotelRoomType){
        hotelRoomTypeService.editRoomType(hotelRoomType);
        return BaseResult.success("更新成功");
    }

    @EnableSysLog("删除房间类型信息")
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("删除信息失败");
        }
        hotelRoomTypeService.deleteById(id);
        return BaseResult.success("删除成功");
    }

    @EnableSysLog("【后台】获取首页房间类型")
    @GetMapping("/all")
    public BaseResult all(){
        List<HotelRoomType> list = hotelRoomTypeService.getAll();
        List<ResultVo> resultVoList = list.stream().map(temp-> {
            ResultVo obj = new ResultVo();
            obj.setName(temp.getTypeName());
            obj.setId(temp.getId());
            return obj;
        }).collect(Collectors.toList());

        return BaseResult.success(resultVoList);
    }
}
