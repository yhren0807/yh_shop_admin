package com.fh.Expen;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//这个注解是springMVC提供的注解 RestControllerAdvice 在我们的程序只要抛异常就会执行
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void noRightException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
            //如果是ajax请求的话将我们定义的标识传到我们的前端
            response.getWriter().print("noRight");
            System.out.print(e.toString());
    }

}
