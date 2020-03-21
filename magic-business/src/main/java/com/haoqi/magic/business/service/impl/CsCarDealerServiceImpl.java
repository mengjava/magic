package com.haoqi.magic.business.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.AuditStatusEnum;
import com.haoqi.magic.business.enums.VipTypeEnum;
import com.haoqi.magic.business.mapper.CsCarDealerMapper;
import com.haoqi.magic.business.model.dto.CsCarDealerDTO;
import com.haoqi.magic.business.model.dto.SysProvinceAndCityDTO;
import com.haoqi.magic.business.model.dto.UserAreaDTO;
import com.haoqi.magic.business.model.dto.UserDTO;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.vo.CarDealerRegisterVO;
import com.haoqi.magic.business.model.vo.CsCarDealerAuditVO;
import com.haoqi.magic.business.model.vo.UserDealerRegisterVO;
import com.haoqi.magic.business.model.vo.UserVO;
import com.haoqi.magic.business.service.ICsCarDealerService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.enums.EnableDisableEnum;
import com.haoqi.magic.common.enums.OpenCloseEnum;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.common.util.PinyinUtil;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 商家表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-05-05
 */
@Slf4j
@Service
public class CsCarDealerServiceImpl extends ServiceImpl<CsCarDealerMapper, CsCarDealer> implements ICsCarDealerService {
    @Autowired
    private CsCarDealerMapper csCarDealerMapper;
    @Autowired
    private SystemServiceClient systemServiceClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;


    @Override
    public Optional<CsCarDealer> getOneById(Long id) {
        Assert.notNull(id, "车商id不能为空");
        CsCarDealer carDealer = this.selectById(id);
        if (Objects.isNull(carDealer)) {
            throw new RiggerException("该车商信息不存在");
        }
        return Optional.of(carDealer);
    }


    @Override
    public Boolean register(CsCarDealer csCarDealer) {
        csCarDealer.setStatus(AuditStatusEnum.SUBMITTED.getKey());
        //首先根据营业执照名称或手机号或营业执照编号查看是否已注册
        CsCarDealer dealer = this.selectOne(new EntityWrapper<CsCarDealer>()
                .or("dealer_name={0}", StrUtil.isEmpty(csCarDealer.getDealerName()) ? null : csCarDealer.getDealerName())
                .or("tel={0}", csCarDealer.getTel())
                .or("licence_no={0}", StrUtil.isEmpty(csCarDealer.getLicenceNo()) ? null : csCarDealer.getLicenceNo()));
        csCarDealer.setCarDearlerPinyin(StrUtil.isNotEmpty(csCarDealer.getDealerName()) ? PinyinUtil.getPinYin(csCarDealer.getDealerName()) : "");
        csCarDealer.setCarDearlerInitial(StrUtil.isNotEmpty(csCarDealer.getDealerName()) ? PinyinUtil.getAllFirstLetter(csCarDealer.getDealerName()) : "");
        Result<UserDTO> userByName = systemServiceClient.getUserByName(csCarDealer.getTel());
        if (Objects.nonNull(userByName.getData())) {
            csCarDealer.setSysUserId(userByName.getData().getId());
        }
	    if (Objects.isNull(dealer)) {
		    return this.insert(csCarDealer);
	    }
	    csCarDealer.setId(dealer.getId());
	    this.updateById(csCarDealer);
	    Integer count = this.selectCount(new EntityWrapper<CsCarDealer>()
			    .or("dealer_name={0}", StrUtil.isEmpty(csCarDealer.getDealerName()) ? null : csCarDealer.getDealerName())
			    .or("tel={0}", csCarDealer.getTel())
			    .or("licence_no={0}", StrUtil.isEmpty(csCarDealer.getLicenceNo()) ? null : csCarDealer.getLicenceNo()));

        if (count>Constants.YES) {
            throw new RiggerException("该营业执照名称或手机号或营业执照编号查已注册");
        }

        return Boolean.TRUE;
    }

    @Override
    public Page findCarDealerByPage(Query query) {
        List<CsCarDealerDTO> list = csCarDealerMapper.findCarDealerByPage(query, query.getCondition());
        list.forEach(m -> {
            if (Objects.nonNull(m.getExpiredDate()) && m.getExpiredDate().compareTo(new Date()) < 1){
                m.setVipType(VipTypeEnum.NON_VIP.getKey());
                m.setPeriod(null);
            }
            if (m.getSysAreaId() != null) {
                Result<SysProvinceAndCityDTO> areaById = systemServiceClient.getAreaById(m.getSysAreaId());
                if (areaById.isSuccess()) {
                    m.setAddress(areaById.getData().getProvinceName() +
                            (StrUtil.isBlank(areaById.getData().getCityName()) ? "" : (StrUtil.DASHED + areaById.getData().getCityName())));
                }
            }

        });
        return query.setRecords(list);
    }
	@Override
	public Page findCarSellerByPage(Query query) {
		List<CsCarDealerDTO> list = csCarDealerMapper.findCarSellerByPage(query, query.getCondition());
		list.forEach(m -> {
			if (m.getSysAreaId() != null) {
				Result<SysProvinceAndCityDTO> areaById = systemServiceClient.getAreaById(m.getSysAreaId());
				if (areaById.isSuccess()) {
					m.setAddress(areaById.getData().getProvinceName() +
							(StrUtil.isBlank(areaById.getData().getCityName()) ? "" : (StrUtil.DASHED + areaById.getData().getCityName())));
				}
			}

		});
		return query.setRecords(list);
	}
    @Override
    @Transactional
    public Boolean updateEnabledById(Long id) {
        Assert.notNull(id, "商家id不能为空");
        Optional<CsCarDealer> optional = this.getOneById(id);
        optional.ifPresent(carDealer -> {
            Integer isEnabled = CommonConstant.STATUS_NORMAL.equals(carDealer.getIsEnabled()) ?
                    CommonConstant.STATUS_DEL : CommonConstant.STATUS_NORMAL;
            carDealer.setIsEnabled(isEnabled);
            this.updateById(carDealer);
            //同时禁用用户
            Result<String> stringResult = systemServiceClient.updateEnabledByLoginName(carDealer.getTel());
            if (!stringResult.isSuccess()) {
                throw new RiggerException("用户禁用启用失败");
            }
        });
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateCreditUnionById(Long id) {
        Assert.notNull(id, "商家id不能为空");
        Optional<CsCarDealer> csCarDealer = this.getOneById(id);
        csCarDealer.ifPresent(o -> {
            Integer status = OpenCloseEnum.OPEN.getKey().equals(o.getCreditUnion()) ?
                    OpenCloseEnum.CLOSE.getKey() : OpenCloseEnum.OPEN.getKey();
            o.setCreditUnion(status);
            this.updateById(o);
        });
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean audit(CsCarDealerAuditVO csCarDealerAuditVO) {
        //首先查出审核信息的json
        Optional<CsCarDealer> optional = this.getOneById(csCarDealerAuditVO.getId());
        CsCarDealer csCarDealer = optional.get();
        //如果已经审核通过则不允许审核
        /*if (!EnableDisableEnum.ENABLE.getKey().equals(csCarDealer.getStatus())) {
            throw new RiggerException("此商家已审核");
        }*/
        List<CsCarDealerAuditVO> auditVOList = new ArrayList<>();
        //如果不是第一次审核 获取已存在的审核信息进行拼接
        if (StrUtil.isNotBlank(csCarDealer.getAuditDetail())) {
            String auditDetail = csCarDealer.getAuditDetail();
            JSONArray objects = JSONUtil.parseArray(auditDetail);
            //把json转换成list,添加本次的审核信息,再转换为json 赋值给csCarDealer
            auditVOList = JSONUtil.toList(objects, CsCarDealerAuditVO.class);
        }
        csCarDealerAuditVO.setAuditTime(new Date());

        auditVOList.add(csCarDealerAuditVO);
        csCarDealer.setAuditDetail(JSONObject.toJSONString(auditVOList));
        //设置审核人和审核日期以及审核人id
        csCarDealer.setLastAuditTime(new Date());
        csCarDealer.setLastAuditLoginName(csCarDealerAuditVO.getLastAuditLoginName());
        csCarDealer.setLastAuditUserId(csCarDealerAuditVO.getLastAuditUserId());
        csCarDealer.setStatus(csCarDealerAuditVO.getAuditType());
        this.updateById(csCarDealer);
        if (!AuditStatusEnum.PASS.getKey().equals(csCarDealerAuditVO.getAuditType())) {
            //审核未通过发送未通过短信
            Result<String> result = systemServiceClient.sendRefuseMessage(csCarDealer.getTel(), csCarDealerAuditVO.getAuditRemarks());
            log.info("短信方式发送商家账号：{}，审核未通过返回结果：{}", csCarDealer.getTel(), result.getMessage());
            return Boolean.TRUE;
        }

        //用户、车商注册添加功能
        Result<UserDTO> user = systemServiceClient.getUserByName(csCarDealer.getTel());
        if (user.isSuccess() && Objects.nonNull(user.getData().getId())) {
            Result<String> result = systemServiceClient.updateUserType(user.getData().getId());
            if (!result.isSuccess()) {
                throw new RiggerException("更新用户类型失败");
            }
            return Boolean.TRUE;
        }
        /**
         * 	审核通过后，生成商家的账号和密码，向sys_user里插入一条记录，
         * 	同时插入sys_user_area表该商家所属区域。密码随机生成，
         * 	不要统一默认，短信方式发送到客户手机上。
         */
        UserVO userVO = buildUser(csCarDealer.getShortName(), RandomUtil.randomString(9), csCarDealer.getTel(), "",
                csCarDealer.getSysAreaId(), UserLevelEnum.SELLER_LEVEL.getLevel());
        Result<Long> addUserResult = systemServiceClient.addUser(userVO);
        if (!addUserResult.isSuccess()) {
            throw new RiggerException("审核失败" + addUserResult.getMessage());
        }
        csCarDealer.setSysUserId(addUserResult.getData());
        this.updateById(csCarDealer);
        //短信方式发送到客户手机上
        Result<String> result = systemServiceClient.sendCarDealerPassword(csCarDealer.getTel(), userVO.getPassword());
        log.info("短信方式发送商家账号：{}，密码返回结果：{}", csCarDealer.getTel(), result.getMessage());
        return Boolean.TRUE;
    }


    @Override
    public List<CsCarDealerDTO> getAllCarDealer(Map<String, Object> params) {
        Integer userType = (Integer) params.get("userType");
        String userName = (String) params.get("userName");
        Long userId = (Long) params.get("userId");
        Assert.notNull(userId, "用户id不能为空");
        Assert.notNull(userType, "用户类型不能为空");
        Assert.notNull(userName, "用户名不能为空");
        if (UserLevelEnum.SELLER_LEVEL.getLevel().equals(userType)) {
            CsCarDealer carDealer = this.getCurrentCarDealerInfo(userName);
            params.put("id", carDealer.getId());
        } else if (UserLevelEnum.INSPECTOR_LEVEL.getLevel().equals(userType)) {
            Result<UserAreaDTO> result = systemServiceClient.getUserAreaByUserId(userId);
            if (result.isSuccess()) {
                params.put("areaId", result.getData().getAreaId());
            }
        }
        return csCarDealerMapper.getAllCarDealer(params);
    }

    @Override
    @Cached(name = "carDealer:getOneByLoginName:", key = "#loginName", expire = 600)
    @CacheRefresh(refresh = 30, stopRefreshAfterLastAccess = 200)
    public Optional<CsCarDealer> getOneByLoginName(String loginName) {
        return Optional.ofNullable(super.selectOne(new EntityWrapper<CsCarDealer>()
                .eq("tel", loginName)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("status", AuditStatusEnum.PASS.getKey())
        ));
    }

    @Override
    public CsCarDealer getCurrentCarDealerInfo(String loginName) {
        Optional<CsCarDealer> optional = this.getOneByLoginName(loginName);
        optional.orElseThrow(() -> new RiggerException("该车商不存在或者进行审核中"));
        CsCarDealer csCarDealer = optional.get();
        if (CommonConstant.STATUS_DEL.equals(csCarDealer.getIsEnabled())) {
            throw new RiggerException("车商被禁用");
        }
        return csCarDealer;
    }

    @Override
    public void setPaymentMethod(Long id, Integer paymentType) {
        CsCarDealer csCarDealer = new CsCarDealer();
        csCarDealer.setId(id);
        csCarDealer.setPaymentType(paymentType);
        this.updateById(csCarDealer);
    }

    @Override
    public String userDealerRegister(UserDealerRegisterVO userDealerRegister) {
        UserVO userVO = buildUser(userDealerRegister.getUsername(), userDealerRegister.getPassword(), userDealerRegister.getTel(), userDealerRegister.getIntroducer(),
                userDealerRegister.getSysAreaId(), UserLevelEnum.USER_LEVEL.getLevel());
        Result<Long> result = systemServiceClient.userRegister(userVO);
        if (!result.isSuccess()) {
            log.error("用户注册失败：{}", result.getMessage());
            throw new RiggerException(result.getMessage());
        }
        if (userDealerRegister.getToCarDealer()) {
            CarDealerRegisterVO dealerRegister = userDealerRegister.getDealer();
            CsCarDealer dealer = BeanUtils.beanCopy(dealerRegister, CsCarDealer.class);
            dealer.setSysUserId(result.getData());
            dealer.setTel(userDealerRegister.getTel());
            dealer.setSysAreaId(userDealerRegister.getSysAreaId());
            dealer.setContactName(userDealerRegister.getUsername());
            try {
                register(dealer);
            } catch (Exception e) {
                log.error("商家注册失败:{}", e);
                throw new RiggerException("商家注册失败，请登录后再申请成为商家");
            }
        }
        return "用户注册成功";
    }

    @Override
    public CsCarDealer getByUserId(Long userId) {
        CsCarDealer carDealer = this.selectOne(new EntityWrapper<CsCarDealer>()
                .eq("sys_user_id", userId)
                .eq("status", AuditStatusEnum.PASS.getKey())
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        Optional.ofNullable(carDealer).orElseThrow(() -> new RiggerException("商家信息不存在或者进行审核中"));
        if (CommonConstant.STATUS_DEL.equals(carDealer.getIsEnabled())) {
            throw new RiggerException("商家信息已被禁用");
        }
        return carDealer;
    }



    /**
     * 构建用户对象
     *
     * @param username
     * @param tel
     * @param introducer
     * @param areaId
     * @param userType
     * @return
     */
    private UserVO buildUser(String username, String password, String tel, String introducer, Long areaId, Integer userType) {
        UserVO userVO = new UserVO();
        userVO.setTel(tel);
        userVO.setType(userType);
        userVO.setLoginName(tel);
        userVO.setAreaId(areaId);
        userVO.setPassword(password);
        userVO.setUsername(username);
        userVO.setIntroducer(introducer);
        userVO.setIsEnabled(EnableDisableEnum.ENABLE.getKey());
        return userVO;
    }

}