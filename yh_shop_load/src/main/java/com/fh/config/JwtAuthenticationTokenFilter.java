package com.fh.config;


import com.fh.utils.SecurityJwtUtils;
import com.fh.utils.returns.CommonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*--------
|姓名:
-------------
|作用:自定义登陆拦截        |
--------------*/
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityJwtUtils securityJwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String pre_token="ACCESS_TOKEN:";

    //拦截器类
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //先获取token值
        String token=request.getHeader("Authorization-token");
        //解析token值
        CommonReturn com = securityJwtUtils.getToken(token);
        //判断token值得一个状态 和 上下文中得值是否存在
        if (com.getCode()==110 && SecurityContextHolder.getContext().getAuthentication()==null){
            //得出我们得用户名称
            String username = securityJwtUtils.getUsername(token);
            //根据名称得出对象
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            String accessKey=pre_token+userDetails.getUsername()+":"+token;
            if (redisTemplate.hasKey(accessKey)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                Long createTime = System.currentTimeMillis();
                //续签
                redisTemplate.opsForValue().set(accessKey,createTime);
                redisTemplate.expire(accessKey,2,TimeUnit.HOURS);
            }

        }
        filterChain.doFilter(request, response);
    }
}
