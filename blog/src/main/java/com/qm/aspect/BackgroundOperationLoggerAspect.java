package com.qm.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.vo.SystemUserVO;
import com.qm.entity.ExceptionLog;
import com.qm.entity.AdminLog;
import com.qm.mapper.ExceptionLogMapper;
import com.qm.mapper.AdminLogMapper;
import com.qm.util.AspectUtils;
import com.qm.util.DateUtils;
import com.qm.util.IpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.qm.common.Constants.CURRENT_USER;

/**
 * @Author: qin
 * @Date: 2023/6/6 17:54
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class BackgroundOperationLoggerAspect {

    private final AdminLogMapper adminLogMapper;

    private final ExceptionLogMapper exceptionLogMapper;

    /**
     * 开始时间
     */
    Date startTime;

    @Pointcut(value = "@annotation(backgroundOperationLogger)")
    public void pointcut(BackgroundOperationLogger backgroundOperationLogger) {

    }

    @Around(value = "pointcut(backgroundOperationLogger)",argNames = "joinPoint,backgroundOperationLogger")
    public Object doAround(ProceedingJoinPoint joinPoint, BackgroundOperationLogger backgroundOperationLogger) throws Throwable {

        startTime = DateUtils.getNowDate();

        // 原业务方法
        Object result = joinPoint.proceed();
        try {
            // 日志处理
            handle(joinPoint, getHttpServletRequest());

        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }


    @AfterThrowing(value = "pointcut(backgroundOperationLogger)", throwing = "e", argNames = "joinPoint,backgroundOperationLogger,e")
    public void doAfterThrowing(JoinPoint joinPoint, BackgroundOperationLogger backgroundOperationLogger, Throwable e)  {
        try {
            HttpServletRequest request = getHttpServletRequest();
            String ip = IpUtils.getIp(request);
            String operationName = AspectUtils.INSTANCE.parseParams(joinPoint.getArgs(), backgroundOperationLogger.value());
            // 获取参数名称字符串
            String paramsJson = getParamsJson((ProceedingJoinPoint) joinPoint);
            SystemUserVO user = (SystemUserVO) StpUtil.getSession().get(CURRENT_USER);

            ExceptionLog exception = ExceptionLog.builder().ip(ip).ipSource(IpUtils.getIp2region(ip))
                    .params(paramsJson).username(user.getUsername()).method(joinPoint.getSignature().getName())
                    .exceptionJson(JSON.toJSONString(e)).exceptionMessage(e.getMessage()).operation(operationName)
                    .createTime(DateUtils.getNowDate()).build();
            exceptionLogMapper.insert(exception);
        }catch (Exception ex){
            log.error("异常处理错误！",ex);
        }
    }

    /**
     * 管理员日志收集
     */
    private void handle(ProceedingJoinPoint point, HttpServletRequest request) throws Exception {

        Method currentMethod = AspectUtils.INSTANCE.getMethod(point);

        //获取操作名称
        BackgroundOperationLogger annotation = currentMethod.getAnnotation(BackgroundOperationLogger.class);
        if (!annotation.save()){
            return;
        }

        String operationName = AspectUtils.INSTANCE.parseParams(point.getArgs(), annotation.value());

        // 获取参数名称字符串
        String paramsJson = getParamsJson(point);

        // 当前操作用户
        SystemUserVO user = (SystemUserVO) StpUtil.getSession().get(CURRENT_USER);
        String type = request.getMethod();
        String ip = IpUtils.getIp(request);
        String url = request.getRequestURI();

        // 存储日志
        Date endTime = new Date();
        Long spendTime = endTime.getTime() - startTime.getTime();
        AdminLog adminLog = new AdminLog(ip, IpUtils.getIp2region(ip), type, url, user.getNickname(),
                paramsJson, point.getTarget().getClass().getName(),
                point.getSignature().getName(), operationName, spendTime);
        adminLogMapper.insert(adminLog);
    }

    private String getParamsJson(ProceedingJoinPoint joinPoint){
        // 参数值
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();

        Object[] args = joinPoint.getArgs();

        // 通过map封装参数和参数值
        int paramsLength = parameterNames.length;
        HashMap<String, Object> paramMap = IntStream.range(0,paramsLength)
                .boxed()
                .collect(Collectors.toMap(
                        i -> parameterNames[i],
                        i -> args[i],
                        (v1,v2) -> v1,
                        HashMap::new
                ));

        //移除request
        boolean isContains = paramMap.containsKey("request");
        if (isContains){
            paramMap.remove("request");
        }

        return JSONObject.toJSONString(paramMap);
    }

    private HttpServletRequest getHttpServletRequest() {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        return (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    }
}
