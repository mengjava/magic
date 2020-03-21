package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsServiceFeeListDTO;
import com.haoqi.magic.business.model.dto.ServiceFreeDTO;
import com.haoqi.magic.business.model.entity.CsServiceFee;
import com.haoqi.rigger.mybatis.Query;

import java.math.BigDecimal;

/**
 * <p>
 * 服务费设置表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-18
 */
public interface ICsServiceFeeService extends IService<CsServiceFee> {


    /**
     * 通过城市id、金额区间，获取服务费信息
     *
     * @param areaId
     * @param price
     * @return
     */
    CsServiceFee getByAreaIdAndPrice(Long areaId, BigDecimal price);

    /**
     * 功能描述: 服务费列表
     *
     * @param query
     * @return com.baomidou.mybatisplus.plugins.Page
     * @auther mengyao
     * @date 2019/12/18 0018 上午 11:42
     */
    Page findByPage(Query query);

    /**
     * 功能描述: 服务费设置列表
     *
     * @param listDTO
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/12/18 0018 下午 1:55
     */
    Boolean add(CsServiceFeeListDTO listDTO);

    /**
     * 功能描述: 根据城市批量删除服务费设置
     *
     * @param sysAreaIds
     * @return void
     * @auther mengyao
     * @date 2019/12/18 0018 下午 2:08
     */
    void deleteServiceFee(Long[] sysAreaIds);

    /**
     * 功能描述: 服务费设置编辑
     *
     * @param listDTO
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/12/18 0018 下午 4:03
     */
    Boolean edit(CsServiceFeeListDTO listDTO);

    /**
     * 通过城市id，获取服务费
     *
     * @param areaId
     * @param price
     * @return
     */
    BigDecimal getServiceFree(Long areaId, BigDecimal price);

    /**
     * 通过城市id和金额获取服务费
     */
    ServiceFreeDTO getCarServiceFree(Long areaId, BigDecimal price);
}
