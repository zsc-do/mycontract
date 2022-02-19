package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglContractPartener;
import cn.it.mycontract.service.HtglContractPartenerService;
import cn.it.mycontract.service.HtglContractService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HtqcController {

    @Autowired
    HtglContractService htglContractService;


    @Autowired
    HtglContractPartenerService htglContractPartenerService;




    /*
    * 保存合同
    */
    @RequestMapping("/addContract")
    public String addContract(@RequestParam("contractName") String contractName,
                              @RequestParam("sponsorName") String sponsorName,
                              @RequestParam("operatorName") String operatorName,
                              @RequestParam("draftTime") String draftTime,
                              @RequestParam("budgetCode") String budgetCode,
                              @RequestParam("contractMoney") String contractMoney,
                              @RequestParam("authorization") String authorization,
                              @RequestParam("contractCnt") String contractCnt,
                              @RequestParam("partenerName") String partenerName,
                              @RequestParam("leaderId") String leaderId,
                              @RequestParam("departmentsId") String departmentsId,
                              @RequestParam("bossId") String bossId
    ){



        HtglContract htglContract = new HtglContract();
        htglContract.setName(contractName);
        htglContract.setSponsorName(sponsorName);
        htglContract.setOperatorName(operatorName);
        htglContract.setBudgetCode(budgetCode);
        htglContract.setContractMoney(contractMoney);
        htglContract.setAuthorization(authorization);
        htglContract.setContractCnt(contractCnt);


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date contractDraftTime = null;

        try {
            contractDraftTime = sdf1.parse(draftTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        htglContract.setDraftTime(contractDraftTime);

        htglContractService.saveHtqc(htglContract,partenerName,leaderId,departmentsId,bossId);



        return "redirect:/queryHtqcPageList";
    }





    @RequestMapping("/toHtqcDetail")
    public String toHtqcDetail(@RequestParam("cid") String cid,
                               Model model){
        HtglContract htglContract = htglContractService.selectList(new EntityWrapper<HtglContract>()
                .eq("id", cid)).get(0);

        List<HtglContractPartener> contractParteners = htglContractPartenerService.selectList(new EntityWrapper<HtglContractPartener>()
                .eq("contract_id", cid));

        model.addAttribute("contract",htglContract);
        model.addAttribute("contractParteners",contractParteners);



        return "contract/htqc/htqc-detail";
    }


    /*
    * 前往合同被退回后重新起草页面
    * */
    @RequestMapping("/toHtqcEdit")
    public String toHtqcEdit(@RequestParam("cid") String cid,
                             Model model){

        HtglContract htglContract = htglContractService.selectList(new EntityWrapper<HtglContract>()
                .eq("id", cid)).get(0);

        List<HtglContractPartener> contractParteners = htglContractPartenerService.selectList(new EntityWrapper<HtglContractPartener>()
                .eq("contract_id", cid));

        model.addAttribute("contract",htglContract);
        model.addAttribute("contractParteners",contractParteners);



        return "contract/htqc/htqc-edit";
    }
}
