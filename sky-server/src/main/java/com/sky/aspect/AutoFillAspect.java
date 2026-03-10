package com.sky.aspect;

import com.sky.anno.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 切面类-自动填充公共字段
 */
@Slf4j
@Component
@Aspect // 声明切面类
public class AutoFillAspect {

    // @annotation，切点表达式：拦截带有 @AutoFill 注解的方法
    // @Before，通知类型：表示在目标方法执行前执行
    @Before("@annotation(com.sky.anno.AutoFill)")
    public void autoFill(JoinPoint joinPoint) {
        // JoinPoint 抽象了切入点（被 AOP 控制的方法）
        // 使用 JoinPoint 获取目标方法上的信息

        // 1.1 使用 AOP 获取目标方法的注解以及注解参数
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); // 获取目标方法签名
        Method method = methodSignature.getMethod(); // 获取目标方法对象
        AutoFill autoFill = method.getAnnotation(AutoFill.class); // 获取目标方法上的指定注解
        OperationType operationType = autoFill.value(); // 获取注解中传入的值

        // 1.2 获取目标方法上的参数对象
        Object[] args = joinPoint.getArgs(); // 获取目标方法的入参列表
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0]; // 参数的实体对象

        // 3. 判断注解中传入的值
        try {
            if (operationType == OperationType.INSERT) {
                // 3.1 如果是 INSERT，则自动填充创建时间、更新时间、创建人、更新人

                // 通过反射（getClass、getDeclaredMethod）直接获取类成员方法
                Method setCreateTime =  entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime =  entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser =  entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser =  entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setCreateTime.invoke(entity, LocalDateTime.now());
                setUpdateTime.invoke(entity, LocalDateTime.now());
                setCreateUser.invoke(entity, BaseContext.getCurrentId());
                setUpdateUser.invoke(entity, BaseContext.getCurrentId());

            } else if (operationType == OperationType.UPDATE) {
                // 3.2 如果是 UPDATE，则自动填充更新时间、更新人

                // 通过反射（getClass、getDeclaredMethod）直接获取类成员方法
                Method setUpdateTime =  entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser =  entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(entity, LocalDateTime.now());
                setUpdateUser.invoke(entity, BaseContext.getCurrentId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
