package com.haoqi.magic.business.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.haoqi.magic.business.enums.DisputeProcessTypeEnum;

import java.io.IOException;

/**
 * 不属实1，赔偿2，买家违约3、卖家违约4、协商平退5
 *
 * @author twg
 * @since 2019/12/4
 */
public class DisputeProcessTypeSerializer extends JsonSerializer<Integer> {


    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DisputeProcessTypeEnum.getNameByKey(value));
    }
}
