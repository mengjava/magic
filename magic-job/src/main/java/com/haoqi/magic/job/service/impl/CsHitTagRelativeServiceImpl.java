package com.haoqi.magic.job.service.impl;

import com.haoqi.magic.job.mapper.CsHitTagRelativeMapper;
import com.haoqi.magic.job.model.dto.CarDTO;
import com.haoqi.magic.job.service.ICsHitTagRelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:com.haoqi.magic.job.service.impl <br/>
 * Function: 车辆标签命中服务实现类<br/>
 * Date:     2019/5/13 11:52 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Service
public class CsHitTagRelativeServiceImpl implements ICsHitTagRelativeService {

    @Autowired
    private CsHitTagRelativeMapper csHitTagRelativeMapper;

    @Override
    public Boolean insertHitTagRelative(List<CarDTO> cars, Long tagId) {
        Map<String,Object> param = new HashMap<>();
        param.put("cars",cars);
        param.put("tagId",tagId);
        return csHitTagRelativeMapper.insertHitTagRelative(param);
    }

    @Override
    public Boolean deleteOldHit(List<CarDTO> cars, Long tagId) {
        Map<String,Object> param = new HashMap<>();
        param.put("cars",cars);
        param.put("tagId",tagId);
        return csHitTagRelativeMapper.deleteOldHit(param);
    }

    @Override
    public Boolean deleteAllOld() {
        return csHitTagRelativeMapper.deleteAllOld();
    }

}
