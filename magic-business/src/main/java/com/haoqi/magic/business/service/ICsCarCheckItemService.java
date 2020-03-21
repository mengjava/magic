package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsCarCheckItemDTO;
import com.haoqi.magic.business.model.entity.CsCarCheckItem;
import com.haoqi.magic.business.model.vo.CarCheckItemTree;

import java.util.List;

/**
 * <p>
 * 车辆检测项 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
public interface ICsCarCheckItemService extends IService<CsCarCheckItem> {


    /**
     * 通过id，删除检查项
     *
     * @param id
     * @return
     */
    Boolean delCarCheckItemById(Long id);

    /**
     * 通过id，获取检查项
     *
     * @param id
     * @return
     */
    CsCarCheckItem getCarCheckItemById(Long id);

    /**
     * 通过id，获取带有父节点项名称的检查项
     *
     * @param id
     * @return
     */
    CsCarCheckItemDTO getCheckItemWithParentItemNameById(Long id);

    /**
     * 添加、更新检查项
     *
     * @param carCheckItem
     * @return
     */
    Boolean saveOrUpdate(CsCarCheckItem carCheckItem);

    /**
     * 通过id，获取检查项信息
     *
     * @param ids
     * @return
     */
    List<CsCarCheckItem> findByIds(List<Long> ids);

    /**
     * 通过检查类型，获取检查项信息
     *
     * @param type
     * @return 检查项树形结构
     */
    List<CarCheckItemTree> findByType(Integer type);

    /**
     * 通过检查类型，获取检查项信息
     *
     * @param type
     * @return 检查项
     */
    List<CsCarCheckItem> findCheckItemByType(Integer type);
}
