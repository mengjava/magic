package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsItemBaseDTO;
import com.haoqi.magic.business.model.entity.CsOrderFile;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.vo.OrderFileVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-12-11
 */
public interface ICsOrderFileService extends IService<CsOrderFile> {

    /**
     * 获取文件类别
     *
     * @return
     */
    List<CsItemBaseDTO> getAllType();

    /**
     * 分页获取附件信息
     *
     * @param query
     * @return
     */
    Page selectOrderFilePage(Query query);

    /**
     * 查询附件列表
     *
     * @param map
     * @return
     */
    List<CsOrderFile> selectOrderFile(Map map);

    /**
     * 保存打款附件
     *
     * @param vo
     * @return
     */
    Boolean saveFile(OrderFileVO vo);
}
