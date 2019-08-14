package tjuninfo.training.task.controller;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.Menu;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.IMenuService;

/**
 * 系统目录表示层控制器      
 * @author shenxianyan
 * @date 2018年5月17日
 */
@Controller
@RequestMapping(value = "/system/menu")
public class SysMenuController extends BaseController{
	@Resource
	private IMenuService menuService;
	@GetMapping(value = "/view")
	public String list(Model model) {
		
		List<Menu> menus = menuService.list();
		model.addAttribute("menus", menus);
		return "/menu/menu_list";
	}
	/**
	 * GET 创建菜单页面
	 * @return
	 */
	@GetMapping(value = "/{menuId}/create")
	public String getInsertPage(Model model, @PathVariable("menuId") Integer menuId) {
		Menu parentMenu = menuService.getByMenuId(menuId);
		model.addAttribute("parentMenu", parentMenu);
		return "/menu/menu_create";
	}
	
	/**
	 * POST 创建菜单
	 * @return 
	 */
	@PostMapping(value = "")
	@ResponseBody
	public Object insert(Menu menu) {
		SysUser user = getUser();
		menu.setCreateBy(user.getLoginAccount());
		menu.setCreateTime(new Date(new java.util.Date().getTime()));
		// 创建菜单及插入菜单目录记录
		menuService.save(menu);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	/**
	 * GET 菜单图标
	 * @return
	 */
	@GetMapping(value = "/icon")
	public String icon(Model model) {
		return "/menu/menu_icon";
	}
	/**
	 * DELETE 删除菜单
	 * @param menuId
	 * @return
	 */
	@DeleteMapping(value = "/{menuId}/delete")
	@ResponseBody
	public Object delete(@PathVariable("menuId") Integer menuId) {
			 menuService.deleteByPK(menuId);
			return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	/**
	 * GET 更新菜单页面
	 * @return
	 */
	@GetMapping(value = "/{menuId}/edit")
	public String getUpdatePage(Model model, @PathVariable("menuId") Integer menuId) {
		Menu menu = menuService.getByMenuId(menuId);
		model.addAttribute("menu", menu);

		Menu parentMenu = menuService.getByMenuId(menu.getParentId());
		model.addAttribute("parentMenu", parentMenu);

		return "/menu/menu_update";
	}
	/**
	 * PUT 更新菜单信息(根据url菜单ID来指定更新对象,并根据传过来的菜单信息来更新菜单信息)
	 * @return
	 */
	@PutMapping(value = "/{menuId}")
	@ResponseBody
	public Object update(Menu menu, @PathVariable("menuId") Integer menuId) {
		
//			更新用户及菜单记录
			 menuService.updateMenu(menu, getUser().getLoginAccount(),menuId);
			return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}	
	/**
	 * 根据url菜单ID显示/隐藏菜单
	 * @param menuId
	 * @return
	 */
	@PutMapping(value = "/{menuId}/audit")
	@ResponseBody
	public Object audit(@PathVariable("menuId") Integer menuId) {
			menuService.updateStatus(menuId);
			return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	
}
