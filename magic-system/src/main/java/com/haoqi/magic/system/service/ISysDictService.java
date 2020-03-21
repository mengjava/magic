package com.haoqi.magic.system.service;


import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.entity.SysDict;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface ISysDictService extends IService<SysDict> {

    /***
     * 删除数据字典根据id
     * @param id
     * @return
     */
    Boolean delDictById(Long id);

    /**
     * 通过id获取数据字典
     *
     * @param id
     * @return
     */
    SysDict getDictById(Long id);

    /***
     * 添加数据字典
     * @param sysDict
     */
    Boolean insertDict(SysDict sysDict);

    /**
     * 根据id更新数据字典
     *
     * @param sysDict
     */
    Boolean updateDictById(SysDict sysDict);

    /**
     * 通过字典关键字，判断字典信息是否存在
     *
     * @param keyWorld
     * @return
     */
    Boolean isExist(String keyWorld);

    /***
     * 通过keyword获取数据字典信息
     * @param keyword
     * @return
     */
    SysDict getDictValueDesc(String keyword);

    /**
     * 通过class获取下面的子节点List
     * @param classType
     * @return
     */
    List<SysDict> getDictByClass(String classType);

    /**
     * 获取首页数据字典树
     * @return
     */
    Map<String,Object> getSelectTree();
}
