package com.haoqi.magic.business.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.mapper.CsCarFileMapper;
import com.haoqi.magic.business.model.dto.CarFileDTO;
import com.haoqi.magic.business.model.entity.CsCarFile;
import com.haoqi.magic.business.service.ICsCarFileService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 车辆图片信息 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@Service
public class CsCarFileServiceImpl extends ServiceImpl<CsCarFileMapper, CsCarFile> implements ICsCarFileService {

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Autowired
    private SystemServiceClient systemServiceClient;


    @Override
    public List<CarFileDTO> findByCarIdAndFileType(Long carId, Integer fileType) {
        Assert.notNull(carId, "车辆ID不能为空");
        CsCarFile carFile = new CsCarFile();
        carFile.setCsCarInfoId(carId);
        if (Objects.nonNull(fileType)) {
            carFile.setFileType(fileType);
        }
        List<CsCarFile> carFiles = this.selectList(new EntityWrapper<>(carFile)
                .orderBy("file_type", true).orderBy("file_child_type_code", true));
        List<CarFileDTO> carFileDTOS = BeanUtils.beansToList(carFiles, CarFileDTO.class);
        carFileDTOS.stream().filter(carFileDTO -> StrUtil.isNotBlank(carFileDTO.getFileChildTypeCode())).forEach(carFileDTO -> {
            Result<String> result = systemServiceClient.getDictValueDesc(carFileDTO.getFileChildTypeCode());
            carFileDTO.setWebUrl(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), StrUtil.EMPTY));
            carFileDTO.setFileChildTypeName(result.isSuccess() ? result.getData() : "");
        });
        return carFileDTOS;
    }

    @Override
    public CarFileDTO getLeftFront45FileByCarId(Long carId) {
        CsCarFile carFile = this.selectOne(new EntityWrapper<CsCarFile>()
                .eq("cs_car_info_id", carId)
                .eq("file_child_type_code", "Y_150001")
                .eq("file_type", CommonConstant.STATUS_DEL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        CarFileDTO carFileDTO = BeanUtils.beanCopy(carFile, CarFileDTO.class);
        carFileDTO.setWebUrl(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), StrUtil.EMPTY));
        return carFileDTO;
    }
}
