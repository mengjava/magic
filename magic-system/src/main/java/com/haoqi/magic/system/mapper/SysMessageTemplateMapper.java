package com.haoqi.magic.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.system.model.dto.SysMessageTemplateDTO;
import com.haoqi.magic.system.model.entity.SysMessageTemplate;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息模板 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
public interface SysMessageTemplateMapper
        extends BaseMapper<SysMessageTemplate> {

    /**
     * 通过IDs删除短信模板信息
     * @param map
     * @return
     * @author huming
     * @date 2019/4/25 16:23
     */
    int delMessageTemplateByIds(Map<String, Object> map);

    /**
     * 分页获取短信模板信息
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/4/25 16:23
     */
    List<SysMessageTemplateDTO> findMessageTemplateByPage(Query<Object> query, Map<String, Object> condition);

    /**
     * 通过编码删除短信模板信息
     * @param map
     * @return
     * @author huming
     * @date 2019/4/25 16:24
     */
    int delMessageTemplateByCodes(Map<String, Object> map);

    /**
     * 通过编码获取短信模板条数
     * @param entity
     * @return
     * @author huming
     * @date 2019/4/25 16:24
     */
    int isCodeExist(SysMessageTemplate entity);

    /**
     * 更新短信模板信息
     * @param entity
     * @return
     * @author huming
     * @date 2019/4/25 16:25
     */
    int updateMessageTemplate(SysMessageTemplate entity);
}
