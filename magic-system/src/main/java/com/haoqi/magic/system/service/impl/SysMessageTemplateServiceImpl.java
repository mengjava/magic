package com.haoqi.magic.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.system.mapper.SysMessageTemplateMapper;
import com.haoqi.magic.system.model.dto.SysMessageTemplateDTO;
import com.haoqi.magic.system.model.entity.SysMessageTemplate;
import com.haoqi.magic.system.service.ISysMessageTemplateService;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 消息模板 服务实现类
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
@Slf4j
@Service
public class SysMessageTemplateServiceImpl
        extends ServiceImpl<SysMessageTemplateMapper, SysMessageTemplate>
        implements ISysMessageTemplateService {

    /**
     * 短信模板ys
     */
    @Autowired
    private SysMessageTemplateMapper sysMessageTemplateMapper;

    @Override
    @Transactional
    public Boolean addMessageTemplate(SysMessageTemplate entity) {
        Assert.notNull(entity,"参数不能为空");
        Assert.notNull(entity.getTemplateCode(),"消息标识不能为空");
        Assert.notNull(entity.getTemplateContent(),"消息内容不能为空");
        Assert.notNull(entity.getTemplateVariables(),"模板变量提示内容不能为空");

        SysMessageTemplate param = new SysMessageTemplate();
        param.setTemplateCode(entity.getTemplateCode());
        if (null != this.getOne(param)){
            if (log.isDebugEnabled()){
                log.debug("messageTemplateCode is repeat：" + "templateCode：" + entity.getTemplateCode());
            }

            throw new RiggerException("消息标识[" + entity.getTemplateCode() + "]已存在");
        }
        return sysMessageTemplateMapper.insert(entity) > 0;
    }

    @Override
    @Transactional
    public Boolean delMessageTemplateByIds(Map<String, Object> map) {
        return sysMessageTemplateMapper.delMessageTemplateByIds(map) > 0;
    }

    @Override
    public Page<SysMessageTemplateDTO> findMessageTemplateByPage(Query query) {
        return query.setRecords(sysMessageTemplateMapper.findMessageTemplateByPage(query, query.getCondition()));
    }

    @Override
    public Boolean delMessageTemplateByCodes(Map<String, Object> map) {
        return sysMessageTemplateMapper.delMessageTemplateByCodes(map) > 0;
    }

    @Override
    public SysMessageTemplate getOne(SysMessageTemplate param) {
        return this.selectOne(new EntityWrapper<>(param));
    }

    @Override
    public Boolean updateMessageTemplate(SysMessageTemplate entity) {
        SysMessageTemplate param = new SysMessageTemplate();
        param.setId(entity.getId());
        param.setTemplateCode(entity.getTemplateCode());
        if (this.isExist(param)){
            if (log.isDebugEnabled()){
                log.debug("messageTemplateCode is exist:" +  "templateCode:" + param.getTemplateCode());
            }
            throw new RiggerException("消息标识[" + entity.getTemplateCode() + "]已存在");
        }
        return sysMessageTemplateMapper.updateMessageTemplate(entity) > 0;
    }

    @Override
    public Boolean isExist(SysMessageTemplate entity) {
        return sysMessageTemplateMapper.isCodeExist(entity) > 0;
    }

}
