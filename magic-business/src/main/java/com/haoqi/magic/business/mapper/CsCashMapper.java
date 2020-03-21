package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.entity.CsCash;
import com.haoqi.magic.business.model.vo.CsCashVO;
import com.haoqi.rigger.mybatis.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现管理表 Mapper 接口
 * </p>
 *
 * @author mengyao
 * @since 2019-12-23
 */
public interface CsCashMapper extends BaseMapper<CsCash> {

    /**
     * 功能描述: 提现管理列表
     *
     * @param query
     * @param condition
     * @return java.util.List<com.haoqi.magic.business.model.vo.CsCashVO>
     * @auther mengyao
     * @date 2019/12/24 0024 上午 11:02
     */
    List<CsCashVO> selectByPage(Query query, Map condition);

    /**
     * 合计
     *
     * @param map
     * @return
     */
    BigDecimal totalAmount(Map map);
}
