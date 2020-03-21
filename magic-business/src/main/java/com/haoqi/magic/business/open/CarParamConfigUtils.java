package com.haoqi.magic.business.open;

import com.alibaba.fastjson.JSONObject;
import com.haoqi.magic.business.enums.AirConditionTypeEnum;
import com.haoqi.magic.business.enums.GlassTypeEnum;
import com.haoqi.magic.business.enums.SeatCharTypeEnum;
import com.haoqi.magic.business.open.result.ParamtersAirResult;
import com.haoqi.magic.business.open.result.ParamtersCaoResult;
import com.haoqi.magic.business.open.result.ParamtersGlassResult;
import com.haoqi.magic.business.open.result.ParamtersMediaResult;
import com.haoqi.magic.business.open.result.ParamtersOutResult;
import com.haoqi.magic.business.open.result.ParamtersSeatResult;

import java.util.Map;

/**
 * Created by yanhao on 2019/8/15.
 */
public class CarParamConfigUtils {

    private static final String YES = "1";


    public static void getCarConfig(Map<String, Object> map, CarParamConfigResult configResult) {

        for (String key : map.keySet()) {
            if (key.equals("操控配置")) {
                ParamtersCaoResult caoResult = JSONObject.parseObject(map.get(key).toString(), ParamtersCaoResult.class);
                //ABS防抱死
                configResult.setHaveAbs(YES.equals(caoResult.getTest()) ? 1 : 0);
            } else if (key.equals("空调/冰箱")) {
                ParamtersAirResult paramtersAirResult = JSONObject.parseObject(map.get(key).toString(), ParamtersAirResult.class);
                //空调 0手动 1自动,3无
                configResult.setAirCondition(AirConditionTypeEnum.getKey(paramtersAirResult.getTest1()));

            } else if (key.equals("玻璃/后视镜")) {
                ParamtersGlassResult paramtersGlassResult = JSONObject.parseObject(map.get(key).toString(), ParamtersGlassResult.class);
                //【数据字典】车窗玻璃类型
                if (YES.equals(paramtersGlassResult.getTest()) && YES.equals(paramtersGlassResult.getTest1())) {
                    configResult.setWindowGlassCode(GlassTypeEnum.TWO.getKey());
                } else if (YES.equals(paramtersGlassResult.getTest()) || YES.equals(paramtersGlassResult.getTest1())) {
                    configResult.setWindowGlassCode(GlassTypeEnum.ZEOR.getKey());
                } else {
                    configResult.setWindowGlassCode(GlassTypeEnum.ONE.getKey());
                }
                //车外后视镜 电动1、手动0
                if (YES.equals(paramtersGlassResult.getTest2()) || YES.equals(paramtersGlassResult.getTest3())) {
                    configResult.setRearviewMirrorType(1);
                } else {
                    configResult.setRearviewMirrorType(0);
                }

            } else if (key.equals("多媒体配置")) {
                ParamtersMediaResult paramtersMediaResult = JSONObject.parseObject(map.get(key).toString(), ParamtersMediaResult.class);
                //导航（0：无，1：加装，2：原装）
                configResult.setNavigate(YES.equals(paramtersMediaResult.getTest()) ? 2 : 0);
                //音响设备 （0：cd,1:收音机，2dvd, 3:无)
                configResult.setMusicType(YES.equals(paramtersMediaResult.getTest1()) ? 2 : 3);

            } else if (key.equals("外部配置")) {

                ParamtersOutResult paramtersOutResult = JSONObject.parseObject(map.get(key).toString(), ParamtersOutResult.class);
                //【数据字典】轮毂290001  铝合金 290002   铁
                configResult.setHubCode(YES.equals(paramtersOutResult.getTest2()) ? "290001" : "290002");
                /**天窗
                 230001 电动
                 230002  手动
                 230003 双天窗
                 230004 全景天窗
                 230005 无
                 */
                if (YES.equals(paramtersOutResult.getTest1())) {
                    configResult.setSkyWindowCode("230004");
                } else if (YES.equals(paramtersOutResult.getTest())) {
                    configResult.setSkyWindowCode("230001");
                } else {
                    configResult.setSkyWindowCode("230005");
                }


            } else if (key.equals("座椅配置")) {
                ParamtersSeatResult paramtersSeatResult = JSONObject.parseObject(map.get(key).toString(), ParamtersSeatResult.class);
                /**
                 * 座椅材质
                 */
                configResult.setSeatMaterialCode(SeatCharTypeEnum.getKey(paramtersSeatResult.getTest()));
                /**
                 * 座椅调节方式 电动 手动 记忆
                 */
                if (YES.equals(paramtersSeatResult.getTest1())) {
                    configResult.setSeatAdjustTypeCode("250002");
                } else if (YES.equals(paramtersSeatResult.getTest2())) {
                    configResult.setSeatAdjustTypeCode("250003");
                } else {
                    configResult.setSeatAdjustTypeCode("250001");
                }
                /**
                 * 座椅功能
                 */
                if (YES.equals(paramtersSeatResult.getTest3()) || YES.equals(paramtersSeatResult.getTest4())) {
                    configResult.setSeatFunctionCode("260003");
                } else if (YES.equals(paramtersSeatResult.getTest5()) || YES.equals(paramtersSeatResult.getTest6())) {
                    configResult.setSeatFunctionCode("260002");
                } else if (YES.equals(paramtersSeatResult.getTest7()) || YES.equals(paramtersSeatResult.getTest8())) {
                    configResult.setSeatFunctionCode("260001");
                } else {
                    configResult.setSeatFunctionCode("260004");
                }
            }

        }

    }
}
