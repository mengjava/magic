package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CsTransferRecordDTO;
import com.haoqi.magic.business.model.entity.CsTransferRecord;
import com.haoqi.magic.business.model.vo.CsTransferRecordVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 调拨记录表 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
public interface CsTransferRecordMapper
        extends BaseMapper<CsTransferRecord> {

    /**
     * 分页获取调拨信息
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/5/6 10:20
     */
    List<CsTransferRecordDTO> findPage(Query query, Map condition);

    /**
     * 获取调拨详细信息
     * @param param
     * @return
     * @author huming
     * @date 2019/5/6 17:47
     */
    CsTransferRecord getOneById(Map<String, Object> param);

    /**
     * 该车辆、该经销商、申请状态的数据全部设置为取消状态
     * @param entity
     * @return
     * @author huming
     * @date 2019/5/7 10:29
     */
    Boolean updateOtherTransferTOCancel(CsTransferRecord entity);

    /**
     * 根据条件获取调拨数据
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/7 10:35
     */
    List<CsTransferRecord> getCsTransferRecordWithCondition(CsTransferRecordVO vo);

}
