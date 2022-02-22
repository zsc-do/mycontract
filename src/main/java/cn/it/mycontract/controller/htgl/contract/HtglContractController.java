package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglProcessRecord;
import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.HtglContractService;
import cn.it.mycontract.service.HtglProcessRecordService;
import cn.it.mycontract.service.SysAreaService;
import cn.it.mycontract.service.SysUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-14
 */
@Controller
public class HtglContractController {
    @Autowired
    public HtglContractService htglContractService;


    @Autowired
    SysAreaService sysAreaService;


    @Autowired
    SysUserService sysUserService;


    @Autowired
    HtglProcessRecordService htglProcessRecordService;

    @RequestMapping("/queryHtqcPageList")
    public String queryHtqcPageList(HttpServletRequest request,Model model){


        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");

        List<HtglContract> htglContracts = htglContractService.selectList(new EntityWrapper<HtglContract>()
                .eq("operator_id",sysUser.getId()));


        model.addAttribute("contracts",htglContracts);


        return "contract/htqc/htqc-query";

    }


    @RequestMapping("/toHtqcAdd")
    public String toHtqcAdd(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");

        SysUser user = sysUserService.selectUserAndArea(sysUser.getId());

        model.addAttribute("user",user);


        return "contract/htqc/htqc-add";
    }


    @ResponseBody
    @RequestMapping("/getAllLeader")
    public List<SysArea> getAllLeader(HttpServletRequest request){

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");

        SysArea sysArea = htglContractService.selectLeader(sysUser.getAccount());


        List<SysArea> nextLeaders = new ArrayList<SysArea>();
        nextLeaders.add(sysArea);


        return nextLeaders;
    }

    @ResponseBody
    @RequestMapping("/getRecieveArea")
    public List<SysArea> getRecieveArea(){

        List<SysArea> sysAreas = sysAreaService.selectList(new EntityWrapper<SysArea>()
                .eq("name", "法规处")
                .or()
                .eq("name", "财务处"));


        return sysAreas;
    }


    @ResponseBody
    @RequestMapping("/getBoss")
    public List<SysUser> getBoss(){

        List<SysUser> sysUsers = sysUserService.selectBoss();

        return sysUsers;
    }








    @RequestMapping("/queryHtqdPageList")
    public String queryHtqdPageList( HttpServletRequest request,
                                     Model model){


        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");

        List<HtglContract> htglContracts = htglContractService.selectList(new EntityWrapper<HtglContract>()
                .eq("operator_id", sysUser.getId())
                .and()
                .eq("flow_status", "2"));

        model.addAttribute("contracts",htglContracts);
        return "contract/htqd/htqd-query";

    }


    @RequestMapping("/signContract")
    public String signContract(@RequestParam("id") String contractId){

        HtglContract htglContract = new HtglContract();
        htglContract.setFlowStatus("3");

        htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                .eq("id",contractId));



        return "contract/htqd/htqd-query";
    }

}
