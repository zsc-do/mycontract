package cn.it.mycontract.controller.sys;


import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysRole;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.SysAreaService;
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
                          @RequestParam("leaderId") String leaderId){


        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("0");
        sysArea.setName(areaName);
        sysArea.setLeaderId(Integer.parseInt(leaderId));

        sysAreaService.addArea(sysArea,leaderId);

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
    public String updateArea(@RequestParam("areaId") String areaId,
                             @RequestParam("areaName") String areaName,
                             @RequestParam("leaderId") String leaderId){


        SysArea sysArea = new SysArea();
        sysArea.setId(Integer.parseInt(areaId));
        sysArea.setName(areaName);
        sysArea.setLeaderId(Integer.parseInt(leaderId));

        sysAreaService.updateArea(sysArea,leaderId);





        return "redirect:/queryAreaPageList";
    }


    @ResponseBody
    @RequestMapping("/getAllAreas")
    public List<SysArea> getAllAreas(){
        List<SysArea> sysArea = sysAreaService.selectList(new EntityWrapper<SysArea>()
                .eq("del_flag",0));


        return sysArea;
    }

}
