package com.poc.common.logging.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class LoggingConfig {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Around("execution(* com.poc..*.*.service..*.*(..)) " +
            "|| execution(* com.poc..*.*.controller..*.*(..)) ")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        Object proceed = pjp.proceed();

        long executionTime = System.currentTimeMillis() - start;

        String packageName = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        Logger LOGGER = LoggerFactory.getLogger(packageName);
//	    MDC.put("execTime", new String(executionTime+"ms,END" ));
        //System.out.println(pjp.getSignature() + " executed in " + executionTime + "ms");

        LOGGER.info(executionTime + "ms,#END-" + pjp.getSignature() + " executed in " + executionTime + "ms");

//	    MDC.remove("execTime");
        //System.out.println(pjp.getSignature().getDeclaringType() + " executed in " + executionTime + "ms");
        return proceed;

    }
}
