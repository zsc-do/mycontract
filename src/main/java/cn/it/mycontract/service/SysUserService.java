package cn.it.mycontract.service;

import cn.it.mycontract.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengsc
 * @since 2022-01-30
 */
public interface SysUserService extends IService<SysUser> {


    List<SysUser> selectUserList();

    void addUser(SysUser sysUser, String[] rolesIdStr);

    void updateUser(SysUser sysUser, String[] rolesIdStr);

    List<SysUser> selectBoss();

    SysUser selectLoginUser(String account);
}
