package com.haoqi.magic.business.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.common.utils.KeyValueDescUtil;
import com.haoqi.magic.business.enums.CarPublishStatusEnum;
import com.haoqi.magic.business.enums.ProcessStatusEnum;
import com.haoqi.magic.business.mapper.CsCustomServiceMapper;
import com.haoqi.magic.business.model.dto.CarDTO;
import com.haoqi.magic.business.model.dto.CsCustomServiceDTO;
import com.haoqi.magic.business.model.dto.SysDictDTO;
import com.haoqi.magic.business.model.dto.UserDTO;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.haoqi.magic.business.model.entity.CsCustomService;
import com.haoqi.magic.business.model.vo.CsCarSourceVO;
import com.haoqi.magic.business.model.vo.CsCustomServiceVO;
import com.haoqi.magic.business.service.ICsCarDealerService;
import com.haoqi.magic.business.service.ICsCarInfoService;
import com.haoqi.magic.business.service.ICsCarSourceService;
import com.haoqi.magic.business.service.ICsCustomServiceService;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 客户服务（咨询）表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-05-14
 */
@Service
public class CsCustomServiceServiceImpl extends ServiceImpl<CsCustomServiceMapper, CsCustomService> implements ICsCustomServiceService {
    @Autowired
    private CsCustomServiceMapper csCustomServiceMapper;
    @Autowired
    private SystemServiceClient systemServiceClient;
    @Autowired
    private ICsCarDealerService carDealerService;
    @Autowired
    private ICsCarInfoService csCarInfoService;

    @Autowired
    private ICsCarSourceService csCarSourceService;

    @Override
    public Page<CsCustomServiceDTO> findByPage(Query query) {
        List<CsCustomServiceDTO> list = csCustomServiceMapper.findByPage(query, query.getCondition());
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> boxCode = systemServiceClient.getDictByClass(DictClassEnum.TRANSMISSION_100000.getClassCode());
        list.forEach(m -> {
            m.setColorCode(KeyValueDescUtil.handleValueDesc(m.getColorCode(), colorCode.getData()));
            m.setGearBoxCode(KeyValueDescUtil.handleValueDesc(m.getGearBoxCode(), boxCode.getData()));
            Optional<CsCarDealer> carDealer = carDealerService.getOneById(m.getCsCarDealerId());
            Optional<CsCarDealer> buyCarDealer = carDealerService.getOneById(m.getCsBuyDealerId());

            if (null != m.getCheckUserId()) {
                Result<UserDTO> result = systemServiceClient.getUserByUserId(m.getCheckUserId());
                if (result.isSuccess() && Objects.nonNull(result.getData())) {
                    m.setCheckLoginName(StringUtils.isEmpty(result.getData().getUsername()) ? "" : result.getData().getUsername());
                }
            }
            carDealer.ifPresent(o -> {
                m.setShortName(o.getShortName());
                m.setContactName(o.getContactName());
                m.setTel(o.getTel());
                m.setFixPhone(o.getFixPhone());
            });
            buyCarDealer.ifPresent(o -> {
                m.setBuyerShortName(o.getShortName());
                m.setBuyerContactName(o.getContactName());
                m.setBuyerTel(o.getTel());
                m.setBuyerFixPhone(o.getFixPhone());
            });
        });
        return query.setRecords(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean Insert(CsCustomService entity) {
        Assert.notNull(entity.getCsBuyDealerId(), "意向经销商id：参数不能为空");
        Assert.notNull(entity.getCsCarInfoId(), "车辆基础信息表id：参数不能为空");
        CsCustomService csCustomService = this.selectOne(new EntityWrapper<CsCustomService>().eq("cs_car_info_id", entity.getCsCarInfoId()).eq("cs_buy_dealer_id", entity.getCsBuyDealerId()));
        if (Objects.isNull(csCustomService)) {
            return this.insert(entity);
        }
        if (Objects.nonNull(csCustomService) && csCustomService.getProcessStatus().equals(ProcessStatusEnum.UNPROCESSED.getKey())) {
            csCustomService.setIntentionTime(new Date());
            return this.updateById(csCustomService);
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean processCustom(CsCustomServiceVO csCustomServiceVO) {
        CsCustomService csCustomService = BeanUtils.beanCopy(csCustomServiceVO, CsCustomService.class);
        csCustomService.setProcessTime(new Date());
        csCustomService.setGmtModified(new Date());
        csCustomService.setProcessStatus(ProcessStatusEnum.PROCESSED.getKey());

        if (csCustomServiceVO.getIsPullOff().equals(CommonConstant.BUTTON)) {
            Optional<CarDTO> optional = csCarInfoService.getCarById(csCustomServiceVO.getCarId());
            CsCarSourceVO carSourceVO = new CsCarSourceVO();
            carSourceVO.setCsCarDealerId(optional.get().getCsCarDealerId());
            carSourceVO.setId(csCustomServiceVO.getCarId());
            csCarSourceService.pullOffCar(carSourceVO);
            csCustomServiceMapper.update(csCustomService, new EntityWrapper<CsCustomService>().eq("cs_car_info_id", csCustomServiceVO.getCarId()).eq("process_status", ProcessStatusEnum.UNPROCESSED.getKey()));
        } else if (CommonConstant.STATUS_EXPIRE.equals(csCustomServiceVO.getIsPullOff())) {
            Optional<CarDTO> optional = csCarInfoService.getCarById(csCustomServiceVO.getCarId());
            CsCarSourceVO carSourceVO = new CsCarSourceVO();
            carSourceVO.setCsCarDealerId(optional.get().getCsCarDealerId());
            carSourceVO.setId(csCustomServiceVO.getCarId());
            csCarSourceService.transferCar(carSourceVO);
            csCustomServiceMapper.update(csCustomService, new EntityWrapper<CsCustomService>().eq("cs_car_info_id", csCustomServiceVO.getCarId()).eq("process_status", ProcessStatusEnum.UNPROCESSED.getKey()));
        } else {
            csCustomServiceMapper.updateById(csCustomService);
        }
        return Boolean.TRUE;
    }

    @Override
    public CsCustomServiceDTO selectDTOById(Long id) {
        CsCustomService csCustomService = csCustomServiceMapper.selectById(id);
        CsCarInfo csCarInfo = csCarInfoService.selectById(csCustomService.getCsCarInfoId());
        CsCustomServiceDTO csCustomServiceDTO = new CsCustomServiceDTO();
        csCustomServiceDTO.setProcessRemark(StrUtil.emptyToNull(csCustomService.getProcessRemark()));
        Integer isPullOff = CommonConstant.STATUS_NORMAL;
        if (csCarInfo.getPublishStatus().equals(CarPublishStatusEnum.SOLD_OUT.getKey())) {
            isPullOff = CommonConstant.BUTTON;
        } else if (csCarInfo.getPublishStatus().equals(CarPublishStatusEnum.ALLOT.getKey())) {
            isPullOff = CommonConstant.STATUS_EXPIRE;
        }
        csCustomServiceDTO.setIsPullOff(isPullOff);
        return csCustomServiceDTO;
    }

    @Override
    public Optional<CsCustomServiceDTO> getLastProcessUserName(Long csCarInfoId) {
        return Optional.ofNullable(csCustomServiceMapper.getLastProcessUserName(csCarInfoId));
    }
}
