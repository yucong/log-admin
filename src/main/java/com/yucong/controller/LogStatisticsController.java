package com.yucong.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.java.util.http.HttpProxy;
import com.yucong.dto.log.ListServerExceptionStatisticsDTO;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;


/**
 * <p>日志统计分析
 * 
 * @author 喻聪
 * @date   2018-09-13
 * 
 */
@Controller
@RequestMapping(value = "logStatistics")
@ApiIgnore
@Slf4j
public class LogStatisticsController  {

	@Value("${logBaseUrl}")
	private String logBaseUrl;
	
	
    @GetMapping("serverExceptionStatistics")
    public String serverExceptionStatistics() {
        return "/module/log/server_exception_statistics.html";
    }
	
	
	@GetMapping(value="listServerExceptionStatistics")
	@ResponseBody
	public Object listServerExceptionLog(@Valid  ListServerExceptionStatisticsDTO dto) {
		log.info("【Log-Admin】获取系统故障统计");
		String requestUrl = logBaseUrl + "/serverExceptionLog/statistics";
		String jsonContent = JSON.toJSONString(dto);
		String result = HttpProxy.postJson(requestUrl, jsonContent);
		Object obj = JSON.parse(result);
		return obj;
	}


}
