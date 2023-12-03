package cn.jiabin.hotel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelFloor;
import cn.jiabin.hotel.repository.HotelFloorRepository;
import cn.jiabin.hotel.service.IHotelFloorService;
import cn.jiabin.hotel.service.dto.FloorQueryCriteria;
import cn.jiabin.utils.PageUtil;
import cn.jiabin.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HotelFloorServiceImpl implements IHotelFloorService {

    private final HotelFloorRepository hotelFloorRepository;

    public HotelFloorServiceImpl(HotelFloorRepository hotelFloorRepository) {
        this.hotelFloorRepository = hotelFloorRepository;
    }

    @Override
    public Object getList(FloorQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelFloor> page = hotelFloorRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFloor(HotelFloor hotelFloor) {
        // 根据楼层号查询记录
        HotelFloor dbHotelFloor = hotelFloorRepository.findByFloorNo(hotelFloor.getFloorNo());
        if(dbHotelFloor!=null){
            throw new BadRequestException("添加失败，楼层号已经存在！");
        }else {
            hotelFloorRepository.save(hotelFloor);
        }
    }

    @Override
    public HotelFloor getById(Long id) {
        return hotelFloorRepository.findById(id).orElseGet(HotelFloor::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editFloor(HotelFloor hotelFloor) {
        // 根据楼层ID获取楼层信息
        HotelFloor dbHotelFloor = hotelFloorRepository.getReferenceById(hotelFloor.getId());

        // 根据楼层号获取楼层信息
        HotelFloor tempHotelFloor = hotelFloorRepository.findByFloorNo(hotelFloor.getFloorNo());
        if(tempHotelFloor!=null&&!tempHotelFloor.getId().equals(hotelFloor.getId())){
            throw new BadRequestException("更新失败，楼层号已经存在！");
        }else {
            BeanUtil.copyProperties(hotelFloor,dbHotelFloor, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            hotelFloorRepository.save(dbHotelFloor);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        hotelFloorRepository.deleteById(id);
    }

    @Override
    public List<HotelFloor> getAll() {
        return hotelFloorRepository.findAll();
    }
}
