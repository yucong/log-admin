package com.yucong.dto.log;

import com.alibaba.fastjson.JSON;
import com.yucong.core.base.dto.PageInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ListServerExceptionLogDTO extends PageInfo {

	@ApiModelProperty(value = "客户端IP")
	private String clientIP;
	
	@ApiModelProperty(value = "请求URL")
	private String requestUrl;
	
	@ApiModelProperty(value = "请求方法")
	private String method;
	
	@ApiModelProperty(value = "错误等级")
	private String errLevel;
	
	@ApiModelProperty(value = "平台类型")
	private String platform;
	
	@ApiModelProperty(value = "服务器环境名称，如：test,prod,dev")
	private String env;
	
	@ApiModelProperty(value = "用户ID")
	private String userId;
	
	@ApiModelProperty(value = "设备类型")
	private String deviceType;
	
	@ApiModelProperty(value = "开始时间")
	private String beginTime;
	
	@ApiModelProperty(value = "结束时间")
	private String endTime;
	
	@ApiModelProperty(value = "解决状态：1未解决，2已解决")
	private Integer status;
	
	
	public String toString() {
		return this.getClass().getSimpleName() + JSON.toJSONString(this);
	}
}
