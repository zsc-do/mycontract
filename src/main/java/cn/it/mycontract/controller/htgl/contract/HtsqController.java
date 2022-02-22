package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglContractPartener;
import cn.it.mycontract.entity.HtglProcessRecord;
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
import java.util.ArrayList;
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




    @RequestMapping("/queryHtsqPageList")
    public String queryHtsqPageList(HttpServletRequest request,
                                    Model model){

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");

        List<HtglContract> htglContractList = htglContractService.selectHtqcRecode(sysUser.getId());

        model.addAttribute("contracts",htglContractList);

        return "contract/htsq/htsq-query";
    }


    @RequestMapping("/auditPass")
    public String auditPass(@RequestParam("id") String id, HttpServletRequest request){

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


        return "redirect:/queryHtsqPageList";
    }



    @RequestMapping("/returnContract")
    public String returnContract(@RequestParam("id") String contractId){



        htglProcessRecordService.delete(new EntityWrapper<HtglProcessRecord>()
                .eq("contract_id",contractId));

        HtglContract htglContract = new HtglContract();
        htglContract.setFlowStatus("0");

        htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                .eq("id",contractId));


        return "redirect:/queryHtsqPageList";
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

}
