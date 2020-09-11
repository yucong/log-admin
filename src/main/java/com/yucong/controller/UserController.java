package com.yucong.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yucong.core.annotion.Auth;
import com.yucong.core.base.vo.BaseVO;
import com.yucong.core.base.vo.CommonVO;
import com.yucong.core.base.vo.DataTableVO;
import com.yucong.core.util.BeanMapper;
import com.yucong.core.util.MenuUtils;
import com.yucong.dto.user.AddUser;
import com.yucong.dto.user.ListUserDTO;
import com.yucong.dto.user.UpdateRoleDTO;
import com.yucong.dto.user.UpdateUser;
import com.yucong.dto.user.UserIdDTO;
import com.yucong.entity.Permission;
import com.yucong.entity.User;
import com.yucong.service.PermissionService;
import com.yucong.service.RoleService;
import com.yucong.service.UserService;
import com.yucong.vo.menu.PermissionVO;
import com.yucong.vo.user.ListUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("user") 
@Api(tags = "02-用户管理")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    
    /**
     * 跳转到主页面
     *
     * @author admin
     * @Date 2020-03-25
     */
    @GetMapping("")
    public String index() {
        return "/module/system/sys_user.html";
    }
    
    @ApiOperation(value="用户列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
    @Auth
    @GetMapping(value = "list")
    @ResponseBody
    public CommonVO<DataTableVO<ListUserVO>> list(ListUserDTO dto) {
    	return new CommonVO<DataTableVO<ListUserVO>>(userService.findAll(dto.getUsername(),dto.getPage(),dto.getSize()));
    }

    @ApiOperation(value="添加用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
    @PostMapping(value = "add")
    @ResponseBody
    public BaseVO add(@RequestBody @Valid AddUser user) {
    	User u = BeanMapper.map(user, User.class);
        userService.createUser(u);
        return BaseVO.success();
    }

    @Auth
    @ApiOperation(value="更新用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
    @PostMapping(value = "update" )
    @ResponseBody
    public BaseVO update(@RequestBody @Valid UpdateUser user) {
    	User u = BeanMapper.map(user, User.class);
    	userService.updateUser(u);
        return BaseVO.success();
    }
    
    @Auth
    @ApiOperation(value="更新用户角色")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
    @PostMapping(value = "updateRole" )
    @ResponseBody
    public BaseVO updateRole(@RequestBody @Valid UpdateRoleDTO dto) {
    	userService.updateUserRole(dto.getUserId(),dto.getRoleIds());
        return BaseVO.success();
    }

    @Auth
    @ApiOperation(value="冻结用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
    @PostMapping(value = "locked")
    @ResponseBody
    public BaseVO locked(@RequestBody @Valid UserIdDTO dto) {
        userService.lockedUser(dto.getUserId());
        return BaseVO.success();
    }

    @Auth
    @ApiOperation(value="修改密码")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
    @PostMapping(value = "changePassword")
    @ResponseBody
    public BaseVO changePassword(Long id, String newPassword) {
        userService.changePassword(id, newPassword);
        return BaseVO.success();
    }
    
    
    /**
	 * 返回的菜单数据需要以树形结构展示
	 * 
	 * @date   2019-4-22
	 */
    @Auth
	@ApiOperation(value="根据用户ID查询权限")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@GetMapping("listPermissionByUserId")
	@ResponseBody
	public CommonVO<List<PermissionVO>> listByUserId(Long userId) {
		
		// 1 获取该用户的角色
		List<Long> roleIds = roleService.findRoleIdByUserId(userId);
		
		// 2 获取用户的权限
		List<Permission> permissions = null;
		if(roleIds.isEmpty()) {
			permissions = new ArrayList<>();
		} else {
			permissions = permissionService.findByRoleIds(roleIds);
		}
		
		// 3 整理出角色拥有的菜单集合
		List<PermissionVO> permissionVOs = new ArrayList<PermissionVO>();
		if(!CollectionUtils.isEmpty(permissions)) {
			for(Permission permission : permissions) {
				PermissionVO permissionVO = new PermissionVO();
				permissionVO.setId(permission.getId());
				permissionVO.setParentId(permission.getParentId());
				permissionVO.setName(permission.getName());
				permissionVO.setPermission(permission.getPermission());
				permissionVO.setSort(permission.getSort());
				permissionVO.setChecked("true");
				permissionVOs.add(permissionVO);
			}
		}
		
		// 4 生成树形结构数据
		List<PermissionVO> data = MenuUtils.formatMenu(permissionVOs);
		return new CommonVO<List<PermissionVO>>(data);
	}


}
