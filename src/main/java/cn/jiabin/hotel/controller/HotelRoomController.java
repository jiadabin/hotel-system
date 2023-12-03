package cn.jiabin.hotel.controller;

import cn.jiabin.annotation.EnableSysLog;
import cn.jiabin.base.BaseResult;
import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelRoom;
import cn.jiabin.hotel.service.IHotelRoomService;
import cn.jiabin.hotel.service.dto.RoomQueryCriteria;
import cn.jiabin.utils.NativeFileUtil;
import cn.jiabin.utils.PageVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("room")
public class HotelRoomController {

    @Value("${room.picture}")
    private String roomPicture;


    private final IHotelRoomService hotelRoomService;

    public HotelRoomController(IHotelRoomService hotelRoomService) {
        this.hotelRoomService = hotelRoomService;
    }

    @EnableSysLog("【后台】获取房间列表数据")
    @GetMapping
    public ResponseEntity<Object> getList(RoomQueryCriteria queryCriteria, PageVo pageVo){

        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(hotelRoomService.getList(queryCriteria,pageable), HttpStatus.OK);

    }

    @EnableSysLog("【后台】添加房间信息")
    @PostMapping
    public BaseResult addRoom(@RequestBody HotelRoom hotelRoom){
        hotelRoomService.addRoom(hotelRoom);
        return BaseResult.success("添加成功");
    }

    @EnableSysLog("【后台】根据ID获取房间详情信息")
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if (null==id){
            throw new BadRequestException("获取信息失败！");
        }else {
            HotelRoom dbHotelRoom = hotelRoomService.getById(id);
            return BaseResult.success(dbHotelRoom);
        }

    }

    @EnableSysLog("【后台】更新房间信息")
    @PutMapping
    public BaseResult editRoom(@RequestBody HotelRoom hotelRoom){
        hotelRoomService.editRoom(hotelRoom);
        return BaseResult.success("更新成功");
    }

    /**
     * 上传封面
     * @param file
     * @return
     */
    @PostMapping("roomPicture")
    @EnableSysLog("上传头像")
    public BaseResult uploadFile(@RequestParam("fileResource") MultipartFile file){
        if(null==file){
            return BaseResult.fail("上传头像失败，文件不能为空");
        }
        try {
            String temFileResource = NativeFileUtil.uploadUserIcon(file,roomPicture);
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("roomPicture",temFileResource);
            return BaseResult.success(resultMap);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResult.fail(e.getMessage());
        }
    }

    @EnableSysLog("【后台】删除房间信息")
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if (null==id){
            throw new BadRequestException("删除信息失败");
        }else {
            hotelRoomService.deleteById(id);
            return BaseResult.success("删除成功");
        }
    }
}
