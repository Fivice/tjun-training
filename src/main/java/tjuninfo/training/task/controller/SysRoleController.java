package tjuninfo.training.task.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.Menu;
import tjuninfo.training.task.entity.RoleMenu;
import tjuninfo.training.task.entity.SysRole;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.IMenuService;
import tjuninfo.training.task.service.IRoleMenuService;
import tjuninfo.training.task.service.ISysRoleService;
import tjuninfo.training.task.service.IUserRoleService;
import tjuninfo.training.task.util.DateUtil;
import tjuninfo.training.task.vo.MenuVo;
import tjuninfo.training.task.vo.RoleMenuVo2;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/role")
public class SysRoleController extends BaseController{
	@Resource
	private ISysRoleService roleService;
	@Resource
	private IMenuService menuService;
	@Resource
	private IRoleMenuService roleMenuService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IRoleMenuService iRoleMenuService;
	/**
	 * 访问角色页面
	 * @return
	 */
	@RequestMapping("/view")
	public String toRole(){
		return "role/role_list";
	}

	/**
	 * 分页查询角色
	 * @param btView
	 * @throws IOException 
	 */
	@RequestMapping(value = "/findRole", method = { RequestMethod.POST, RequestMethod.GET })
	public void findUser(SysRole role,BTView<SysRole> btView) throws IOException{
		List<SysRole> list = roleService.findSysRolePage(btView, null);
		btView.setRows(list);
		super.writeJSON(btView);
	}
	/**
	 * GET 创建角色页面
	 * @return
	 */
	@GetMapping(value = "/add/view")
	public String getInsertPage(Model model) {
		return "/role/role_add";
	}
	
	/**
	 * POST 创建角色
	 * @return 
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object insert(SysRole sysRole) {
		sysRole.setCreateTime(DateUtil.getDateTime(new Date()));
//		roleService.save(sysRole);
		roleService.saveOrUpdate(sysRole);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/**
	 * 查询级别是否存在
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/findRoleValue")
	@ResponseBody
	public Object findIdCard(String roleValue,String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null !=roleValue && !roleValue.equals("")){
			SysRole sysRole=roleService.getByRoleValue(roleValue,roleId);
			if(sysRole==null){
				map.put("valid",true);
			}else{
				map.put("valid",false);
			}

		}

		return map;
	}
	/**
	 * 删除角色
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam(value="roleIds",required=false)String roleIds) {
       
		String [] result = roleIds.split(",");
		Long RoleIdUsedCountInSysUserRole = null;
		Long RoleIdUsedCountInSysRoleMenu = null;
        for(int i = 0;i<result.length;i++){
        	Long roleId=Long.parseLong(result[i]);//强制转换;
			RoleIdUsedCountInSysUserRole = userRoleService.getCountByProerties(new String[]{"roleId"},new Long[]{roleId});
			RoleIdUsedCountInSysRoleMenu = (long) iRoleMenuService.findMenuIdByRoleId(roleId).size();
			if (RoleIdUsedCountInSysUserRole>0||RoleIdUsedCountInSysRoleMenu>0){
				return new CmsResult(CommonReturnCode.FAILED,2);
			}else{
				roleService.deleteByRoleId(roleId);
			}
        }
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	/**
	 * GET 更新角色页面
	 * @return
	 */
	@GetMapping(value = "/{roleId}/edit")
	public String getUpdatePage(Model model, @PathVariable("roleId") long roleId) {
		SysRole role = roleService.get(roleId);
		model.addAttribute("role", role);
		return "role/role_update";
	}
	
	/**
	 * PUT 根据roleId来更新对象
	 * @return
	 */
	@PutMapping(value = "/{roleId}")
	@ResponseBody
	public Object update(SysRole role, @PathVariable("roleId") long roleId) {
		roleService.updateRole(role);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	/**
	 * GET 创建角色授权页面
	 * @return
	 */
	@GetMapping(value = "/{roleId}/auth")
	public String getInsertPage1(Model model,@PathVariable("roleId") long roleId) {
		SysRole role = roleService.get(roleId);
		model.addAttribute("role", role);
		List<Menu> menus = menuService.list1();
		model.addAttribute("menus", menus);
		List<MenuVo> menuVoList = new ArrayList<MenuVo>();
		for (Menu menu : menus) {
			MenuVo menuVo = new MenuVo(menu.getMenuId(),menu.getParentId(),menu.getMenuName());
			menuVoList.add(menuVo);
			model.addAttribute("nodes",JSONArray.toJSONString(menuVoList));
		}
		//获取选中角色拥有的菜单权限
				List <RoleMenuVo2> roleMenuList =  roleMenuService.findMenuIdByRoleId(roleId);
				List<MenuVo> menuVoList2 = new ArrayList<MenuVo>();
				for (RoleMenuVo2 roleMenuVo2 : roleMenuList) {
					MenuVo menuVo2 = new MenuVo((roleMenuVo2.getMenuId()).intValue());
					menuVoList2.add(menuVo2);
					model.addAttribute("selNodes",JSONArray.toJSONString(menuVoList2));
				}
		return "/role/role_auth";
	}
	
	@RequestMapping(value = "/authorise")
    @ResponseBody
    public Object authorise(@RequestParam(value="params",required=false)String params){

        String[] strs = params.split(";");
		String roleId = strs[0];
		SysRole role = roleService.get(Long.valueOf(roleId));

		if( strs.length==1){
			roleMenuService.doDel(Long.valueOf(roleId));

		}else{
			String menuIdarr = strs[1];
			String[] menuIds = menuIdarr.split(",");
			roleMenuService.doDel(Long.valueOf(roleId));

			for (String string : menuIds) {
				RoleMenu roleMenu = new RoleMenu();
				//System.out.println(string);
				Menu menu = menuService.getByMenuId(Integer.parseInt(string));
				//System.out.println(menu);
				roleMenu.setMenu(menu);
				roleMenu.setSysRole(role);
				//System.out.println(roleMenu);
				roleMenuService.add(roleMenu);
			}
		}

    	return new CmsResult(CommonReturnCode.SUCCESS, 1);

	}
}