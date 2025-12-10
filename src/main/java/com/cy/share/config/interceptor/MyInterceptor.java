package com.cy.share.config.interceptor;

import com.cy.share.common.annotation.UnInterception;
import com.cy.share.common.utils.JwtResult;
import com.cy.share.common.constant.Jwtconstant;
import com.cy.share.common.utils.JwtUtil;
import com.cy.share.common.utils.Result;
import com.cy.share.common.constant.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("请求路径：{}", request.getRequestURI());
            //是方法接口不是资源
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String methodName = method.getName();
            log.info("====拦截到方法：{}，准备执行前置校验====", methodName);

            // 检查是否有跳过拦截的注解
            UnInterception annotation = method.getAnnotation(UnInterception.class);
            if (annotation != null) {
                log.info("方法 {} 标记了跳过拦截，直接放行", methodName);
                return true;
            }

            // 从请求头获取token
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                log.warn("方法 {} 未携带token，拒绝访问", methodName);
                // 无token时使用 401 未授权状态码（对应 ResultCode.UNAUTHORIZED）
                Result<Object> result = Result.fail(ResultCode.UNAUTHORIZED, "无权限，请先登录");
                print(response, result,401);
                return false;
            }

            // 校验token有效性
            JwtResult checkResult = JwtUtil.validateJWT(token);
            if (checkResult.isSuccess()) {
                log.info("方法 {} 的token校验通过，放行", methodName);
                return true;
            } else {
                // 根据错误码处理不同的token异常
                switch (checkResult.getErrCode()) {
                    case Jwtconstant.JWT_ERRCODE_FAIL:
                        log.warn("方法 {} 的token校验不通过", methodName);
                        // token无效时使用 401 未授权
                        Result<Object> failResult = Result.fail(ResultCode.UNAUTHORIZED, "token校验不通过");
                        print(response, failResult,401);
                        return false;

                    case Jwtconstant.JWT_ERRCODE_EXPIRE:
                        log.warn("方法 {} 的token已过期", methodName);
                        // token过期时也使用 401 未授权（前端可根据msg区分处理）
                        Result<Object> expireResult = Result.fail(ResultCode.UNAUTHORIZED, "token已过期，请重新登录");
                        print(response, expireResult,401);
                        return false;

                    default:
                        // 兜底处理未知错误码，使用业务错误码600
                        log.error("方法 {} 的token出现未知错误，错误码：{}", methodName, checkResult.getErrCode());
                        Result<Object> defaultResult = Result.fail(ResultCode.BUSINESS_ERROR, "token验证失败，请联系管理员");
                        print(response, defaultResult,600);
                        return false;
                }
            }
        }
        // 非接口处理器（如静态资源）直接放行
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("postHandle：Controller方法执行后，视图渲染前");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        if (ex != null) {
            log.error("请求处理过程中发生异常", ex);
        } else {
            log.info("afterCompletion：请求处理完成，执行收尾工作");
        }
    }

    /**
     * 向响应流写入JSON格式数据（依赖Result的toString()返回正确JSON）
     */
    public void print(HttpServletResponse response, Object result,int httpStatus) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(httpStatus); // 新增：设置HTTP状态码
        try (PrintWriter writer = response.getWriter()) {
            writer.println(result); // 依赖Result的toString()正确返回JSON字符串
            writer.flush();
        } catch (IOException e) {
            log.error("向响应流写入数据失败", e);
        }
    }
}
