package com.yucong.dto.log;

import com.alibaba.fastjson.JSON;
import com.yucong.core.base.dto.PageInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ListServerExceptionStatisticsDTO extends PageInfo {

	@ApiModelProperty(value = "请求URL")
	private String requestUrl;
	
	@ApiModelProperty(value = "服务器环境名称，如：test,prod,dev")
	private String env;
	
	@ApiModelProperty(value = "开始时间")
	private String beginTime;
	
	@ApiModelProperty(value = "结束时间")
	private String endTime;
	
	@ApiModelProperty(value = "排序字段")
	private String orderBy;
	
	
	public String toString() {
		return this.getClass().getSimpleName() + JSON.toJSONString(this);
	}
}
