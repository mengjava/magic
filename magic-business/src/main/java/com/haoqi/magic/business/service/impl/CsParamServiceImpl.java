package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.common.utils.KeyValueDescUtil;
import com.haoqi.magic.business.mapper.CsParamMapper;
import com.haoqi.magic.business.model.dto.CsParamDTO;
import com.haoqi.magic.business.model.dto.SysDictDTO;
import com.haoqi.magic.business.model.entity.CsParam;
import com.haoqi.magic.business.model.entity.CsTag;
import com.haoqi.magic.business.model.vo.CsParamVO;
import com.haoqi.magic.business.model.vo.CsTagVO;
import com.haoqi.magic.business.service.ICsParamService;
import com.haoqi.magic.business.service.ICsTagService;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.magic.common.enums.ParamAuditEnum;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 参数管理表 服务实现类
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
@Service
public class CsParamServiceImpl
        extends ServiceImpl<CsParamMapper, CsParam>
        implements ICsParamService {

    @Autowired
    private CsParamMapper csParamMapper;

    @Autowired
    private ICsTagService csTagService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Override
    public Page<List<CsParamDTO>> findPage(Query query) {
        List<CsParam> list = csParamMapper.findPage(query, query.getCondition());
        List<CsParamDTO> csParamDTOS = BeanUtils.beansToList(list, CsParamDTO.class);
        Result<List<SysDictDTO>> carType = systemServiceClient.getDictByClass(DictClassEnum.CAR_TYPE_110000.getClassCode());
        csParamDTOS.forEach(csParam -> {
            csParam.setCarTypeCodeName(KeyValueDescUtil.handleValueDesc(csParam.getCarTypeCode(), carType.getData()));
        });
        return query.setRecords(csParamDTOS);
    }

    @Override
    @Transactional
    public Boolean insert(CsParamVO vo) {
        //1、参数校验
        Assert.notNull(vo, "新增参数：参数不能为空");
        Assert.notBlank(vo.getParamName(), "新增参数：参数名称不能为空");

        //2根据参数名称判断重名与否
        Integer num = this.countByParamName(vo.getParamName());
        if (num > 0) {
            throw new RiggerException("新增参数【" + vo.getParamName() + "】已存在");
        }

        //3、新增参数
        CsParam tag = BeanUtils.beanCopy(vo, CsParam.class);

        //4、根据条件生成SQL语句
        String sb = generateSql(tag);
        if (StrUtil.isNotBlank(sb)) {
            tag.setSqlStr(sb);
        }

        return super.insert(tag);
    }

    @Override
    public Integer countByParamName(String paramName) {
        return super.selectCount(new EntityWrapper<CsParam>().eq("param_name", paramName));
    }

    @Override
    @Transactional
    public Boolean deleteCsParamById(Long id) {
        CsTagVO vo = new CsTagVO();
        vo.setCsParamId(id);
        List<CsTag> list = csTagService.getCsTagWithCondition(vo);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new RiggerException("自定义参数已经被使用，无法删除");
        }
        return super.deleteById(id);
    }

    @Override
    @Transactional
    public Boolean updateCsParamById(CsParamVO vo) {
        Assert.notNull(vo, "更新参数：参数不能为空");
        Assert.notNull(vo.getId(), "更新参数：id不能为空");
        CsParam tag = BeanUtils.beanCopy(vo, CsParam.class);
        super.updateById(tag);

        //查询出数据，重新转换SQL语句
        CsParam csParam = super.selectById(tag.getId());
        if (null != csParam) {
            String sb = generateSql(tag);

            if (StrUtil.isNotBlank(sb)) {
                tag.setSqlStr(sb);
            }
            super.updateById(tag);
        }

        return Boolean.TRUE;
    }

    @Override
    public CsParamDTO getOneById(Long id) {
        CsParam csParam = super.selectById(id);
        if (Objects.isNull(csParam)) {
            return new CsParamDTO();
        }
        Result<List<SysDictDTO>> carType = systemServiceClient.getDictByClass(DictClassEnum.CAR_TYPE_110000.getClassCode());
        CsParamDTO paramDTO = BeanUtils.beanCopy(csParam, CsParamDTO.class);
        paramDTO.setCarTypeCodeName(KeyValueDescUtil.handleValueDesc(paramDTO.getCarTypeCode(),carType.getData()));
        return paramDTO;
    }

    @Override
    public List<CsParam> getAllCsParam() {
        return super.selectList(new EntityWrapper<CsParam>().eq("is_deleted", CommonConstant.STATUS_NORMAL));
    }

    @Override
    @Transactional
    public Boolean deleteCsParamByIds(List<Long> lIds) {
        if (CollectionUtil.isNotEmpty(lIds)) {
            List<CsParam> entities = new ArrayList<>(lIds.size());
            for (Long i : lIds) {
                CsTagVO vo = new CsTagVO();
                vo.setCsParamId(i);
                List<CsTag> list = csTagService.getCsTagWithCondition(vo);
                if (CollectionUtil.isNotEmpty(list)) {
                    CsParamDTO dto = getOneById(i);
                    if (null != dto) {
                        throw new RiggerException("自定义参数" + dto.getParamName() + "已经被使用，无法删除");
                    } else {
                        throw new RiggerException("自定义参数已经被使用，无法删除");
                    }
                }
                CsParam c = new CsParam();
                c.setId(i);
                c.setIsDeleted(CommonConstant.STATUS_DEL);
                c.setGmtModified(new Date());
                entities.add(c);
            }
            super.updateBatchById(entities);
        }

        return Boolean.TRUE;
    }


    /**
     * 根据条件生成sql语句
     *
     * @param tag
     * @return
     * @author huming
     * @date 2019/5/8 14:44
     */
    protected String generateSql(CsParam tag) {
        StringBuilder sb = new StringBuilder();
        if (null != tag.getMinPrice()) {
            sb.append("AND cs_car_info.price >=");
            sb.append(tag.getMinPrice());
        }

        if (null != tag.getMaxPrice()) {
            sb.append(" AND cs_car_info.price <=");
            sb.append(tag.getMaxPrice());
        }

        if (null != tag.getMinTravelDistance()) {
            sb.append(" AND cs_car_info.travel_distance >=");
            sb.append(tag.getMinTravelDistance());

        }

        if (null != tag.getMaxTravelDistance()) {
            sb.append(" AND cs_car_info.travel_distance <=");
            sb.append(tag.getMaxTravelDistance());
        }

        if (null != tag.getCarAge()) {
            sb.append(" AND TIMESTAMPDIFF(YEAR,cs_car_info.init_date,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) <= ");
            sb.append(tag.getCarAge());
        }

        if (StrUtil.isNotBlank(tag.getCarTypeCode())) {
            sb.append(" AND cs_car_info.car_type_code = ");
            sb.append(tag.getCarTypeCode());
        }

        if (null != tag.getTransferNum()) {
            sb.append(" AND cs_car_info.transfer_num <= ");
            sb.append(tag.getTransferNum());
        }

        if (null != tag.getAuditTime()) {

            switch (tag.getAuditTime()) {
                case 1:
                    sb.append(" AND TIMESTAMPDIFF(DAY,cs_car_info.audit_time,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) <= ");
                    sb.append(ParamAuditEnum.getValue(1));
                    break;
                case 2:
                    sb.append(" AND TIMESTAMPDIFF(DAY,cs_car_info.audit_time,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) <= ");
                    sb.append(ParamAuditEnum.getValue(2));
                    break;
                case 3:
                    sb.append(" AND TIMESTAMPDIFF(DAY,cs_car_info.audit_time,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) <= ");
                    sb.append(ParamAuditEnum.getValue(3));
                    break;
                case 4:
                    sb.append(" AND TIMESTAMPDIFF(DAY,cs_car_info.audit_time,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) <= ");
                    sb.append(ParamAuditEnum.getValue(4));
                    break;
                case 5:
                    sb.append(" AND TIMESTAMPDIFF(DAY,cs_car_info.audit_time,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) <= ");
                    sb.append(ParamAuditEnum.getValue(5));
                    break;
                case 6:
                    sb.append(" AND TIMESTAMPDIFF(DAY,cs_car_info.audit_time,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')) > ");
                    sb.append(ParamAuditEnum.getValue(6));
                    break;

            }
        }
        if (null != tag.getCreditUnion()) {
            sb.append(" AND cs_car_dealer.credit_union = ");
            sb.append(tag.getCreditUnion());
        }

        return sb.toString();
    }
}
