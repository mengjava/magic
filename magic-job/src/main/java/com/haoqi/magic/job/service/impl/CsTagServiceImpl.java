package com.haoqi.magic.job.service.impl;

import com.haoqi.magic.job.mapper.CsTagMapper;
import com.haoqi.magic.job.model.dto.TagParamDTO;
import com.haoqi.magic.job.service.ICsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:com.haoqi.magic.job.service.impl <br/>
 * Function: 标签服务<br/>
 * Date:     2019/5/13 10:30 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Service
public class CsTagServiceImpl implements ICsTagService {

    @Autowired
    private CsTagMapper csTagMapper;


    @Override
    public List<TagParamDTO> getAllDetailTag(Integer tagType) {
        Map<String,Object> param = new HashMap<>();
        param.put("tagType",tagType);
        return csTagMapper.getAllDetailTag(param);
    }
}
