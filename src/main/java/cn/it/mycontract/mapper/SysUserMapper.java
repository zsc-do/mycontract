package cn.it.mycontract.mapper;

import cn.it.mycontract.entity.SysArea;
import cn.it.mycontract.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    List<SysUser> selectBoss();

    SysUser selectLoginUser(@Param("account") String account);

    SysUser selectUserAndArea(@Param("user_id") Integer id);


    List<SysUser> selectCSUser(@Param("areaName") String areaName);


}
