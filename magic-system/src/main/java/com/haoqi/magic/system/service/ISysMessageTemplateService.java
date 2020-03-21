package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.SysMessageTemplateDTO;
import com.haoqi.magic.system.model.entity.SysMessageTemplate;
import com.haoqi.rigger.mybatis.Query;

import java.util.Map;

/**
 * <p>
 * 消息模板 服务类
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
public interface ISysMessageTemplateService
        extends IService<SysMessageTemplate> {

    /**
     * 添加消息模板
     * @param entity
     * @return
     * @author huming
     * @date 2019/4/25 16:26
     */
    Boolean addMessageTemplate(SysMessageTemplate entity);

    /**
     * 通过ids删除消息模板
     * @param map
     * @return
     * @author huming
     * @date 2019/4/25 16:26
     */
    Boolean delMessageTemplateByIds(Map<String, Object> map);

    /**
     * 分页获取短信模板信息
     * @param query
     * @return
     * @author huming
     * @date 2019/4/25 16:27
     */
    Page<SysMessageTemplateDTO> findMessageTemplateByPage(Query query);

    /**
     * 通过codes删除消息模板
     * @param map
     * @return
     * @author huming
     * @date 2019/4/25 16:27
     */
    Boolean delMessageTemplateByCodes(Map<String, Object> map);

    /**
     * 获取一条消息模板信息
     * @param param
     * @return
     * @author huming
     * @date 2019/4/25 16:27
     */
    SysMessageTemplate getOne(SysMessageTemplate param);

    /**
     * 修改消息模板信息
     * @param entity
     * @return
     * @author huming
     * @date 2019/4/25 16:27
     */
    Boolean updateMessageTemplate(SysMessageTemplate entity);

    /**
     * 通过条件判断短信模板是否存在
     * @param entity
     * @return
     * @author huming
     * @date 2019/4/25 16:27
     */
    Boolean isExist(SysMessageTemplate entity);
}
