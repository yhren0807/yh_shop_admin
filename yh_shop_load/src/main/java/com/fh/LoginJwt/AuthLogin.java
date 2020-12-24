package com.fh.LoginJwt;



import com.fh.utils.returns.CommonReturn;
import com.fh.utils.returns.ReturnCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 作用：在Aop中进行解析
 */
/*@Component
@Aspect
@Order(2)*/
public class AuthLogin {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginJwtUtils loginJwtUtils;

    private static final String pre_token="ACCESS_TOKEN:";

    @Autowired
    private RedisTemplate redisTemplate;

    //控制返回值
    @Around(value ="execution(public * com.fh.*.controller..*(..)))")
    public Object AroundDay(ProceedingJoinPoint joinPoint) throws Throwable {
        //先获取token值
        String token=request.getHeader("Authorization-token");
        //判断token值 为空没登录
        if (StringUtils.isBlank(token)){
          return  CommonReturn.error(ReturnCode.LOGIN_OUTTIME_ERROR);
        }
        //解析token
        CommonReturn commonReturn = loginJwtUtils.jokeToken(token);
        //判断解析的代码 不等于 110的话就是解析失败 将这个错误的状态码 返回 之后再前端重新登录
        if (commonReturn.getCode()!=110){
            return commonReturn;
        }

        Claims claims= (Claims) commonReturn.getData();
        String userId = String.valueOf(claims.get("userId"));
        String accessKey=pre_token+userId+":"+token;
        //有值为true 无值 false
        if (!redisTemplate.hasKey(accessKey)){
            return CommonReturn.error(ReturnCode.LOGIN_OUTTIME_ERROR);
        }
        Long createTime = System.currentTimeMillis();
        //续签
        redisTemplate.opsForValue().set(accessKey,createTime);
        redisTemplate.expire(accessKey,2,TimeUnit.MINUTES);

        Object proceed=null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }

        return proceed;
    }



}
