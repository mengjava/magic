package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsCarDealerDTO;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.vo.CsCarDealerAuditVO;
import com.haoqi.magic.business.model.vo.UserDealerRegisterVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 商家表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-05-05
 */
public interface ICsCarDealerService extends IService<CsCarDealer> {

    /**
     * 通过ID获取经销商信息
     *
     * @param id
     * @return
     * @author huming
     * @date 2019/5/6 17:19
     */
    Optional<CsCarDealer> getOneById(Long id);

    /**
     * 功能描述: 商家进行注册 如果
     *
     * @param csCarDealer
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/5/6 0006 下午 5:48
     */
    Boolean register(CsCarDealer csCarDealer);

    /**
     * 用户、车商注册
     *
     * @param userDealerRegister
     * @return
     */
    String userDealerRegister(UserDealerRegisterVO userDealerRegister);

    /**
     * 功能描述:商家审核页面分页
     *
     * @param query
     * @return com.haoqi.rigger.core.page.Page
     * @auther mengyao
     * @date 2019/5/7 0007 下午 3:08
     */
    Page findCarDealerByPage(Query query);

    /**
     * 功能描述: 商家启用禁用
     *
     * @param id
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/5/8 0008 上午 9:53
     */
    Boolean updateEnabledById(Long id);

    /**
     * 功能描述: 诚信联盟的开启与关闭
     *
     * @param id
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/5/8 0008 上午 10:36
     */
    Boolean updateCreditUnionById(Long id);

    /**
     * 功能描述: 商家审核
     *
     * @param csCarDealerAuditVO
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/5/8 0008 下午 3:31
     */
    Boolean audit(CsCarDealerAuditVO csCarDealerAuditVO);

    /**
     * 获取全部车商
     *
     * @param params
     * @return
     * @author huming
     * @date 2019/5/13 10:51
     */
    List<CsCarDealerDTO> getAllCarDealer(Map<String, Object> params);

    /**
     * 通过登录名获取车商详细信息
     *
     * @param loginName
     * @return
     * @author huming
     * @date 2019/5/20 13:42
     */
    Optional<CsCarDealer> getOneByLoginName(String loginName);

    /**
     * 获取当前登录人对应的车商信息
     *
     * @param loginName
     * @return
     * @author huming
     * @date 2019/5/23 11:01
     */
    CsCarDealer getCurrentCarDealerInfo(String loginName);

    /**
     * 功能描述:
     *
     * @param id
     * @param paymentType
     * @return void
     * @auther mengyao
     * @date 2019/12/2 0002 下午 8:32
     */
    void setPaymentMethod(Long id, Integer paymentType);

    /**
     * 通过联系人ID，获取车商信息
     *
     * @param userId
     * @return
     */
    CsCarDealer getByUserId(Long userId);

    /**
     * 功能描述: 卖家管理列表
     * @param query
     * @return com.baomidou.mybatisplus.plugins.Page
     * @auther mengyao
     * @date 2019/12/25 0025 上午 11:29
     */
	Page findCarSellerByPage(Query query);
}
