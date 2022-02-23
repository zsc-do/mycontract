package cn.it.mycontract.service;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

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
                  String partenerName,
                  String leaderId,
                  String departmentsId,
                  String bossId,
                  SysArea sysArea);

    List<HtglContract> selectHtqcRecode(Integer id);

}
