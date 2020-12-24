package com.fh.AopLogs;



import com.fh.Aop.entity.TAop;
import com.fh.Aop.service.ITAopService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/*@Aspect
@Component
@Order(5)*/
public class AopController {


    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ITAopService itAopService;


    //配置切点表达式
    @Pointcut("execution(public * com.fh.*.controller..*(..)))")
    public void BrokerAspect(){
    }


    @Around("BrokerAspect()")
    public Object AroundDay(ProceedingJoinPoint joinPoint) throws Throwable {
        TAop tAop=new TAop();
        System.out.println("我叫AOP");
        Object proceed=null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }
        Date date = new Date();
        tAop.setCreatetime(date);
        //获取访问的方法
        String methodname = joinPoint.getSignature().getName();
        tAop.setMethodname(methodname);
        //获取访问的路径
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        tAop.setUrl(declaringTypeName);
        //获取访问人员的Ip地址
        String header = httpServletRequest.getHeader("Authorization-Ip");
        tAop.setAreaip(header);
        itAopService.save(tAop);
        return proceed;

    }

    public static String getIp(HttpServletRequest request){

//代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");



        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_CLIENT_IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_X_FORWARDED_FOR");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("X-Real-IP");

        }



//如果没有代理，则获取真实ip

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();

        }

        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;

    }

}
