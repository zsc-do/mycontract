package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.*;
import cn.it.mycontract.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HtsqController {

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

    @Autowired
    HtglOpinionService htglOpinionService;




    @RequestMapping("/queryHtsqPageList")
    public String queryHtsqPageList(HttpServletRequest request,
                                    Model model,
                                    @RequestParam(value="cur",required=false,defaultValue="1") Integer cur){

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");



        List<HtglContract> htglContractList = htglContractService.selectHtqcRecode((cur-1)*10,sysUser.getId());

        model.addAttribute("contracts",htglContractList);
        model.addAttribute("cur",cur);

        return "contract/htsq/htsq-query";
    }




    @RequestMapping("/auditPass")
    public String auditPass(@RequestParam("id") String id,
                            @RequestParam("methodId") String methodId,
                            @RequestParam("opinionContent") String opinionContent,
                            HttpServletRequest request){

        //退回合同
        if (!"2".equals(methodId)){
            returnContract(id,opinionContent,request);
            return "redirect:/queryHtsqPageList";
        }

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");


        List<HtglProcessRecord> processRecords = htglProcessRecordService.selectList(new EntityWrapper<HtglProcessRecord>()
                .eq("contract_id", id)
                .eq("now_handler",sysUser.getId()));


        HtglProcessRecord tempProcessRecord = new HtglProcessRecord();

        for (HtglProcessRecord processRecord :processRecords){
            if ("1".equals(processRecord.getStatus())){
                processRecord.setStatus("2");
                htglProcessRecordService.update(processRecord,new EntityWrapper<HtglProcessRecord>()
                        .eq("id",processRecord.getId()));

                tempProcessRecord = processRecord;
                break;
            }
        }



        List<HtglProcessRecord> processRecords2 = htglProcessRecordService.selectList(new EntityWrapper<HtglProcessRecord>()
                .eq("contract_id", id));

        for (HtglProcessRecord processRecord :processRecords2){

            if (Integer.valueOf(processRecord.getDelSort()).equals(Integer.valueOf(tempProcessRecord.getDelSort()) + 1)){

                processRecord.setStatus("1");
                htglProcessRecordService.update(processRecord,new EntityWrapper<HtglProcessRecord>()
                        .eq("id",processRecord.getId()));
            } else {

                HtglContract htglContract = new HtglContract();
                htglContract.setFlowStatus("2");
                htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                        .eq("id",id));

            }
        }

        HtglOpinion htglOpinion = new HtglOpinion();
        htglOpinion.setContractId(Integer.parseInt(id));
        htglOpinion.setIsPassed("1");
        htglOpinion.setPersonId(sysUser.getId());
        htglOpinion.setPersonName(sysUser.getName());
        htglOpinion.setInputTime(new Date());
        htglOpinion.setAreaName(tempProcessRecord.getAreaName());
        htglOpinion.setOpinionContent(opinionContent);

        htglOpinionService.insert(htglOpinion);

        return "redirect:/queryHtsqPageList";
    }



    public void returnContract(String contractId,String opinionContent,HttpServletRequest request){


        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");


        List<HtglProcessRecord> processRecords = htglProcessRecordService.selectList(new EntityWrapper<HtglProcessRecord>()
                .eq("contract_id", contractId)
                .eq("now_handler",sysUser.getId()));

        HtglProcessRecord tempProcessRecord = new HtglProcessRecord();

        for (HtglProcessRecord processRecord :processRecords){
            if ("1".equals(processRecord.getStatus())){
                tempProcessRecord = processRecord;
                break;
            }
        }

        HtglOpinion htglOpinion = new HtglOpinion();
        htglOpinion.setContractId(Integer.parseInt(contractId));
        htglOpinion.setIsPassed("0");
        htglOpinion.setPersonId(sysUser.getId());
        htglOpinion.setPersonName(sysUser.getName());
        htglOpinion.setInputTime(new Date());
        htglOpinion.setAreaName(tempProcessRecord.getAreaName());
        htglOpinion.setOpinionContent(opinionContent);


        htglProcessRecordService.delete(new EntityWrapper<HtglProcessRecord>()
                .eq("contract_id",contractId));

        HtglContract htglContract = new HtglContract();
        htglContract.setFlowStatus("0");

        htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                .eq("id",contractId));





        htglOpinionService.insert(htglOpinion);


    }


    @RequestMapping("/toHtsqPage")
    public String toHtsqPage(@RequestParam("cid") String cid,Model model){

        HtglContract htglContract = htglContractService.selectList(new EntityWrapper<HtglContract>()
                .eq("id", cid)).get(0);

        List<HtglContractPartener> contractParteners = htglContractPartenerService.selectList(new EntityWrapper<HtglContractPartener>()
                .eq("contract_id", cid));

        model.addAttribute("contract",htglContract);
        model.addAttribute("contractParteners",contractParteners);

        return "contract/htsq/htsq-edit";
    }


    @ResponseBody
    @RequestMapping("/getCountOfSq")
    public Integer getCountOfSq(HttpServletRequest request){

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");

        Integer count = htglProcessRecordService.selectCount(new EntityWrapper<HtglProcessRecord>()
                .eq("now_handler", sysUser.getId())
                .eq("status", "1"));

        return count;
    }

}
