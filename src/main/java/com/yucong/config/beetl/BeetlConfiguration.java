package com.yucong.config.beetl;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import com.yucong.core.shiro.ShiroKit;

/**
 * beetl拓展配置, 绑定一些工具类, 
 * 方便在模板中直接调用.直接配置groupTemplate
 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {
        groupTemplate.registerFunctionPackage("shiro", new ShiroKit());
    }
}
