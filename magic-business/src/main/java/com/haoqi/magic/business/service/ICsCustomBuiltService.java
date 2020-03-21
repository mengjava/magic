package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsCustomBuiltDTO;
import com.haoqi.magic.business.model.entity.CsCustomBuilt;
import com.haoqi.magic.business.model.vo.CsCustomBuiltVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * <p>
 * 客户定制表 服务类
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
public interface ICsCustomBuiltService
        extends IService<CsCustomBuilt> {

    /**
     * 分页获取定制信息
     * @param query
     * @return
     * @author huming
     * @date 2019/5/5 11:34
     */
    Page findPage(Query query);

    /**
     * 新增定制
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/5 11:34
     */
    Boolean insert(CsCustomBuiltVO vo);

    /**
     * 通过ID更新定制信息
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/5 11:36
     */
    Boolean updateCsCustomBuiltById(CsCustomBuiltVO vo);


    /**
     * 通过ID获取定制信息
     * @param id 定制数据ID
     * @return
     * @author huming
     * @date 2019/5/5 11:36
     */
    CsCustomBuiltDTO getOneById(Long id, Long csCarDealerId);

    /**
     * 通过ids删除定制信息
     * @param lIds
     * @return
     * @author huming
     * @date 2019/5/20 14:04
     */
    Boolean deleteByIds(List<Long> lIds);
}
