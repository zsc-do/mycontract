package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.*;
import cn.it.mycontract.mapper.SysUserMapper;
import cn.it.mycontract.service.*;
import cn.it.mycontract.vo.htglContractVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    HtglOpinionService htglOpinionService;

    @Autowired
    HtglFileService htglFileService;


    @Autowired
    SysUserMapper sysUserMapper;


    @RequestMapping("/queryHtqcPageList")
    public String queryHtqcPageList(HttpServletRequest request,Model model,
                                    @RequestParam(value="cur",required=false,defaultValue="1") Integer cur,
                                    @RequestParam(value="contractName",required=false) String contractName){


        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");





        List<HtglContract> htglContracts = htglContractService.queryHtqcPageList((cur-1)*10,sysUser.getId(),contractName);


        model.addAttribute("contracts",htglContracts);

        model.addAttribute("cur",cur);



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
    * 保存起草的合同
    */
    @RequestMapping("/addContract")
    public String addContract(@Valid htglContractVo htglContractVo, BindingResult bindingResult,
                              @RequestParam("opinionContent") String opinionContent,
                              HttpServletRequest request,
                              @RequestParam("htzwFile") MultipartFile file){



        System.out.println("文件的名字"+file.getOriginalFilename());


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

        } catch (ParseException e) {
            e.printStackTrace();
        }
        htglContract.setDraftTime(contractDraftTime);

        htglContract.setFlowStatus("1");

        htglContractService.saveHtqc(htglContract,htglContractVo.getPartenerName(),
                htglContractVo.getLeaderId(),htglContractVo.getDepartmentsId(),
                htglContractVo.getBossId(),sysArea,opinionContent,file);




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

        String flowStatus = htglContract.getFlowStatus();

        if ("-1".equals(flowStatus)){

           SysUser sysUser =  htglProcessRecordService.selectNextHandlerNoBack(cid);

           model.addAttribute("nextHandler",sysUser);

        }

        model.addAttribute("contract",htglContract);
        model.addAttribute("contractParteners",contractParteners);



        return "contract/htqc/htqc-edit";
    }


    @RequestMapping("updateContract")
    public String updateContract(@RequestParam("cid") String cid,
                                 @RequestParam(value = "leaderId",required = false) String leaderId,
                                 @RequestParam(value = "departmentsId",required = false) String departmentsId,
                                 @RequestParam(value = "bossId",required = false) String bossId,
                                 @RequestParam("sponsorId") String sponsorId,
                                 @RequestParam("opinionContent") String opinionContent,
                                 @RequestParam("htzwFile") MultipartFile file,
                                 @RequestParam("flowStatus") String flowStatus,
                                 HttpServletRequest request){





        //修改附件代码
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        int pos = file.getOriginalFilename().lastIndexOf('.');
        String houZui = "";
        if (pos > -1) {
            houZui = file.getOriginalFilename().substring(pos);
        }

        String filePath = "D:\\contractUpload\\"+uuid + houZui;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }


        HtglFile htglFileOld = new HtglFile();
        htglFileOld.setStatus(0);
        htglFileService.update(htglFileOld,new EntityWrapper<HtglFile>()
                .eq("contract_id",cid)
                .eq("type","1"));


        HtglFile htglFileNew = new HtglFile();
        htglFileNew.setFileName(file.getOriginalFilename());
        htglFileNew.setContractId(Integer.parseInt(cid));
        htglFileNew.setFilePath(filePath);
        htglFileNew.setStatus(1);
        htglFileNew.setType("1");
        htglFileService.insert(htglFileNew);




        //处理完全退回合同
        if ("0".equals(flowStatus)){

            HtglContract htglContract = new HtglContract();
            htglContract.setFlowStatus("1");

            htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                    .eq("id",cid));


            SysArea userArea = sysAreaService.selectOne(new EntityWrapper<SysArea>()
                    .eq("id", sponsorId));

            HtglProcessRecord processRecord1 = new HtglProcessRecord();
            processRecord1.setContractId(Integer.parseInt(cid));
            processRecord1.setNowHandler(Integer.parseInt(leaderId));
            processRecord1.setStatus("1");
            processRecord1.setDelSort("1");
            processRecord1.setAreaName(userArea.getName());
            htglProcessRecordService.insert(processRecord1);


            String[] departmentIdSplit = departmentsId.split(",");


            Integer cur2 = 2;
            for (String deptId : departmentIdSplit){
                SysArea sysArea = sysAreaService.selectList(new EntityWrapper<SysArea>()
                        .eq("id", deptId)).get(0);


                List<SysUser> sysCSUsers = sysUserMapper.selectCSUser(sysArea.getName());

                if (sysCSUsers != null){
                    for (SysUser sysCSUser : sysCSUsers){
                        HtglProcessRecord processRecord = new HtglProcessRecord();
                        processRecord.setContractId(Integer.parseInt(cid));
                        processRecord.setNowHandler(sysCSUser.getId());
                        processRecord.setStatus("0");
                        processRecord.setDelSort(cur2.toString());
                        processRecord.setAreaName(sysArea.getName());
                        htglProcessRecordService.insert(processRecord);

                        cur2 ++;
                    }
                }



                HtglProcessRecord processRecord2 = new HtglProcessRecord();
                processRecord2.setContractId(Integer.parseInt(cid));
                processRecord2.setNowHandler(sysArea.getLeaderId());
                processRecord2.setStatus("0");
                processRecord2.setDelSort(cur2.toString());
                processRecord2.setAreaName(sysArea.getName());
                htglProcessRecordService.insert(processRecord2);

                cur2 ++;
            }




            HtglProcessRecord processRecord3 = new HtglProcessRecord();
            processRecord3.setContractId(Integer.parseInt(cid));
            processRecord3.setNowHandler(Integer.parseInt(bossId));
            processRecord3.setStatus("0");
            processRecord3.setDelSort(cur2.toString());
            processRecord3.setAreaName("局级");
            htglProcessRecordService.insert(processRecord3);
        }


        //处理非完全退回合同
        if ("-1".equals(flowStatus)){
            HtglProcessRecord htglProcessRecord = htglProcessRecordService.selectNextProcessNoBack(cid);
            htglProcessRecord.setStatus("1");
            htglProcessRecordService.update(htglProcessRecord,new EntityWrapper<HtglProcessRecord>()
                    .eq("id",htglProcessRecord.getId()));

            HtglContract htglContract = new HtglContract();
            htglContract.setFlowStatus("1");

            htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                    .eq("id",cid));
        }


        HtglContract ContractForOpinion = htglContractService.selectOne(new EntityWrapper<HtglContract>()
                .eq("id", cid));

        HtglOpinion htglOpinion = new HtglOpinion();
        htglOpinion.setContractId(Integer.parseInt(cid));
        htglOpinion.setIsPassed("1");
        htglOpinion.setPersonId(ContractForOpinion.getOperatorId());
        htglOpinion.setPersonName(ContractForOpinion.getOperatorName());
        htglOpinion.setInputTime(new Date());
        htglOpinion.setAreaName(ContractForOpinion.getSponsorName());
        htglOpinion.setOpinionContent(opinionContent);

        htglOpinionService.insert(htglOpinion);



        return "redirect:/queryHtqcPageList";
    }

    @ResponseBody
    @RequestMapping("/getMatchLeader")
    public SysArea getMatchLeader(@RequestParam("sponsorId") String sponsorId){

        SysArea sysAreas = sysAreaService.getMatchLeader(sponsorId);


        return sysAreas;
    }


    @ResponseBody
    @RequestMapping("/getCountOfQd")
    public Integer getCountOfQd(HttpServletRequest request){

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");

        Integer count = htglContractService.selectCount(new EntityWrapper<HtglContract>()
                .eq("operator_id", sysUser.getId())
                .eq("flow_status", "2"));

        return count;

    }


    @ResponseBody
    @RequestMapping("/getCountOfTh")
    public Integer getCountOfTh(HttpServletRequest request){

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");

        Integer count = htglContractService.selectCount(new EntityWrapper<HtglContract>()
                .eq("operator_id", sysUser.getId())
                .eq("flow_status", "0")
                .or()
                .eq("flow_status", "-1"));

        return count;

    }
}
