package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.mapper.HtglContractMapper;
import cn.it.mycontract.service.HtglContractService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public SysArea selectLeader(String account) {

        SysArea sysArea = htglContractMapper.selectLeader(account);
        return sysArea;
    }
}
