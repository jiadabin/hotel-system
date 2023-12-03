package cn.jiabin.hotel.service.impl;

import cn.jiabin.hotel.domain.HotelCheckin;
import cn.jiabin.hotel.domain.HotelRoom;
import cn.jiabin.hotel.repository.HotelCheckinRepository;
import cn.jiabin.hotel.repository.HotelRoomRepository;
import cn.jiabin.hotel.service.IHotelCheckinService;
import cn.jiabin.hotel.service.dto.CheckinQueryCriteria;
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
public class HotelCheckinServiceImpl implements IHotelCheckinService {

    private final HotelCheckinRepository hotelCheckinRepository;
    private final HotelRoomRepository hotelRoomRepository;

    public HotelCheckinServiceImpl(HotelCheckinRepository hotelCheckinRepository, HotelRoomRepository hotelRoomRepository) {
        this.hotelCheckinRepository = hotelCheckinRepository;
        this.hotelRoomRepository = hotelRoomRepository;
    }

    @Override
    public Object getList(CheckinQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelCheckin> page = hotelCheckinRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        setRoomToCHeckin(page.stream().toList());
        return PageUtil.toPage(page);
    }

    /**
     * 添加房间信息到入住列表
     * @param hotelCheckins
     * @return
     */
    private List<HotelCheckin> setRoomToCHeckin(List<HotelCheckin> hotelCheckins){
        for(HotelCheckin r:hotelCheckins){
            // 房间信息
            if(r.getRoomId()!=null&&r.getRoomId()!=0){
                HotelRoom dbHotelRoom = hotelRoomRepository.findById(r.getRoomId()).orElseGet(HotelRoom::new);
                if(StringUtils.isNotBlank(dbHotelRoom.getRoomName())){
                    r.setHotelRoom(dbHotelRoom);
                }
            }
        }
        return hotelCheckins;
    }
}
