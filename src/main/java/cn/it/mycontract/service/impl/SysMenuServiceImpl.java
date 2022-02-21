package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.SysMenu;
import cn.it.mycontract.mapper.SysMenuMapper;
import cn.it.mycontract.service.SysMenuService;
import cn.it.mycontract.util.TreeUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengsc
 * @since 2022-01-30
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    @Autowired
    SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getMenu(String account) {


        List<SysMenu> sysMenus = sysMenuMapper.selectMenu(account);


        List<SysMenu> tree = TreeUtils.formatTree(sysMenus, false);


        return tree;
    }

    @Override
    public List<SysMenu> selectMenusList() {

        List<SysMenu> sysMenus = sysMenuMapper.selectMenuList();
        return sysMenus;
    }

    @Override
    public SysMenu selectOneMenu(String id) {

        SysMenu sysMenu = sysMenuMapper.selectOneMenu(id);
        return sysMenu;
    }

    @Override
    public ArrayList<String> selectParentMenuId(List<String> sonMenus) {

        ArrayList<String> partentMenuIds = sysMenuMapper.selectParentMenuId(sonMenus);

        return partentMenuIds;

    }


}
