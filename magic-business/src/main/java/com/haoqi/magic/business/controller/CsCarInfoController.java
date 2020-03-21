package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.enums.CarPublishStatusEnum;
import com.haoqi.magic.business.enums.CarPullOffTypeEnum;
import com.haoqi.magic.business.model.dto.CarAuditDTO;
import com.haoqi.magic.business.model.dto.CarDTO;
import com.haoqi.magic.business.model.dto.CsCarHomeDTO;
import com.haoqi.magic.business.model.vo.CarPageVO;
import com.haoqi.magic.business.model.vo.PullCarVO;
import com.haoqi.magic.business.model.vo.TransferPageVO;
import com.haoqi.magic.business.service.ICsCarAuditService;
import com.haoqi.magic.business.service.ICsCarInfoService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/carInfo")
@Api(tags = "车辆信息Controller")
@Slf4j
public class CsCarInfoController extends BaseController {
    @Autowired
    private ICsCarInfoService carInfoService;

    @Autowired
    private BeanValidatorHandler validatorHandler;

    @Autowired
    private ICsCarAuditService csCarAuditService;

    /**
     * 分页获取车辆信息
     *
     * @param carPage
     * @return
     */
    @PostMapping("carPage")
    @ApiOperation(value = "车辆信息分页查询")
    public Result<Page> carPage(@RequestBody CarPageVO carPage) {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = Maps.newHashMap();
        BeanUtil.beanToMap(carPage, params, false, true);
        params.put("isDeleted", CommonConstant.STATUS_NORMAL);
        params.put("userId", userInfo.getId());
        params.put("userType", userInfo.getUserType());
        params.put("publishStatus", Objects.isNull(carPage.getPublishStatus()) ? CarPublishStatusEnum.PUBLISH.getKey() : carPage.getPublishStatus());
        handlerOrderByField(carPage.getPublishStatus(), carPage.getNewAudit(), params);
        return Result.buildSuccessResult(carInfoService.findByPage(new Query(params)));
    }

    /**
     * 通过车辆ID，获取车辆基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("getCarById/{id}")
    @ApiOperation(value = "通过车辆id，获取车辆基本信息")
    public Result<CarDTO> getCarById(@PathVariable("id") Long id) {
        currentUser();
        Optional<CarDTO> optional = carInfoService.getCarById(id);
        return Result.buildSuccessResult(optional.get());
    }

    /**
     * 车辆保存
     *
     * @param carDTO
     * @return
     */
    @PostMapping("save")
    @ApiOperation(value = "车辆保存")
    public Result<CarDTO> save(@RequestBody CarDTO carDTO) {
        UserInfo userInfo = currentUser();
        carDTO.setCheckTime(DateUtil.date());
        carDTO.setCheckUserId(userInfo.getId());
        carDTO.setCheckLoginName(userInfo.getUserName());
        //carDTO.setCheckLoginName(userInfo.getRealName());
        carDTO.setPublishStatus(CarPublishStatusEnum.SAVE.getKey());
        return saveOrPublishCarInfo(carDTO, "保存成功");
    }


    /**
     * 车辆发布
     *
     * @param carDTO
     * @return
     */
    @PostMapping("publish")
    @ApiOperation(value = "车辆发布")
    public Result<CarDTO> publish(@RequestBody CarDTO carDTO) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(carDTO);
        carDTO.setCheckTime(DateUtil.date());
        carDTO.setPublishTime(DateUtil.date());
        carDTO.setCheckUserId(userInfo.getId());
        carDTO.setCheckLoginName(userInfo.getUserName());
        carDTO.setPublishStatus(CarPublishStatusEnum.PUBLISH.getKey());
        return saveOrPublishCarInfo(carDTO, "发布成功");
    }


    /**
     * 下架车辆
     *
     * @param id 车辆ID
     * @return
     * @author huming
     * @date 2019/5/29 9:53
     */
    @PutMapping("/pullOffCar/{id}")
    @ApiOperation(value = "下架车辆")
    public Result<String> pullOffCar(@PathVariable("id") Long id) {
        UserInfo userInfo = currentUser();
        CsCarHomeDTO carDTO = new CsCarHomeDTO();
        carDTO.setId(id);
        carDTO.setCheckId(userInfo.getId());
        carDTO.setUserType(userInfo.getUserType());
        carDTO.setCheckLoginName(userInfo.getUserName());
        carDTO.setPullOffType(CarPullOffTypeEnum.PULLOFF_CHECK_TYPE.getKey());
        carInfoService.pullOffCar(carDTO);
        return Result.buildSuccessResult("操作成功");
    }


    /***
     * 审核下架车辆
     * @param vo
     * @return
     */
    @PostMapping("/pullOffCar")
    @ApiOperation(value = "后台下架车辆")
    public Result<String> pullOffCar(@RequestBody PullCarVO vo) {
        validatorHandler.validator(vo);
        UserInfo userInfo = currentUser();
        CsCarHomeDTO carDTO = new CsCarHomeDTO();
        carDTO.setId(vo.getId());
        carDTO.setCheckId(userInfo.getId());
        carDTO.setUserType(userInfo.getUserType());
        carDTO.setCheckLoginName(userInfo.getUserName());
        carDTO.setPullOffType(CarPullOffTypeEnum.PULLOFF_CHECK_TYPE.getKey());
        carInfoService.pullOffCar(carDTO);
        try {
            addAuditPullCar(vo.getId(), 3, userInfo.getId(), userInfo.getRealName(), userInfo.getUserName(), vo.getRemark());
        } catch (Exception e) {
            log.error("下架记录添加失败:");
        }
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 删除车辆
     *
     * @param id 车辆ID
     * @return
     * @author huming
     * @date 2019/5/29 10:45
     */
    @PutMapping("/deleteCar/{id}")
    @ApiOperation(value = "删除车辆")
    public Result<String> deleteCar(@PathVariable("id") Long id) {
        UserInfo userInfo = currentUser();
        CsCarHomeDTO carDTO = new CsCarHomeDTO();
        carDTO.setId(id);
        carDTO.setCheckId(userInfo.getId());
        carDTO.setUserType(userInfo.getUserType());
        carDTO.setCheckLoginName(userInfo.getUserName());
        carInfoService.deleteCar(carDTO);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 上架车辆
     *
     * @param id 车辆ID
     * @return
     * @author huming
     * @date 2019/5/30 15:23
     */
    @PutMapping("/pullUpCar/{id}")
    @ApiOperation(value = "上架车辆")
    public Result<String> pullUpCar(@PathVariable("id") Long id) {
        UserInfo userInfo = currentUser();
        CsCarHomeDTO carDTO = new CsCarHomeDTO();
        carDTO.setId(id);
        carDTO.setCheckId(userInfo.getId());
        carDTO.setUserType(userInfo.getUserType());
        carDTO.setCheckLoginName(userInfo.getUserName());
        carDTO.setCheckUserName(userInfo.getRealName());
	    try {
		    carInfoService.pullUpCar(carDTO);
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
        /*try {
            addAuditPullCar(id, 2, userInfo.getId(), userInfo.getRealName(), userInfo.getUserName(), "上架");
        } catch (Exception e) {
            log.error("上架记录添加失败:");
        }*/
        return Result.buildSuccessResult("操作成功");
    }

    private void addAuditPullCar(Long id, int auditStatus, Long id2, String realName, String userName, String remark) {
        CarAuditDTO carAuditDTO = new CarAuditDTO();
        carAuditDTO.setCsCarInfoId(id);
        carAuditDTO.setAuditStatus(auditStatus);
        carAuditDTO.setAuditUserId(id2);
        carAuditDTO.setAuditUserName(realName);
        carAuditDTO.setAuditLoginName(userName);
        carAuditDTO.setRemark(remark);
        csCarAuditService.addAuditPullUp(carAuditDTO);
    }


    /**
     * 处理排序
     *
     * @param publishStatus
     * @param params
     */
    private void handlerOrderByField(Integer publishStatus, Integer type, Map<String, Object> params) {
        if (Objects.isNull(type)) {
            if (Objects.isNull(publishStatus)) {
                return;
            }
            if (CarPublishStatusEnum.AUDIT_BACK.getKey().equals(publishStatus)) {
                params.put("orderByField", "gmt_modified");
            } else if (CarPublishStatusEnum.PUBLISH.getKey().equals(publishStatus)) {
                params.put("orderByField", "publish_time");
                params.put("isAsc", false);
            } else if (CarPublishStatusEnum.PURT_AWAY.getKey().equals(publishStatus)) {
                params.put("orderByField", "audit_time");
                params.put("isAsc", false);
            } else if (CarPublishStatusEnum.SOLD_OUT.getKey().equals(publishStatus)) {
                params.put("orderByField", "pull_off_time");
                params.put("isAsc", false);
            } else if (CarPublishStatusEnum.SAVE.getKey().equals(publishStatus) ||
                    CarPublishStatusEnum.ALLOT.getKey().equals(publishStatus)) {
                params.put("orderByField", "gmt_modified");
                params.put("isAsc", false);
            }
        } else {
            //web排序
            //1
            if (CarPublishStatusEnum.PUBLISH.getKey().equals(publishStatus)) {
                params.put("orderByField", "publish_time");
                params.put("isAsc", true);
                //2
            } else if (CarPublishStatusEnum.PURT_AWAY.getKey().equals(publishStatus)) {
                params.put("orderByField", "audit_time");
                params.put("isAsc", false);
                //-2
            } else if (CarPublishStatusEnum.SOLD_OUT.getKey().equals(publishStatus)) {
                params.put("orderByField", "audit_time");
                params.put("isAsc", false);
                //-1
            } else if (CarPublishStatusEnum.AUDIT_BACK.getKey().equals(publishStatus)) {
                params.put("orderByField", "audit_time");
                params.put("isAsc", false);
            }
        }
    }

    /**
     * 添加、保存车辆信息
     *
     * @param carDTO
     * @param msg
     * @return
     */
    private Result<CarDTO> saveOrPublishCarInfo(CarDTO carDTO, String msg) {
        carInfoService.insertOrUpdateCarInfo(carDTO);
        return Result.buildSuccessResult(carDTO, msg);
    }


    /**
     * 车辆ID判断车辆是否命中标签
     *
     * @param carId  车辆ID
     * @param sqlStr 自定义参数
     * @return
     * @author huming
     * @date 2019/5/14 14:58
     */
    @GetMapping("/checkTagWithSqlStr")
    @ApiOperation(value = "通过自定义参数、车辆ID判断车辆是否命中标签")
    public Result<String> checkTagWithSqlStr(@RequestParam("carId") Long carId, @RequestParam("sqlStr") String sqlStr) {
        carInfoService.checkTagWithSqlStr(carId, sqlStr);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 功能描述:
     * PC后台车源调拨列表
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/8/15 18:16
     * @Description:
     */
    @PostMapping("/transferRecordPage")
    @ApiOperation(value = "分页获取调拨列表")
    public Result<Page> csTransferRecordPage(@RequestBody TransferPageVO param) {
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        params.put("orderByField", "transfer_handle_time");
        params.put("isAsc", false);
        Page page = carInfoService.transferRecordPage(new Query(params));
        return Result.buildSuccessResult(page);

    }


    /**
     * 功能描述:
     * 功能:(上架或者发布时生成SVG检测项目图 检测报告)
     *
     * @Author: yanhao
     * @Date: 2019/12/9 13:49
     * @Param:
     * @Description:
     */
    @PostMapping(value = "/uploadCheckReport")
    @ApiOperation(value = "上架或者发布时生成SVG检测项目图 检测报告")
    public Result uploadCheckReport(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        long size = file.getSize();
        String filename = file.getOriginalFilename();
        String extensionName = FileUtil.extName(filename);
        try {
            carInfoService.uploadCheckReport(id, file.getInputStream(), size, filename, extensionName);
        } catch (IOException e) {
            return Result.buildErrorResult(e.getMessage());
        }
        return Result.buildSuccessResult("上传成功");
    }
}

