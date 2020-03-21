package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.PushMessageDTO;
import com.haoqi.magic.business.model.entity.CsPushMessage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息推送表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-12-10
 */
public interface CsPushMessageMapper extends BaseMapper<CsPushMessage> {
    /**
     * 分页查询消息
     *
     * @param query
     * @param condition
     * @return
     */
    List<PushMessageDTO> selectByPage(Query query, Map condition);
}
