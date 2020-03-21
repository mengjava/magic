package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.UserVipDTO;
import com.haoqi.magic.business.model.entity.CsUserVip;

/**
 * <p>
 * 用户会员关联表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
public interface CsUserVipMapper extends BaseMapper<CsUserVip> {

    /**
     * 获取有效的用户会员卡信息
     *
     * @param userId
     * @return
     */
    UserVipDTO getEnableUserVipByUserId(Long userId);


    CsUserVip selectNoVipByUserId(Long id);
}
