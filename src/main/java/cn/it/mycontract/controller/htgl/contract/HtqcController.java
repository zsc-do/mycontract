package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglContractPartener;
import cn.it.mycontract.service.HtglContractPartenerService;
import cn.it.mycontract.service.HtglContractService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import cn.it.mycontract.vo.htglContractVo;

import javax.validation.Valid;

@Controller
public class HtqcController {

    @Autowired
    HtglContractService htglContractService;


    @Autowired
    HtglContractPartenerService htglContractPartenerService;



    /*
    * @RequestParam("contractName") String contractName,
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
    *
    * */

    /*
    * 保存合同
    */
    @RequestMapping("/addContract")
    public String addContract(@Valid htglContractVo htglContractVo,BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            System.out.println("有校验错误");

            for (FieldError error : bindingResult.getFieldErrors()){
                System.out.println(error.getDefaultMessage());//错误信息
                System.out.println(error.getField());//错误的字段名
            }
            return "redirect:/queryHtqcPageList";
        }


        HtglContract htglContract = new HtglContract();
        htglContract.setName(htglContractVo.getContractName());
        htglContract.setSponsorName(htglContractVo.getSponsorName());
        htglContract.setOperatorName(htglContract.getOperatorName());
        htglContract.setBudgetCode(htglContractVo.getBudgetCode());
        htglContract.setContractMoney(htglContractVo.getContractMoney());
        htglContract.setAuthorization(htglContractVo.getAuthorization());
        htglContract.setContractCnt(htglContractVo.getContractCnt());


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date contractDraftTime = null;

        try {
            contractDraftTime = sdf1.parse(htglContractVo.getDraftTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        htglContract.setDraftTime(contractDraftTime);

        htglContractService.saveHtqc(htglContract,htglContractVo.getPartenerName(),
                htglContractVo.getLeaderId(),htglContractVo.getDepartmentsId(),
                htglContractVo.getBossId());



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
