package com.gunnarro.dietmanager.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class RequestLoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

    @Bean
    public CustomizableTraceInterceptor customizableTraceInterceptor() {
        CustomizableTraceInterceptor cti = new CustomizableTraceInterceptor();
        cti.setExitMessage("class=" + CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME + " method="
                + CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME + "(" + CustomizableTraceInterceptor.PLACEHOLDER_ARGUMENTS + ")" + " responsetime="
                + CustomizableTraceInterceptor.PLACEHOLDER_INVOCATION_TIME + "ms" + " response=" + CustomizableTraceInterceptor.PLACEHOLDER_RETURN_VALUE);
        return cti;
    }

    /**
     * Endpoint response/request logging with response time
     * 
     * @return
     */
    @Bean
    public Advisor endpointAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * com.gunnarro.dietmanager.endpoint.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
    }
}
