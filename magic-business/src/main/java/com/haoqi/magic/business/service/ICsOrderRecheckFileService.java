package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsOrderRecheckFileDTO;
import com.haoqi.magic.business.model.entity.CsOrderRecheckFile;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 复检附件 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
public interface ICsOrderRecheckFileService extends IService<CsOrderRecheckFile> {

    /**
     * 通过订单id和itemId获取指定的复检项
     *
     * @param orderId
     * @param itemId
     * @return
     */
    List<CsOrderRecheckFileDTO> findByOrderIdCheckItemId(Long orderId, Long itemId);

    /**
     * 通过订单id获取检测限
     *
     * @param orderId
     * @return
     */
    List<CsOrderRecheckFileDTO> findWithCheckItemByOrderId(Long orderId);

    /**
     * 保存或者更新复检项
     *
     * @param carCheckDTO
     * @return
     */
    CsOrderRecheckFileDTO saveOrUpdate(CsOrderRecheckFileDTO carCheckDTO);

    /**
     * 通过类别获取复检项
     *
     * @param orderId
     * @param type
     * @return
     */
    List<Map<String, Object>> findByCarIdCheckType(Long orderId, Integer type);


    /**
     * 复检信息分页列表
     *
     * @param query
     * @return
     */
    Page<CsOrderRecheckFileDTO> findPage(Query query);

    /**
     * 通过订单id和复检项id删除
     *
     * @param orderId
     * @param ids
     * @return
     */
    Boolean deleteByOrderIdCheckId(Long orderId, List<Long> ids);
}
