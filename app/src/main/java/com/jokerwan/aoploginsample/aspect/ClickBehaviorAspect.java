package com.jokerwan.aoploginsample.aspect;

import android.util.Log;

import com.jokerwan.aoploginsample.annotation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by JokerWan on 2019-11-29.
 * Function: 定义点击行为切面类
 */

@Aspect
public class ClickBehaviorAspect {

    private static final String TAG = "JokerWan";

    /**
     * 1、找到需要处理的切入点（找到被ClickBehavior修饰的方法，放到当前的切入点进行处理）
     * * *(..)) 可以处理ClickBehavior这个类所有的方法
     */
    @Pointcut("execution(@com.jokerwan.aoploginsample.annotation.ClickBehavior * *(..))")
    public void methodPointcut() {

    }

    /**
     * 2、对切入点如何处理
     */
    @Around("methodPointcut()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取签名方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取方法所属的类名
        String className = methodSignature.getDeclaringType().getSimpleName();

        // 获取方法名
        String methodName = methodSignature.getName();

        // 返回值类型
        Class returnType = methodSignature.getReturnType();

        // 参数类型
        Class[] parameterTypes = methodSignature.getParameterTypes();

        // 参数名
        String[] parameterNames = methodSignature.getParameterNames();

        // 获取方法的注解值(需要统计的用户行为)
        String funName = methodSignature.getMethod().getAnnotation(ClickBehavior.class).value();

        // 统计方法的执行时间、统计用户点击某功能行为。（存储到本地，每过x天上传到服务器）
        long begin = System.currentTimeMillis();
        Log.e(TAG, "ClickBehavior Method Start >>> ");
        // 执行MainActivity中切点的方法（方法原来的业务逻辑）
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        Log.e(TAG, "ClickBehavior Method End >>> ");
        Log.e(TAG, String.format("统计了：%s功能，在%s类的%s方法，用时%d ms",
                funName, className, methodName, duration));

        return result;
    }

}
