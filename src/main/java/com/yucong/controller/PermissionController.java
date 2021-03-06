package com.yucong.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.yucong.core.util.BeanMapper;
import com.yucong.core.util.MenuUtils;
import com.yucong.dto.menu.AddMenuDTO;
import com.yucong.dto.menu.PermissionIdDTO;
import com.yucong.dto.menu.UpdateMenuDTO;
import com.yucong.entity.Permission;
import com.yucong.service.PermissionService;
import com.yucong.vo.menu.PermissionVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("permission")
@Api(tags = "04-权限管理")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

//	@Autowired
//	private RolePermissionService menuRoleService;
	
	
//	@Autowired
//	private RoleService roleService;

	
	/**
     * 跳转到主页面
     *
     * @author admin
     * @Date 2020-03-25
     */
    @GetMapping("")
    public String index() {
        return "/module/system/sys_menu.html";
    }
	
	
	/**
	 * 查询所有菜单数据
	 * 以树形结构展示所有菜单数据
	 * 返回值中有一个checked字段，用于角色or用户模块时的选中状态
	 * 
	 * @author YN
	 * @date   2019-04-22
	 *         
	 */
    @Auth
	@ApiOperation(value="查询所有菜单")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@GetMapping("listAll")
	@ResponseBody
	public CommonVO<List<PermissionVO>> findAll() {
		List<Permission> listAll = permissionService.listAll();
		List<PermissionVO> listMenuVO = BeanMapper.mapList(listAll, PermissionVO.class);
		for(int i=0,length=listAll.size(); i<length; i++) {
			if(listAll.get(i).getAvailable()) {
				listMenuVO.get(i).setChecked("true");
				listMenuVO.get(i).setMemo("冻结");
			} else {
				listMenuVO.get(i).setChecked("false");
				listMenuVO.get(i).setMemo("解除");
			}
		}
		List<PermissionVO> data = MenuUtils.formatMenu(listMenuVO);
		return new CommonVO<List<PermissionVO>>(data);
	}

	/**
	 * 添加菜单
	 * 
	 * @author YN
	 * @date   2019-4-22
	 */
    @Auth
	@ApiOperation(value="添加权限")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@PostMapping("add")
	@ResponseBody
	public CommonVO<Object> addMenu(@RequestBody @Valid AddMenuDTO dto,@RequestHeader("X-User-Id") Long userId) {
		return new CommonVO<Object>(permissionService.addMenu(dto,userId));
	}

	/**
	 * 更新菜单
	 * 
	 * @author YN
	 * @date   2019-4-22
	 */
    @Auth
	@ApiOperation(value="更新权限")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@PostMapping("update")
	@ResponseBody
	public BaseVO updateMenu(@Valid @RequestBody UpdateMenuDTO dto, 
			@RequestHeader("X-User-Id") Long userId) {
		permissionService.updateMenu(dto,userId);
		return new BaseVO();
	}

	/**
	 * 通过主键id查询菜单详情
	 * 
	 * @author YN
	 * @date:  2019-4-22
	 */
    @Auth
	@ApiOperation(value="权限详情")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@GetMapping("detail")
	@ResponseBody
	public CommonVO<PermissionVO> detailMenu(@Valid PermissionIdDTO dto) {
		return new CommonVO<PermissionVO>(permissionService.detailMenu(dto.getId()));
	}

	/**
	 * 通过主键id逻辑删除删除数据
	 * 
	 * @author YN
	 * @date   2019-4-22
	 */
    @Auth
	@ApiOperation(value="冻结权限")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
    })
	@PostMapping("locked")
	@ResponseBody
	public BaseVO locked(@Valid @RequestBody PermissionIdDTO dto, @RequestHeader("X-User-Id") Long userId) {
		return permissionService.locked(dto.getId(),userId);
	}

//	/**
//	 * 返回的菜单数据需要以树形结构展示
//	 * 
//	 * @author YN
//	 * @date   2019-4-22
//	 */
//    @Auth
//	@ApiOperation(value="根据角色查询权限")
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
//    })
//	@GetMapping("listPermissionByRoleId")
//	@ResponseBody
//	public CommonVO<List<PermissionVO>> ListMenuByRoleId(@Valid RoleIdDTO dto) {
//		//1所有的有效菜单
//		List<Permission> listAll = permissionService.findAvailablePermissions();
//		
//		//2该角色下的所有 菜单角色 集合
//		List<RolePermission> listMenuRole = menuRoleService.findMenuRoleByRoleId(dto.getRoleId());
//
//		// 3整理出角色下的菜单Id集合
//		List<Long> menuRoleIds = new ArrayList<>();
//		if (!CollectionUtils.isEmpty(listMenuRole)) {
//			for (RolePermission sysMenuRole : listMenuRole) {
//				menuRoleIds.add(sysMenuRole.getPermissionId());
//			}
//		}
//
//		// 4 整理出角色拥有的菜单集合
//		List<PermissionVO> sysMenuVOList = new ArrayList<PermissionVO>();
//		if(!CollectionUtils.isEmpty(listAll)) {
//			for(Permission sysMenu : listAll) {
//				PermissionVO sysMenuVO = new PermissionVO();
//				sysMenuVO.setId(sysMenu.getId());
//				sysMenuVO.setParentId(sysMenu.getParentId());
//				sysMenuVO.setName(sysMenu.getName());
//				sysMenuVO.setPermission(sysMenu.getPermission());
//				sysMenuVO.setSort(sysMenu.getSort());
//				//菜单设置成选中状态
//				if(menuRoleIds.contains(sysMenuVO.getId())) {
//					sysMenuVO.setChecked("true");//TODO
//				} 
//				sysMenuVOList.add(sysMenuVO);
//			}
//		}
//		
//		//生成树形结构数据
//		List<PermissionVO> data = MenuUtils.formatMenu(sysMenuVOList);
//		return new CommonVO<List<PermissionVO>>(data);
//	}
	
//	/**
//	 * 返回的菜单数据需要以树形结构展示
//	 * 
//	 * @date   2019-4-22
//	 */
//    @Auth
//	@ApiOperation(value="根据用户ID查询权限")
//	@ApiImplicitParams({
//        @ApiImplicitParam(name = "Authorization", value = "用户令牌", required = true, dataType = "string", paramType = "header"),
//    })
//	@GetMapping("listByUserId")
//	@ResponseBody
//	public CommonVO<List<PermissionVO>> listByUserId(Long userId) {
//		
//		// 1 获取该用户的角色
//		List<Long> roleIds = roleService.findRoleIdByUserId(userId);
//		
//		// 2 获取用户的权限
//		List<Permission> permissions = null;
//		if(roleIds.isEmpty()) {
//			permissions = new ArrayList<>();
//		} else {
//			permissions = permissionService.findByRoleIds(roleIds);
//		}
//		
//		// 3 整理出角色拥有的菜单集合
//		List<PermissionVO> permissionVOs = new ArrayList<PermissionVO>();
//		if(!CollectionUtils.isEmpty(permissions)) {
//			for(Permission permission : permissions) {
//				PermissionVO permissionVO = new PermissionVO();
//				permissionVO.setId(permission.getId());
//				permissionVO.setParentId(permission.getParentId());
//				permissionVO.setName(permission.getName());
//				permissionVO.setPermission(permission.getPermission());
//				permissionVO.setSort(permission.getSort());
//				permissionVO.setChecked("true");
//				permissionVOs.add(permissionVO);
//			}
//		}
//		
//		// 4 生成树形结构数据
//		List<PermissionVO> data = MenuUtils.formatMenu(permissionVOs);
//		return new CommonVO<List<PermissionVO>>(data);
//	}


}
