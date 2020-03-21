package com.haoqi.magic.system.mapper;

import com.haoqi.magic.system.model.entity.SysCarBrand;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface SysCarBrandMapper extends BaseMapper<SysCarBrand> {
    /**
     * 分页查询
     * @param query
     * @param condition
     * @return
     */
    List<SysCarBrand> selectCarBrandPage(Query query, Map condition);

    /***
     * 品牌列表
     * @return
     */
    List<SysCarBrand> selectCarBrandList();

    /***
     * 查询最大的brandId
     * @return
     */
    Integer selectMaxBrandId();
}
