package com.haoqi.magic.system.service.impl;


import cn.hutool.core.lang.Assert;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoqi.magic.system.mapper.SysDictMapper;
import com.haoqi.magic.system.model.dto.DictDTO;
import com.haoqi.magic.system.model.entity.SysDict;
import com.haoqi.magic.system.model.entity.SysFileVersionDetail;
import com.haoqi.magic.system.service.ISysDictService;
import com.haoqi.magic.system.service.ISysFileVersionDetailService;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {


    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private ISysFileVersionDetailService fileVersionDetailService;
    @Autowired
    private FastDfsFileService fastDfsFileService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delDictById(Long id) {
        Assert.notNull(id, "dictId not be null");
        SysDict dict = this.getDictById(id);
        //判断是根节点 或者子节点 (删除该id,以及parentId下子节点)
        this.deleteById(id);
        //删除子节点
        SysDict _dict = new SysDict();
        _dict.setParentId(id);
        this.delete(new EntityWrapper<SysDict>(_dict)); //判断是否为根节点或者子节点
        SysFileVersionDetail detail = fileVersionDetailService.getByFileName(dict.getClassType());
        //删除父节点为0的字典
        if (dict.getParentId().equals(0L)) {
            fileVersionDetailService.deleteByFileName(dict.getClassType());
            fastDfsFileService.deleteFile(detail.getUrl());
            return Boolean.TRUE;
        }
        //非父节点为0的字典即更新
        EntityWrapper param = new EntityWrapper<SysDict>();
        param.eq("class", dict.getClassType());
        List<SysDict> dicts = this.selectList(param);
        fileVersionDetailService.updateFileVersion(dict.getClassType(), "sys_dict", dicts);
        Set<String> keys = redisTemplate.keys("dict:*");
        redisTemplate.delete(keys);
        return Boolean.TRUE;
    }

    @Override
    @Cached(name = "dict:dictById:", key = "#id", expire = 604800)
    public SysDict getDictById(Long id) {
        Assert.notNull(id, "dictId not be null");
        return sysDictMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertDict(SysDict sysDict) {
        if (this.isExist(sysDict.getKeyworld())) {
            throw new RiggerException("关键字 [" + sysDict.getKeyworld() + "] 已存在！");
        }
        this.insert(sysDict);
        //如果是根节点生成新的字典文件否则更新字典文件
        if (null != sysDict.getParentId() && sysDict.getParentId().equals(0L)) {
            fileVersionDetailService.insertFileVersion(sysDict.getClassType(), sysDict.getValueDesc(), "sys_dict", Lists.newArrayList());
            return Boolean.TRUE;
        }
        EntityWrapper param = new EntityWrapper<SysDict>();
        param.eq("class", sysDict.getClassType());
        param.eq("class_level", 1);
        List<SysDict> dicts = this.selectList(param);
        fileVersionDetailService.updateFileVersion(sysDict.getClassType(), "sys_dict", dicts);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateDictById(SysDict sysDict) {
        Assert.notNull(sysDict.getId(), "dictId not be null");
        Assert.notNull(sysDict.getClassType(), "classType not be null");
        SysDict dict = sysDictMapper.selectById(sysDict.getId());
        if (Objects.isNull(dict)) {
            throw new RiggerException("数据字典不存在！");
        }
        if (this.isExist(sysDict.getKeyworld(), sysDict.getId())) {
            throw new RiggerException("关键字 [" + sysDict.getKeyworld() + "] 已存在！");
        }
        this.updateById(sysDict);
        EntityWrapper param = new EntityWrapper<SysDict>();
        param.eq("class", sysDict.getClassType());
        param.eq("class_level", 1);
        List<SysDict> dicts = this.selectList(param);
        fileVersionDetailService.updateFileVersion(sysDict.getClassType(), "sys_dict", dicts);
        return Boolean.TRUE;
    }


    @Override
    @Cached(name = "dict:isExist:", key = "#keyword", expire = 3000)
    public Boolean isExist(String keyword) {
        Assert.notBlank(keyword, "字典关键字不能为空");
        SysDict dict = new SysDict();
        dict.setKeyworld(keyword);
        SysDict sysDict = this.selectOne(new EntityWrapper<SysDict>(dict));
        if (sysDict == null) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    @Cached(name = "dict:getDictValueDesc:", key = "#keyword", expire = 604800)
    public SysDict getDictValueDesc(String keyword) {
        SysDict dict = new SysDict();
        dict.setKeyworld(keyword);
        return this.selectOne(new EntityWrapper<SysDict>(dict));
    }

    @Override
   @Cached(name = "dict:getDictByClass:", key = "#classType", expire = 604800)
    public List<SysDict> getDictByClass(String classType) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("class", classType);
        map.put("class_level", 1);
        return this.selectByMap(map);
    }

    @Override
    @Cached(name = "dict:getSelectTree:", expire = 86400)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)
    public Map<String, Object> getSelectTree() {
        Map<String, Object> selectTree = Maps.newHashMap();
        SysDict dict = new SysDict();
        dict.setClassLevel(1);
        dict.setClassType("000000");
        List<SysDict> sysDicts = this.selectList(new EntityWrapper<>(dict));
        List<Object> list = new ArrayList<>();
        sysDicts.forEach(d -> {
            //R开头是单选 C开头是多选
            if (d.getKeyworld().startsWith("R") || d.getKeyworld().startsWith("C")) {
                DictDTO dto = new DictDTO();
                BeanUtils.copyProperties(d, dto);
                String dictKey = getDictKey(d.getKeyworld());
                //设置model
                //dto.setModel(SelectModelTypeEnum.getTypeName(dictKey));
                dto.setModel(d.getClassFlag());
                List<SysDict> dicts = getDictByClass(dictKey);
                dto.setChildren(com.haoqi.rigger.common.util.BeanUtils.beansToList(dicts, DictDTO.class));
                list.add(dto);
            }
        });
        selectTree.put("selectTree", list);
        return selectTree;
    }

    public Boolean isExist(String keyWord, Long id) {
        Assert.notBlank(keyWord, "字典关键字不能为空");
        Assert.notNull(id, "id不能为空");
        SysDict dict = new SysDict();
        dict.setKeyworld(keyWord);
        SysDict sysDict = this.selectOne(new EntityWrapper<SysDict>(dict));
        if (sysDict != null && !sysDict.getId().equals(id)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private String getDictKey(String key) {
        Assert.notBlank(key, "字典关键字不能为空");
        String newKey = key.substring(key.lastIndexOf("_") + 1);
        return newKey;
    }
}
