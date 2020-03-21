package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsDisputeItemDTO;
import com.haoqi.magic.business.model.dto.CsItemBaseDTO;
import com.haoqi.magic.business.model.entity.CsDisputeItem;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 争议项管理 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
public interface ICsDisputeItemService extends IService<CsDisputeItem> {
    /**
     * 获取所有的争议类别
     *
     * @return
     */
    List<CsItemBaseDTO> getAllItemType();

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    Page selectPageByParam(Query query);

    /**
     * 添加或者编辑
     *
     * @param csDisputeItem
     * @return
     */
    Boolean addOrUpdate(CsDisputeItem csDisputeItem);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Boolean deleteByIds(Long id);

    /**
     * 条件查询
     *
     * @param params
     * @return
     */
    List<CsDisputeItemDTO> selectListByParam(Map<String, Object> params);
}
