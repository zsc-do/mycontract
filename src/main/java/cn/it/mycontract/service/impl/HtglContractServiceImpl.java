package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.*;
import cn.it.mycontract.mapper.*;
import cn.it.mycontract.service.HtglContractService;
import cn.it.mycontract.service.HtglOpinionService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-14
 */
@Service
public class HtglContractServiceImpl extends ServiceImpl<HtglContractMapper, HtglContract> implements HtglContractService {


    @Autowired
    HtglContractMapper htglContractMapper;


    @Autowired
    HtglContractPartenerMapper htglContractPartenerMapper;

    @Autowired
    SysAreaMapper sysAreaMapper;

    @Autowired
    HtglProcessRecordMapper htglProcessRecordMapper;

    @Autowired
    HtglOpinionService htglOpinionService;

    @Autowired
    HtglFileMapper htglFileMapper;

    @Override
    public SysArea selectLeader(String account) {

        SysArea sysArea = htglContractMapper.selectLeader(account);
        return sysArea;
    }

    @Override
    @Transactional
    public void saveHtqc(HtglContract htglContract,
                         String partenerName,
                         String leaderId,
                         String departmentsId,
                         String bossId,
                         SysArea userArea,
                         String opinionContent,
                         MultipartFile file) {

        htglContractMapper.insert(htglContract);


        String[] partenerNameSplit = partenerName.split(",");

        String[] s = {"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
        Integer cur = 0;

        for (String pName : partenerNameSplit){
            HtglContractPartener htglContractPartener = new HtglContractPartener();
            htglContractPartener.setContractId(htglContract.getId());
            htglContractPartener.setPartenerName(pName);
            htglContractPartener.setPartenerType(s[cur] + "方");

            htglContractPartenerMapper.insert(htglContractPartener);

            cur ++;
        }



        HtglProcessRecord processRecord1 = new HtglProcessRecord();
        processRecord1.setContractId(htglContract.getId());
        processRecord1.setNowHandler(Integer.parseInt(leaderId));
        processRecord1.setStatus("1");
        processRecord1.setDelSort("1");
        processRecord1.setAreaName(userArea.getName());
        htglProcessRecordMapper.insert(processRecord1);


        String[] departmentIdSplit = departmentsId.split(",");


        Integer cur2 = 2;
        for (String deptId : departmentIdSplit){
            SysArea sysArea = sysAreaMapper.selectList(new EntityWrapper<SysArea>()
                    .eq("id", deptId)).get(0);

            HtglProcessRecord processRecord2 = new HtglProcessRecord();
            processRecord2.setContractId(htglContract.getId());
            processRecord2.setNowHandler(sysArea.getLeaderId());
            processRecord2.setStatus("0");
            processRecord2.setDelSort(cur2.toString());
            processRecord2.setAreaName(sysArea.getName());
            htglProcessRecordMapper.insert(processRecord2);

            cur2 ++;
        }




        HtglProcessRecord processRecord3 = new HtglProcessRecord();
        processRecord3.setContractId(htglContract.getId());
        processRecord3.setNowHandler(Integer.parseInt(bossId));
        processRecord3.setStatus("0");
        processRecord3.setDelSort(cur2.toString());
        processRecord3.setAreaName("局级");
        htglProcessRecordMapper.insert(processRecord3);


        HtglOpinion htglOpinion = new HtglOpinion();
        htglOpinion.setContractId(htglContract.getId());
        htglOpinion.setIsPassed("1");
        htglOpinion.setPersonId(htglContract.getOperatorId());
        htglOpinion.setPersonName(htglContract.getOperatorName());
        htglOpinion.setInputTime(new Date());
        htglOpinion.setAreaName(htglContract.getSponsorName());
        htglOpinion.setOpinionContent(opinionContent);

        htglOpinionService.insert(htglOpinion);


        uploadHTZW(file,htglContract);

    }


    //上传合同正文
    private void uploadHTZW(MultipartFile file,HtglContract htglContract) {
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

        HtglFile htglFile = new HtglFile();
        htglFile.setFileName(file.getOriginalFilename());
        htglFile.setContractId(htglContract.getId());
        htglFile.setFilePath(filePath);
        htglFile.setStatus(1);

        htglFileMapper.insert(htglFile);
    }



    @Override
    public List<HtglContract> selectHtqcRecode(Integer cur,Integer handlerId,String contractName) {

        List<HtglContract> htglContractList = htglContractMapper.selectHtqcRecode(cur,handlerId,contractName);

        return htglContractList;
    }

    @Override
    public List<HtglContract> queryHtqcPageList(Integer cur, Integer operatorId,String contractName) {

        List<HtglContract> htglContractList = htglContractMapper.queryHtqcPageList(cur,operatorId,contractName);

        return htglContractList;
    }

    @Override
    public List<HtglContract> queryHtqdPageList(Integer cur, Integer operatorId,String contractName) {
        List<HtglContract> htglContractList = htglContractMapper.queryHtqdPageList(cur,operatorId,contractName);

        return htglContractList;
    }


    public static void main(String[] args) {
        String fname = "文件的名字郑少畅-应聘求职分析报告.docx";
        int pos = fname.lastIndexOf('.');

        if (pos > -1) {

            System.out.println(fname.substring(pos));


        } else {

            System.out.println(fname);
        }



    }

}
