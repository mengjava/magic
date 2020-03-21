package com.haoqi.magic.system;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Maps;
import com.haoqi.magic.system.model.entity.SysAdvertConfig;
import com.haoqi.magic.system.service.ISysAdvertConfigService;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MagicSystemApplication.class)
public class SysAdvertConfigTests {

    @Autowired
    private ISysAdvertConfigService sysAdvertConfigService;

    @Autowired
    private FastDfsFileService fastDfsFileService;


    @Test
    public void testSysAdvertSave() {
        SysAdvertConfig advertConfig = new SysAdvertConfig();
        advertConfig.setCreator(0L);
        advertConfig.setModifier(0L);
        advertConfig.setGmtCreate(DateUtil.date());
        advertConfig.setGmtModified(DateUtil.date());
        advertConfig.setTitle("特大新闻");
        advertConfig.setPositionCode("ADFG");
        advertConfig.setPicturePath("www.baidu.com");
        advertConfig.setPictureName("百度");
        advertConfig.setLinkUrl("www");
        advertConfig.setSort(1);
        advertConfig.setJumpType(1);
        advertConfig.setContent("内容");
        sysAdvertConfigService.insertAdvert(advertConfig);
    }

    @Test
    public void testSysAdvertPage() {
        Map params = Maps.newHashMap();
        params.put("title", "特大新闻");
        params.put("status", 1);
        params.put("page", 1);
        params.put("limit", 10);
        sysAdvertConfigService.findAdvertByPage(new Query(params));
    }

    @Test
    public void testIsExist() {
        Boolean b = sysAdvertConfigService.isExist("特大新闻");
        Assert.assertTrue(b);
    }


    public static void main(String[] args) {
        String key = String.format("%s:sms:%s", "aa", "13945055929");
        System.out.println(key);
    }


}
