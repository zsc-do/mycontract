package cn.it.mycontract.service;

import cn.it.mycontract.entity.SysRole;
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
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> selectRoleList();

    void addRole(SysRole sysRole, List<String> menusId);

    void updateRole(SysRole sysRole, List<String> menusId);
}
