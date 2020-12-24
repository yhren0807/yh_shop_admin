package com.fh.utils;
/*--------
|姓名:
-------------
|作用:        |
--------------*/

import com.alibaba.fastjson.JSON;
import com.fh.utils.returns.CommonReturn;
import com.fh.utils.returns.ReturnCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**--------
|姓名:
-------------
|作用:生成token值|
--------------*/
@Component
public class SecurityJwtUtils {
    //导入我们的配置文件的值
    @Value("${user.jwtset.signature}")
    private String signature;
    @Value("${user.jwtset.exptime}")
    private Long exptime;
    @Value("${user.jwtset.alg}")
    private String alg;
    @Value("${user.jwtset.type}")
    private String type;


    public String createToken(UserDetails userDetails){
        /*-----------------------
         *  token的头部信        |
         -----------------------*/
        Map<String,Object> headMap=new HashMap<>();
        //HS256 设置加密规则
        headMap.put("alg",alg);
        //JWT 指明声明token值的类型
        headMap.put("type",type);

        /*-----------------------
         *  有效负载 里边放的是用户的信息|
         -----------------------*/
        Map<String,Object> payLoadMap=new HashMap<>();
        payLoadMap.put("username",userDetails.getUsername());
        payLoadMap.put("createTime",new Date());
         /*-----------------------
         *  设置失效时间|
         -----------------------*/
        long startTime=System.currentTimeMillis();
        long endTime=startTime+exptime;
          /*-----------------------
         *  签名 相当于标签 生成JWT|
         -----------------------*/
        String token = Jwts.builder()
                .setHeader(headMap)
                .setClaims(payLoadMap)
                .setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
         return token;
    }
    public CommonReturn getToken(String token){

        Map<String,Object> map=new HashMap<>();
        try {
            Claims claims = Jwts.parser()
                    //输入验证标签
                    .setSigningKey(signature)
                    //输入token值
                    .parseClaimsJws(token)
                    .getBody();
            return CommonReturn.success(claims);
        }catch (Exception e){
            e.printStackTrace();
            return CommonReturn.error(ReturnCode.LOGIN_OUTTIME_ERROR);
        }
    }

    public String getUsername(String token){

        Map<String,Object> map=new HashMap<>();
        try {
            Claims claims = Jwts.parser()
                    //输入验证标签
                    .setSigningKey(signature)
                    //输入token值
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("username");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
