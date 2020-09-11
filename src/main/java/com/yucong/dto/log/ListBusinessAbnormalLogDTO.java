package com.yucong.dto.log;

import com.alibaba.fastjson.JSON;
import com.yucong.core.base.dto.PageInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(description="查询异常日志模型")
public class ListBusinessAbnormalLogDTO extends PageInfo {

	@ApiModelProperty(value = "用户ID")
	private String userId;
	
	@ApiModelProperty(value = "问题描述")
	private String description;
	
	@ApiModelProperty(value = "可能原因")
	private String probableCause;
	
	@ApiModelProperty(value = "环境")
	private String env;
	
	@ApiModelProperty(value = "级别")
	private String level;
	
	
	public String toString() {
		return this.getClass().getSimpleName() + JSON.toJSONString(this);
	}
}
