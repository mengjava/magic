package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsCarSourceDTO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * ClassName:com.haoqi.magic.business.mapper <br/>
 * Function: <br/>
 * Date:     2019/5/8 10:38 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public interface CsCarSourceMapper {

    /**
     * 分页获取车商的车源信息
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/5/8 11:41
     */
    List<CsCarSourceDTO> findPage(Query query, Map condition);
}
