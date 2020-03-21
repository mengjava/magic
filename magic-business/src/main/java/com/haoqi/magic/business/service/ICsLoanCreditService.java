package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsLoanCreditDTO;
import com.haoqi.magic.business.model.entity.CsLoanCredit;
import com.haoqi.magic.business.model.vo.CsLoanCreditVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * <p>
 * 分期表 服务类
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
public interface ICsLoanCreditService
        extends IService<CsLoanCredit> {

    /**
     * 分页获取分期列表信息
     * @param query
     * @return
     * @author huming
     * @date 2019/5/5 9:51
     */
    Page findPage(Query query);

    /**
     * 新增分期数据
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/5 9:55
     */
    Boolean insert(CsLoanCreditVO vo);


    /**
     * 通过ID更新分期数据
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/5 9:59
     */
    Boolean updateCsLoanCreditById(CsLoanCreditVO vo);


    /**
     * 通过ID获取分期数据
     * @param id 分期主键
     * @return
     * @author huming
     * @date 2019/5/5 10:00
     */
    CsLoanCreditDTO getOneById(Long id,Long csCarDealerId);

    /**
     * 通过ids删除分期信息
     * @param lIds
     * @return
     * @author huming
     * @date 2019/5/20 14:10
     */
    Boolean deleteByIds(List<Long> lIds);
}
