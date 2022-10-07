package cn.it.mycontract.mobile;


import cn.it.mycontract.entity.*;
import cn.it.mycontract.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class MobileController {


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



    @RequestMapping("/mobile/mobileQueryHtsqPageList")
    @ResponseBody
    public List<HtglContract> mobileQueryHtsqPageList(HttpServletRequest request,
                                                      @RequestParam(value="cur",required=false,defaultValue="1") Integer cur,
                                                      @RequestParam(value="contractName",required=false) String contractName,
                                                      @RequestParam(value="flowStatus",required=false) String flowStatus){

        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");



        List<HtglContract> htglContractList = htglContractService.selectHtqcRecode((cur-1)*10,sysUser.getId(),contractName);


        return htglContractList;
    }


    @RequestMapping("/mobile/toHtsqPage")
    public String toHtsqPage(@RequestParam("cid") String cid,Model model){

        HtglContract htglContract = htglContractService.selectList(new EntityWrapper<HtglContract>()
                .eq("id", cid)).get(0);

        List<HtglContractPartener> contractParteners = htglContractPartenerService.selectList(new EntityWrapper<HtglContractPartener>()
                .eq("contract_id", cid));

        model.addAttribute("contract",htglContract);
        model.addAttribute("contractParteners",contractParteners);

        return "/mobile/mhtsq-edit";
    }



    @RequestMapping("/mobile/auditPass")
    public String auditPass(@RequestParam("id") String id,
                            @RequestParam("methodId") String methodId,
                            @RequestParam("opinionContent") String opinionContent,
                            HttpServletRequest request){

        //退回合同
        if (!"2".equals(methodId)){
            returnContract(methodId,id,opinionContent,request);
            return "mobile/mhtsq.html";
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

        return "mobile/mhtsq.html";
    }



    public void returnContract(String methodId,String contractId,String opinionContent,HttpServletRequest request){


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


        //处理完全退回
        if ("1".equals(methodId)){
            htglProcessRecordService.delete(new EntityWrapper<HtglProcessRecord>()
                    .eq("contract_id",contractId));

            HtglContract htglContract = new HtglContract();
            htglContract.setFlowStatus("0");

            htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                    .eq("id",contractId));
        }

        //处理非完全退回
        if ("0".equals(methodId)){
            tempProcessRecord.setStatus("0");
            htglProcessRecordService.update(tempProcessRecord,new EntityWrapper<HtglProcessRecord>()
                    .eq("id",tempProcessRecord.getId()));

            HtglContract htglContract = new HtglContract();
            htglContract.setFlowStatus("-1");

            htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                    .eq("id",contractId));

        }

        htglOpinionService.insert(htglOpinion);


    }


    @RequestMapping("/mobile/mobileQueryHtqdPageList")
    @ResponseBody
    public List<HtglContract> queryHtqdPageList( HttpServletRequest request,
                                     Model model,
                                     @RequestParam(value="cur",required=false,defaultValue="1") Integer cur,
                                     @RequestParam(value="contractName",required=false) String contractName){


        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");


        List<HtglContract> htglContracts = htglContractService.queryHtqdPageList((cur-1)*10,sysUser.getId(),contractName);





        return htglContracts;

    }



    @RequestMapping("/mobile/toSignContractPage")
    public String toSignContractPage(@RequestParam("cid") String cid,
                                     Model model){

        HtglContract htglContract = htglContractService.selectList(new EntityWrapper<HtglContract>()
                .eq("id", cid)).get(0);

        List<HtglContractPartener> contractParteners = htglContractPartenerService.selectList(new EntityWrapper<HtglContractPartener>()
                .eq("contract_id", cid));

        model.addAttribute("contract",htglContract);
        model.addAttribute("contractParteners",contractParteners);

        return "mobile/mhtqd-edit.html";

    }


    @RequestMapping("/mobile/signContract")
    public String signContract(@RequestParam("id") String contractId,
                               @RequestParam("htsmjFile") MultipartFile file){

        HtglContract htglContract = new HtglContract();
        htglContract.setFlowStatus("3");

        htglContractService.update(htglContract,new EntityWrapper<HtglContract>()
                .eq("id",contractId));





        String uuid = UUID.randomUUID().toString().replaceAll("-", "");


        int pos = file.getOriginalFilename().lastIndexOf('.');
        String houZui = "";
        if (pos > -1) {
            houZui = file.getOriginalFilename().substring(pos);
        }

//        String filePath = "D:\\contractUpload\\htsmj\\"+uuid + houZui;


        String filePath = "/opt/contractUpload/htsmj/"+uuid + houZui;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HtglFile htglFile = new HtglFile();
        htglFile.setFileName(file.getOriginalFilename());
        htglFile.setContractId(Integer.parseInt(contractId));
        htglFile.setFilePath(filePath);
        htglFile.setStatus(1);
        htglFile.setType("2");

        htglFileService.insert(htglFile);



        return "mobile/mhtqd.html";
    }



    @RequestMapping("/mobile/toHtsq")
    public String toHtsq(){
        return "mobile/mhtsq.html";
    }



    @RequestMapping("/mobile/toHtqd")
    public String toHtqd(){
        return "mobile/mhtqd.html";
    }



}
