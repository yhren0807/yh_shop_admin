package com.fh.t_ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fh.t_ums.entity.UmsAdmin;
import com.fh.t_ums.entity.UmsMenu;
import com.fh.t_ums.entity.UmsResource;
import com.fh.t_ums.entity.UmsRole;
import com.fh.t_ums.mapper.UmsAdminMapper;
import com.fh.t_ums.service.IUmsAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fh.utils.SecurityJwtUtils;
import com.fh.utils.returns.CommonReturn;
import com.fh.utils.returns.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author yhren
 * @since 2020-12-07
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService {

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    //注入存放用户信息的类
    @Autowired
    private UserDetailsService userDetailsService;
    //注入验证密码的方法
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SecurityJwtUtils securityJwtUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String pre_token="ACCESS_TOKEN:";
    private static final String pre_login="LOGIN:";

    @Override
    public List<UmsResource> queryResourceByAdmin(Long id, String username) {
        String resourcekey=pre_login+username+":resource";
        if (redisTemplate.hasKey(resourcekey)){
            return (List<UmsResource>) redisTemplate.opsForValue().get(resourcekey);
        }
        List<UmsResource> umsResources = umsAdminMapper.queryResourceByAdmin(id);
        redisTemplate.opsForValue().set(resourcekey,umsResources);
        redisTemplate.expire(resourcekey,2,TimeUnit.HOURS);
        return umsResources;
    }

    @Override
    public List<UmsRole> queryRoleByAdmin(Long id, String username) {
        String roleKey=pre_login+username+":role";
        if (redisTemplate.hasKey(roleKey)){
          return (List<UmsRole>) redisTemplate.opsForValue().get(roleKey);
        }
        List<UmsRole> umsRoles = umsAdminMapper.queryRoleByAdmin(id);
        redisTemplate.opsForValue().set(roleKey,umsRoles);
        redisTemplate.expire(roleKey,2,TimeUnit.HOURS);
        return umsRoles;
    }

    @Override
    public String login(String username, String password) {
        String token=null;
        try {
             clearToken(username);
            //通过用户名称获取对应的数据
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //判断密码
            if (!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            //这个就是要存入我们上下文的对象
            UsernamePasswordAuthenticationToken userToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            //将我们这个对象放到上下文中
            SecurityContextHolder.getContext().setAuthentication(userToken);
            //声明token的值
            token = securityJwtUtils.createToken(userDetails);
//登陆成功生成token
            String create = String.valueOf(System.currentTimeMillis());
            String keys=pre_token+userDetails.getUsername()+":"+token;
            redisTemplate.opsForValue().set(keys,create);
            redisTemplate.expire(keys,2,TimeUnit.HOURS);
        }catch (Exception e){
            System.out.println("登陆异常");
            return "500";
        }
        return token;
    }

    private void clearToken(String username) {
        //删除上一次的token值
        String tokenkey=pre_token+username+":*";
        Set<String> keys = redisTemplate.keys(tokenkey);
        if (keys.size()>0){
            redisTemplate.delete(keys);
        }
        //删除数据
        String loginKey=pre_login+username+":*";
        Set<String> loginKeys = redisTemplate.keys(loginKey);
        if (loginKeys.size()>0){
            redisTemplate.delete(loginKeys);
        }
    }

    @Override
    public UmsAdmin queryAdminByUserName(QueryWrapper<UmsAdmin> name,String username) {
         String adminKey=pre_login+username+":user";
         if (redisTemplate.hasKey(adminKey)){
             return (UmsAdmin) redisTemplate.opsForValue().get(adminKey);
         }
        UmsAdmin one = getOne(name);
        redisTemplate.opsForValue().set(adminKey,one);
        redisTemplate.expire(adminKey,2,TimeUnit.HOURS);
        return one;
    }

    @Override
    public List<Map<String, Object>> queryMenu(String username) {
        List<UmsMenu> menuList=umsAdminMapper.queryMenuByAdmin(username);
        return getItems(menuList,0L);
    }

    @Override
    public void loginOut(String username1) {
        clearToken(username1);
    }

    private List<Map<String,Object>> getItems(List<UmsMenu> menuList, Long pid) {

        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        menuList.forEach(umsMenu -> {
            Map<String,Object> map=null;
            if (umsMenu.getParentId().equals(pid)){
                map=new HashMap<String,Object>();
                map.put("icon",umsMenu.getIcon());
                map.put("index",umsMenu.getName());
                map.put("title",umsMenu.getTitle());
                if (umsMenu.getLevel()==0){
                    map.put("subs",getItems(menuList,umsMenu.getId()));
                }

            }
            if (map!=null){
               list.add(map);
            }
        });
        return list;
    }
}
