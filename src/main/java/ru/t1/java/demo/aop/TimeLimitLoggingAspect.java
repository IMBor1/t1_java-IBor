package ru.t1.java.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;

@Aspect
@Component
public class TimeLimitLoggingAspect {
    @Autowired
private TimeLimitExceedLogRepository timeLimitExceedLogRepository;
    @Value("${time.limit.ms:2000}")
    private long timeLimit;

    @Around("execution(* com.example..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;

        if (executionTime > timeLimit) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

            TimeLimitExceedLog timeLimitLog = new TimeLimitExceedLog();
            timeLimitLog.setMethodSignature(methodSignature.toShortString());
            timeLimitLog.setExecutionTime(executionTime);


            timeLimitExceedLogRepository.save(timeLimitLog);
        }

        return proceed;
    }
}
