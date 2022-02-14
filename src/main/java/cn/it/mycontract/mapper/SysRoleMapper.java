package cn.it.mycontract.mapper;

import cn.it.mycontract.entity.SysRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhengsc
 * @since 2022-01-30
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRoleList();

}
