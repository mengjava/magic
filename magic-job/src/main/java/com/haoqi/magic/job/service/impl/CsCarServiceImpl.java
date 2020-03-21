package com.haoqi.magic.job.service.impl;

import com.haoqi.magic.job.mapper.CsCarMapper;
import com.haoqi.magic.job.model.dto.CarDTO;
import com.haoqi.magic.job.service.ICsCarService;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:com.haoqi.magic.job.service.impl <br/>
 * Function: 车辆服务实现类<br/>
 * Date:     2019/5/13 11:29 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Service
public class CsCarServiceImpl implements ICsCarService {

    @Autowired
    private CsCarMapper csCarMapper;

    @Override
    public List<CarDTO> getCarWithSqlStr(Query query) {
        return csCarMapper.getCarWithSqlStr(query, query.getCondition());
    }

    @Override
    public Integer getCarTotalCount() {
        return csCarMapper.getCarTotalCount();
    }
}
