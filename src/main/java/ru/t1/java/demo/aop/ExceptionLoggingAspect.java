package ru.t1.java.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

@Aspect
@Component
public class ExceptionLoggingAspect {

    @Autowired
    private DataSourceErrorLogRepository errorLogRepository;

    @AfterThrowing(pointcut = "execution(* com.example..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint,Exception ex) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        DataSourceErrorLog errorLog = new DataSourceErrorLog();
        errorLog.setStackTrace(ex.getStackTrace().toString());
        errorLog.setMessage(ex.getMessage());
        errorLog.setMethodSignature(methodSignature.toShortString());

        errorLogRepository.save(errorLog);
    }
}
