package com.haoqi.magic.system;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.haoqi.magic.system.model.dto.MenuDTO;
import com.haoqi.magic.system.model.entity.*;
import com.haoqi.magic.system.model.vo.UserVO;
import com.haoqi.magic.system.service.*;
import com.haoqi.rigger.core.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MagicSystemApplication.class)
public class MagicSystemApplicationTests {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysPermissionService permissionService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysRolePermissionService rolePermissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ICsAccountService accountService;

    @Test
    public void testUserSaveFormCongtroller() {
        String url = "http://127.0.0.1:13000/user/getUserRolesByName/hq_admin";
        UserVO userVO = new UserVO();
        userVO.setLoginName("carDealer");
        userVO.setPassword("admin");
        RestTemplate restTemplate = new RestTemplate();
        String body = JSONUtil.toJsonStr(userVO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        ResponseEntity response = restTemplate.postForEntity(url, entity, Result.class);
        Assert.assertTrue(response.getStatusCode().OK.value() == 200);
    }

    //	@Test
    public void testUserSave() {
        String salt = RandomUtil.randomString(32);
        String pass = DigestUtil.bcrypt("123456" + salt);
        SysUser user = new SysUser();
        user.setCreator(0L);
        user.setModifier(0L);
        user.setGmtCreate(DateUtil.date());
        user.setGmtModified(DateUtil.date());
        user.setLoginName("hq_admin");
        user.setType(1);
        user.setSalt(salt);
        user.setPassword(pass);
        userService.insert(user);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setCreator(0L);
        sysUserRole.setModifier(0L);
        sysUserRole.setGmtCreate(DateUtil.date());
        sysUserRole.setGmtModified(DateUtil.date());
        sysUserRole.setSysUserId(user.getId());
        sysUserRole.setSysRoleId(1L);
        userRoleService.insert(sysUserRole);
    }

    //	@Test
//	@Transactional(rollbackFor = Exception.class)
    public void testRoleSave() {
        /*SysRole role = new SysRole();
        role.setCreator(0L);
		role.setModifier(0L);
		role.setGmtCreate(DateUtil.date());
		role.setGmtModified(DateUtil.date());
		role.setRoleName("超级管理员");
		role.setRoleCode(PinyinUtil.getPinYin("超级管理员"));
		roleService.insert(role);*/

        List<SysPermission> permissions = permissionService.selectList(new EntityWrapper<>());
        permissions.forEach(permission -> {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setCreator(0L);
            rolePermission.setModifier(0L);
            rolePermission.setGmtCreate(DateUtil.date());
            rolePermission.setGmtModified(DateUtil.date());
            rolePermission.setSysRoleId(1L);
            rolePermission.setSysPermissionId(permission.getId());
            rolePermissionService.insert(rolePermission);
        });
    }

    @Test
    public void testFindMenuByRoleCodes() {
        String[] strings = new String[]{"guanliyuanA", "400kefu"};
        List<MenuDTO> menuDTOS = permissionService.findMenuByRoleCodes(0L, strings);
        List<MenuDTO> m = menuDTOS.stream().distinct().collect(Collectors.toList());
        Assert.assertNotNull(menuDTOS);
    }

    @Test
    public void getUserWithRoleByLoginName() {
        userService.getUserWithRoleByLoginName("hq_admin");
    }

    @Test
    public void testRedisScan() {
        Cursor d = (Cursor) redisTemplate.execute(new RedisCallback<Object>() {
            RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                ScanOptions options = ScanOptions.scanOptions().count(1000).match("user:*").build();
                return new ConvertingCursor<>(connection.scan(options), redisSerializer::deserialize);
            }
        });

        List<String> keys = Lists.newArrayList();
        while (d.hasNext()) {
            keys.add((String) d.next());
        }
        //user:getUserWithRoleByLoginName:hq_admin
        redisTemplate.delete(keys);
        Assert.assertNotNull(d);
    }


    @Test
    public void testAccountSave() {
        CsAccount account = new CsAccount();
        account.setModifier(1L);
        account.setRemark("12133333");
//        accountService.insert(account);
        boolean r = accountService.update(account, new EntityWrapper<CsAccount>()
//                .isNull("remark")
                .eq("remark","1213")
                .eq("id", 1L));
        System.out.println("MagicSystemApplicationTests.testAccountSave === " + r);

    }
}
