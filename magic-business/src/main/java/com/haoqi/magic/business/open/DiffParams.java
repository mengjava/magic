package com.haoqi.magic.business.open;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by yanhao on 2019/8/14.
 */
@Data
public class DiffParams {

    private List<Object> various_group;

    private Map<String, Object> standard_group;

    private List<String> sort;
}
