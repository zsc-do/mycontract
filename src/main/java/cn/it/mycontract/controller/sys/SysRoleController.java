package cn.it.mycontract.controller.sys;


import cn.it.mycontract.entity.SysMenu;
import cn.it.mycontract.entity.SysRole;
import cn.it.mycontract.service.SysMenuService;
import cn.it.mycontract.service.SysRoleService;
import cn.it.mycontract.service.SysUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengsc
 * @since 2022-01-30
 */
@Controller
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping("/queryRolePageList")
    public String queryRolePageList(Model model){

        List<SysRole> sysRoles = sysRoleService.selectRoleList();


        model.addAttribute("roles",sysRoles);



        return "role/role-query";
    }


    @RequestMapping("/toAddRolePage")
    public String toAddRolePage(){
        return "role/role-add";
    }


    @RequestMapping("/addRole")
    public String addRole(@RequestParam("roleName") String roleName,
                          @RequestParam("roleRemarks") String roleRemarks,
                          @RequestParam("menuIds") String menuIds){


        List<String> menusList = new ArrayList<String>(Arrays.asList(menuIds.split(",")));
        List<String> partentMenuIds = sysMenuService.selectParentMenuId(menusList);


        menusList.addAll(partentMenuIds);


        SysRole sysRole = new SysRole();
        sysRole.setDelFlag("0");
        sysRole.setName(roleName);
        sysRole.setRemarks(roleRemarks);


        sysRoleService.addRole(sysRole,menusList);



        return "redirect:/queryRolePageList";

    }


    @RequestMapping("/deleteRole")
    public String deleteRole(@RequestParam("id") String id){

        SysRole sysRole = new SysRole();
        sysRole.setDelFlag("1");
        sysRoleService.update(sysRole,new EntityWrapper<SysRole>()
                .eq("id",id));




        return "redirect:/queryRolePageList";
    }


    @RequestMapping("/updateRolePage")
    public String updateRolePage(@RequestParam("id") String id,
                                 Model model){


        SysRole sysRole = sysRoleService.selectById(id);


        model.addAttribute("role",sysRole);


        return "role/role-edit";
    }


    @RequestMapping("/updateRole")
    public String updateRole(@RequestParam("roleId") String roleId,
                             @RequestParam("roleName") String roleName,
                             @RequestParam("roleRemarks") String roleRemarks,
                             @RequestParam("menuIds") String menuIds){


        List<String> menusList = new ArrayList<String>(Arrays.asList(menuIds.split(",")));
        List<String> partentMenuIds = sysMenuService.selectParentMenuId(menusList);


        menusList.addAll(partentMenuIds);



        SysRole sysRole = new SysRole();
        sysRole.setId(Integer.parseInt(roleId));
        sysRole.setName(roleName);
        sysRole.setRemarks(roleRemarks);


        sysRoleService.updateRole(sysRole,menusList);



        return "redirect:/queryRolePageList";
    }


    @ResponseBody
    @RequestMapping("/getAllRoles")
    public List<SysRole> getAllRoles(){
        List<SysRole> sysRoles = sysRoleService.selectList(new EntityWrapper<SysRole>()
                .eq("del_flag",0));


        return sysRoles;
    }

}

