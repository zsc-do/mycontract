package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglContractPartener;
import cn.it.mycontract.entity.HtglProcessRecord;
import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.mapper.HtglContractMapper;
import cn.it.mycontract.mapper.HtglContractPartenerMapper;
import cn.it.mycontract.mapper.HtglProcessRecordMapper;
import cn.it.mycontract.mapper.SysAreaMapper;
import cn.it.mycontract.service.HtglContractService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public SysArea selectLeader(String account) {

        SysArea sysArea = htglContractMapper.selectLeader(account);
        return sysArea;
    }

    @Override
    @Transactional
    public void saveHtqc(HtglContract htglContract,
                         String partenerName,
                         String leaderId,String departmentsId,String bossId) {

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
            htglProcessRecordMapper.insert(processRecord2);

            cur2 ++;
        }




        HtglProcessRecord processRecord3 = new HtglProcessRecord();
        processRecord3.setContractId(htglContract.getId());
        processRecord3.setNowHandler(Integer.parseInt(bossId));
        processRecord3.setStatus("0");
        processRecord3.setDelSort(cur2.toString());
        htglProcessRecordMapper.insert(processRecord3);



    }

    @Override
    public List<HtglContract> selectHtqcRecode(Integer id) {

        List<HtglContract> htglContractList = htglContractMapper.selectHtqcRecode(id);
        return htglContractList;
    }


}
