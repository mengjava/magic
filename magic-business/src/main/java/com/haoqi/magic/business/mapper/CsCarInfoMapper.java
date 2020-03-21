package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CarDTO;
import com.haoqi.magic.business.model.dto.CsConsoleDTO;
import com.haoqi.magic.business.model.dto.TransferRecordPageDTO;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
public interface CsCarInfoMapper extends BaseMapper<CsCarInfo> {

    /**
     * 车辆信息分页列表
     *
     * @param query
     * @param param
     * @return
     */
    List<CarDTO> findByPage(Query query, Map param);

    /**
     * 功能描述: 控制台
     *
     * @param query
     * @param condition
     * @return java.util.List<com.haoqi.magic.business.model.dto.CsConsoleDTO>
     * @auther mengyao
     * @date 2019/5/10 0010 下午 4:26
     */
    List<CsConsoleDTO> findConsoleByPage(Query query, Map condition);

    /**
     * 通过自定义参数、车辆ID判断车辆是否命中标签
     *
     * @return
     * @author huming
     * @date 2019/5/14 15:03
     */
    int checkTagWithSqlStr(Map<String, Object> params);

    /**
     * 通过条件查询车辆信息
     *
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/5/14 16:32
     */
    List<Long> getCarWithSqlStr(Query query, Map condition);

    /**
     * 修改车辆所属车商
     *
     * @param car
     * @return
     * @author huming
     * @date 2019/5/28 17:52
     */
    Boolean changeCarDearler(CarDTO car);

    /***
     * 浏览量+1
     * @param carId
     * @return
     */
    Boolean updateScanNum(Long carId);

    /***
     * 后台审核人员调拨记录列表
     * @param query
     * @param condition
     * @return
     */
    List<TransferRecordPageDTO> selectTransferRecordPage(Query query, Map condition);
}
