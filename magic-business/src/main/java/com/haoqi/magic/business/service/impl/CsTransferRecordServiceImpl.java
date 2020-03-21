package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.common.utils.KeyValueDescUtil;
import com.haoqi.magic.business.enums.CarPublishStatusEnum;
import com.haoqi.magic.business.enums.CarTransferRecordStatusEnum;
import com.haoqi.magic.business.mapper.CsTransferRecordMapper;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.CsCarCheck;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.haoqi.magic.business.model.entity.CsTransferRecord;
import com.haoqi.magic.business.model.vo.CsTransferRecordVO;
import com.haoqi.magic.business.service.ICsCarCheckService;
import com.haoqi.magic.business.service.ICsCarDealerService;
import com.haoqi.magic.business.service.ICsCarInfoService;
import com.haoqi.magic.business.service.ICsTransferRecordService;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 调拨记录表 服务实现类
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@Service
public class CsTransferRecordServiceImpl
        extends ServiceImpl<CsTransferRecordMapper, CsTransferRecord>
        implements ICsTransferRecordService {

    @Autowired
    private CsTransferRecordMapper csTransferRecordMapper;

    //车辆信息服务
    @Autowired
    private ICsCarInfoService csCarInfoService;

    @Autowired
    private ICsCarCheckService csCarCheckService;

    //经销商服务
    @Autowired
    private ICsCarDealerService csCarDealerService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    private static ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public Page<List<CsTransferRecordDTO>> findPage(Query query) {
        List<CsTransferRecordDTO> transferRecordDTOS = csTransferRecordMapper.findPage(query, query.getCondition());
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> result = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        Result<List<SysDictDTO>> resultBox = systemServiceClient.getDictByClass(DictClassEnum.TRANSMISSION_100000.getClassCode());
        transferRecordDTOS.forEach(csTransferRecordDTO -> {
            csTransferRecordDTO.setColorCodeName(KeyValueDescUtil.handleValueDesc(csTransferRecordDTO.getColorCode(), colorCode.getData()));
            csTransferRecordDTO.setGearBoxCodeName(KeyValueDescUtil.handleValueDesc(csTransferRecordDTO.getGearBoxCode(), resultBox.getData()));
            csTransferRecordDTO.setEmissionStandardCodeName(KeyValueDescUtil.handleValueDesc(csTransferRecordDTO.getEmissionStandardCode(), result.getData()));
            csTransferRecordDTO.setPublishStatusName(CarPublishStatusEnum.getValue(csTransferRecordDTO.getPublishStatus()));
        });
        return query.setRecords(transferRecordDTOS);
    }

    @Override
    @Transactional
    public Boolean insert(CsTransferRecordVO vo) {
        //1、参数校验
        Assert.notNull(vo, "调拨：参数不能为空");
        Assert.notNull(vo.getCsCarDearlerIdFrom(), "调拨：调拨经销商Id(from)不能为空");
        Assert.notNull(vo.getCsCarDearlerIdTo(), "调拨：调拨经销商Id(To)不能为空");
        Assert.notNull(vo.getCsCarInfoId(), "调拨：车辆ID不能为空");
        //1、1 逻辑参数校验
        CsCarInfo car = csCarInfoService.getOneById(vo.getCsCarInfoId());
        Assert.notNull(car, "调拨:对应的车辆信息不存在");
        if (!vo.getCsCarDearlerIdFrom().equals(car.getCsCarDealerId())) {
            throw new RiggerException("调拨：车辆所属经销商ID不匹配");
        }
        if (vo.getCsCarDearlerIdTo().equals(car.getCsCarDealerId())) {
            throw new RiggerException("调拨：不能对自己的车辆进行调拨");
        }
        if (!CarPublishStatusEnum.PURT_AWAY.getKey().equals(car.getPublishStatus())) {
            throw new RiggerException("调拨:只能对上架的车辆进行调拨");
        }
        //1、2是否已经对该车进行调拨
        CsTransferRecordVO param = BeanUtils.beanCopy(vo, CsTransferRecordVO.class);
        param.setTransferStatus(CarTransferRecordStatusEnum.APPLY.getKey());
        List<CsTransferRecord> tfList = this.getCsTransferRecordWithCondition(param);
        if (CollectionUtil.isNotEmpty(tfList)) {
            throw new RiggerException("调拨：该车辆已经申请调拨，请等待车商审核");
        }

        //2、修改车辆状态
        CsCarInfo e = new CsCarInfo();
        e.setId(car.getId());
        e.setTransferStatus(CarTransferRecordStatusEnum.APPLY.getKey());
        csCarInfoService.updateById(e);

        //3、获取车辆来源经销商信息
        CsTransferRecord entity = BeanUtils.beanCopy(vo, CsTransferRecord.class);
        entity.setTransferStatus(CarTransferRecordStatusEnum.APPLY.getKey());
        entity.setTransferUserId(vo.getCsCarDearlerIdTo());
        entity.setTransferTime(new Date());
        Optional<CsCarDealer> optional = csCarDealerService.getOneById(vo.getCsCarDearlerIdFrom());
        optional.ifPresent(csCarDealer -> {
            entity.setCsCarDearlerNameFrom(csCarDealer.getDealerName());
        });
        return super.insert(entity);
    }

    @Override
    public Boolean cancelTransfer(CsTransferRecordVO vo) {
        //1、参数校验
        Assert.notNull(vo, "取消调拨：参数不能为空");
        Assert.notNull(vo.getId(), "取消调拨:调拨ID不能为空");
        Assert.notNull(vo.getCsCarDearlerIdTo(), "取消调拨:调拨经销商ID(to)不能为空");
        CsTransferRecord record = this.getOneById(vo.getId(), vo.getCsCarDearlerIdTo());
        Assert.notNull(record, "取消调拨：调拨数据不存在");

        if (!CarTransferRecordStatusEnum.APPLY.getKey().equals(record.getTransferStatus())) {
            throw new RiggerException("取消调拨:调拨状态已变更，请刷新后重新操作");
        }

        //2、修改车辆状态
        CsCarInfo car = new CsCarInfo();
        car.setId(record.getCsCarInfoId());
        car.setTransferStatus(CarTransferRecordStatusEnum.CANCEL_ALLOT.getKey());
        csCarInfoService.updateById(car);

        //3、更新调拨状态
        CsTransferRecord entity = new CsTransferRecord();
        entity.setId(vo.getId());
        entity.setTransferStatus(CarTransferRecordStatusEnum.CANCEL_ALLOT.getKey());
        entity.setTransferCancelTime(new Date());
        entity.setModifier(vo.getCsCarDearlerIdFrom());
        return super.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean auditTransfer(CsTransferRecordVO vo) {
        //1、参数校验
        Assert.notNull(vo, "审核调拨：参数不能为空");
        Assert.notNull(vo.getId(), "审核调拨:调拨ID不能为空");
        Assert.notNull(vo.getTransferStatus(), "审核调拨:调拨状态不能为空");
        Assert.notNull(vo.getCsCarDearlerIdFrom(), "审核调拨:调拨经销商ID(Form)不能为空");

        try {
            reentrantLock.lock();
            CsTransferRecord record = this.getOneById(vo.getId(), vo.getCsCarDearlerIdFrom());
            Assert.notNull(record, "审核调拨：调拨数据不存在");
            if (!CarTransferRecordStatusEnum.APPLY.getKey().equals(record.getTransferStatus())) {
                throw new RiggerException("审核调拨:调拨状态已变更，请刷新后重新操作");
            }
            if (!CarTransferRecordStatusEnum.APPLY_AGREE.getKey().equals(vo.getTransferStatus())
                    && !CarTransferRecordStatusEnum.APPLY_REFUSE.getKey().equals(vo.getTransferStatus())) {
                throw new RiggerException("审核调拨:调拨状态参数不正确");
            }
            if (!record.getCsCarDearlerIdFrom().equals(vo.getCsCarDearlerIdFrom())) {
                throw new RiggerException("审核调拨:请勿操作其他车商数据");
            }
            //2、更新调拨状态
            CsTransferRecord entity = new CsTransferRecord();
            entity.setId(vo.getId());
            entity.setTransferStatus(vo.getTransferStatus());
            entity.setTransferCancelTime(new Date());
            entity.setTransferAuditUserId(vo.getCsCarDearlerIdFrom());
            entity.setTransferAuditTime(new Date());
            entity.setModifier(vo.getCsCarDearlerIdFrom());
            entity.setGmtModified(new Date());
            entity.setRemark(vo.getRemark());
            super.updateById(entity);

            //3、该车辆、该经销商、申请状态的数据全部设置为取消状态
            if (CarTransferRecordStatusEnum.APPLY_AGREE.getKey().equals(vo.getTransferStatus())) {
                CsTransferRecord param = new CsTransferRecord();
                param.setTransferStatus(CarTransferRecordStatusEnum.APPLY.getKey());
                param.setModifier(vo.getCsCarDearlerIdFrom());
                param.setCsCarInfoId(record.getCsCarInfoId());
                param.setCsCarDearlerIdFrom(vo.getCsCarDearlerIdFrom());
                param.setRemark("该车辆已被其他商家调拨过");
                csTransferRecordMapper.updateOtherTransferTOCancel(param);

                //4、清空监测信息
                //fastdfs 上的文件是否清除？
                csCarCheckService.delete(new EntityWrapper<CsCarCheck>()
                        .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                        .eq("cs_car_info_id", record.getCsCarInfoId()));

                //5、更新车辆信息（修改车辆的经销商、清空车辆录入人、录入时间、录入人名称）
                CarDTO car = new CarDTO();
                Optional<CsCarDealer> optional = csCarDealerService.getOneById(record.getCsCarDearlerIdTo());
                Long sysAreaId = optional.get().getSysAreaId();
                Result<SysProvinceAndCityDTO> areaById = systemServiceClient.getAreaById(sysAreaId);
                if (areaById.isSuccess()) {
                    car.setLocate(areaById.getData().getProvinceName() +
                            (StrUtil.isBlank(areaById.getData().getCityName()) ? "" :
                                                                (StrUtil.DASHED + areaById.getData().getCityName())));
                }
                car.setId(record.getCsCarInfoId());
                car.setCsCarDealerId(record.getCsCarDearlerIdTo());
                car.setPublishStatus(CarPublishStatusEnum.ALLOT.getKey());
                car.setTransferStatus(CarTransferRecordStatusEnum.APPLY_AGREE.getKey());
                csCarInfoService.changeCarDealer(car);
            } else {
                CsCarInfo car = new CsCarInfo();
                car.setId(record.getCsCarInfoId());
                car.setTransferStatus(CarTransferRecordStatusEnum.APPLY_REFUSE.getKey());
                csCarInfoService.updateById(car);
            }
        } finally {
            reentrantLock.unlock();
        }
        return Boolean.TRUE;
    }

    @Override
    public CsTransferRecord getOneById(Long id, Long csCarDealerId) {
        Assert.notNull(id, "获取调拨信息：调拨ID不能为空");
        Assert.notNull(id, "获取调拨信息：经销商ID不能为空");
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("csCarDealerId", csCarDealerId);
        return csTransferRecordMapper.getOneById(param);
    }

    @Override
    public List<CsTransferRecord> getCsTransferRecordWithCondition(CsTransferRecordVO vo) {
        return csTransferRecordMapper.getCsTransferRecordWithCondition(vo);
    }

    @Override
    public List<CsTSRecordDTO> getCsTransferRecordByCarId(Long carId) {
        List<CsTransferRecord> transferRecords =
                csTransferRecordMapper.selectList(new EntityWrapper<CsTransferRecord>().eq("cs_car_info_id", carId)
                        .eq("transfer_status", CarTransferRecordStatusEnum.APPLY_AGREE.getKey()));
        return BeanUtils.beansToList(transferRecords, CsTSRecordDTO.class);
    }


}
