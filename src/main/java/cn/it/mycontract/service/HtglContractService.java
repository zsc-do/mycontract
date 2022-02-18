package cn.it.mycontract.service;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.SysArea;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-14
 */
public interface HtglContractService extends IService<HtglContract> {

    SysArea selectLeader(String account);

    void saveHtqc(HtglContract htglContract,
                  String partenerjia, String parteneryi,
                  String leaderId,String departmentsId,String bossId);

}
