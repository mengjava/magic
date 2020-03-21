package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsVipRecordDetailDTO;
import com.haoqi.magic.business.model.entity.CsVipRecordDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户会员查询记录表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
public interface CsVipRecordDetailMapper extends BaseMapper<CsVipRecordDetail> {

    /**
     * 功能描述:会员车况查询历史记录列表
     * @param query
     * @return com.baomidou.mybatisplus.plugins.Page
     * @auther mengyao
     * @date
     */
    List<CsVipRecordDetailDTO> selectByPage(Query query, Map condition);
}
