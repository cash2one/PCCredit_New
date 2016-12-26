package com.cardpay.basic.common.exception.global;

import com.cardpay.basic.base.model.ResultTo;
import com.cardpay.basic.common.context.ContextProperty;
import com.cardpay.basic.common.enums.ResultEnum;
import com.cardpay.basic.common.exception.base.BusinessBaseException;
import com.cardpay.basic.common.log.LogTemplate;
import com.cardpay.basic.mail.MailSend;
import com.cardpay.basic.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * 全局异常处理
 *
 * @author rankai .
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    @Autowired
    private MailSend mailSend;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        LogTemplate.error(GlobalExceptionHandler.class, ex, "页面请求出现异常", null);
         /*判断是否ajax请求*/
        if (!(request.getHeader("accept").indexOf("application/json") > -1
                || (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            ResultTo resultTo = new ResultTo();
            if (ex instanceof BusinessBaseException) {
                BusinessBaseException errorCodeEx = (BusinessBaseException) ex;
                resultTo.setMsg(errorCodeEx.getCode(), errorCodeEx.getMessage());
            } else {
                resultTo.setCode(ResultEnum.SERVICE_ERROR.getValue());
            }
            ModelAndView error = new ModelAndView("error", resultTo);
            error.setViewName("/error/500");
            return error;
        } else {
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                ResultTo resultTo = new ResultTo();
                // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
                if (ex instanceof BusinessBaseException) {
                    BusinessBaseException errorCodeEx = (BusinessBaseException) ex;
                    resultTo.setMsg(errorCodeEx.getCode(), errorCodeEx.getMessage());
                } else {
                    resultTo.setCode(ResultEnum.SERVICE_ERROR.getValue());
                }
                writer.write(JsonUtil.toJson(resultTo));
                writer.flush();
                writer.close();
            } catch (Exception e) {
                LogTemplate.error(GlobalExceptionHandler.class, e, "异常信息", null);
            }
        }
        Object obj = ContextProperty.getContextProperty("exceptionEmails");
        if (obj != null) {
            String[] emails = obj.toString().split(",");
            mailSend.send(null, emails, ex.toString());
        }
        return null;
    }
}