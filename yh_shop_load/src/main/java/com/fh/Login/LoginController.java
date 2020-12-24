package com.fh.Login;/*--------
|姓名:
-------------
|作用:登录认证        |
--------------*/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.LoginJwt.LoginJwtUtils;
import com.fh.t_ums.entity.UmsAdmin;
import com.fh.t_ums.service.IUmsAdminService;
import com.fh.utils.returns.CommonReturn;
import com.fh.utils.returns.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("LoginController")
public class LoginController {

@Autowired
private IUmsAdminService umsAdminService;
    @Autowired
    private RedisTemplate redisTemplate;

//用户登陆方法
    @PostMapping("login")
    public CommonReturn login(@RequestParam("password") String password,@RequestParam("username")String username){

        UmsAdmin usm = umsAdminService.getOne(new QueryWrapper<UmsAdmin>().eq("username",username));
        //判断账户
        if (usm==null){
           return CommonReturn.error(ReturnCode.ERROR_USER_NO);
        }
        String token=umsAdminService.login(username,password);

        if (token.equals("500")){
            return CommonReturn.error(ReturnCode.LOGIN_OUTTIME_ERROR);
        }
        return CommonReturn.success(ReturnCode.SUCCESS_LOGIN_YES,token);
    }

}
