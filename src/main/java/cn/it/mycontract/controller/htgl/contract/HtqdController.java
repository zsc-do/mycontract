package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglContractPartener;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HtqdController {


    @Autowired
    public HtglContractService htglContractService;


    @Autowired
    SysAreaService sysAreaService;


    @Autowired
    SysUserService sysUserService;


    @Autowired
    HtglProcessRecordService htglProcessRecordService;

    @Autowired
    HtglContractPartenerService htglContractPartenerService;


    @RequestMapping("/queryHtqdPageList")
    public String queryHtqdPageList( HttpServletRequest request,
                                     Model model,
                                     @RequestParam(value="cur",required=false,defaultValue="1") Integer cur,
                                     @RequestParam(value="contractName",required=false) String contractName){


        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");


       List<HtglContract> htglContracts = htglContractService.queryHtqdPageList((cur-1)*10,sysUser.getId(),contractName);



        model.addAttribute("contracts",htglContracts);

        model.addAttribute("cur",cur);

        return "contract/htqd/htqd-query";

    }


    @RequestMapping("toSignContractPage")
    public String toSignContractPage(@RequestParam("cid") String cid,
                                     Model model){

        HtglContract htglContract = htglContractService.selectList(new EntityWrapper<HtglContract>()
                .eq("id", cid)).get(0);

        List<HtglContractPartener> contractParteners = htglContractPartenerService.selectList(new EntityWrapper<HtglContractPartener>()
                .eq("contract_id", cid));

        model.addAttribute("contract",htglContract);
        model.addAttribute("contractParteners",contractParteners);

        return "contract/htqd/htqd-edit";

    }


    @RequestMapping("/signContract")
    public String signContract(@RequestParam("id") String contractId){

        HtglContract htglContract = new HtglContract();
        htglContract.setFlowStatus("3");

        htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                .eq("id",contractId));



        return "redirect:/queryHtqdPageList";
    }



}
