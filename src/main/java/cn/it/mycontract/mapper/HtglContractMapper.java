package cn.it.mycontract.mapper;

import cn.it.mycontract.entity.HtglContract;
import cn.it.mycontract.entity.SysArea;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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
}
