package ru.t1.java.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;

import java.util.Arrays;

@Aspect
@Component
public class TimeLimitExceedAspect {
    @Autowired
private TimeLimitExceedLogRepository timeLimitExceedLogRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${time.limit.ms:2000}")
    private long timeLimit;

    @Around("@annotation(ru.t1.java.demo.aop.TimeLimitExceedLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        if (executionTime > timeLimit) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String methodName = methodSignature.toShortString();
            String parameters = Arrays.toString(joinPoint.getArgs());
            TimeLimitExceedLog timeLimitLog = new TimeLimitExceedLog();
            timeLimitLog.setMethodSignature(methodName);
            timeLimitLog.setExecutionTime(executionTime);
            timeLimitExceedLogRepository.save(timeLimitLog);
            String message = String.format("Method %s executed in %d ms with parameters: %s",
                    methodName, executionTime, parameters);
            kafkaTemplate.send("t1_demo_metric_trace", message);
        }

        return proceed;
    }
}
