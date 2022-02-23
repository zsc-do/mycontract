package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.*;
import cn.it.mycontract.service.*;
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
import java.util.ArrayList;
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
    public HtglContractService htglContractService;


    @Autowired
    SysAreaService sysAreaService;


    @Autowired
    SysUserService sysUserService;


    @Autowired
    HtglProcessRecordService htglProcessRecordService;

    @Autowired
    HtglContractPartenerService htglContractPartenerService;



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
    * 前往合同被退回后修改页面
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


    @RequestMapping("updateContract")
    public String updateContract(@RequestParam("cid") String cid,
                                 @RequestParam("leaderId") String leaderId,
                                 @RequestParam("departmentsId") String departmentsId,
                                 @RequestParam("bossId") String bossId){

        //修改附件代码
        //······



        HtglContract htglContract = new HtglContract();
        htglContract.setFlowStatus("1");

        htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                .eq("id",cid));


        HtglProcessRecord processRecord1 = new HtglProcessRecord();
        processRecord1.setContractId(Integer.parseInt(cid));
        processRecord1.setNowHandler(Integer.parseInt(leaderId));
        processRecord1.setStatus("1");
        processRecord1.setDelSort("1");
        htglProcessRecordService.insert(processRecord1);


        String[] departmentIdSplit = departmentsId.split(",");


        Integer cur2 = 2;
        for (String deptId : departmentIdSplit){
            SysArea sysArea = sysAreaService.selectList(new EntityWrapper<SysArea>()
                    .eq("id", deptId)).get(0);

            HtglProcessRecord processRecord2 = new HtglProcessRecord();
            processRecord2.setContractId(Integer.parseInt(cid));
            processRecord2.setNowHandler(sysArea.getLeaderId());
            processRecord2.setStatus("0");
            processRecord2.setDelSort(cur2.toString());
            htglProcessRecordService.insert(processRecord2);

            cur2 ++;
        }




        HtglProcessRecord processRecord3 = new HtglProcessRecord();
        processRecord3.setContractId(Integer.parseInt(cid));
        processRecord3.setNowHandler(Integer.parseInt(bossId));
        processRecord3.setStatus("0");
        processRecord3.setDelSort(cur2.toString());
        htglProcessRecordService.insert(processRecord3);



        return "redirect:/queryHtqcPageList";
    }

    @ResponseBody
    @RequestMapping("/getMatchLeader")
    public SysArea getMatchLeader(@RequestParam("sponsorId") String sponsorId){

        SysArea sysAreas = sysAreaService.getMatchLeader(sponsorId);


        return sysAreas;
    }
}
