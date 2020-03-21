/**
 * Project Name:dispatch-user-service
 * File Name:SysFileVersionDetailServiceImpl.java
 * Package Name:com.haoyun.dispatch.system.service.impl
 * Date:2018年8月2日下午1:49:54
 * Copyright (c) 2018, cyzhf@sina.cn All Rights Reserved.
 */

package com.haoqi.magic.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.haoqi.magic.system.mapper.SysFileVersionDetailMapper;
import com.haoqi.magic.system.model.dto.FileVersionDTO;
import com.haoqi.magic.system.model.entity.SysFileVersionDetail;
import com.haoqi.magic.system.service.ISysFileVersionDetailService;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.web.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:SysFileVersionDetailServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年8月2日 下午1:49:54 <br/>
 *
 * @author 陈泳志
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Service
public class SysFileVersionDetailServiceImpl extends ServiceImpl<SysFileVersionDetailMapper, SysFileVersionDetail> implements ISysFileVersionDetailService {

    @Autowired
    private FastDfsFileService fastDfsFileService;
    @Autowired
    private ISysFileVersionDetailService fileVersionDetailService;

    @Override
    public boolean hasFile(String fileName) {
        EntityWrapper param = new EntityWrapper<SysFileVersionDetail>();
        param.eq("file_name", fileName);
        int count = this.selectCount(param);
        return count > 0 ? true : false;
    }

    @Override
    public boolean deleteByFileName(String fileName) {
        EntityWrapper param = new EntityWrapper<SysFileVersionDetail>();
        param.eq("file_name", fileName);
        return this.delete(param);
    }

    @Override
    public SysFileVersionDetail getByFileName(String fileName) {
        EntityWrapper param = new EntityWrapper<SysFileVersionDetail>();
        param.eq("file_name", fileName);
        return this.selectOne(param);
    }

    @Override
    public Boolean updateFileVersion(String fileName, String source, Collection rows) {
        String _fileName = fileName + FileUtils.JSON_EXT;
        //获取文件版本记录
        SysFileVersionDetail file = this.getByFileName(fileName);
        Map<String, Object> data = new HashMap<>();
        data.put("name", file.getName());
        data.put("type", fileName);
        data.put("rows", rows);

        //上传最新json文件
        String fileJson = JSONObject.toJSONString(data);
        ByteArrayInputStream inputStream = IoUtil.toStream(fileJson, Charset.defaultCharset());
        String filePath = fastDfsFileService.uploadFile(inputStream, inputStream.available(), _fileName, FileUtil.extName(_fileName));

        //更新字典文件MD5内容
        fastDfsFileService.deleteFile(file.getUrl());
        file.setUrl(filePath);
        file.setGmtModified(DateUtil.date());
        file.setFileMd5(SecureUtil.md5(fileJson));
        return this.updateById(file);
    }

    @Override
    public Boolean insertFileVersion(FileVersionDTO fileVersionDTO) {
        // 先删除原来的同一个类型的文件记录信息
        String fileName = FileUtil.mainName(fileVersionDTO.getFile());
        this.deleteByFileName(fileName);
        // 插入新的文件记录信息
        SysFileVersionDetail fileVersion = new SysFileVersionDetail();
        fileVersion.setSourceName(fileVersionDTO.getSourceName());
        fileVersion.setFile(fileVersionDTO.getFile());
        fileVersion.setRemark("静态资源文件");
        fileVersion.setCreator(0L);
        fileVersion.setModifier(0L);
        fileVersion.setGmtCreate(DateUtil.date());
        fileVersion.setGmtModified(DateUtil.date());
        fileVersion.setName(fileVersionDTO.getName());
        fileVersion.setFileName(fileName);
        fileVersion.setUrl(fileVersionDTO.getUrl());
        fileVersion.setFileMd5(fileVersionDTO.getFileMD5());
        return this.insert(fileVersion);
    }

    @Override
    public Boolean insertFileVersion(String type, String value, String source, Collection rows) {
        String fileName = type + FileUtils.JSON_EXT;
        //判断文件是否已生成，生成了就不在重新创建
        boolean isExist = fileVersionDetailService.hasFile(type);
        if (!isExist) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("name", value);
            data.put("type", type);
            data.put("rows", rows);
            String fileJson = JSONObject.toJSONString(data);
            ByteArrayInputStream inputStream = IoUtil.toStream(fileJson, Charset.defaultCharset());

            log.info("$$$$$$$$$$$$插入文件版本信息$$$$$$$$ rrrr inputStream ：{}，available:{}，fileName：{}",inputStream, inputStream.available(), fileName);
            String filePath = fastDfsFileService.uploadFile(inputStream, inputStream.available(), fileName, FileUtil.extName(fileName));
            log.info("$$$$$$$$$$$$插入文件版本信息$$$$$$$$，inputStream ：{}，available:{}，fileName：{}，filePath：{}", inputStream, inputStream.available(), fileName, filePath);
            FileVersionDTO fileVersionDTO = new FileVersionDTO();
            fileVersionDTO.setFile(fileName);
            fileVersionDTO.setName(value);
            fileVersionDTO.setUrl(filePath);
            fileVersionDTO.setSourceName(source);
            fileVersionDTO.setFileMD5(SecureUtil.md5(fileJson));
            return fileVersionDetailService.insertFileVersion(fileVersionDTO);
        }
        return false;
    }
}

