package com.haoqi.magic.business.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsTransferRecordAuditDTO;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.entity.CsTransferRecord;
import com.haoqi.magic.business.model.vo.CsTransferRecordPageVO;
import com.haoqi.magic.business.model.vo.CsTransferRecordVO;
import com.haoqi.magic.business.service.ICsCarDealerService;
import com.haoqi.magic.business.service.ICsTransferRecordService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 调拨记录表 前端控制器
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/car/home/csTransferRecord")
@Api(tags = "前端调拨Controller")
public class CsTransferRecordController extends BaseController {

    @Autowired
    private ICsTransferRecordService csTransferRecordService;

    @Autowired
    private ICsCarDealerService csCarDealerService;


    /**
     * 分页获取调拨列表
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/5/7 11:38
     */
    @PostMapping("/csTransferRecordPage")
    @ApiOperation(value = "分页获取调拨列表")
    public Result<Page> csTransferRecordPage(@RequestBody CsTransferRecordPageVO param) {
        Map<String, Object> params = new HashMap<>();
        params.put("tab", Objects.isNull(param.getTab()) ? 1 : param.getTab());
        params.put("transferAuditTimeStart", StrUtil.isBlank(param.getTransferAuditTimeStart()) ? StrUtil.emptyToNull("") :
                DateUtil.beginOfDay(DateUtil.parseDate(param.getTransferAuditTimeStart())));
        params.put("transferAuditTimeEnd", StrUtil.isBlank(param.getTransferAuditTimeEnd()) ? StrUtil.emptyToNull("") :
                DateUtil.endOfDay(DateUtil.parseDate(param.getTransferAuditTimeEnd())));
        params.put("transferTimeStart", StrUtil.isBlank(param.getTransferTimeStart()) ? StrUtil.emptyToNull("") :
                DateUtil.beginOfDay(DateUtil.parseDate(param.getTransferTimeStart())));
        params.put("transferTimeEnd", StrUtil.isBlank(param.getTransferTimeEnd()) ? StrUtil.emptyToNull("") :
                DateUtil.endOfDay(DateUtil.parseDate(param.getTransferTimeEnd())));
        params.put("transferStatus", param.getTransferStatus());
        params.put("sysCarModelName", StrUtil.emptyToNull(param.getSysCarModelName()));
        params.put("csCarDealerId", csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        Page page = csTransferRecordService.findPage(query);
        return Result.buildSuccessResult(page);

    }

    /**
     * 新增调拨
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/7 11:40
     */
    @PostMapping("/addCsTransferRecord")
    @ApiOperation(value = "新增调拨")
    public Result<String> addCsTransferRecord(@RequestBody CsTransferRecordVO vo) {
        UserInfo userInfo = currentUser();
        CsCarDealer carDealer = csCarDealerService.getCurrentCarDealerInfo(userInfo.getUserName());
        vo.setCsCarDearlerIdTo(carDealer.getId());
        vo.setCsCarDearlerNameTo(carDealer.getDealerName());
        csTransferRecordService.insert(vo);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 取消调拨
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/7 14:34
     */
    @PostMapping("/cancelTransfer")
    @ApiOperation(value = "取消调拨")
    public Result<String> cancelTransfer(@RequestBody CsTransferRecordVO vo) {
        UserInfo userInfo = currentUser();
        CsCarDealer carDealer = csCarDealerService.getCurrentCarDealerInfo(userInfo.getUserName());
        vo.setCsCarDearlerIdTo(carDealer.getId());
        vo.setCsCarDearlerNameTo(carDealer.getDealerName());
        csTransferRecordService.cancelTransfer(vo);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 审核调拨
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/7 14:36
     */
    @PostMapping("/auditTransfer")
    @ApiOperation(value = "审核调拨")
    public Result<String> auditTransfer(@RequestBody CsTransferRecordVO vo) {
        UserInfo userInfo = currentUser();
        CsCarDealer carDealer = csCarDealerService.getCurrentCarDealerInfo(userInfo.getUserName());
        vo.setCsCarDearlerIdFrom(carDealer.getId());
        vo.setCsCarDearlerNameFrom(carDealer.getDealerName());
        csTransferRecordService.auditTransfer(vo);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 通过ID获取调拨信息
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/5/7 14:40
     */
    @GetMapping("/getOne/{id}")
    @ApiOperation(value = "通过ID获取调拨信息")
    public Result<CsTransferRecordAuditDTO> getOneById(@PathVariable Long id) {
        UserInfo userInfo = currentUser();
        CsTransferRecord record = csTransferRecordService.getOneById(id,
                csCarDealerService.getCurrentCarDealerInfo(userInfo.getUserName()).getId());
        return Result.buildSuccessResult(BeanUtils.beanCopy(record, CsTransferRecordAuditDTO.class));

    }

}

