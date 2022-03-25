package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.HtglProcessRecord;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.mapper.HtglContractMapper;
import cn.it.mycontract.mapper.HtglProcessRecordMapper;
import cn.it.mycontract.service.HtglProcessRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-15
 */
@Service
public class HtglProcessRecordServiceImpl extends ServiceImpl<HtglProcessRecordMapper, HtglProcessRecord> implements HtglProcessRecordService {

    @Autowired
    HtglContractMapper htglContractMapper;
    @Override
    public SysUser selectNextHandlerNoBack(String cid) {

        SysUser sysUser = htglContractMapper.selectNextHandlerNoBack(cid);
        return sysUser;
    }

    @Override
    public HtglProcessRecord selectNextProcessNoBack(String cid) {
        HtglProcessRecord htglProcessRecord = htglContractMapper.selectNextProcessNoBack(cid);
        return htglProcessRecord;
    }
}
