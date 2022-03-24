package cn.it.mycontract.controller.htgl.contract;


import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglContractPartener;
import cn.it.mycontract.entity.HtglFile;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.service.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    HtglFileService htglFileService;


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

        String filePath = "D:\\contractUpload\\htsmj\\"+uuid + houZui;

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



        return "redirect:/queryHtqdPageList";
    }



}
