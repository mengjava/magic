package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsOrderFileDTO;
import com.haoqi.magic.business.model.entity.CsOrderFile;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-12-11
 */
public interface CsOrderFileMapper extends BaseMapper<CsOrderFile> {

    /**
     * 分页获取订单附件
     *
     * @param query
     * @param condition
     * @return
     */
    List<CsOrderFileDTO> selectOrderFilePage(Query query, Map condition);

    /**
     * 列表分类查询
     * @param condition
     * @return
     */
    List<CsOrderFile> selectOrderFile(Map condition);
}
