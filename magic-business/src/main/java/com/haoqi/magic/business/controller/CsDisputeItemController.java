package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.model.dto.CsDisputeItemDTO;
import com.haoqi.magic.business.model.dto.CsItemBaseDTO;
import com.haoqi.magic.business.model.entity.CsDisputeItem;
import com.haoqi.magic.business.model.vo.CsDisputeItemPageVO;
import com.haoqi.magic.business.model.vo.CsDisputeItemVO;
import com.haoqi.magic.business.service.ICsDisputeItemService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.haoqi.rigger.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 争议项管理 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
@RestController
@RequestMapping("/csDisputeItem")
@Api(tags = "PC争议项管理")
public class CsDisputeItemController extends BaseController {

    @Autowired
    private ICsDisputeItemService csDisputeItemService;


    @GetMapping("getAllType")
    @ApiOperation(value = "获取所有的争议项类别")
    public Result<List<CsItemBaseDTO>> getAllItemType() {
        return Result.buildSuccessResult(csDisputeItemService.getAllItemType());
    }

    @PostMapping("/page")
    @ApiOperation(value = "争议项管理分页查询")
    public Result<Page> page(@RequestBody CsDisputeItemPageVO vo) {
        Map<String, Object> params = Maps.newHashMap();
        BeanUtil.beanToMap(vo, params, false, true);
        params.put("orderByField", "gmt_modified");
        params.put("isAsc", false);
        Page page = csDisputeItemService.selectPageByParam(new Query(params));
        return Result.buildSuccessResult(page);
    }

    @PostMapping("/add")
    @ApiOperation(value = "争议项管理添加")
    public Result<Page> add(@RequestBody CsDisputeItemVO vo) {
        validatorHandler.validator(vo);
        CsDisputeItem csDisputeItem = BeanUtils.beanCopy(vo, CsDisputeItem.class);
        csDisputeItemService.addOrUpdate(csDisputeItem);
        return Result.buildSuccessResult("添加成功");
    }

    @PutMapping("/edit")
    @ApiOperation(value = "争议项管理添加")
    public Result<Page> edit(@RequestBody CsDisputeItemVO vo) {
        Assert.notNull(vo.getId(), "id not be null");
        CsDisputeItem csDisputeItem = BeanUtils.beanCopy(vo, CsDisputeItem.class);
        csDisputeItemService.addOrUpdate(csDisputeItem);
        return Result.buildSuccessResult("编辑成功");
    }

    @ApiOperation(value = "启用(或禁用)应用")
    @PostMapping("/updateStatus")
    public Result<String> updateStatus(@RequestParam("id") Long id, @RequestParam("isEnabled") Integer isEnabled) {
        CsDisputeItem item = new CsDisputeItem();
        item.setId(id);
        item.setIsDeleted(isEnabled);
        csDisputeItemService.addOrUpdate(item);
        return Result.buildSuccessResult("启用(或禁用)成功");
    }


    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "删除")
    public Result<String> delete(@PathVariable("ids") String ids) {
        String[] deIds = ids.split(",");
        for (int i = 0; i < deIds.length; i++) {
            csDisputeItemService.deleteByIds(Long.valueOf(deIds[i]));
        }
        return Result.buildSuccessResult("操作成功");
    }


    @PostMapping("/list")
    @ApiOperation(value = "APP争议项管理分页查询")
    public Result<Page> list(@RequestBody CsDisputeItemPageVO vo) {
        Map<String, Object> params = Maps.newHashMap();
        BeanUtil.beanToMap(vo, params, false, true);
        Page page = csDisputeItemService.selectPageByParam(new Query(params));
        return Result.buildSuccessResult(page);
    }


    @PostMapping("/parentList")
    @ApiOperation(value = "父级争议项: 不传参")
    public Result parentList(@RequestBody CsDisputeItemPageVO vo) {
        Map<String, Object> params = Maps.newHashMap();
        vo.setParentId(Constants.ZEOR);
        BeanUtil.beanToMap(vo, params, false, true);
        List<CsDisputeItemDTO> list = csDisputeItemService.selectListByParam(params);
        return Result.buildSuccessResult(list);
    }

    @PostMapping("/childList")
    @ApiOperation(value = "子项争议项: parentId 必须传 type 必传")
    public Result childList(@RequestBody CsDisputeItemPageVO vo) {
        Map<String, Object> params = Maps.newHashMap();
        BeanUtil.beanToMap(vo, params, false, true);
        List<CsDisputeItemDTO> list = csDisputeItemService.selectListByParam(params);
        return Result.buildSuccessResult(list);
    }
}

