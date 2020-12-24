package com.fh.config;/*--------
|姓名:
-------------
|作用:        |
--------------*/

import cn.hutool.json.JSONUtil;
import com.fh.utils.returns.CommonReturn;
import com.fh.utils.returns.ReturnCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonReturn.error(ReturnCode.LOGIN_ERROR)));
        response.getWriter().flush();


    }
}
