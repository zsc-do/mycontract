package cn.it.mycontract.service;

import cn.it.mycontract.entity.HtglProcessRecord;
import cn.it.mycontract.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-15
 */
public interface HtglProcessRecordService extends IService<HtglProcessRecord> {

    SysUser selectNextHandlerNoBack(String cid);

    HtglProcessRecord selectNextProcessNoBack(String cid);

}
