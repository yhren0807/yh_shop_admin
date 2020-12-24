package com.fh.config;/*--------
|姓名:
-------------
|作用:        |
--------------*/

import cn.hutool.json.JSONUtil;
import com.fh.utils.returns.CommonReturn;
import com.fh.utils.returns.ReturnCode;
import jdk.nashorn.internal.ir.Assignment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonReturn.error(ReturnCode.POWER_ERROR)));
        response.getWriter().flush();
    }
}
