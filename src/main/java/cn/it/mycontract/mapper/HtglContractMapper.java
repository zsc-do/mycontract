package cn.it.mycontract.mapper;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.HtglProcessRecord;
import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengsc
 * @since 2022-02-14
 */
public interface HtglContractMapper extends BaseMapper<HtglContract> {

    SysArea selectLeader(@Param("account") String account);

    List<HtglContract> selectHtqcRecode(@Param("cur") Integer cur,@Param("handlerId") Integer handlerId,
                                        @Param("contractName") String contractName);

    HtglProcessRecord selectHtqcRecode2(@Param("handlerId") Integer handlerId);

    List<HtglContract> queryHtqcPageList(@Param("cur") Integer cur, @Param("operatorId") Integer operatorId,
                                         @Param("contractName") String contractName);

    List<HtglContract> queryHtqdPageList(@Param("cur") Integer cur, @Param("operatorId") Integer operatorId,
                                         @Param("contractName") String contractName);

    SysUser selectNowProcessHandler(@Param("cid") String cid);

    SysUser selectNextHandlerNoBack(@Param("cid") String cid);

    HtglProcessRecord selectNextProcessNoBack(@Param("cid") String cid);
}
