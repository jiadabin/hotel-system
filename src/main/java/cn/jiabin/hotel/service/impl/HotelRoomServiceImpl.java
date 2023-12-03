package cn.jiabin.hotel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelFloor;
import cn.jiabin.hotel.domain.HotelRoom;
import cn.jiabin.hotel.domain.HotelRoomType;
import cn.jiabin.hotel.repository.HotelFloorRepository;
import cn.jiabin.hotel.repository.HotelRoomRepository;
import cn.jiabin.hotel.repository.HotelRoomTypeRepository;
import cn.jiabin.hotel.service.IHotelRoomService;
import cn.jiabin.hotel.service.dto.RoomQueryCriteria;
import cn.jiabin.utils.PageUtil;
import cn.jiabin.utils.QueryHelp;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HotelRoomServiceImpl implements IHotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;
    private final HotelFloorRepository hotelFloorRepository;
    private final HotelRoomTypeRepository hotelRoomTypeRepository;

    public HotelRoomServiceImpl(HotelRoomRepository hotelRoomRepository, HotelFloorRepository hotelFloorRepository, HotelRoomTypeRepository hotelRoomTypeRepository) {
        this.hotelRoomRepository = hotelRoomRepository;
        this.hotelFloorRepository = hotelFloorRepository;
        this.hotelRoomTypeRepository = hotelRoomTypeRepository;
    }

    @Override
    public Object getList(RoomQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelRoom> page = hotelRoomRepository.findAll((root, query, criteriaBuilder)-> QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        setFloorAndTypeToRoom(page.stream().toList());
        return PageUtil.toPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoom(HotelRoom hotelRoom) {
        // 根据房间号查询记录
        HotelRoom dbHotelRoom = hotelRoomRepository.findByRoomNumber(hotelRoom.getRoomNumber());
        if(dbHotelRoom!=null){
            throw new BadRequestException("添加失败，房间号已经存在！");
        }else {
            hotelRoomRepository.save(hotelRoom);
        }
    }

    @Override
    public HotelRoom getById(Long id) {
        return hotelRoomRepository.findById(id).orElseGet(HotelRoom::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRoom(HotelRoom hotelRoom) {
        // 根据房间号获取信息
        HotelRoom dbHotelRoom = hotelRoomRepository.findByRoomNumber(hotelRoom.getRoomNumber());
        if(dbHotelRoom!=null&&!dbHotelRoom.getId().equals(hotelRoom.getId())){
            throw new BadRequestException("更新失败，房间号已经存在！");
        }else {
            dbHotelRoom = hotelRoomRepository.getReferenceById(hotelRoom.getId());
            BeanUtil.copyProperties(hotelRoom,dbHotelRoom, CopyOptions.create().setIgnoreError(true).setIgnoreNullValue(true));
            hotelRoomRepository.save(dbHotelRoom);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        hotelRoomRepository.deleteById(id);
    }

    private List<HotelRoom> setFloorAndTypeToRoom(List<HotelRoom> hotelRooms) {
        for (HotelRoom r: hotelRooms){

            // 处理楼层
            if(r.getFloorId()!=null&&r.getFloorId()!=0){
                HotelFloor hotelFloor = hotelFloorRepository.findById(r.getFloorId()).orElseGet(HotelFloor::new);
                if (StringUtils.isNotBlank(hotelFloor.getFloorName())){
                    r.setHotelFloor(hotelFloor);
                }
            }
            // 房间类型
            if(r.getRoomTypeId()!=null&&r.getRoomTypeId()!=0){
                HotelRoomType hotelRoomType = hotelRoomTypeRepository.findById(r.getRoomTypeId()).orElseGet(HotelRoomType::new);
                if(StringUtils.isNotBlank(hotelRoomType.getTypeName())){
                    r.setHotelRoomType(hotelRoomType);
                }
            }

        }
        return hotelRooms;
    }
}
