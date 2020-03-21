package com.haoqi.magic.business.service.impl;

import cn.hutool.core.lang.Assert;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.common.utils.TreeUtil;
import com.haoqi.magic.business.mapper.CsCarCheckItemMapper;
import com.haoqi.magic.business.model.dto.CsCarCheckItemDTO;
import com.haoqi.magic.business.model.entity.CsCarCheckItem;
import com.haoqi.magic.business.model.vo.CarCheckItemTree;
import com.haoqi.magic.business.service.ICsCarCheckItemService;
import com.haoqi.rigger.common.util.PinyinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 车辆检测项 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
@Service
public class CsCarCheckItemServiceImpl extends ServiceImpl<CsCarCheckItemMapper, CsCarCheckItem> implements ICsCarCheckItemService {

    @Autowired
    private CsCarCheckItemMapper csCarCheckItemMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delCarCheckItemById(Long id) {
        Assert.notNull(id, "id not be null");
        this.deleteById(id);
        CsCarCheckItem item = new CsCarCheckItem();
        item.setParentId(id);
        //遍历删除
        List<CsCarCheckItem> csCarCheckItems = this.selectList(new EntityWrapper<>(item));
        csCarCheckItems.forEach(
                items -> {
                    deleteByParentId(items.getId());
                }
        );
        Set<String> keys = redisTemplate.keys("carCheckItem:*");
        redisTemplate.delete(keys);
        return Boolean.TRUE;
    }

    private void deleteByParentId(Long id) {
        CsCarCheckItem checkItem = this.selectById(id);
        CsCarCheckItem item = new CsCarCheckItem();
        item.setParentId(checkItem.getId());
        this.deleteById(id);
        List<CsCarCheckItem> csCarCheckItems = this.selectList(new EntityWrapper<>(item));
        csCarCheckItems.forEach(items -> {
            deleteByParentId(items.getId());
        });
    }

    @Override
    /*@Cached(name = "carCheckItem:getCarCheckItemById:", key = "#id", expire = 7200)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600)*/
    public CsCarCheckItem getCarCheckItemById(Long id) {
        Assert.notNull(id, "id not be null");
        return csCarCheckItemMapper.selectById(id);
    }

    @Override
    @Cached(name = "carCheckItem:getCheckItemWithParentItemNameById:", key = "#id", expire = 7200)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600)
    public CsCarCheckItemDTO getCheckItemWithParentItemNameById(Long id) {
        return csCarCheckItemMapper.getCheckItemWithParentItemNameById(id);
    }

    @Override
    public Boolean saveOrUpdate(CsCarCheckItem carCheckItem) {
        String code = String.format("%s_%s", carCheckItem.getType(), PinyinUtil.getPinYin(carCheckItem.getName()));
        carCheckItem.setCode(code);
        return this.insertOrUpdate(carCheckItem);
    }

    @Override
    @Cached(name = "carCheckItem:findByIds:", key = "#ids.toArray()", expire = 7200)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600)
    public List<CsCarCheckItem> findByIds(List<Long> ids) {
        Assert.notEmpty(ids, "检查项ids不能为空");
        return csCarCheckItemMapper.selectBatchIds(ids);
    }

    @Override
    @Cached(name = "carCheckItem:findByType:", key = "#type", expire = 7200)
    @CacheRefresh(refresh = 3, stopRefreshAfterLastAccess = 300)
    public List<CarCheckItemTree> findByType(Integer type) {
        CsCarCheckItem checkItem = new CsCarCheckItem();
        checkItem.setType(type);
        List<CsCarCheckItem> csCarCheckItems = csCarCheckItemMapper.selectList(new EntityWrapper<CsCarCheckItem>(checkItem)
                .orderBy("type").orderBy("order_no").orderBy("id")
        );
        return getCarCheckItemTree(csCarCheckItems, 0L);
    }

    @Override
    @Cached(name = "carCheckItem:findCheckItemByType:", key = "#type", expire = 7200)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600)
    public List<CsCarCheckItem> findCheckItemByType(Integer type) {
        CsCarCheckItem checkItem = new CsCarCheckItem();
        checkItem.setType(type);
        return csCarCheckItemMapper.selectList(new EntityWrapper<CsCarCheckItem>(checkItem));
    }

    private List<CarCheckItemTree> getCarCheckItemTree(List<CsCarCheckItem> checkItems, long root) {
        List<CarCheckItemTree> list = new ArrayList<>();
        CarCheckItemTree tree = null;
        for (CsCarCheckItem checkItem : checkItems) {
            tree = new CarCheckItemTree();
            tree.setId(checkItem.getId());
            tree.setCode(checkItem.getCode());
            tree.setName(checkItem.getName());
            tree.setType(checkItem.getType());
            tree.setOrderNo(checkItem.getOrderNo());
            tree.setParentId(checkItem.getParentId());
            list.add(tree);
        }
        return TreeUtil.build(list, root);
    }
}
