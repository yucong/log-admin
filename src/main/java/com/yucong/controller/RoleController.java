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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yucong.core.annotion.Auth;
import com.yucong.core.base.vo.BaseVO;
import com.yucong.core.base.vo.CommonVO;
import com.yucong.core.base.vo.DataTableVO;
import com.yucong.core.util.MenuUtils;
import com.yucong.dto.role.AddMenuRoleDTO;
import com.yucong.dto.role.ListRoleDTO;
import com.yucong.dto.role.RoleIdDTO;
import com.yucong.dto.role.UpdateMenuRoleDTO;
import com.yucong.entity.Permission;
import com.yucong.entity.Role;
import com.yucong.entity.RolePermission;
import com.yucong.service.PermissionService;
import com.yucong.service.RolePermissionService;
import com.yucong.service.RoleService;
import com.yucong.vo.menu.PermissionVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("role")
@Api(tags = "03-角色管理")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RolePermissionService menuRoleService;
	
	
	/**
     * 跳转到主页面
     *
     * @author admin
     * @Date 2020-03-25
     */
    @GetMapping("")
    public String index() {
        return "module/system/sys_role.html";
    }
	
	/**
	 * 查找所有的角色，分页展示，可模糊查询条件为roleName;
	 * 
	 * @author YN
	 * @date   2019-4-22
	 */
	@Auth
	@ApiOperation(value="角色列表")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@GetMapping("list")
	@ResponseBody
	public CommonVO<DataTableVO<Role>> list(ListRoleDTO dto) {
		return new CommonVO<>(roleService.list(dto.getRoleName(),dto.getAvailable(),dto.getPage(),dto.getSize()));
	}
	
	/**
	 * 查找所有的角色，分页展示，可模糊查询条件为roleName;
	 * 
	 * @author YN
	 * @date   2019-4-22
	 */
	@Auth
	@ApiOperation(value="根据用户ID查询角色")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@GetMapping("listByUserId")
	@ResponseBody
	public CommonVO<List<Role>> listByUserId(Long userId) {
		return new CommonVO<>(roleService.listByUserId(userId));
	}

	
	/**
	 * 添加一个角色，同时可添加这个角色的菜单权限
	 * 需注意的一个参数menuIds，表示这个角色的菜单权限的id集合
	 * 
	 * @author YN
	 * @date   2019-4-22
	 */
	@Auth
	@ApiOperation(value="添加角色")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@PostMapping("addRolePermission")
	@ResponseBody
	public BaseVO addRolePermission(@RequestBody @Valid AddMenuRoleDTO dto,@RequestHeader("X-User-Id") Long userId) {
		return roleService.addRoleMenu(dto,userId);
	}
	
	/**
	 * 修改一个角色，及该角色的菜单权限
	 * 需注意的一个参数menuIds，表示这个角色的菜单权限的id集合
	 * 
	 * @author  YN
	 * @date    2019-4-23
	 */
	@Auth
	@ApiOperation(value="更新角色")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@PostMapping("updateRolePermission")
	@ResponseBody
	public BaseVO updateRolePermission(@RequestBody @Valid UpdateMenuRoleDTO dto, @RequestHeader("X-User-Id") Long userId) {
		return roleService.updateRolePermission(dto,userId);
	}
	
	@Auth
	@ApiOperation(value="冻结角色")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
    @PostMapping(value = "locked")
	@ResponseBody
    public BaseVO locked(@RequestBody @Valid RoleIdDTO dto) {
		roleService.lockedRole(dto.getRoleId());
        return BaseVO.success();
    }
	
	/**
	 * 返回的菜单数据需要以树形结构展示
	 * 
	 * @author YN
	 * @date   2019-4-22
	 */
    //@Auth
	@ApiOperation(value="根据角色查询权限")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@GetMapping("listPermissionByRoleId")
	@ResponseBody
	public CommonVO<List<PermissionVO>> ListMenuByRoleId(@Valid RoleIdDTO dto) {
		//1所有的有效菜单
		List<Permission> listAll = permissionService.findAvailablePermissions();
		
		//2该角色下的所有 菜单角色 集合
		List<RolePermission> listMenuRole = menuRoleService.findMenuRoleByRoleId(dto.getRoleId());

		// 3整理出角色下的菜单Id集合
		List<Long> menuRoleIds = new ArrayList<>();
		if (!CollectionUtils.isEmpty(listMenuRole)) {
			for (RolePermission sysMenuRole : listMenuRole) {
				menuRoleIds.add(sysMenuRole.getPermissionId());
			}
		}

		// 4 整理出角色拥有的菜单集合
		List<PermissionVO> sysMenuVOList = new ArrayList<PermissionVO>();
		if(!CollectionUtils.isEmpty(listAll)) {
			for(Permission sysMenu : listAll) {
				PermissionVO sysMenuVO = new PermissionVO();
				sysMenuVO.setId(sysMenu.getId());
				sysMenuVO.setParentId(sysMenu.getParentId());
				sysMenuVO.setName(sysMenu.getName());
				sysMenuVO.setPermission(sysMenu.getPermission());
				sysMenuVO.setSort(sysMenu.getSort());
				//菜单设置成选中状态
				if(menuRoleIds.contains(sysMenuVO.getId())) {
					sysMenuVO.setChecked("true");//TODO
				} 
				sysMenuVOList.add(sysMenuVO);
			}
		}
		
		//生成树形结构数据
		List<PermissionVO> data = MenuUtils.formatMenu(sysMenuVOList);
		return new CommonVO<List<PermissionVO>>(data);
	}
	
}
