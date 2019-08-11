package com.linln.component.actionLog.action.base;

import com.linln.common.enums.ResultEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.actionLog.annotation.EntityParam;
import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.ActionLog;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义日志数据
 * @author 小懒虫
 * @date 2018/10/14
 */
@Data
public class ResetLog {

    /* 封装操作对象 */
    /** 注解日志的方法返回值 */
    private Object retValue;
    /** 获取日志实体对象 */
    private ActionLog actionLog;
    /** Aop连接点信息对象 */
    private JoinPoint joinPoint;
    /** 是否记录日志（默认记录） */
    private Boolean record = true;

    /* 辅助方法 */
    /**
     * 判断返回值是否为ResultVo对象
     */
    public boolean isResultVo(){
        return retValue instanceof ResultVo;
    }
    /**
     * 判断ResultVo状态码是否为成功
     */
    public boolean isSuccess(){
        return retValue instanceof ResultVo &&
                ((ResultVo) retValue).getCode().equals(ResultEnum.SUCCESS.getCode());
    }
    /**
     * 判断ResultVo状态码是否为成功，且设置是否记录日志
     */
    public boolean isSuccessRecord(){
        return record = retValue instanceof ResultVo &&
                ((ResultVo) retValue).getCode().equals(ResultEnum.SUCCESS.getCode());
    }
    /**
     * 获取切入点方法指定名称的参数值
     */
    public Object getParam(String name){
        Object[] args = joinPoint.getArgs();
        if(args.length > 0){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String[] parameterNames = methodSignature.getParameterNames();
            for (int i = 0; i < parameterNames.length; i++) {
                if(parameterNames[i].equals(name)){
                    return args[i];
                }
            }
        }
        return null;
    }
    /**
     * 获取切入点参数注解@EntityParam的对象
     */
    public Object getEntityParam(){
        Object[] args = joinPoint.getArgs();
        if(args.length > 0){
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    if(parameterAnnotations[i][j] instanceof EntityParam){
                        return args[i];
                    }
                }
            }
        }
        return null;
    }
    private static final Pattern FILL_PATTERN = Pattern.compile("\\$\\{[a-zA-Z0-9]+\\}");
    /**
     * 内容填充规则
     */
    public String fillRule(Object beanObject, String content){
        Matcher matcher = FILL_PATTERN.matcher(content);
        while(matcher.find()){
            String matchWord = matcher.group(0);
            String property = matchWord.substring(2, matchWord.length()-1);
            String fill = null;
            try {
                fill = String.valueOf(EntityBeanUtil.getField(beanObject, property));
            } catch (InvocationTargetException | IllegalAccessException e) {
            } finally {
                content = content.replace(matchWord, fill);
            }
        }
        return content;
    }

    /* 快捷数据 */
    /**
     * 获取用户名
     */
    public String getUsername(){
        return ShiroUtil.getSubject().getUsername();
    }
    /**
     * 获取用户昵称
     */
    public String getNickname(){
        return ShiroUtil.getSubject().getNickname();
    }
    /**
     * 获取当前时间
     */
    public String getDatetime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
    /**
     * 获取当前时间（自定义时间格式）
     */
    public String getDatetime(String pattern){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }
}
