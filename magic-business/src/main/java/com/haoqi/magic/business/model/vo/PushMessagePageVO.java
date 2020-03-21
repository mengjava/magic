package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import lombok.Data;

/**
 * @Author: mengyao
 * @Date: 2018/9/1 0001 09:49
 * @Description:
 */
@Data
public class PushMessagePageVO extends Page {


    /**
     * 推送人
     */
    private Integer userId;
    /**
     * 是否已读
     */
    private Integer isRead;


}
