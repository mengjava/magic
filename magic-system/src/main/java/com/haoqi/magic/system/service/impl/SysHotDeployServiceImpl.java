package com.haoqi.magic.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.system.common.enums.AppTypeEnum;
import com.haoqi.magic.system.mapper.SysHotDeployMapper;
import com.haoqi.magic.system.model.dto.SysHotDeployDTO;
import com.haoqi.magic.system.model.entity.SysHotDeploy;
import com.haoqi.magic.system.model.vo.SysHotDeployVO;
import com.haoqi.magic.system.service.ISysHotDeployService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.common.util.PinyinUtil;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * APP热更新管理 服务实现类
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
@Service
public class SysHotDeployServiceImpl
        extends ServiceImpl<SysHotDeployMapper, SysHotDeploy>
        implements ISysHotDeployService {

    @Autowired
    private SysHotDeployMapper sysHotDeployMapper;

    @Autowired
    private FastDfsFileService fastDfsFileService;

    @Override
    public Page findPage(Query query) {
        List<SysHotDeploy> list = sysHotDeployMapper.findHotDeployPage(query, query.getCondition());
        return query.setRecords(list);
    }

    @Override
    @Transactional
    public Boolean insert(SysHotDeployVO vo) {
        SysHotDeploy entity = new SysHotDeploy();
        BeanUtil.copyProperties(vo, entity);

        //1、根据appName生成appKey
        String appKey = PinyinUtil.getPinYin(vo.getName());
        entity.setAppKey(appKey);

        //2、md5 生成使用 appkey+apptype+version
        String md5 = SecureUtil.md5(appKey + entity.getType() + entity.getVersion());
        entity.setMd5(md5);

        entity.setIsDeleted(CommonConstant.STATUS_NORMAL);
        if (AppTypeEnum.ANDROID.getKey().equals(vo.getType()) || AppTypeEnum.ANDROID_PAD.getKey().equals(vo.getType())) {
            entity.setUrl("groupName=" + entity.getFileGroup() + "&fileId=" + entity.getFilePath());
        }

        return super.insert(entity);
    }

    @Override
    public Boolean deleteHotDeploy(Long id) {
        Assert.notNull(id, "id不能为空");
        return super.baseMapper.deleteById(id) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean updateAppById(SysHotDeployVO vo) {
        Assert.notNull(vo, "更新热发布信息：参数不能为空");
        Assert.notNull(vo.getId(), "更新热发布信息：id不能为空");
        SysHotDeploy old = this.selectById(vo.getId());
        if (null == old) {
            return Boolean.TRUE;
        }
        old.setIsDeleted(vo.getIsDeleted());
        return this.updateById(old);
    }

    @Override
    public SysHotDeployVO getHotDeploy(Long id) {
        Assert.notNull(id, "通过ID获取热发布信息：id不能为空");
        SysHotDeployVO vo = new SysHotDeployVO();
        SysHotDeploy old = this.selectById(id);
        BeanUtil.copyProperties(old, vo);
        return vo;
    }

    @Override
    public SysHotDeployDTO checkAppVersion(String type) {
        SysHotDeploy deploy = new SysHotDeploy();
        deploy.setType(type);
        deploy.setIsDeleted(CommonConstant.STATUS_NORMAL);
        List<SysHotDeploy> list = sysHotDeployMapper.selectList(new EntityWrapper<>(deploy).orderBy("gmt_modified", false));
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        Optional<SysHotDeploy> optional = list.stream().findFirst();
        SysHotDeployDTO hotDeployDTO = BeanUtils.beanCopy(optional.get(), SysHotDeployDTO.class);
        hotDeployDTO.setUrl(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), hotDeployDTO.getFilePath()));
        return hotDeployDTO;
    }

    @Override
    public List<SysHotDeployDTO> getAllAppType() {
        return sysHotDeployMapper.getAllAppType();
    }
}
