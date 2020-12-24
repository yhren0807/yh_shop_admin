package com.fh.config;/*--------
|姓名:
-------------
|作用:        |
--------------*/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.config.bo.AdminUserDetails;
import com.fh.t_ums.entity.UmsAdmin;
import com.fh.t_ums.entity.UmsResource;
import com.fh.t_ums.entity.UmsRole;
import com.fh.t_ums.service.IUmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IUmsAdminService umsAdminService;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;


    /**
     * 拦截器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/uploadController/**","/LoginController/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest()
                .authenticated();
        //禁用缓存
        http.headers().cacheControl();
        //添加jwt token验证
        //jwtAuthenticationTokenFilter 我们自定义的拦截器类 通过@Bean的方式注入进来
        //UsernamePasswordAuthenticationFilter Security的过滤器主要是针对post请求的表单提交
        http.addFilterBefore(jwtAuthenticationTokenFilter(),UsernamePasswordAuthenticationFilter.class);

//添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     *用户登录授权
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //将我们封装好的信息交给我们的框架进行管理
    auth.userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder());
    }
    @Bean
    public UserDetailsService userDetailsService(){
        //获取用户信息
        return username->{
            UmsAdmin admin = umsAdminService.queryAdminByUserName(new QueryWrapper<UmsAdmin>().eq("username", username),username);
            if (admin!=null){
               List<UmsResource> umsResourcesList=umsAdminService.queryResourceByAdmin(admin.getId(),username);
                List<UmsRole> roleList=umsAdminService.queryRoleByAdmin(admin.getId(),username);
                return new AdminUserDetails(admin,umsResourcesList,roleList);
            }
            throw  new UsernameNotFoundException("用户不存在");
        };
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
       return new JwtAuthenticationTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
