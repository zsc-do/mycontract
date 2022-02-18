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
                         String partenerjia, String parteneryi,
                         String leaderId,String departmentsId,String bossId) {

        htglContractMapper.insert(htglContract);

        HtglContractPartener htglContractPartenerjia = new HtglContractPartener();
        HtglContractPartener htglContractParteneryi = new HtglContractPartener();


        htglContractPartenerjia.setContractId(htglContract.getId());
        htglContractPartenerjia.setPartenerType("甲方");
        htglContractPartenerjia.setPartenerName(partenerjia);

        htglContractParteneryi.setContractId(htglContract.getId());
        htglContractParteneryi.setPartenerType("乙方");
        htglContractParteneryi.setPartenerName(parteneryi);



        htglContractPartenerMapper.insert(htglContractPartenerjia);
        htglContractPartenerMapper.insert(htglContractParteneryi);

        HtglProcessRecord processRecord1 = new HtglProcessRecord();
        processRecord1.setContractId(htglContract.getId());
        processRecord1.setNowHandler(Integer.parseInt(leaderId));
        processRecord1.setStatus("1");
        processRecord1.setDelSort("1");
        htglProcessRecordMapper.insert(processRecord1);


        Integer departmentLeaderId = sysAreaMapper.selectList(new EntityWrapper<SysArea>()
                .eq("id", departmentsId)).get(0).getLeaderId();

        HtglProcessRecord processRecord2 = new HtglProcessRecord();
        processRecord2.setContractId(htglContract.getId());
        processRecord2.setNowHandler(departmentLeaderId);
        processRecord2.setStatus("0");
        processRecord2.setDelSort("2");
        htglProcessRecordMapper.insert(processRecord2);


        HtglProcessRecord processRecord3 = new HtglProcessRecord();
        processRecord3.setContractId(htglContract.getId());
        processRecord3.setNowHandler(Integer.parseInt(bossId));
        processRecord3.setStatus("0");
        processRecord3.setDelSort("3");
        htglProcessRecordMapper.insert(processRecord3);

    }


}
