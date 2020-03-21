package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.mapper.CsTagMapper;
import com.haoqi.magic.business.model.dto.CsTagDTO;
import com.haoqi.magic.business.model.dto.CsTagParamDTO;
import com.haoqi.magic.business.model.entity.CsHitTagRelative;
import com.haoqi.magic.business.model.entity.CsTag;
import com.haoqi.magic.business.model.vo.CsTagVO;
import com.haoqi.magic.business.service.ICsHitTagRelativeService;
import com.haoqi.magic.business.service.ICsTagService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 标签管理 服务实现类
 * </p>
 *
 * @author huming
 * @since 2019-04-30
 */
@Service
public class CsTagServiceImpl extends ServiceImpl<CsTagMapper, CsTag> implements ICsTagService {

    @Autowired
    private CsTagMapper csTagMapper;

    @Autowired
    private FastDfsFileService fastDfsFileService;

    @Autowired
    private ICsHitTagRelativeService csHitTagRelativeService;

    @Override
    public Page findPage(Query query) {
        List<CsTag> tags = csTagMapper.findPage(query, query.getCondition());
        List<CsTagDTO> tagDTOList = BeanUtils.beansToList(tags, CsTagDTO.class);
        tagDTOList.stream().filter(csTagDTO -> StrUtil.isNotBlank(csTagDTO.getFilePath())).forEach(csTagDTO -> {
            csTagDTO.setFilePath(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), csTagDTO.getFilePath()));
        });
        return query.setRecords(tagDTOList);
    }

    @Override
    public Boolean insert(CsTagVO vo) {
        //1 、参数校验
        Assert.notNull(vo, "新增标签：参数不能为空");
        Assert.notBlank(vo.getTagName(), "新增标签：名称不能为空");
        Assert.notNull(vo.getType(), "新增标签：类型不能为空");
        //2根据标签名称、类型判断重名与否
        Integer num = this.countByCsTagName(null, vo.getTagName(), vo.getType());
        if (num > 0) {
            throw new RiggerException("新增标签【" + vo.getTagName() + "】已存在");
        }
        return super.insert(BeanUtils.beanCopy(vo, CsTag.class));
    }

    @Override
    public Integer countByCsTagName(Long id, String csTagName, Integer tageType) {
        if (null != id) {
            return super.selectCount(new EntityWrapper<CsTag>()
                    .ne("id", id)
                    .eq("tag_name", csTagName)
                    .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                    .eq("type", tageType));
        }
        return super.selectCount(new EntityWrapper<CsTag>()
                .eq("tag_name", csTagName)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("type", tageType));
    }

    @Override
    public Boolean updateCsTagById(CsTagVO vo) {
        Assert.notNull(vo, "更新标签：参数不能为空");
        Assert.notNull(vo.getId(), "更新标签：id不能为空");
        //根据标签名称、类型判断重名与否
        Integer num = this.countByCsTagName(vo.getId(), vo.getTagName(), vo.getType());
        if (num > 0) {
            throw new RiggerException("更新标签【" + vo.getTagName() + "】已存在");
        }
        return super.updateById(BeanUtils.beanCopy(vo, CsTag.class));
    }

    @Override
    public CsTagDTO getOneById(Long id) {
        CsTag csTag = super.selectById(id);
        CsTagDTO tagDTO = BeanUtils.beanCopy(csTag, CsTagDTO.class);
        tagDTO.setPictureURL(fastDfsFileService.getFastWebUrl());
        return tagDTO;
    }

    @Override
    public List<CsTag> getAllCsTag() {
        return super.selectList(new EntityWrapper<CsTag>().eq("is_deleted", CommonConstant.STATUS_NORMAL));
    }

    @Override
    public List<CsTag> getCsTagWithCondition(CsTagVO vo) {
        return csTagMapper.getCsTagWithCondition(vo);
    }

    @Override
    @Transactional
    public Boolean deleteCsTagByIds(List<Long> lIds) {
        if (CollectionUtil.isNotEmpty(lIds)) {
            List<CsTag> entities = new ArrayList<>(lIds.size());
            for (Long i : lIds) {
                CsTag c = new CsTag();
                c.setId(i);
                c.setIsDeleted(CommonConstant.STATUS_DEL);
                c.setGmtModified(new Date());
                entities.add(c);
                //删除车辆命中标签关系表中命中的关系
                csHitTagRelativeService.delete(new EntityWrapper<CsHitTagRelative>().eq("cs_tag_id", i));
            }
            super.updateBatchById(entities);
        }
        return Boolean.TRUE;
    }

    @Override
    @Cached(name = "csTag:csSqlStrByTagId:", key = "#id", expire = 86400)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)
    public String getCsSqlStrByTagId(Long id) {
        return csTagMapper.selectCsSqlStrByTagId(id);
    }

    @Override
    public List<CsTagParamDTO> getAllDetailTag(Integer tagType) {
        Map<String, Object> param = new HashMap<>();
        param.put("tagType", tagType);
        return csTagMapper.getAllDetailTag(param);
    }
}
