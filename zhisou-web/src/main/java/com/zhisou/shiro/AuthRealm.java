package com.zhisou.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhisou.api.model.SysUser;
import com.zhisou.api.service.SysUserService;

public class AuthRealm extends AuthorizingRealm{
    
	@Autowired
	private SysUserService userService;
    
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权");
        //获取当前用户
        SysUser user = (SysUser)principals.fromRealm(getName()).iterator().next();
        //得到权限字符串
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

//        info.addRole("admin");
//        Set<Role> roles = user.getRoles();
//        List<String> list = new ArrayList();
//        for(Role role :roles){
//            Set<Module> modules = role.getModules();
//            for(Module m:modules){
//                if(m.getCtype()==0){
//                    //说明是主菜单
//                    list.add(m.getCpermission());
//                }
//            }
//        }

//        info.addStringPermissions(list);
        return info;
    }
    //认证  登录
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;

//        User user = userService.findUserByName(upToken.getUsername());
        SysUser user = new SysUser();
        user.setLoginName("cuixiang");
        user.setPassword("123456");
        if(user==null){
            return null;
        }else{
            AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return info;
        }

    }

}
