package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsTSRecordDTO;
import com.haoqi.magic.business.model.entity.CsTransferRecord;
import com.haoqi.magic.business.model.vo.CsTransferRecordVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * <p>
 * 调拨记录表 服务类
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
public interface ICsTransferRecordService
        extends IService<CsTransferRecord> {

    /**
     * 分页获取调拨信息
     * @param query
     * @return
     * @author huming
     * @date 2019/5/6 9:55
     */
    Page findPage(Query query);

    /**
     * 新增调拨
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/6 10:09
     */
    Boolean insert(CsTransferRecordVO vo);


    /**
     * 通过取消调拨
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/6 10:13
     */
    Boolean cancelTransfer(CsTransferRecordVO vo);

    /**
     * 调拨审核
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/7 9:52
     */
    Boolean auditTransfer(CsTransferRecordVO vo);


    /**
     * 通过ID、代理商ID获取调拨信息
     * @param id 数据ID
     * @param csCarDealerId 代理商ID
     * @return
     * @author huming
     * @date 2019/5/6 10:16
     */
    CsTransferRecord getOneById(Long id,Long csCarDealerId);


    /**
     * 通过条件获调拨数据
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/6 10:16
     */
    List<CsTransferRecord> getCsTransferRecordWithCondition(CsTransferRecordVO vo);

    /***
     * 通过车辆id获取车辆调拨历史记录
     * yanhao
     * @param
     * @return
     */
    List<CsTSRecordDTO> getCsTransferRecordByCarId(Long carId);
}
