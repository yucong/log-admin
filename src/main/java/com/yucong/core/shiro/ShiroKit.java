package com.yucong.core.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * shiro工具类
 *
 */
public class ShiroKit {

	
	/**
     * 获取封装的 ShiroUser
     */
    public static Long getUser() {
        if (isGuest()) {
            return null;
        } else {
            return (Long) getSubject().getPrincipals().getPrimaryPrincipal();
        }
    }
    
    
    /**
     * 认证通过或已记住的用户。与guset搭配使用。
     */
    public static boolean isUser() {
        return getSubject() != null && getSubject().getPrincipal() != null;
    }

    /**
     * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
     */
    public static boolean isGuest() {
        return !isUser();
    }
    
    /**
     * 获取当前 Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
     *
     * @param permission 权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean hasPermission(String permission) {
        return getSubject() != null && permission != null
                && permission.length() > 0
                && getSubject().isPermitted(permission);
    }
    
    
    
    /** 
     * 重新赋值权限(在比如:给一个角色临时添加一个权限,需要调用此方法刷新权限,否则还是没有刚赋值的权限) 
     * https://blog.csdn.net/jizhunboss/article/details/53606808
     * 
     * @param myRealm 自定义的realm 
     * @param username 用户名 
     */  
    public static void reloadAuthorizing(Long userId){  
    	
    	RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
    	ShiroAuthRealm shiroRealm = (ShiroAuthRealm)rsm.getRealms().iterator().next();
		
    	Subject subject = SecurityUtils.getSubject(); 
    	String realmName = subject.getPrincipals().getRealmNames().iterator().next();
    	SimplePrincipalCollection principals = new SimplePrincipalCollection(userId,realmName);
    	shiroRealm.clearCachedAuthorizationInfo(principals);
    	
    	// 如果是当前用户直接退出登录，因为角色发生变化，主页的菜单也很可能发生了变化
    	Long currentUserId = (Long) subject.getPrincipal();
    	if(currentUserId.equals(userId)) {
    		subject.logout();
    	} else {
    		shiroRealm.clearCachedSession(userId);
    	}
    	
    }
    
    
    /** 
     * 重新赋值权限(在比如:给一个角色临时添加一个权限,需要调用此方法刷新权限,否则还是没有刚赋值的权限) 
     * https://blog.csdn.net/jizhunboss/article/details/53606808
     * 
     * @param myRealm 自定义的realm 
     * @param username 用户名 
     */  
    public static void reloadAuthorizing(List<Long> userIds){  
    	
    	RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
    	ShiroAuthRealm shiroRealm = (ShiroAuthRealm)rsm.getRealms().iterator().next();
		
    	Subject subject = SecurityUtils.getSubject(); 
    	
    	// 清除跟此角色关联用户的权限缓存 
    	/*String realmName = subject.getPrincipals().getRealmNames().iterator().next();
    	for(Long userId : userIds) {
    		SimplePrincipalCollection principals = new SimplePrincipalCollection(userId,realmName);
        	shiroRealm.clearCachedAuthorizationInfo(principals);
    	}*/
    	String realmName = subject.getPrincipals().getRealmNames().iterator().next();
    	for(Long userId : userIds) {
    		SimplePrincipalCollection principals = new SimplePrincipalCollection(userId,realmName);
    		subject.runAs(principals); 
			shiroRealm.getAuthorizationCache().remove(subject.getPrincipals()); 
			shiroRealm.getAuthorizationCache().remove(userId); 
			subject.releaseRunAs();
    	}
    	
    	
    	// 如果是当前用户，直接退出登录，因为角色发生变化，主页的菜单也很可能发生了变化
    	Long currentUserId = (Long) subject.getPrincipal();
    	if(userIds.contains(currentUserId)) {
    		subject.logout();
    		userIds.remove(currentUserId);
    	}
    	shiroRealm.clearCachedSession(userIds);
    }


    
}
