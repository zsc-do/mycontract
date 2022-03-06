package cn.it.mycontract.service;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysUser;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

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
                  SysArea sysArea,
                  String opinionContent,
                  MultipartFile file);

    List<HtglContract> selectHtqcRecode(Integer cur,Integer handlerId,String contractName);

    List<HtglContract> queryHtqcPageList(Integer cur, Integer operatorId,String contractName);

    List<HtglContract> queryHtqdPageList(Integer cur, Integer operatorId,String contractName);

}
