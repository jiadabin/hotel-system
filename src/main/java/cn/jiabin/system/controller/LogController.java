package cn.jiabin.system.controller;

import cn.jiabin.system.service.ILogService;
import cn.jiabin.system.service.dto.SysLogQueryCriteria;
import cn.jiabin.utils.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**功能描述：日志管理前端控制器
 * @author:梁志杰
 * @date:2023/3/21
 * @description:cn.xueden.system.controller
 * @version:1.0
 */
@RestController
@RequestMapping("log")
public class LogController {

    private final ILogService logService;

    public LogController(ILogService logService) {
        this.logService = logService;
    }

    /**
     * 获取日志列表数据
     * @param queryCriteria
     * @param pageVo
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> getList(SysLogQueryCriteria queryCriteria, PageVo pageVo){
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1, pageVo.getPageSize(),
                Sort.Direction.DESC,"id");
        return new ResponseEntity<>(logService.getList(queryCriteria,pageable), HttpStatus.OK);
    }
}
