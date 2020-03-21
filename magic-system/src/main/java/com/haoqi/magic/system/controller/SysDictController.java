package com.haoqi.magic.system.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haoqi.magic.system.common.utils.TreeUtil;
import com.haoqi.magic.system.model.entity.SysDict;
import com.haoqi.magic.system.model.vo.DictTree;
import com.haoqi.magic.system.model.vo.SysDictVO;
import com.haoqi.magic.system.service.ISysDictService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "数据字典相关")
public class SysDictController extends BaseController {

    @Autowired
    private ISysDictService sysDictService;


    /**
     * @return
     * @author yanhao
     * @date 2018/7/2 13:32
     * 获取数据字典树
     */
    @GetMapping(value = "/tree")
    @ApiOperation(value = "获取数据字典树")
    public Result<List<DictTree>> getTree() {
        SysDict condition = new SysDict();
        return Result.buildSuccessResult(getDictTree(sysDictService.selectList(new EntityWrapper<>(condition)), 0L));
    }

    /**
     * 功能描述:
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/4/25 14:58
     * @Description:
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "通过id删除数据字典")
    public Result<Boolean> delDictById(@PathVariable Long id) {
        return Result.buildSuccessResult(sysDictService.delDictById(id));
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "通过id获取数据字典")
    public Result<SysDict> getDictById(@PathVariable Long id) {
        return Result.buildSuccessResult(sysDictService.getDictById(id));
    }

    /**
     * @return
     * @author yanhao
     * @date 2018/7/2 14:28
     * 添加数据字典
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加数据字典")
    public Result<String> addDict(@RequestBody SysDictVO dict) {
        currentUser();
        SysDict sysDict = new SysDict();
        BeanUtil.copyProperties(dict, sysDict);
        sysDictService.insertDict(sysDict);
        return Result.buildSuccessResult("添加成功");
    }

    /**
     * 通过code码获取字典值
     *
     * @param keyword
     * @return
     */
    @GetMapping("/getDictValueDesc/{keyword}")
    public Result<String> getDictValueDesc(@PathVariable("keyword") String keyword) {
        SysDict dicts = sysDictService.getDictValueDesc(keyword);
        return Result.buildSuccessResult(dicts == null ? "" : dicts.getValueDesc(), "");
    }

    @GetMapping("/getDictKeyword/{value}")
    public Result<String> getDictKeyword(@PathVariable("value") String value) {
        SysDict dict = new SysDict();
        dict.setValueDesc(value);
        SysDict dicts = sysDictService.selectOne(new EntityWrapper<SysDict>(dict));
        return Result.buildSuccessResult(dicts == null ? "" : dicts.getKeyworld(), "");
    }

    /**
     * @return
     * @author yanhao
     * @date 2018/7/2 14:30
     * 更新数据字典
     */
    @PutMapping("update")
    @ApiOperation(value = "更新数据字典")
    public Result<String> updateDict(@RequestBody SysDictVO dict, BindingResult bindingResult) {
        SysDict sysDict = new SysDict();
        BeanUtil.copyProperties(dict, sysDict);
        sysDictService.updateDictById(sysDict);
        return Result.buildSuccessResult("更新成功");
    }

    /**
     * 功能描述:
     *
     * @auther: yanhao
     * @param: 通过class 找到下面的子节点
     * @date: 2018/8/3 11:16
     * @Description:
     */
    @GetMapping("/getDictByClass/{classType}")
    public Result<List<SysDict>> getDictByClass(@PathVariable("classType") String classType) {
        List<SysDict> sysDicts = sysDictService.getDictByClass(classType);
        return Result.buildSuccessResult(sysDicts);
    }


    private List<DictTree> getDictTree(List<SysDict> sysDicts, long root) {
        List<DictTree> list = new ArrayList<>();
        DictTree tree = null;
        for (SysDict dict : sysDicts) {
            tree = new DictTree();
            tree.setId(dict.getId());
            tree.setKeyworld(dict.getKeyworld());
            tree.setParentId(dict.getParentId().longValue());
            tree.setValueDesc(dict.getValueDesc());
            tree.setClassLevel(dict.getClassLevel());
            tree.setClassOrder(dict.getClassOrder());
            tree.setClassType(dict.getClassType());
            list.add(tree);
        }
        return TreeUtil.build(list, root);
    }


    /**
     * 功能描述:
     * 查询筛选:  000000
     * 此功能禁止乱动 (涉及到PC和app筛选项)
     * @auther: yanhao
     * @param:
     * @date: 2019/5/19 16:57
     * @Description:
     */
    @GetMapping(value = "/selectTree")
    @ApiOperation(value = "获取首页筛选数据字典树")
    public Result<Object> getSelectTree() {
        Map<String, Object> map = sysDictService.getSelectTree();
        return Result.buildSuccessResult(map);
    }

    /***
     * 通过key获取数据字典
     * @param keyword
     * @return
     */
    @GetMapping("/getDict/{keyword}")
    public Result<SysDict> getDict(@PathVariable("keyword") String keyword) {
        SysDict dicts = sysDictService.getDictValueDesc(keyword);
        return Result.buildSuccessResult(dicts);
    }
}

