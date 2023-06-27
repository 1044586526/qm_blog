package com.qm.aspect;

import com.qm.annotation.ForegroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.UserLog;
import com.qm.mapper.UserLogMapper;
import com.qm.util.DateUtils;
import com.qm.util.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面处理
 *
 * @Author: qin
 * @Date: 2023/6/7 15:05
 */

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ForegroundOperationLoggerAspect {

    private final UserLogMapper sysLogMapper;

    @Pointcut("@annotation(foregroundOperationLogger)")
    public void pointcut(ForegroundOperationLogger foregroundOperationLogger) {
    }

    @Around(value = "pointcut(foregroundOperationLogger)", argNames = "joinPoint,foregroundOperationLogger")
    public Object doAround(ProceedingJoinPoint joinPoint, ForegroundOperationLogger foregroundOperationLogger) throws Throwable {

        //原业务方法
        Object result = joinPoint.proceed();

        try {
            //日志处理
            handle(joinPoint,(ResponseResult) result);

        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    /**
     * 记录操作日志
     */
    public void handle(ProceedingJoinPoint  joinPoint, ResponseResult result) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //是否操作数据库
        ForegroundOperationLogger annotation = method.getAnnotation(ForegroundOperationLogger.class);
        if (!annotation.save()) {
            return;
        }

        //获取requestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        assert request != null;

        //请求体信息
        String ip = IpUtils.getIp(request);
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        String clientType = userAgent.getOperatingSystem().getDeviceType().toString();
        String os = userAgent.getOperatingSystem().getName();
        String browser = userAgent.getBrowser().toString();

        //参数设置
        UserLog userLog = UserLog
                .builder()
                .model(annotation.value())
                .type(annotation.type())
                .description(annotation.desc())
                .createTime(DateUtils.getNowDate())
                .ip(ip)
                .address(IpUtils.getIp2region(ip))
                .clientType(clientType)
                .accessOs(os)
                .browser(browser)
                .result(result.getMessage())
                .build();

        //记录日志
        sysLogMapper.insert(userLog);
    }

}
