package com.yucong.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.java.util.http.HttpProxy;
import com.yucong.dto.log.exception.ExceptionDetailDTO;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 业务异常日志管理
 * 
 * @author 喻聪
 * @date   2018-09-13
 * 
 */
@Controller
@RequestMapping(value = "serverExceptionLog")
@ApiIgnore
@Slf4j
public class ServerExceptionLogController  {

	@Value("${logBaseUrl}")
	private String logBaseUrl;
	
	
	/**
	 * <p>打开异常
	 */
	@PostMapping(value="open")
	@ApiOperation(value="打开异常", notes="打开异常")
	@ResponseBody
	public Object open(@RequestBody ExceptionDetailDTO dto) {
		log.info("【Log-Admin】关闭异常");
		String requestUrl = logBaseUrl + "/serverExceptionLog/open";
		String jsonContent = JSON.toJSONString(dto);
		String result = HttpProxy.postJson(requestUrl, jsonContent);
		Object obj = JSON.parse(result);
		return obj;
	}
	
	
	/**
	 * <p>关闭异常
	 */
	@PostMapping(value="close")
	@ApiOperation(value="关闭异常", notes="关闭异常")
	@ResponseBody
	public Object close(@RequestBody ExceptionDetailDTO dto) {
		log.info("【Log-Admin】打开异常");
		String requestUrl = logBaseUrl + "/serverExceptionLog/close";
		String jsonContent = JSON.toJSONString(dto);
		String result = HttpProxy.postJson(requestUrl, jsonContent);
		Object obj = JSON.parse(result);
		return obj;
	}
	
	
    /*@GetMapping("httpRequestLog")
    public String httpRequestLog() {
        return "/module/log/http_request_log.html";
    }
    
    @GetMapping("httpRequestSlowLog")
    public String httpRequestSlowLog() {
        return "/module/log/http_request_slow_log.html";
    }
    
    @GetMapping("businessAbnormalLog")
    public String businessAbnormalLog() {
        return "/module/log/business_abnormal_log.html";
    }
    
    @GetMapping("serverExceptionLog")
    public String serverExceptionLog() {
        return "/module/log/server_exception_log.html";
    }
	
	
	@GetMapping(value="listHttpRequestLog")
	@ResponseBody
	public Object listHttpRequestLog(@Valid  ListHttpRequestLogDTO dto) {
		log.info("【Log-Admin】获取接口请求日志");
		String requestUrl = logBaseUrl + "/httpRequestLog/list";
		String jsonContent = JSON.toJSONString(dto);
		String result = HttpProxy.postJson(requestUrl, jsonContent);
		Object obj = JSON.parse(result);
		return obj;
	}
	
	
	@GetMapping(value="listHttpRequestSlowLog")
	@ResponseBody
	public Object listHttpRequestSlowLog(@Valid  ListHttpRequestLogDTO dto) {
		log.info("【Log-Admin】获取慢接口请求日志");
		String requestUrl = logBaseUrl + "/httpRequestLog/listSlow";
		String jsonContent = JSON.toJSONString(dto);
		String result = HttpProxy.postJson(requestUrl, jsonContent);
		Object obj = JSON.parse(result);
		return obj;
	}
	
	@GetMapping(value="listBusinessAbnormalLog")
	@ResponseBody
	public Object listBusinessAbnormalLog(@Valid  ListBusinessAbnormalLogDTO dto) {
		log.info("【Log-Admin】获取业务异常日志");
		String requestUrl = logBaseUrl + "/businessAbnormalLog/list";
		String jsonContent = JSON.toJSONString(dto);
		String result = HttpProxy.postJson(requestUrl, jsonContent);
		Object obj = JSON.parse(result);
		return obj;
	}
	
	@GetMapping(value="listServerExceptionLog")
	@ResponseBody
	public Object listServerExceptionLog(@Valid  ListServerExceptionLogDTO dto) {
		log.info("【Log-Admin】获取系统故障日志");
		String requestUrl = logBaseUrl + "/serverExceptionLog/list";
		String jsonContent = JSON.toJSONString(dto);
		String result = HttpProxy.postJson(requestUrl, jsonContent);
		Object obj = JSON.parse(result);
		return obj;
	}*/


}
