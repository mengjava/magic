package com.haoqi.magic.business.open;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanhao on 2019/8/9.
 */
@Data
public class IdentifyModelByVinResult implements Serializable {

    private String status;

    private List<ModelInfo> modelInfo;
}
