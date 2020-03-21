package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.vo.CsCarSourceVO;

import java.util.Map;

/**
 * ClassName:com.haoqi.magic.business.service <br/>
 * Function: <br/>
 * Date:     2019/5/8 10:20 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public interface ICsCarSourceService {

    /**
     * 分页获取车商的车源信息
     * @param params
     * @return
     * @author huming
     * @date 2019/5/8 11:40
     */
    Page findPage(Map<String, Object> params);

    /**
     * 下架车源
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/8 15:46
     */
    Boolean pullOffCar(CsCarSourceVO vo);


    /***
     * 更改调拨状态
     * @param vo
     * @return
     */
    Boolean transferCar(CsCarSourceVO vo);
}
