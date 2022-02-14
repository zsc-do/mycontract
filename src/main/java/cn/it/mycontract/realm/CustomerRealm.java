package cn.it.mycontract.realm;

import cn.it.mycontract.entity.SysUser;
import cn.it.mycontract.mapper.SysUserMapper;
import cn.it.mycontract.service.SysUserService;
import cn.it.mycontract.util.ApplicationContextUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

public class CustomerRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //根据身份信息
        String principal = (String) token.getPrincipal();
        //在工厂中获取service对象
        SysUserMapper sysUserMapper = (SysUserMapper) ApplicationContextUtils.getBean("sysUserMapper");
        //根据身份信息查询
        SysUser sysUser1 = new SysUser();
        sysUser1.setAccount(principal);

        SysUser sysUser = sysUserMapper.selectOne(sysUser1);

        if(!ObjectUtils.isEmpty(sysUser)){
            //返回数据库信息
            return new SimpleAuthenticationInfo(sysUser.getAccount(),sysUser.getPassword(),
                    ByteSource.Util.bytes(sysUser.getSalt()),this.getName());
        }
        return null;
    }
}