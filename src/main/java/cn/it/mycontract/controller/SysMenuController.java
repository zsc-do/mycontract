package cn.it.mycontract.controller;


import cn.it.mycontract.entity.SysMenu;
import cn.it.mycontract.service.SysMenuService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class SysMenuController {


    @Autowired
    public SysMenuService sysMenuService;



    @RequestMapping("/queryMenuPageList")
    public String queryMenuPageList(Model model){

        List<SysMenu> sysMenus = sysMenuService.selectMenusList();

        model.addAttribute("menus",sysMenus);


        return "menus/menu-query1";
    }



    @RequestMapping("/toAddMenuPage")
    public String toAddMenuPage(){
        return "menus/menu-add";
    }


    @ResponseBody
    @RequestMapping("/getAllParentMenu")
    public List<SysMenu> getAllParentMenu(){
        List<SysMenu> sysMenus = sysMenuService.selectList(new EntityWrapper<SysMenu>().
                isNull("parent_id")
                .eq("del_flag",0));


        return sysMenus;
    }


    @RequestMapping("/addMenu")
    public String addMenu(@RequestParam("menuName") String menuName,
                          @RequestParam("menuPath") String menuPath,
                          @RequestParam("menuRemark") String menuRemark,
                          @RequestParam("menuParentId") String menuParentId){





        SysMenu sysMenu = new SysMenu();
        sysMenu.setDelFlag("0");
        sysMenu.setName(menuName);
        sysMenu.setHref(menuPath);

        if (!"0".equals(menuParentId)){
            sysMenu.setParentId(menuParentId);
        }
        sysMenu.setRemarks(menuRemark);

        sysMenuService.insert(sysMenu);


        return "redirect:/queryMenuPageList";
    }



    @RequestMapping("/deleteMenu")
    public String deleteMenu(@RequestParam("id") String id){

        SysMenu sysMenu = new SysMenu();
        sysMenu.setDelFlag("1");

        sysMenuService.update(sysMenu,new EntityWrapper<SysMenu>()
                .eq("id",id));

        return "redirect:/queryMenuPageList";
    }



    @RequestMapping("/updateMenuPage")
    public String updateMenuPage(@RequestParam("id") String id,
                                 Model model){


        SysMenu sysMenu = sysMenuService.selectOneMenu(id);

        model.addAttribute("menu",sysMenu);


        return "menus/menu-edit";
    }



    @RequestMapping("/updateMenu")
    public String addMenu(@RequestParam("menuId") String menuId,
                          @RequestParam("menuName") String menuName,
                          @RequestParam("menuPath") String menuPath,
                          @RequestParam("menuRemark") String menuRemark,
                          @RequestParam("menuParentId") String menuParentId){





        SysMenu sysMenu = new SysMenu();
        sysMenu.setName(menuName);
        sysMenu.setHref(menuPath);

        if (!"0".equals(menuParentId)){
            sysMenu.setParentId(menuParentId);
        }
        sysMenu.setRemarks(menuRemark);

        sysMenuService.update(sysMenu,new EntityWrapper<SysMenu>()
                .eq("id",Integer.parseInt(menuId))
                .eq("del_flag","0"));


        return "redirect:/queryMenuPageList";
    }


}
