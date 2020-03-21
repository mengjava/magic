package com.haoqi.magic.business;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.enums.AuditStatusEnum;
import com.haoqi.magic.business.enums.CarPublishStatusEnum;
import com.haoqi.magic.business.enums.MessagePushTypeEnum;
import com.haoqi.magic.business.model.dto.CarDTO;
import com.haoqi.magic.business.model.dto.CarInfoDTO;
import com.haoqi.magic.business.model.dto.OrderPushMessageDTO;
import com.haoqi.magic.business.model.entity.CsCarCheckItem;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.entity.CsUserVip;
import com.haoqi.magic.business.model.vo.CarPageVO;
import com.haoqi.magic.business.model.vo.CsCustomBuiltVO;
import com.haoqi.magic.business.service.*;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.PinyinUtil;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MagicBusinessApplication.class)
public class MagicBusinessApplicationTests {

    @Autowired
    private ICsCarCheckItemService carCheckItemService;

    @Autowired
    private ICsCarDealerService carDealerService;

    @Autowired
    private ICsCarInfoService carInfoService;

    @Autowired
    private ICsHitTagRelativeService csHitTagRelativeService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ICsUserVipService userVipService;


    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;


    @Test
    public void testRedisList() {
        for (int i = 0; i < 10; i++) {
            redisTemplate.opsForList().leftPush(StrUtil.format("{}:thirdSerialNo", prefix), RandomUtil.randomString(10));
        }
    }

    @Test
    public void testRemoveRedisList() {
        redisTemplate.opsForList().remove(StrUtil.format("{}:thirdSerialNo", prefix), 0,"6rco3l18uy");
        List<Object> list = redisTemplate.opsForList().range(StrUtil.format("{}:thirdSerialNo", prefix), 0, -1);
        System.out.println("MagicBusinessApplicationTests.testRemoveRedisList === " + list.size());
    }


    @Test
    public void contextLoads() {
        List<Long> longs = Arrays.stream(StrUtil.split("1,2,3", StrUtil.COMMA)).map(operand -> Long.parseLong(operand)).collect(Collectors.toList());
        Assert.assertNotNull(longs);
    }

    @Test
    public void testCarCheckItem() {
        List<Long> arr = Lists.newArrayList();
        arr.add(20L);
        arr.add(21L);
        List<CsCarCheckItem> checkItemDTO = carCheckItemService.findByIds(arr);
        Assert.assertNotNull(checkItemDTO);
    }

    @Test
    public void testDealer() {
        CsCarDealer csCarDealer = new CsCarDealer();
        csCarDealer.setDealerName("测试");
        csCarDealer.setCreator(0L);
        csCarDealer.setModifier(0L);
        csCarDealer.setGmtCreate(DateUtil.date());
        csCarDealer.setGmtModified(DateUtil.date());
        csCarDealer.setCarDearlerPinyin(PinyinUtil.getPinYin(csCarDealer.getDealerName()));
        csCarDealer.setCarDearlerInitial(PinyinUtil.getAllFirstLetter(csCarDealer.getDealerName()));
        csCarDealer.setStatus(AuditStatusEnum.SUBMITTED.getKey());
        csCarDealer.setAuditDetail(JSONUtil.toJsonStr(csCarDealer));
        carDealerService.insertOrUpdate(csCarDealer);
    }

    @Test
    public void testGetCarDealer() {
    }


    @Test
    public void testGetCarInfoById() {
        CarInfoDTO carInfoDTO = carInfoService.getCarInfoById(3L);
        Assert.assertNotNull(carInfoDTO.getCarFiles());
    }

    @Test
    public void testCarPage() {
        CarPageVO carPage = new CarPageVO();
        Map params = Maps.newHashMap();
        params.put("isDeleted", CommonConstant.STATUS_NORMAL);
        params.put("page", carPage.getPage());
        params.put("limit", carPage.getLimit());
        params.put("vin", carPage.getVin());
        params.put("carBrand", carPage.getCarBrand());
        params.put("carDealerId", carPage.getCarDealerId());
        params.put("plateNo", carPage.getPlateNo());
        params.put("sysCarModelName", "奥迪");
        params.put("userType", 1);
        params.put("userId", 1L);
        params.put("publishStatus", Objects.isNull(carPage.getPublishStatus()) ? CarPublishStatusEnum.PUBLISH.getKey() : carPage.getPublishStatus());
        handlerOrderByField(carPage.getPublishStatus(), params);
        Page<CarDTO> page = carInfoService.findByPage(new Query(params));
        Assert.assertNotNull(page);

    }


    @Test
    public void testUserSaveFormCongtroller() {
        String url = "http://127.0.0.1:13003/carInfo/carPage";
        CarPageVO carPage = new CarPageVO();
        RestTemplate restTemplate = new RestTemplate();
        String body = JSONUtil.toJsonStr(carPage);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        ResponseEntity response = restTemplate.postForEntity(url, entity, Result.class);
        Assert.assertTrue(response.getStatusCode().OK.value() == 200);
    }

    @Test
    public void testCustomBuiltPage() {
        String url = "http://127.0.0.1:13003/car/home/csCustomBuiltPage";
        CsCustomBuiltVO csCustomBuiltVO = new CsCustomBuiltVO();
        csCustomBuiltVO.setApplyTimeStart("2019-5-06");
//        csCustomBuiltVO.setApplyTimeEnd("2019-5-16");
        RestTemplate restTemplate = new RestTemplate();
        String body = JSONUtil.toJsonStr(csCustomBuiltVO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        ResponseEntity response = restTemplate.postForEntity(url, entity, Result.class);
        Assert.assertTrue(response.getStatusCode().OK.value() == 200);
    }

    @Test
    public void htiTagByCarId() {
        csHitTagRelativeService.htiTagByCarId(30L);
    }


    /**
     * 处理排序
     *
     * @param publishStatus
     * @param params
     */
    private void handlerOrderByField(Integer publishStatus, Map params) {
        if (Objects.isNull(publishStatus)) {
            return;
        }
        if (CarPublishStatusEnum.AUDIT_BACK.getKey().equals(publishStatus)) {
            params.put("orderByField", "gmt_modified");
        } else if (CarPublishStatusEnum.PUBLISH.getKey().equals(publishStatus)) {
            params.put("orderByField", "publish_time");
            params.put("isAsc", false);
        } else if (CarPublishStatusEnum.PURT_AWAY.getKey().equals(publishStatus)) {
            params.put("orderByField", "audit_time");
            params.put("isAsc", false);
        } else if (CarPublishStatusEnum.SOLD_OUT.getKey().equals(publishStatus)) {
            params.put("orderByField", "pull_off_time");
            params.put("isAsc", false);
        } else if (CarPublishStatusEnum.SAVE.getKey().equals(publishStatus) ||
                CarPublishStatusEnum.ALLOT.getKey().equals(publishStatus)) {
            params.put("orderByField", "gmt_modified");
            params.put("isAsc", false);
        }
    }

    @Test
    public void testDate() {
        DateTime dateTime =  DateUtil.beginOfMonth(DateUtil.parseDate("2020-01-01"));

       boolean b = LocalDateTime.now().isAfter(LocalDateTime.of(2020, 1, 8, 10, 20));
        System.out.println("DateTime == " + b);
    }

    @Test
    public void testListener(){
        buildMessagePush(1L, 2L, new BigDecimal("3000"), 1L, "test", MessagePushTypeEnum.CANCEL_BUYING_PASS.getKey());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testUserVip(){
        CsUserVip csUserVip = new CsUserVip();
        csUserVip.setSysUserId(1L);
        csUserVip.setMoney(BigDecimal.ONE);
        csUserVip.setUsername("22");
        csUserVip.setCsVipId(0L);
        csUserVip.setBeginDate(DateUtil.date());
        csUserVip.setExpiredDate(DateUtil.nextMonth());
        csUserVip.setVipNo(StrUtil.format("{}{}", RandomUtil.randomLong(5), DateUtil.currentSeconds()));
        csUserVip.setEvaluateFreeNum(1);
        csUserVip.setEmissionFreeNum(1);
        csUserVip.setCarModelFreeNum(1);
        csUserVip.setInsuranceFreeNum(1);
        csUserVip.setMaintenanceFreeNum(1);
        csUserVip.setMaintenancePrice(BigDecimal.ONE);
        csUserVip.setEmissionPrice(BigDecimal.ONE);
        csUserVip.setEvaluatePrice(BigDecimal.ONE);
        csUserVip.setCarModelPrice(BigDecimal.ONE);
        csUserVip.setInsurancePrice(BigDecimal.ONE);
        userVipService.insert(csUserVip);
    }


    private void buildMessagePush(Long sender, Long receiver, BigDecimal salPrice, Long carInfoId, String brandName, Integer messageType) {
        OrderPushMessageDTO pushMessage = new OrderPushMessageDTO();
        pushMessage.setSender(sender);
        pushMessage.setPrice(salPrice);
        pushMessage.setCarInfoId(carInfoId);
        pushMessage.setReceiver(receiver);
        pushMessage.setCarBrandName(brandName);
        pushMessage.setMessageType(messageType);
        pushMessage.setTitle(MessagePushTypeEnum.getNameByKey(messageType));
        pushMessage.setStatus(MessagePushTypeEnum.getNameByKey(messageType));
        applicationEventPublisher.publishEvent(pushMessage);
    }

}
