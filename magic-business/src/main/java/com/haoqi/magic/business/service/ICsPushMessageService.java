package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.OrderPushMessageDTO;
import com.haoqi.magic.business.model.entity.CsPushMessage;
import com.haoqi.rigger.mybatis.Query;

/**
 * <p>
 * 消息推送表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-12-10
 */
public interface ICsPushMessageService extends IService<CsPushMessage> {


    /**
     * 推送订单消息给app
     *
     * @param orderPushMessageDTO
     * @return
     */
    Boolean pushOrderToApp(OrderPushMessageDTO orderPushMessageDTO);

    /**
     * @param appType
     * @param senderId
     * @param content
     * @param jsonParam
     * @param receiver
     * @param messageType
     * @param compId
     * @return
     */
    Boolean messagePush(Integer appType, Long senderId, String content, String jsonParam, Long receiver, Integer messageType, Long compId);

    /**
     * 分页查询消息
     *
     * @param query
     * @return
     */
    Page selectByPage(Query query);
}
