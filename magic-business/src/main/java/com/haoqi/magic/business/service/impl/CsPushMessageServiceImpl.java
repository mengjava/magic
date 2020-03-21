package com.haoqi.magic.business.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.mapper.CsPushMessageMapper;
import com.haoqi.magic.business.model.dto.OrderPushMessageDTO;
import com.haoqi.magic.business.model.dto.PushMessageDTO;
import com.haoqi.magic.business.model.entity.CsPushMessage;
import com.haoqi.magic.business.service.ICsPushMessageService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 消息推送表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-12-10
 */
@Service
@Slf4j
public class CsPushMessageServiceImpl extends ServiceImpl<CsPushMessageMapper, CsPushMessage> implements ICsPushMessageService {

    /**
     * 极光secret
     */
    @Value("${jiguang.carSecret:1d004a23b9736b504fe7f1e2}")
    private String carSecret;
    /**
     * 极光appKey
     */
    @Value("${jiguang.carAppKey:63b2022e20d28f03a24b2426}")
    private String carAppKey;
    /**
     * 是否正式环境
     */
    @Value("${jiguang.apnsProduction:false}")
    private Boolean apnsProduction;

    @Autowired
    private CsPushMessageMapper csPushMessageMapper;

    @Override
    public Boolean pushOrderToApp(OrderPushMessageDTO orderPushMessageDTO) {
        String context = JSONUtil.toJsonStr(orderPushMessageDTO);
        return this.messagePush(orderPushMessageDTO.getAppType(), orderPushMessageDTO.getSender(), orderPushMessageDTO.getTitle(),
                context, orderPushMessageDTO.getReceiver(), orderPushMessageDTO.getMessageType(), orderPushMessageDTO.getCompId());
    }

    @Override
    public Boolean messagePush(Integer appType, Long senderId, String content, String jsonParam, Long receiver, Integer messageType, Long compId) {
        Objects.requireNonNull(appType, "app类型不能为空");
        Objects.requireNonNull(senderId, "消息发送人不能为空");
        Objects.requireNonNull(receiver, "消息接受人不能为空");
        Objects.requireNonNull(messageType, "消息类型不能为空");
        Assert.notBlank(content, "发送内容不能为空");
        PushResult result = null;
        if (appType.equals(Constants.YES)) {
            //消息推送到APP
            result = push(receiver.toString(), content, carSecret, carAppKey);
        }
        String message = result != null && result.isResultOK() ? "推送成功" : "推送失败";
        insetPushMessage(senderId, content, jsonParam, receiver, messageType, compId, Constants.YES, "推送给接收方id[" + receiver + "]的信息" + message);
        log.info("推送给接收方id：{} 的信息{}！", receiver, message);
        return true;
    }

    @Override
    public Page selectByPage(Query query) {
        List<PushMessageDTO> list = csPushMessageMapper.selectByPage(query, query.getCondition());
        return query.setRecords(list);
    }


    private void insetPushMessage(Long senderId, String content, String jsonParam, Long userId, Integer pushType, Long compId, Integer successOrError, String remark) {
        CsPushMessage message = new CsPushMessage();
        message.setCreator(senderId);
        message.setModifier(senderId);
        message.setCompId(compId);
        message.setGmtCreate(DateUtil.date());
        message.setGmtModified(DateUtil.date());
        message.setIsRead(Constants.NO);
        message.setIsDeleted(Constants.NO);
        message.setJsonContent(jsonParam);
        message.setPushContent(content);
        message.setPushType(pushType);
        message.setPushResult(successOrError);
        message.setRemark(remark);
        message.setUserId(userId);
        message.setPushTime(DateUtil.date());
        csPushMessageMapper.insert(message);

    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     *
     * @param alias
     * @param alert
     * @return apnsProduction
     */
    private static PushPayload buildPushObject_android_ios_alias_alert(String alias, String alert, Boolean apnsProduction) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setApnsProduction(apnsProduction)
                        //消息在JPush服务器的失效时间（测试使用参数）
                        .setTimeToLive(90)
                        .build())
                .build();
    }

    /**
     * 极光推送方法(采用java SDK)
     *
     * @param alias
     * @param alert
     * @param secret
     * @param appKey
     * @return
     */
    private PushResult push(String alias, String alert, String secret, String appKey) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(secret, appKey, null, clientConfig);
        PushPayload payload = buildPushObject_android_ios_alias_alert(alias, alert, apnsProduction);
        try {
            return jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return null;
        }
    }
}
