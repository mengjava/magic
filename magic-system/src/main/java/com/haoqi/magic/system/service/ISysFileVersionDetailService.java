/**
 * Project Name:dispatch-user-service
 * File Name:ISysFileVersionDetailService.java
 * Package Name:com.haoyun.dispatch.system.service
 * Date:2018年8月2日下午1:49:00
 * Copyright (c) 2018, cyzhf@sina.cn All Rights Reserved.
 */

package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.FileVersionDTO;
import com.haoqi.magic.system.model.entity.SysFileVersionDetail;

import java.util.Collection;

/**
 * ClassName:ISysFileVersionDetailService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2018年8月2日 下午1:49:00 <br/>
 *
 * @author 陈泳志
 * @see
 * @since JDK 1.8
 */
public interface ISysFileVersionDetailService extends IService<SysFileVersionDetail> {

    boolean hasFile(String fileName);

    /**
     * 根据文件名删除记录
     *
     * @param fileName
     * @return
     * @throws
     */
    boolean deleteByFileName(String fileName);

    /**
     * 根据文件名查询记录
     *
     * @param fileName
     * @return
     * @throws
     */
    SysFileVersionDetail getByFileName(String fileName);


    /**
     * 通过文件名更新
     *
     * @param fileName
     * @param source   数据来源：sys_dict、common_advert_config
     * @return
     * @throws
     */
    Boolean updateFileVersion(String fileName, String source, Collection rows);

    /**
     * 添加文件版本
     *
     * @param fileVersionDTO
     * @return
     * @throws
     */
    Boolean insertFileVersion(FileVersionDTO fileVersionDTO);

    /**
     * @param type
     * @param value
     * @param source
     * @param rows
     * @return
     * @throws
     */
    Boolean insertFileVersion(String type, String value, String source, Collection rows);

}

