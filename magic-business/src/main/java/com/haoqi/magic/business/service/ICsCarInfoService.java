package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CarDTO;
import com.haoqi.magic.business.model.dto.CarInfoDTO;
import com.haoqi.magic.business.model.dto.CsCarHomeDTO;
import com.haoqi.magic.business.model.dto.CsConsoleDTO;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.haoqi.rigger.mybatis.Query;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 车辆信息表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
public interface ICsCarInfoService extends IService<CsCarInfo> {

    /**
     * 通过车辆id，获取车辆、配置、图片文件、检修等信息
     *
     * @param id
     * @return
     */
    Optional<CarDTO> getCarById(Long id);

    /**
     * 车辆信息分页查询
     *
     * @param query
     * @return
     */
    Page<CarDTO> findByPage(Query query);

    /**
     * 保存、更新车辆信息
     *
     * @param carDTO
     * @return
     */
    CarDTO insertOrUpdateCarInfo(CarDTO carDTO);

    /**
     * 通过车辆ID获取车辆信息
     *
     * @param csCarInfoId
     * @return
     * @author huming
     * @date 2019/5/6 10:33
     */
    CsCarInfo getOneById(Long csCarInfoId);

    /**
     * 功能描述: 控制台
     *
     * @param query
     * @return com.baomidou.mybatisplus.plugins.Page<consoleDTO>
     * @auther mengyao
     * @date 2019/5/10 0010 下午 4:22
     */
    Page<CsConsoleDTO> findConsoleByPage(Query query);

    /***
     * C端B端页面获取车辆信息
     * @param id
     * @return
     */
    CarInfoDTO getCarInfoById(Long id);

    /**
     * 通过自定义参数、车辆ID判断车辆是否命中标签
     *
     * @param carId  车辆ID
     * @param sqlStr 自定义参数
     * @return
     * @author huming
     * @date 2019/5/14 15:01
     */
    Boolean checkTagWithSqlStr(Long carId, String sqlStr);

    /**
     * 通过条件查询车辆信息
     *
     * @param query
     * @return
     * @author huming
     * @date 2019/5/14 16:29
     */
    List<Long> getCarWithSqlStr(Query query);

    /**
     * 修改车辆所属车商
     *
     * @param car
     * @return
     * @author huming
     * @date 2019/5/28 17:50
     */
    Boolean changeCarDealer(CarDTO car);

    /**
     * 下架车辆
     *
     * @param carDTO
     * @return
     * @author huming
     * @date 2019/5/29 10:03
     */
    Boolean pullOffCar(CsCarHomeDTO carDTO);

    /**
     * 删除车辆
     *
     * @param carDTO
     * @return
     * @author huming
     * @date 2019/5/29 10:46
     */
    Boolean deleteCar(CsCarHomeDTO carDTO);

    /**
     * 上架车辆
     *
     * @param carDTO
     * @return
     * @author huming
     * @date 2019/5/30 15:25
     */
    Boolean pullUpCar(CsCarHomeDTO carDTO) throws Exception;

    /***
     * 后台车源调拨记录
     * @param query
     * @return
     */
    Page transferRecordPage(Query query);


    /**
     * 通过车辆ID、车辆状态、交易状态，获取信息
     *
     * @param carId
     * @param status
     * @param tradeFlag
     * @return
     */
    CarDTO getCarByIdAndStatusTradeFlag(Long carId, Integer status, Integer tradeFlag);

    /**
     * 上传SVG检测报告
     * @return
     * @param id
     * @param inputStream
     * @param size
     * @param filename
     * @param extensionName
     */
    Boolean uploadCheckReport(Long id, InputStream inputStream, long size, String filename, String extensionName);

    /**
     * 订单完成 下架车辆 更改交易状态
     * @param csCarInfoId
     * @param flag
     * @return
     */
    Boolean completeAndPutOffCar(Long csCarInfoId, Boolean flag);
}
