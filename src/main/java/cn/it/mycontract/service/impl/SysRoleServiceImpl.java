package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.SysMenu;
import cn.it.mycontract.entity.SysRole;
import cn.it.mycontract.entity.SysRoleMenu;
import cn.it.mycontract.mapper.SysMenuMapper;
import cn.it.mycontract.mapper.SysRoleMapper;
import cn.it.mycontract.mapper.SysRoleMenuMapper;
import cn.it.mycontract.service.SysRoleMenuService;
import cn.it.mycontract.service.SysRoleService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public List<SysRole> selectRoleList() {
        List<SysRole> sysRoles = sysRoleMapper.selectRoleList();
        return sysRoles;
    }

    @Override
    @Transactional
    public void addRole(SysRole sysRole, String[] menusId) {
        sysRoleMapper.insert(sysRole);

        for (String menuId : menusId){

            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(sysRole.getId());
            sysRoleMenu.setMenuId(Integer.parseInt(menuId));
            sysRoleMenuMapper.insert(sysRoleMenu);
        }

    }

    @Override
    @Transactional
    public void updateRole(SysRole sysRole, String[] menusId) {
        sysRoleMapper.update(sysRole,new EntityWrapper<SysRole>()
                .eq("id",sysRole.getId()));


        sysRoleMenuMapper.delete(new EntityWrapper<SysRoleMenu>()
                .eq("role_id",sysRole.getId()));

        for (String menuId : menusId){

            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(sysRole.getId());
            sysRoleMenu.setMenuId(Integer.parseInt(menuId));
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }
}
