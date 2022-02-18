package cn.it.mycontract.controller.sys;


import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.SysAreaService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-13
 */
@Controller
public class SysAreaController {


    @Autowired
    SysAreaService sysAreaService;

    @RequestMapping("/queryAreaPageList")
    public String queryAreaPageList(Model model){

        List<SysArea> sysAreas = sysAreaService.selectAreaList();


        model.addAttribute("areas",sysAreas);


        return "area/area-query";
    }


    @RequestMapping("/toAddAreaPage")
    public String toAddAreaPage(){
        return "area/area-add";
    }



    @RequestMapping("/addArea")
    public String addUser(@RequestParam("areaName") String areaName,
                          @RequestParam("usersId") String usersId){


        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("0");
        sysArea.setName(areaName);
        sysArea.setLeaderId(Integer.parseInt(usersId));

        sysAreaService.insert(sysArea);

        return "redirect:/queryAreaPageList";

    }



    @RequestMapping("/deleteArea")
    public String deleteArea(@RequestParam("id") String id){

        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("1");
        sysAreaService.update(sysArea,new EntityWrapper<SysArea>()
                .eq("id",id));

        return "redirect:/queryAreaPageList";
    }



    @RequestMapping("/updateAreaPage")
    public String updateUserPage(@RequestParam("id") String id,
                                 Model model){


        SysArea sysArea = sysAreaService.selectById(id);
        model.addAttribute("area",sysArea);


        return "area/area-edit";
    }


    @RequestMapping("/updateArea")
    public String updateUser(@RequestParam("areaId") String areaId,
                             @RequestParam("areaName") String areaName,
                             @RequestParam("usersId") String usersId){


        SysArea sysArea = new SysArea();
        sysArea.setName(areaName);
        sysArea.setLeaderId(Integer.parseInt(usersId));

        sysAreaService.update(sysArea,new EntityWrapper<SysArea>()
                .eq("id",areaId));





        return "redirect:/queryAreaPageList";
    }

}
