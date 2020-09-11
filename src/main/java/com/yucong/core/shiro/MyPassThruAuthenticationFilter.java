package com.yucong.core.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyPassThruAuthenticationFilter extends PassThruAuthenticationFilter {

	
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getMethod().equals(RequestMethod.OPTIONS.name())) {
			// System.err.println("options 请求无需登录验证");
			return true;
		}
		return super.onPreHandle(request, response, mappedValue);
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		
		
		 HttpServletRequest httpRequest = WebUtils.toHttp(request);
		 
         if (isAjax(httpRequest)) {
             HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
             httpServletResponse.sendError(401);
             return false;
         } else {
             if (log.isTraceEnabled()) {
                 log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                         "Authentication url [" + getLoginUrl() + "]");
             }
             saveRequestAndRedirectToLogin(request, response);
         }

         return false;
	}
	
	/*
     * 判断ajax请求
     * @param request
     * @return
     */
    boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
    }
	
}
