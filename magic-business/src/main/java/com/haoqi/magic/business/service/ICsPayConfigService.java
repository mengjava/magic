package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsPayConfigDTO;
import com.haoqi.magic.business.model.dto.CsPayConfigListVO;
import com.haoqi.magic.business.model.entity.CsPayConfig;
import com.haoqi.magic.business.model.vo.CsPayConfigVO;

import java.util.List;

/**
 * <p>
 * 支付配置 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-12
 */
public interface ICsPayConfigService extends IService<CsPayConfig> {

    /**
     * 功能描述: 支付设置列表
     *
     * @return java.util.List<com.haoqi.magic.business.model.dto.CsPayConfigListVO>
     * @auther mengyao
     * @date 2019/12/12 0012 下午 2:40
     */
    List<CsPayConfigListVO> payConfigList();

    /**
     * 功能描述: 新增支付设置
     *
     * @param dto
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/12/12 0012 下午 2:52
     */
    Boolean add(CsPayConfigDTO dto);

    /**
     * 功能描述: 修改支付设置
     *
     * @param list
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/12/12 0012 下午 2:52
     */
    Boolean edit(List<CsPayConfigDTO> list);


    /**
     * 功能描述: 删除设置
     *
     * @param ids
     * @return void
     * @auther mengyao
     * @date 2019/12/12 0012 下午 3:01
     */
    void deletePayConfig(Long[] ids);

    /**
     * 功能描述: 启用禁用
     *
     * @param dto
     * @return void
     * @auther mengyao
     * @date 2019/12/16 0016 下午 1:44
     */
    void updateEnabled(CsPayConfigDTO dto);

    /**
     * 通过id，获取支付配置
     *
     * @param id
     * @return
     */
    CsPayConfigDTO getById(Long id);

    /**
     * 功能描述: 获取支付类型列表
     * @param 
     * @return java.util.List<com.haoqi.magic.business.model.vo.CsPayConfigVO>
     * @auther mengyao
     * @date 2019/12/25 0025 下午 2:02
     */
	List<CsPayConfigVO> payTypeList();


	/**
	 * 功能描述: 设置是否推荐
	 * @param id
	 * @param isRecommend
	 * @return void
	 * @auther mengyao
	 * @date 2019/12/30 0030 上午 11:34
	 */
	void setRecommend(Long id, Integer isRecommend);
}
