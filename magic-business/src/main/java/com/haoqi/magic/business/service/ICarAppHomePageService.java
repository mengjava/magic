package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CarInfoDTO;
import com.haoqi.magic.business.model.dto.CsAppHotCityDTO;
import com.haoqi.magic.business.model.dto.CsHotCityDTO;
import com.haoqi.magic.business.model.dto.UserDTO;
import com.haoqi.magic.business.model.vo.AppHomePageVO;
import com.haoqi.magic.business.model.vo.PullOffCarVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * APP首页接口
 *
 * @Author: yanhao
 * @Date: 2019/11/28 11:07
 * @Param:
 * @Description:
 */
public interface ICarAppHomePageService {


    /**
     * 获取热门城市
     *
     * @return
     */
    public List<CsAppHotCityDTO> getHotCity();

    /**
     * 获取所有的城市 首字母排序
     *
     * @return
     */
    public List<CsHotCityDTO> getAllCity();

    /**
     * APP分页查询车辆订单数据
     *
     * @param param
     * @param currentUserInfo
     * @return
     */
    public Page selectCarSearchParam(AppHomePageVO param, UserDTO currentUserInfo);

    /**
     * 获取车辆详情
     *
     * @param id
     * @param currentUserInfo
     * @return
     */
    public CarInfoDTO getCarInfoById(Long id, UserDTO currentUserInfo);

    /**
     * 买家&卖家中心
     *
     * @param currentUserInfo
     * @return
     */
    public Map<String, Object> getOrderCount(UserDTO currentUserInfo);

    /**
     * 下架车辆
     *
     * @param vo
     * @param currentUserInfo
     * @return
     */
    public Boolean pullOffCar(PullOffCarVO vo, UserDTO currentUserInfo);

    /**
     * APP车辆分页查询(卖家)
     *
     * @param query
     * @return
     */
    public Page findByPage(Query query);

    /**
     * 买车校验规则
     *
     * @param id
     * @param currentUserInfo
     * @return
     */
    public Boolean checkCarBuyRule(Long id, UserDTO currentUserInfo);

    /**
     * 卖出校验规则
     *
     * @param id
     * @param currentUserInfo
     * @return
     */
    public Boolean checkCarSaleRule(Long id, UserDTO currentUserInfo);
}
