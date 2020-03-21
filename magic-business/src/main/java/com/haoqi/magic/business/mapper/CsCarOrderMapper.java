package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.AppBuyerCountDTO;
import com.haoqi.magic.business.model.dto.AppSellerCountDTO;
import com.haoqi.magic.business.model.dto.CarOrderDTO;
import com.haoqi.magic.business.model.entity.CsCarOrder;
import com.haoqi.rigger.mybatis.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交易订单表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-12-03
 */
public interface CsCarOrderMapper extends BaseMapper<CsCarOrder> {

    /**
     * 卖家订单数量
     *
     * @param userId
     * @return
     */
    AppBuyerCountDTO selectBuyerOrderCount(@Param("userId") Long userId);

    /***
     * 卖家订单数量
     * @param userId
     * @return
     */
    AppSellerCountDTO selectSellerOrderCount(@Param("userId") Long userId);

    /**
     * 订单分页列表
     *
     * @param query
     * @param condition
     * @return
     */
    List<CarOrderDTO> findPage(Query query, Map condition);

    /**
     * 分页获取冻结车辆
     *
     * @param query
     * @param condition
     * @return
     */
    List<CarOrderDTO> freezingCar(Query query, Map condition);

    /**
     * 订单、审核详情信息
     *
     * @param orderId 订单id
     * @return
     */
    CarOrderDTO getOrderAuditInfo(@Param("orderId") Long orderId);


    /**
     * 功能描述: 跑批处理打款超时订单
     *
     * @param payMoneyOverTime
     * @return void
     * @auther mengyao
     * @date 2019/12/23 0023 上午 11:35
     */
    void updatePayMoneyOverTimeOrder(String payMoneyOverTime);

    /**
     * 功能描述: 跑批处理过户超时订单
     *
     * @param transferOverTime
     * @return void
     * @auther mengyao
     * @date 2019/12/23 0023 上午 11:49
     */
    void updateTransferOverTimeOrder(String transferOverTime);

    /**
     * 换审核
     *
     * @param ids
     */
    void changeAuditor(@Param("ids") List<Long> ids);

    /**
     * 订单、审核详情信息
     *
     * @param orderId 订单id
     * @param carId   车辆id
     * @return
     */
    CarOrderDTO getOrderAndAuditByOrderIdAndCarId(@Param("orderId") Long orderId, @Param("carId") Long carId);

}
