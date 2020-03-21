package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.haoqi.magic.business.enums.OrderFileTypeEnum;
import com.haoqi.magic.business.model.dto.CsItemBaseDTO;
import com.haoqi.magic.business.model.dto.CsOrderFileDTO;
import com.haoqi.magic.business.model.entity.CsCarOrder;
import com.haoqi.magic.business.model.entity.CsOrderFile;
import com.haoqi.magic.business.mapper.CsOrderFileMapper;
import com.haoqi.magic.business.model.vo.BaseFileVO;
import com.haoqi.magic.business.model.vo.OrderFileVO;
import com.haoqi.magic.business.service.ICsCarOrderService;
import com.haoqi.magic.business.service.ICsOrderFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-12-11
 */
@Service
public class CsOrderFileServiceImpl extends ServiceImpl<CsOrderFileMapper, CsOrderFile> implements ICsOrderFileService {

    @Autowired
    private CsOrderFileMapper csOrderFileMapper;

    @Autowired
    private ICsCarOrderService orderService;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Override
    public List<CsItemBaseDTO> getAllType() {
        List<CsItemBaseDTO> list = new ArrayList<>();
        OrderFileTypeEnum[] values = OrderFileTypeEnum.values();
        for (OrderFileTypeEnum typeEnum : values) {
            CsItemBaseDTO dto = new CsItemBaseDTO();
            dto.setKey(typeEnum.getKey());
            dto.setValue(typeEnum.getName());
            list.add(dto);
        }
        return list;
    }

    @Override
    public Page selectOrderFilePage(Query query) {
        List<CsOrderFileDTO> list = this.baseMapper.selectOrderFilePage(query, query.getCondition());
        list.forEach(
                file -> {
                    file.setPictureURL(fastDfsFileService.getFastWebUrl());
                }
        );
        return query.setRecords(list);
    }

    @Override
    public List<CsOrderFile> selectOrderFile(Map map) {
        return this.baseMapper.selectOrderFile(map);
    }

    @Override
    public Boolean saveFile(OrderFileVO vo) {
        CsCarOrder order = orderService.getById(vo.getCsCarOrderId());
        //打款附件
        List<BaseFileVO> files = vo.getFiles();
        files.forEach(
                file -> {
                    CsOrderFile csOrderFile = new CsOrderFile();
                    csOrderFile.setCsCarOrderId(vo.getCsCarOrderId());
                    csOrderFile.setType(vo.getType());
                    csOrderFile.setFileName(file.getFileName());
                    csOrderFile.setFilePath(file.getFilePath());
                    csOrderFileMapper.insert(csOrderFile);
                }
        );
        return Boolean.TRUE;
    }
}
