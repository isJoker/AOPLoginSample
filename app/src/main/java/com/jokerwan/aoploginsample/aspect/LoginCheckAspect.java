package com.jokerwan.aoploginsample.aspect;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jokerwan.aoploginsample.LoginActivity;
import com.jokerwan.aoploginsample.utils.SharedPreferencesUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import static com.jokerwan.aoploginsample.LoginActivity.LOGIN_FLAG;

/**
 * Created by JokerWan on 2019-11-29.
 * Function: 登录验证切面
 */

@Aspect
public class LoginCheckAspect {

    private static final String TAG = "JokerWan";

    @Pointcut("execution(@com.jokerwan.aoploginsample.annotation.LoginCheck * *(..))")
    public void loginPointcutMethod() {

    }

    @Around("loginPointcutMethod()")
    public Object loginJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Context context = (Context) joinPoint.getThis();
        boolean isLogin = SharedPreferencesUtil.getBoolean(context,LOGIN_FLAG,false);
        if(isLogin) {
            Log.e(TAG, "检测到已登录！");
            return joinPoint.proceed();
        } else {
            Log.e(TAG, "检测到未登录！");
            Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
            return null;// 不再执行方法（切入点）
        }
    }
}
