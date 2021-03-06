package com.yucong.controller;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yucong.core.base.vo.BaseVO;
import com.yucong.core.base.vo.CommonVO;
import com.yucong.core.shiro.ShiroKit;
import com.yucong.dto.login.LoginDTO;
import com.yucong.entity.User;
import com.yucong.service.UserService;
import com.yucong.vo.user.LoginVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@Api(tags = "01-登录")
public class LoginController {

	@Autowired
	private UserService userService;
	
	
	/**
     * <p>跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //判断用户是否登录
    	if(ShiroKit.isGuest()) {
    		return "/login.html";
    	} else {
    		return "/index.html";
    	}
    }
	
	
    /**
     * <p>跳转到登录页面
     *
     */
    @GetMapping(value = "/login")
    public String login() {
        if (ShiroKit.isGuest()) {
            return "redirect:/";
        } else {
            return "/login.html";
        }
    }
	
	@ApiOperation(value="登录")
    @PostMapping(value = "/login")
	@ResponseBody
    public CommonVO<LoginVO> loginPost(@RequestBody @Valid LoginDTO dto) {
		
		CommonVO<LoginVO> commonVO = new CommonVO<>();
        commonVO.setMessage("登录成功");
    	// 想要得到 SecurityUtils.getSubject() 的对象．．访问地址必须跟 shiro 的拦截地址内．不然后会报空指针
        Subject subject = SecurityUtils.getSubject();
        // 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
        // 认证执行者交由 com.yucong.config.AuthRealm 中 doGetAuthenticationInfo 处理
        // 当以上认证成功后会向下执行,认证失败会抛出异常
        String username = dto.getUsername();
        String password = dto.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        
        try {
            subject.login(token);
            Long userId = (Long) subject.getPrincipal();
            LoginVO loginVO = new LoginVO();
            
            User u = userService.findById(userId);
            
            loginVO.setUserId(u.getId());
            loginVO.setUsername(u.getUsername());
            loginVO.setNickName(u.getUsername());
            Session session = subject.getSession();
            String sessionId = (String) session.getId();
            loginVO.setTokenId((sessionId) );
            commonVO.setData(loginVO);
        } catch (UnknownAccountException e) {
            log.error("对用户[{}]进行登录验证,验证未通过,用户不存在", username);
            token.clear();
            commonVO.setMessage("用户名或密码错误");
            commonVO.setCode(-1);
        } catch (LockedAccountException lae) {
            log.error("对用户[{}]进行登录验证,验证未通过,账户已锁定", username);
            token.clear();
            commonVO.setMessage("账号已锁定");
            commonVO.setCode(-1);
        } catch (ExcessiveAttemptsException e) {
            log.error("对用户[{}]进行登录验证,验证未通过,错误次数过多", username);
            token.clear();
            commonVO.setMessage("错误次数过多");
            commonVO.setCode(-1);
        } catch (AuthenticationException e) {
            log.error("对用户[{}]进行登录验证,验证未通过,堆栈轨迹如下", username, e);
            token.clear();
            commonVO.setMessage("用户名或密码错误");
            commonVO.setCode(-1);
        }
        return commonVO;
    }
	
	
	@ApiOperation(value="退出登录")
    @PostMapping(value = "/logout")
	@ResponseBody
    public BaseVO logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return new BaseVO();
    }
    
    /*@GetMapping(value = "/unLogin")
    @ResponseBody
    public BaseVO unLogin() {
        BaseVO baseVO = new BaseVO();
        baseVO.setCode(CodeEnum.AUTH_FAIL.getCode());
        baseVO.setMessage("请登录");
        return baseVO;
    }*/
    
    @GetMapping(value = "/unLogin")
    public String unLogin() {
    	return "/login.html";
    }
	
    
    @GetMapping(value = "/denied")
    public String denied() {
        return "denied...";
    }


}
