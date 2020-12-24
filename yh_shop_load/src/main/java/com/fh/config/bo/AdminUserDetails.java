package com.fh.config.bo;/*--------
|姓名:任英豪
-------------
|作用:        |
--------------*/

import com.fh.t_ums.entity.UmsAdmin;
import com.fh.t_ums.entity.UmsResource;
import com.fh.t_ums.entity.UmsRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//这个类的组要作用就是放我们的用户信息 以及用户所拥有的权限角色的信息

public class AdminUserDetails implements UserDetails {

    private UmsAdmin umsAdmin;
    private List<UmsResource> umsResourceList;
    private List<UmsRole> umsRoleList;

    public AdminUserDetails(UmsAdmin umsAdmin,List<UmsResource> umsResourceList,List<UmsRole> umsRoleList){
        this.umsAdmin=umsAdmin;
        this.umsResourceList=umsResourceList;
        this.umsRoleList=umsRoleList;
    }

    //设置用户拥有的权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //将角色与权限放到这个集合中并进行返回 简单来说就是将这些角色和权限交给我们的Security框架
        List<SimpleGrantedAuthority> list=new ArrayList<SimpleGrantedAuthority>();
        umsResourceList.forEach(umsResource -> {
            list.add(new SimpleGrantedAuthority(umsResource.getKeyword()));
        });
        umsRoleList.forEach(umsRole -> {
            list.add(new SimpleGrantedAuthority("ROLE_"+umsRole.getKeyword()));
        });

        return list;
    }

    //用户密码 交给Security
    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    //用户名称 交给Security
    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    //是否设置超时时间
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //设置用户状态
    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
