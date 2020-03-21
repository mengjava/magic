package com.haoqi.magic.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.system.model.dto.CsAccountDTO;
import com.haoqi.magic.system.model.entity.CsAccount;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 账户表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
public interface CsAccountMapper extends BaseMapper<CsAccount> {

    /**
     * 通过id，获取账户、用户信息
     *
     * @param id
     * @return
     */
    CsAccountDTO getAccountWithUserById(Long id);

    /**
     * 通过用户id，获取账户、用户信息
     *
     * @param userId
     * @return
     */
    CsAccountDTO getAccountByUserId(@Param("userId") Long userId);

}
