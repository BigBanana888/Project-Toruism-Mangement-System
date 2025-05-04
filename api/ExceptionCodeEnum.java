package com.dzk.web.common.myEnum;

import com.dzk.web.common.exception.LoginMethodException;
import com.dzk.web.common.exception.UserNotExistException;
import com.dzk.web.common.exception.VerifyCodeException;
import com.dzk.web.common.exception.VerifyCodeTimeOutException;
import org.springframework.security.authentication.*;

/**
 * 异常枚举类
 */
public enum ExceptionCodeEnum {
    LOCKEDEXCEPTION(new LockedException("账户被锁定"),1102),
    ACCOUNTEXPIREDEXCEPTION(new AccountExpiredException("账户过期"),1101),
    DISABLEDEXCEPTION(new DisabledException("账户被禁用"),1103),
    USERNOTEXCEPTION(new UserNotExistException("账户不存在"),1104),
    CREDENTIALSEXPIREDEXCEPTION(new CredentialsExpiredException("密码过期"),1201),
    BADCREDENTIALSEXCEPTION(new BadCredentialsException("密码错误"),1202),
    VERIFYCODEEXCEPTION(new VerifyCodeException("验证码错误"),1300),
    VERIFYCODETIMEOUTEXCEPTION(new VerifyCodeTimeOutException("验证码过期"),1301),
    LOGINMETHODEXCEPTION(new LoginMethodException("登入方式错误"),1401),
    EXCEPTION(new Exception("未知异常"),1500);
    private Exception exceptionClass;
    private int exceptionCode;
    private ExceptionCodeEnum(Exception exceptionClass, int exceptionCode){
        this.exceptionClass=exceptionClass;
        this.exceptionCode=exceptionCode;
    }

    public Exception getExceptionClass() {
        return exceptionClass;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }
}

