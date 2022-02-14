package cn.it.mycontract.mapper;

import cn.it.mycontract.entity.SysUser;
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
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> selectUserList();
}
