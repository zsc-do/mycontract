package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglContractPartener;
import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.HtglContractPartenerService;
import cn.it.mycontract.service.HtglContractService;
import cn.it.mycontract.service.SysAreaService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import cn.it.mycontract.vo.htglContractVo;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HtqcController {

    @Autowired
    HtglContractService htglContractService;


    @Autowired
    HtglContractPartenerService htglContractPartenerService;

    @Autowired
    SysAreaService sysAreaService;




    /*
    * 保存合同
    */
    @RequestMapping("/addContract")
    public String addContract(@Valid htglContractVo htglContractVo, BindingResult bindingResult,
                              HttpServletRequest request){


        if (bindingResult.hasErrors()){
            System.out.println("有校验错误");

            for (FieldError error : bindingResult.getFieldErrors()){
                System.out.println(error.getDefaultMessage());//错误信息
                System.out.println(error.getField());//错误的字段名
            }
            return "redirect:/queryHtqcPageList";
        }


        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");


        HtglContract htglContract = new HtglContract();
        htglContract.setName(htglContractVo.getContractName());
        htglContract.setBudgetCode(htglContractVo.getBudgetCode());
        htglContract.setContractMoney(htglContractVo.getContractMoney());
        htglContract.setAuthorization(htglContractVo.getAuthorization());
        htglContract.setContractCnt(htglContractVo.getContractCnt());



        htglContract.setSponsorId(Integer.parseInt(htglContractVo.getSponsorId()));

        SysArea sysArea = sysAreaService.selectOne(new EntityWrapper<SysArea>()
                .eq("id", htglContractVo.getSponsorId()));
        htglContract.setSponsorName(sysArea.getName());


        htglContract.setOperatorName(htglContractVo.getOperatorName());
        htglContract.setOperatorId(sysUser.getId());


        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date contractDraftTime = null;

        try {
            contractDraftTime = sdf1.parse(htglContractVo.getDraftTime());

            //利用Calendar 实现 Date日期+1天
            Calendar c = Calendar.getInstance();
            c.setTime(contractDraftTime);
            c.add(Calendar.DAY_OF_MONTH, 1);

            contractDraftTime = c.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        htglContract.setDraftTime(contractDraftTime);

        htglContract.setFlowStatus("1");

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


    @ResponseBody
    @RequestMapping("/getMatchLeader")
    public SysArea getMatchLeader(@RequestParam("sponsorId") String sponsorId){

        SysArea sysAreas = sysAreaService.getMatchLeader(sponsorId);


        return sysAreas;
    }
}
