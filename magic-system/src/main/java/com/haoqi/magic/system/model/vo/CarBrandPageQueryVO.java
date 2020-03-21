package com.haoqi.magic.system.model.vo;

import com.haoqi.rigger.core.page.Page;
import lombok.Data;

/**
 * Created by yanhao on 2019/4/25.
 */
@Data
public class CarBrandPageQueryVO extends Page {
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 品牌首字母
     */
    private String brandInitial;
}
