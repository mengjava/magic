package com.haoqi.magic.system.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoqi.magic.system.model.entity.SysDict;
import com.haoqi.magic.system.service.ISysDictService;
import com.haoqi.magic.system.service.ISysFileVersionDetailService;
import com.haoqi.rigger.common.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author twg
 * @since 2018/8/9
 * 系统初始化配置
 */
@Slf4j
@Configuration
public class SystemInitializationConfig {

    @Autowired
    private ISysDictService dictService;

    @Autowired
    private ISysFileVersionDetailService fileVersionDetailService;

    @PostConstruct
    public void init() {
        log.info("=============系统初始化开始===========");
        initDictFileVersion();
    }

    private void initDictFileVersion() {
        Map<String, String> dictValueMap = Maps.newHashMap();
        //值集存储对象
        Map<String, List<SysDict>> dictMapList = Maps.newLinkedHashMap();
        SysDict dict = new SysDict();
        dict.setIsDeleted(CommonConstant.STATUS_NORMAL);
        List<SysDict> dicts = dictService.selectList(new EntityWrapper<>(dict));
        dicts.sort((o1, o2) -> o1.getParentId().compareTo(o2.getParentId()));
        dicts.forEach(sysDict -> {
            String classType = sysDict.getClassType();
            if (!dictMapList.containsKey(classType) && sysDict.getParentId().equals(0L)) {
                dictValueMap.put(classType, String.format("%s_%s", classType, sysDict.getValueDesc()));
                dictMapList.put(classType, Lists.newArrayList());
            } else if (dictMapList.containsKey(classType)) {
                dictMapList.get(classType).add(sysDict);
            }
        });

        dictMapList.forEach((k, sysDicts) -> {
            String dictValue = StrUtil.subAfter(dictValueMap.get(k), StrUtil.UNDERLINE, true);
            fileVersionDetailService.insertFileVersion(k, dictValue, "sys_dict", sysDicts);
        });
    }

}
