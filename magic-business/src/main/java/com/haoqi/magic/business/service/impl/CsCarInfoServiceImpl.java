package com.haoqi.magic.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.common.utils.KeyValueDescUtil;
import com.haoqi.magic.business.enums.AuditStatusEnum;
import com.haoqi.magic.business.enums.CarPublishStatusEnum;
import com.haoqi.magic.business.enums.CarPullOffTypeEnum;
import com.haoqi.magic.business.mapper.CsCarInfoMapper;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.*;
import com.haoqi.magic.business.service.*;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.magic.common.utils.UrlUtils;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.mybatis.provider.OrderNumberProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 车辆信息表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
@Slf4j
@Service
public class CsCarInfoServiceImpl extends ServiceImpl<CsCarInfoMapper, CsCarInfo> implements ICsCarInfoService {

    @Autowired
    private CsCarInfoMapper carInfoMapper;
    @Autowired
    private ICsCarConfigService carConfigService;

    @Autowired
    private ICsCarFileService carFileService;

    @Autowired
    private ICsCarProcedureService carProcedureService;

    @Autowired
    private ICsCarCheckService carCheckService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Autowired
    private ICsHitTagRelativeService csHitTagRelativeService;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Autowired
    private OrderNumberProvider orderNumberProvider;

    @Autowired
    private ICsCarDealerService carDealerService;

    @Autowired
    private ICsCarAuditService carAuditService;

    @Autowired
    private ICsCustomServiceService csCustomService;

    @Autowired
    private ICsCarDealerService csCarDealerService;

    /**
     * 新车指导价 省约比例
     */
    private static final BigDecimal divide = new BigDecimal(11.7);
    /**
     * 左前45°照片同步到车辆信息表
     */
    private static final String LEFT_CAR_PIC = "Y_150001";

    @Override
    public Optional<CarDTO> getCarById(Long id) {
        Assert.notNull(id, "车辆id不能为空");
        CsCarInfo carInfo = this.selectById(id);
        if (Objects.isNull(carInfo)) {
            throw new RiggerException("查无此车辆信息");
        }
        CarDTO carDTO = BeanUtils.beanCopy(carInfo, CarDTO.class);
        carDTO.setCarDealer(setCarDealerName(carDTO.getCsCarDealerId()));
        //carDTO.setPlateNoName(getDictValue(carDTO.getPlateNo()));
        carDTO.setPlateNoName(carDTO.getPlateNo());
        carDTO.setEmissionStandardCodeName(getDictValue(carDTO.getEmissionStandardCode()));
        carDTO.setGearBoxCodeName(getDictValue(carDTO.getGearBoxCode()));
        carDTO.setCarTypeCodeName(getDictValue(carDTO.getCarTypeCode()));
        carDTO.setColorCodeName(getDictValue(carDTO.getColorCode()));
        carDTO.setFuelTypeCodeName(getDictValue(carDTO.getFuelTypeCode()));
        carDTO.setDriveMethodCodeName(getDictValue(carDTO.getDriveMethodCode()));
        return Optional.of(carDTO);
    }

    @Override
    public Page<CarDTO> findByPage(Query query) {
        Map<String, Object> param = query.getCondition();
        Long userId = (Long) param.get("userId");
        Integer userType = (Integer) param.get("userType");
        Assert.notNull(userId, "当前用户id不能为空");
        Assert.notNull(userType, "当前用户类型不能为空");
        query.setCondition(setInspectorQueryParam(param));
        Result<List<SysDictDTO>> standardCode = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> boxCode = systemServiceClient.getDictByClass(DictClassEnum.TRANSMISSION_100000.getClassCode());
        Result<List<SysDictDTO>> carTypeCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_TYPE_110000.getClassCode());
        Result<List<SysDictDTO>> driveTypeCode = systemServiceClient.getDictByClass(DictClassEnum.DRIVE_WAY_120000.getClassCode());
        Result<List<SysDictDTO>> fuelTypeCode = systemServiceClient.getDictByClass(DictClassEnum.FUEL_TYPE_130000.getClassCode());
        List<CarDTO> carDTOList = carInfoMapper.findByPage(query, query.getCondition());
        carDTOList.forEach(carDTO -> {
            carDTO.setPictureURL(fastDfsFileService.getFastWebUrl());
            carDTO.setCarDealer(setCarDealerName(carDTO.getCsCarDealerId()));
            carDTO.setEmissionStandardCode(KeyValueDescUtil.handleValueDesc(carDTO.getEmissionStandardCode(), standardCode.getData()));
            carDTO.setGearBoxCode(KeyValueDescUtil.handleValueDesc(carDTO.getGearBoxCode(), boxCode.getData()));
            carDTO.setColorCode(KeyValueDescUtil.handleValueDesc(carDTO.getColorCode(), colorCode.getData()));
            carDTO.setCarTypeCode(KeyValueDescUtil.handleValueDesc(carDTO.getCarTypeCode(), carTypeCode.getData()));
            carDTO.setDriveMethodCode(KeyValueDescUtil.handleValueDesc(carDTO.getDriveMethodCode(), driveTypeCode.getData()));
            carDTO.setFuelTypeCode(KeyValueDescUtil.handleValueDesc(carDTO.getFuelTypeCode(), fuelTypeCode.getData()));
            carDTO.setPlateNoName(carDTO.getPlateNo());
            carDTO.setTravelDistance(carDTO.getInstrumentShowDistance());
            Optional<CarAuditDTO> carAuditDTO = carAuditService.getLastCarAuditByCarId(carDTO.getId());
            if (carAuditDTO.isPresent()) {
                carDTO.setAuditLoginName(StringUtils.isEmpty(carAuditDTO.get().getAuditUserName()) ? carAuditDTO.get().getAuditLoginName() : carAuditDTO.get().getAuditUserName());
                carDTO.setAuditUserId(carAuditDTO.get().getAuditUserId());
            }
            if (Objects.nonNull(carDTO.getCheckUserId())) {
                Result<UserDTO> userDTO = systemServiceClient.getUserByUserId(carDTO.getCheckUserId());
                if (userDTO.isSuccess() && Objects.nonNull(userDTO.getData())) {
                    carDTO.setCheckLoginName(StringUtils.isEmpty(userDTO.getData().getUsername()) ? "" : userDTO.getData().getUsername());
                }
            }

        });
        return query.setRecords(carDTOList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CarDTO insertOrUpdateCarInfo(CarDTO carDTO) {
        Assert.notNull(carDTO.getCheckUserId(), "检测员信息不能为空");
        saveOrUpdateCarInfo(carDTO);
        saveOrUpdateCarConfig(carDTO.getId(), carDTO.getCarConfig());
        saveOrUpdateCarProcedure(carDTO.getId(), carDTO.getCarProcedure());
        carDTO.setCarFiles(saveOrUpdateCarFile(carDTO.getId(), carDTO.getCarFiles()));
        carDTO.setCarChecks(saveOrUpdateCarCheck(carDTO.getId(), carDTO.getCarChecks()));
        return carDTO;
    }


    @Override
    public CsCarInfo getOneById(Long csCarInfoId) {
        Assert.notNull(csCarInfoId, "车辆id不能为空");
        CsCarInfo carInfo = this.selectById(csCarInfoId);
        if (Objects.isNull(carInfo)) {
            throw new RiggerException("该车辆信息不存在");
        }
        return carInfo;
    }

    @Override
    public Page<CsConsoleDTO> findConsoleByPage(Query query) {
        Map<String, Object> param = query.getCondition();
        Long userId = (Long) param.get("userId");
        Integer userType = (Integer) param.get("userType");
        Assert.notNull(userId, "当前用户id不能为空");
        Assert.notNull(userType, "当前用户类型不能为空");
        query.setCondition(setInspectorQueryParam(param));
        Result<List<SysDictDTO>> standardCode = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> boxCode = systemServiceClient.getDictByClass(DictClassEnum.TRANSMISSION_100000.getClassCode());
        List<CsConsoleDTO> csConsoleList = carInfoMapper.findConsoleByPage(query, query.getCondition());
        csConsoleList.forEach(carDTO -> {
            carDTO.setCarDealer(setCarDealerName(carDTO.getCsCarDealerId()));
            carDTO.setEmissionStandardCode(KeyValueDescUtil.handleValueDesc(carDTO.getEmissionStandardCode(), standardCode.getData()));
            carDTO.setGearBoxCode(KeyValueDescUtil.handleValueDesc(carDTO.getGearBoxCode(), boxCode.getData()));
            carDTO.setColorCode(KeyValueDescUtil.handleValueDesc(carDTO.getColorCode(), colorCode.getData()));
            Optional<CarAuditDTO> carAuditDTO = carAuditService.getLastCarAuditByCarId(carDTO.getId());
            if (null != carDTO.getCheckUserId()) {
                Result<UserDTO> result = systemServiceClient.getUserByUserId(carDTO.getCheckUserId());
                if (result.isSuccess() && Objects.nonNull(result.getData())) {
                    carDTO.setCheckLoginName(StringUtils.isEmpty(result.getData().getUsername()) ? "" : result.getData().getUsername());
                }
            }
            if (carAuditDTO.isPresent()) {
                carDTO.setAuditLoginName(StringUtils.isEmpty(carAuditDTO.get().getAuditUserName()) ? "" : carAuditDTO.get().getAuditUserName());
            }

        });
        return query.setRecords(csConsoleList);
    }

    @Override
    public CarInfoDTO getCarInfoById(Long id) {
        CsCarInfo csCarInfo = getOneById(id);
        if (Objects.isNull(csCarInfo)) {
            return null;
        }
        CarInfoDTO dto = BeanUtils.beanCopy(csCarInfo, CarInfoDTO.class);
        dto.setTravelDistance(csCarInfo.getInstrumentShowDistance());
        dto.setProvincialPrice(getProvincialPrice(csCarInfo.getSuggestPrice(), csCarInfo.getPrice()));
        Optional<CsCarDealer> csCarDealer = carDealerService.getOneById(csCarInfo.getCsCarDealerId());
        if (csCarDealer.isPresent()) {
            dto.setCreditUnion(csCarDealer.get().getCreditUnion());
        }
        List<CarFileDTO> fileDTOS = carFileService.findByCarIdAndFileType(id, Constants.YES);
        List<TagsDTO> tagsDTOS = csHitTagRelativeService.getCsTagsByCarId(id);
        tagsDTOS.forEach(tag -> {
            tag.setFilePath(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), tag.getFilePath()));
        });
        Result<List<SysDictDTO>> standardCode = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> boxCode = systemServiceClient.getDictByClass(DictClassEnum.TRANSMISSION_100000.getClassCode());
        Result<List<SysDictDTO>> carTypeCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_TYPE_110000.getClassCode());
        Result<List<SysDictDTO>> driveTypeCode = systemServiceClient.getDictByClass(DictClassEnum.DRIVE_WAY_120000.getClassCode());
        Result<List<SysDictDTO>> fuelTypeCode = systemServiceClient.getDictByClass(DictClassEnum.FUEL_TYPE_130000.getClassCode());
        dto.setEmissionStandardCode(KeyValueDescUtil.handleValueDesc(dto.getEmissionStandardCode(), standardCode.getData()));
        dto.setGearBoxCode(KeyValueDescUtil.handleValueDesc(dto.getGearBoxCode(), boxCode.getData()));
        dto.setColorCode(KeyValueDescUtil.handleValueDesc(dto.getColorCode(), colorCode.getData()));
        dto.setCarTypeCode(KeyValueDescUtil.handleValueDesc(dto.getCarTypeCode(), carTypeCode.getData()));
        dto.setDriveMethodCode(KeyValueDescUtil.handleValueDesc(dto.getDriveMethodCode(), driveTypeCode.getData()));
        dto.setFuelTypeCode(KeyValueDescUtil.handleValueDesc(dto.getFuelTypeCode(), fuelTypeCode.getData()));
        dto.setScanNum(getScanNum(csCarInfo));
        dto.setTagNames(tagsDTOS);
        dto.setCarFiles(fileDTOS);
        if (StrUtil.isNotEmpty(csCarInfo.getMaintenanceUrl()) && !UrlUtils.checkUrlLegal(csCarInfo.getMaintenanceUrl())) {
            dto.setMaintenanceUrl(null);
        }
        if (StrUtil.isNotEmpty(csCarInfo.getInsuranceUrl()) && !UrlUtils.checkUrlLegal(csCarInfo.getInsuranceUrl())) {
            dto.setInsuranceUrl(null);
        }
        return dto;
    }

    /**
     * 比新车省约
     *
     * @param suggestPrice
     * @param price
     * @return
     */
    private BigDecimal getProvincialPrice(BigDecimal suggestPrice, BigDecimal price) {
        //车源平台比新车省约计算公式调整：比新车省约计算公式= （新车指导价 + (新车指导价 / 11.7)）- 零售价，直接取整
        return suggestPrice.add(suggestPrice.divide(divide, BigDecimal.ROUND_HALF_DOWN)).subtract(price);
    }


    @Override
    public Boolean checkTagWithSqlStr(Long carId, String sqlStr) {
        Map<String, Object> params = new HashMap<>();
        params.put("carId", carId);
        params.put("sqlStr", sqlStr);
        return carInfoMapper.checkTagWithSqlStr(params) > 0;
    }

    @Override
    public List<Long> getCarWithSqlStr(Query query) {
        return carInfoMapper.getCarWithSqlStr(query, query.getCondition());
    }

    @Override
    public Boolean changeCarDealer(CarDTO car) {
        Assert.notNull(car.getId(), "车辆id不能为空");
        CsCarInfo carInfo = this.getOneById(car.getId());
        BeanUtil.copyProperties(car, carInfo, CopyOptions.create(null, true, null));
        carInfo.setCheckTime(null);
        carInfo.setCheckUserId(null);
        carInfo.setCheckLoginName(null);
        this.updateAllColumnById(carInfo);
        return Boolean.TRUE;
    }

    @Override
    public Boolean pullOffCar(CsCarHomeDTO carDTO) {
        Assert.notNull(carDTO, "下架车辆：参数不能为空");
        Assert.notNull(carDTO.getId(), "下架车辆：车辆Id不能为空");
        Assert.notNull(carDTO.getPullOffType(), "下架车辆：下架类型不能为空");
        CsCarInfo car = this.getOneById(carDTO.getId());
        if (!CarPublishStatusEnum.PURT_AWAY.getKey().equals(car.getPublishStatus())) {
            throw new RiggerException("车辆下架：车辆未上架,不能进行下架操作");
        }
        if (Constants.YES.equals(car.getTradeFlag())) {
            throw new RiggerException("车辆交易中,不能进行下架操作");
        }
        if (CarPullOffTypeEnum.PULLOFF_CHECK_TYPE.getKey().equals(carDTO.getPullOffType())) {
            checkCarUserArea(car, carDTO);
        } else if (CarPullOffTypeEnum.PULLOFF_USER_TYPE.getKey().equals(carDTO.getPullOffType())) {
            //用户自己下架自己的
            checkCarUserBelong(car, carDTO);
        }
        CsCarInfo csCarInfo = new CsCarInfo();
        csCarInfo.setId(car.getId());
        csCarInfo.setPullOffTime(new Date());
        csCarInfo.setPublishStatus(CarPublishStatusEnum.SOLD_OUT.getKey());
        csCarInfo.setRemark(carDTO.getRemark());
        return super.updateById(csCarInfo);
    }

    private void checkCarUserBelong(CsCarInfo car, CsCarHomeDTO carDTO) {
        if (Objects.isNull(car) || Objects.isNull(carDTO)) {
            return;
        }
        if (UserLevelEnum.SELLER_LEVEL.getLevel().equals(carDTO.getUserType())) {
            Long carDearId = csCarDealerService.getCurrentCarDealerInfo(carDTO.getPullOffLoginName()).getId();
            if (!car.getCsCarDealerId().equals(carDearId)) {
                throw new RiggerException("非自己车商车辆,请误操作");
            }
        } else {
            throw new RiggerException("非车商,不能下架该车辆,请误操作");
        }
    }

    @Override
    public Boolean deleteCar(CsCarHomeDTO carDTO) {
        Assert.notNull(carDTO, "删除车辆：参数不能为空");
        Assert.notNull(carDTO.getId(), "删除车辆：车辆Id不能为空");
        CsCarInfo car = this.getOneById(carDTO.getId());
        if (CarPublishStatusEnum.PURT_AWAY.getKey().equals(car.getPublishStatus())
                || CarPublishStatusEnum.PUBLISH.getKey().equals(car.getPublishStatus())) {
            throw new RiggerException("删除车辆：发布(上架)状态的车辆,不能进行删除操作");
        }
        checkCarUserArea(car, carDTO);
        CsCarInfo csCarInfo = new CsCarInfo();
        csCarInfo.setId(car.getId());
        csCarInfo.setIsDeleted(CommonConstant.STATUS_DEL);
        return super.updateById(csCarInfo);
    }

    @Override
    public Boolean pullUpCar(CsCarHomeDTO carDTO) throws Exception {
        Assert.notNull(carDTO, "上架车辆：参数不能为空");
        Assert.notNull(carDTO.getId(), "上架车辆：车辆Id不能为空");
        CsCarInfo car = this.getOneById(carDTO.getId());
        if (!CarPublishStatusEnum.SOLD_OUT.getKey().equals(car.getPublishStatus())) {
            throw new RiggerException("上架车辆：只能对下架的车辆进行上架操作");
        }
        checkCarUserArea(car, carDTO);
        CarAuditDTO dto = new CarAuditDTO();
        dto.setAuditStatus(AuditStatusEnum.PASS.getKey());
        dto.setAuditUserId(carDTO.getCheckId());
        dto.setAuditLoginName(carDTO.getCheckLoginName());
        dto.setAuditUserName(carDTO.getCheckUserName());
        dto.setCsCarInfoId(carDTO.getId());
        car.setVin(car.getCarNo());
        return carAuditService.add(dto);
    }

    @Override
    public Page transferRecordPage(Query query) {
        List<TransferRecordPageDTO> list = carInfoMapper.selectTransferRecordPage(query, query.getCondition());
        if (list.isEmpty()) {
            return query.setRecords(list);
        }
        Result<List<SysDictDTO>> standardCode = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> boxCode = systemServiceClient.getDictByClass(DictClassEnum.TRANSMISSION_100000.getClassCode());
        list.forEach(
                trans -> {
                    trans.setCarDealer(setCarDealerName(trans.getCsCarDealerId()));
                    trans.setEmissionStandardCode(KeyValueDescUtil.handleValueDesc(trans.getEmissionStandardCode(), standardCode.getData()));
                    trans.setGearBoxCode(KeyValueDescUtil.handleValueDesc(trans.getGearBoxCode(), boxCode.getData()));
                    trans.setColorCode(KeyValueDescUtil.handleValueDesc(trans.getColorCode(), colorCode.getData()));
                    Optional<CarAuditDTO> carAuditDTO = carAuditService.getLastCarAuditByCarId(trans.getId());
                    if (null != trans.getCheckUserId()) {
                        Result<UserDTO> result = systemServiceClient.getUserByUserId(trans.getCheckUserId());
                        if (result.isSuccess() && Objects.nonNull(result.getData())) {
                            trans.setCheckLoginName(StringUtils.isEmpty(result.getData().getUsername()) ? "" : result.getData().getUsername());
                        }
                    }
                    if (carAuditDTO.isPresent()) {
                        trans.setAuditLoginName(StringUtils.isEmpty(carAuditDTO.get().getAuditUserName()) ? "" : carAuditDTO.get().getAuditUserName());
                    }
                    trans.setTravelDistance(trans.getInstrumentShowDistance());

                }
        );
        return query.setRecords(list);
    }

    @Override
    public CarDTO getCarByIdAndStatusTradeFlag(Long carId, Integer status, Integer tradeFlag) {
        CsCarInfo carInfo = this.selectOne(new EntityWrapper<CsCarInfo>()
                .eq("id", carId)
                .eq("trade_flag", tradeFlag)
                .eq("publish_status", status)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        Optional.ofNullable(carInfo).orElseThrow(() -> new RiggerException("该车辆信息已下架"));
        CarDTO carDTO = BeanUtils.beanCopy(carInfo, CarDTO.class);
        carDTO.setColorCodeName(getDictValue(carDTO.getColorCode()));
        carDTO.setGearBoxCodeName(getDictValue(carDTO.getGearBoxCode()));
        carDTO.setEmissionStandardCodeName(getDictValue(carDTO.getEmissionStandardCode()));
        if (Objects.nonNull(carDTO.getCheckUserId())) {
            Result<UserDTO> userDTO = systemServiceClient.getUserByUserId(carDTO.getCheckUserId());
            if (userDTO.isSuccess() && Objects.nonNull(userDTO.getData())) {
                carDTO.setCheckLoginName(StringUtils.isEmpty(userDTO.getData().getUsername()) ? "" : userDTO.getData().getUsername());
            }
        }
        Optional<CarAuditDTO> carAuditDTO = carAuditService.getLastCarAuditByCarId(carDTO.getId());
        if (carAuditDTO.isPresent()) {
            carDTO.setAuditRemark(carAuditDTO.get().getRemark());
            carDTO.setAuditUserId(carAuditDTO.get().getAuditUserId());
            carDTO.setAuditLoginName(StringUtils.isEmpty(carAuditDTO.get().getAuditUserName()) ? carAuditDTO.get().getAuditLoginName() : carAuditDTO.get().getAuditUserName());
        }
        return carDTO;
    }

    @Override
    public Boolean uploadCheckReport(Long id, InputStream inputStream, long size, String filename, String extensionName) {
        CsCarInfo csCarInfo = getOneById(id);
        String url = fastDfsFileService.uploadFile(inputStream, size, filename, extensionName);
        CsCarInfo carInfo = new CsCarInfo();
        carInfo.setId(csCarInfo.getId());
        carInfo.setCarModelFileName(filename);
        carInfo.setCarModelFilePath(url);
        return this.updateById(carInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean completeAndPutOffCar(Long csCarInfoId, Boolean flag) {
        Assert.notNull(csCarInfoId, "车辆id不能为空");
        CsCarInfo csCarInfo = carInfoMapper.selectById(csCarInfoId);
        if (Objects.isNull(csCarInfo) || Constants.TWO.equals(csCarInfo.getTradeFlag())
                || CarPublishStatusEnum.SOLD_OUT.getKey().equals(csCarInfo.getPublishStatus())) {
            throw new RiggerException("车辆不存在或已下架");
        }
        if (flag) {
            csCarInfo.setTradeFlag(Constants.NO);
        } else {
            csCarInfo.setPublishStatus(CarPublishStatusEnum.SOLD_OUT.getKey());
            csCarInfo.setTradeFlag(Constants.TWO);
        }
        return this.updateById(csCarInfo);
    }

    private void checkCarUserArea(CsCarInfo car, CsCarHomeDTO carDTO) {
        if (Objects.isNull(car) || Objects.isNull(carDTO)) {
            return;
        }
        //车辆所在地
        String locate = car.getLocate();
        if (UserLevelEnum.INSPECTOR_LEVEL.getLevel().equals(carDTO.getUserType())) {
            Result<UserAreaDTO> userArea = systemServiceClient.getUserAreaByUserId(carDTO.getCheckId());
            if (!userArea.isSuccess()) {
                throw new RiggerException("获取城市信息失败");
            }
            String user_area = userArea.getData().getProvinceName() + StrUtil.DASHED + userArea.getData().getCityName();
            if (!user_area.equals(locate)) {
                throw new RiggerException("请勿操作其他区域车辆");
            }

        }
    }

    /**
     * 通过code获取字典值
     *
     * @param code 字典码
     * @return
     */
    private String getDictValue(String code) {
        return StrUtil.isBlank(code) ? "" : getValue(code);
    }

    private String getValue(String code) {
        Result<String> result = systemServiceClient.getDictValueDesc(code);
        if (result.isSuccess()) {
            return result.getData();
        }
        return "";
    }

    private void saveOrUpdateCarInfo(CarDTO carDTO) {
        CsCarInfo carInfo = BeanUtils.beanCopy(carDTO, CsCarInfo.class);
        if (Objects.nonNull(carInfo.getId())) {
            carInfo.setCarNo(getOneById(carInfo.getId()).getCarNo());
        } else {
            carInfo.setCarNo(orderNumberProvider.building());
            carInfo.setScanBaseNum(RandomUtil.randomInt(1, 100));
        }
        if (StrUtil.isNotBlank(carDTO.getVin())) {
            CsCarInfo csCarInfo = this.selectOne(new EntityWrapper<CsCarInfo>().eq("vin", carDTO.getVin()));
            if (Objects.nonNull(csCarInfo) && !csCarInfo.getId().equals(carInfo.getId()) &&
                    !CarPublishStatusEnum.ALLOT.getKey().equals(csCarInfo.getPublishStatus())) {
                throw new RiggerException("该VIN码已存在");
            }
        }
        carInfo.setPublishTime(DateUtil.date());
        this.insertOrUpdate(carInfo);
        carDTO.setId(carInfo.getId());
    }

    private void saveOrUpdateCarConfig(Long carInfoId, CarConfigDTO carConfigDTO) {
        Assert.notNull(carInfoId, "车辆id不能为空");
        if (Objects.isNull(carConfigDTO)) {
            return;
        }
        carConfigDTO.setCsCarInfoId(carInfoId);
        CsCarConfig carConfig = BeanUtils.beanCopy(carConfigDTO, CsCarConfig.class);
        carConfigService.insertOrUpdate(carConfig);
        carConfigDTO.setId(carConfig.getId());
    }

    private void saveOrUpdateCarProcedure(Long carInfoId, CarProcedureDTO carProcedureDTO) {
        Assert.notNull(carInfoId, "车辆id不能为空");
        if (Objects.isNull(carProcedureDTO)) {
            return;
        }
        carProcedureDTO.setCsCarInfoId(carInfoId);
        CsCarProcedure carProcedure = BeanUtils.beanCopy(carProcedureDTO, CsCarProcedure.class);
        carProcedureService.insertOrUpdate(carProcedure);
        carProcedureDTO.setId(carProcedure.getId());
    }

    private List<CarFileDTO> saveOrUpdateCarFile(Long carInfoId, List<CarFileDTO> carFileDTOS) {
        Assert.notNull(carInfoId, "车辆id不能为空");
        if (CollectionUtil.isEmpty(carFileDTOS)) {
            return Lists.newArrayList();
        }
        List<CsCarFile> carFiles = Lists.newArrayList();
        carFileDTOS.forEach(carFileDTO -> {
            carFileDTO.setCsCarInfoId(carInfoId);
            carFiles.add(BeanUtils.beanCopy(carFileDTO, CsCarFile.class));
            if (LEFT_CAR_PIC.equals(carFileDTO.getFileChildTypeCode())) {
                //左前45°照片同步 Y_150001
                updateIconFileById(carInfoId, carFileDTO.getFilePath(), carFileDTO.getFileName());
            }
        });
        carFileService.insertOrUpdateBatch(carFiles);
        List<CsCarFile> csCarFiles = getCsCarFiles(carInfoId);
        return BeanUtils.beansToList(csCarFiles, CarFileDTO.class);
    }

    private Boolean updateIconFileById(Long carInfoId, String filePath, String fileName) {
        Assert.notNull(carInfoId, "车辆id不能为空");
        CsCarInfo csCarInfo = new CsCarInfo();
        csCarInfo.setId(carInfoId);
        csCarInfo.setIconFileName(fileName);
        csCarInfo.setIconFilePath(filePath);
        return this.updateById(csCarInfo);
    }

    private List<CsCarFile> getCsCarFiles(Long carInfoId) {
        return carFileService.selectList(new EntityWrapper<CsCarFile>().eq("cs_car_info_id", carInfoId));
    }

    private List<CarCheckDTO> saveOrUpdateCarCheck(Long carInfoId, List<CarCheckDTO> carCheckDTOS) {
        Assert.notNull(carInfoId, "车辆id不能为空");
        if (CollectionUtil.isEmpty(carCheckDTOS)) {
            return Lists.newArrayList();
        }
        List<CsCarCheck> carChecks = Lists.newArrayList();
        carCheckDTOS.forEach(carCheckDTO -> {
            carCheckDTO.setCsCarInfoId(carInfoId);
            carChecks.add(BeanUtils.beanCopy(carCheckDTO, CsCarCheck.class));
        });
        carCheckService.insertOrUpdateBatch(carChecks);
        List<CsCarCheck> checks = carCheckService.selectList(new EntityWrapper<CsCarCheck>().eq("cs_car_info_id", carInfoId));
        return BeanUtils.beansToList(checks, CarCheckDTO.class);
    }

    private int getScanNum(CsCarInfo csCarInfo) {
        int scanNum = Objects.isNull(csCarInfo.getScanNum()) ? 0 : csCarInfo.getScanNum();
        int baseNum = Objects.isNull(csCarInfo.getScanBaseNum()) ? 0 : csCarInfo.getScanBaseNum();
        return scanNum + baseNum;
    }

    private String setCarDealerName(Long carDealerId) {
        if (Objects.isNull(carDealerId)) {
            return null;
        }
        Optional<CsCarDealer> optional = carDealerService.getOneById(carDealerId);
        return optional.get().getShortName();
    }

    private Map<String, Object> setInspectorQueryParam(Map<String, Object> param) {
        if (UserLevelEnum.INSPECTOR_LEVEL.getLevel().equals(param.get("userType"))) {
            if (!CarPublishStatusEnum.ALLOT.getKey().equals(param.get("publishStatus"))) {
                param.put("checkUserId", param.get("userId"));
            } else {
                Result<UserAreaDTO> userArea = systemServiceClient.getUserAreaByUserId((Long) param.get("userId"));
                if (!userArea.isSuccess()) {
                    throw new RiggerException("获取城市信息失败");
                }
                param.put("locate", userArea.getData().getProvinceName() + StrUtil.DASHED + userArea.getData().getCityName());
            }
        }
        return param;
    }

}
