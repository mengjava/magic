package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.enums.OrderFileTypeEnum;
import com.haoqi.magic.business.model.dto.CsItemBaseDTO;
import com.haoqi.magic.business.model.entity.CsOrderFile;
import com.haoqi.magic.business.model.vo.OrderFileQueryPageVO;
import com.haoqi.magic.business.model.vo.OrderFileVO;
import com.haoqi.magic.business.service.ICsOrderFileService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.haoqi.rigger.web.controller.BaseController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-12-11
 */
@RestController
@RequestMapping("/csOrderFile")
@Api(tags = "订单附件")
public class CsOrderFileController extends BaseController {


    @Autowired
    private ICsOrderFileService csOrderFileService;

    @Value("${fdfs.downloadTempPath:/usr/local/temp}")
    private String downloadTempPath;

    @Autowired
    private FastDfsFileService fastDfsFileService;

    @GetMapping("getAllType")
    @ApiOperation(value = "获取所有的文件类别")
    public Result<List<CsItemBaseDTO>> getAllType() {
        return Result.buildSuccessResult(csOrderFileService.getAllType());
    }


    @PostMapping("/page")
    @ApiOperation(value = "分页获取附件")
    public Result<Page> orderFilePage(@RequestBody OrderFileQueryPageVO vo) {
        validatorHandler.validator(vo);
        UserInfo userInfo = currentUser();
        Map<String, Object> params = BeanUtil.beanToMap(vo);
        Page page = csOrderFileService.selectOrderFilePage(new Query(params));
        return Result.buildSuccessResult(page);
    }


    @PostMapping("/saveFile")
    @ApiOperation(value = "保存打款附件)")
    public Result<Page> saveFile(@RequestBody OrderFileVO vo) {
        validatorHandler.validator(vo);
        csOrderFileService.saveFile(vo);
        return Result.buildSuccessResult("保存成功");
    }


    /**
     * 附件下载
     */
    @PostMapping("/downloadFile")
    @ApiOperation(value = "根据订单id下载订单附件")
    public void downloadFile(@RequestBody OrderFileQueryPageVO file, HttpServletResponse response) {
        validatorHandler.validator(file);
        StringBuilder builder = new StringBuilder();
        builder.append(downloadTempPath).append(File.separator).append(DateUtil.currentSeconds())
                .append(File.separator).append(file.getCsCarOrderId()).append(File.separator);
        String fileSavePath = builder.toString();
        Map<String, Object> params = BeanUtil.beanToMap(file);
        List<CsOrderFile> csOrderFiles = csOrderFileService.selectOrderFile(params);
        if (CollectionUtil.isEmpty(csOrderFiles)) {
            return;
        }
        csOrderFiles.forEach(
                orderFile -> {
                    try {
                        FileUtils.generateFile(fileSavePath, URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), orderFile.getFilePath()), orderFile.getFileName());
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
        );
        FileUtils.downloadZipFile(fileSavePath, downloadTempPath, OrderFileTypeEnum.getTypeName(file.getType()) + "附件材料.zip", response);
    }


}

