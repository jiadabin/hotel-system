package cn.jiabin.hotel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.jiabin.exception.BadRequestException;
import cn.jiabin.hotel.domain.HotelRoomType;
import cn.jiabin.hotel.repository.HotelRoomTypeRepository;
import cn.jiabin.hotel.service.IHotelRoomTypeService;
import cn.jiabin.hotel.service.dto.RoomTypeQueryCriteria;
import cn.jiabin.utils.PageUtil;
import cn.jiabin.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HotelRoomTypeServiceImpl implements IHotelRoomTypeService {

    private final HotelRoomTypeRepository hotelRoomTypeRepository;

    public HotelRoomTypeServiceImpl(HotelRoomTypeRepository hotelRoomTypeRepository) {
        this.hotelRoomTypeRepository = hotelRoomTypeRepository;
    }

    @Override
    public Object getList(RoomTypeQueryCriteria queryCriteria, Pageable pageable) {
        Page<HotelRoomType> page= hotelRoomTypeRepository.findAll((root, query, criteriaBuilder)->
                QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoomType(HotelRoomType hotelRoomType) {
        HotelRoomType dbHotelRoomType =  hotelRoomTypeRepository.save(hotelRoomType);
        if(dbHotelRoomType.getId()==null||dbHotelRoomType.getId()==0){
            throw new BadRequestException("新增房间类型失败");
        }
    }

    @Override
    public HotelRoomType getById(Long id) {
        return hotelRoomTypeRepository.findById(id).orElseGet(HotelRoomType::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRoomType(HotelRoomType hotelRoomType) {
        HotelRoomType dbHotelRoomType =  hotelRoomTypeRepository.getReferenceById(hotelRoomType.getId());
        BeanUtil.copyProperties(hotelRoomType,dbHotelRoomType, CopyOptions.create().setIgnoreError(true).setIgnoreNullValue(true));
        hotelRoomTypeRepository.save(dbHotelRoomType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        hotelRoomTypeRepository.deleteById(id);
    }

    @Override
    public List<HotelRoomType> getAll() {
        return hotelRoomTypeRepository.findAll();
    }
}
