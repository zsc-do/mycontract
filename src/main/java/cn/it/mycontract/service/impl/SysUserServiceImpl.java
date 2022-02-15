package cn.it.mycontract.service.impl;

import cn.it.mycontract.entity.SysRole;
import cn.it.mycontract.entity.SysRoleMenu;
import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.entity.SysUserRole;
import cn.it.mycontract.mapper.SysUserMapper;
import cn.it.mycontract.mapper.SysUserRoleMapper;
import cn.it.mycontract.service.SysUserService;
import cn.it.mycontract.util.SaltUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengsc
 * @since 2022-01-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public List<SysUser> selectUserList() {

        List<SysUser> sysUsers = sysUserMapper.selectUserList();
        return sysUsers;
    }

    @Override
    @Transactional
    public void addUser(SysUser sysUser, String[] rolesId) {

        //处理业务调用dao
        //1.生成随机盐
        String salt = SaltUtils.getSalt(8);
        //2.将随机盐保存到数据
        sysUser.setSalt(salt);
        //3.明文密码进行md5 + salt + hash散列
        Md5Hash md5Hash = new Md5Hash(sysUser.getPassword(),salt,1024);
        sysUser.setPassword(md5Hash.toHex());
        //4.dao层保存注册数据
        sysUserMapper.insert(sysUser);


        for (String roleId : rolesId){

            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(Integer.parseInt(roleId));


            sysUserRoleMapper.insert(sysUserRole);
        }

    }

    @Override
    @Transactional
    public void updateUser(SysUser sysUser, String[] rolesId) {

        sysUserMapper.update(sysUser,new EntityWrapper<SysUser>()
                .eq("id",sysUser.getId()));


        sysUserRoleMapper.delete(new EntityWrapper<SysUserRole>()
                .eq("user_id",sysUser.getId()));

        for (String roleId : rolesId){


            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(Integer.parseInt(roleId));


            sysUserRoleMapper.insert(sysUserRole);

        }

    }

    @Override
    public List<SysUser> selectBoss() {

        List<SysUser> sysUsers = sysUserMapper.selectBoss();
        return sysUsers;
    }
}
