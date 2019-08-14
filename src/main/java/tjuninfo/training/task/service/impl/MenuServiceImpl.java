package tjuninfo.training.task.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IMenuDao;
import tjuninfo.training.task.entity.Menu;
import tjuninfo.training.task.enums.MenuTypeEnum;
import tjuninfo.training.task.enums.StatusEnum;
import tjuninfo.training.task.service.IMenuService;

/**
 * 菜单表业务层接口实现类
 * @author shenxianyan
 * @date 2018年5月18日
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {
	private IMenuDao menuDao;
	@Resource
	public void setSysAuthorityDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		this.dao = menuDao;
	}
	

	@Override
	public List<Menu> list() {
		List<Menu> menus = new ArrayList<>();
		//  查询一级目录
		List<Menu> parentMenus = menuDao.findByType(MenuTypeEnum.FIRST_MENU.getType());
		// 查询二级目录
		List<Menu> childMenus = menuDao.findByType(MenuTypeEnum.SECOND_MENU.getType());
		// 查询操作目录
		List<Menu> handleMenus = menuDao.findByType(MenuTypeEnum.HANDLE_MENU.getType());
		
		for (Menu parentMenu : parentMenus) {// 遍历一级目录
			menus.add(parentMenu);
			for (Menu childMenu : childMenus) {// 遍历二级目录
				if (parentMenu.getMenuId() == childMenu.getParentId()) {
					menus.add(childMenu);
					for (Menu handleMenu : handleMenus) {// 遍历操作目录
						if (childMenu.getMenuId() == handleMenu.getParentId()) {
							menus.add(handleMenu);
						}
					}
				}
			}
		}
		return menus;
	}

	@Override
	public Menu getByMenuId(Integer menuId) {
		return menuDao.get(menuId);
	}

	@Override
	public void updateMenu(Menu updateMenu, String userName,Integer menuId) {
		Menu menu = menuDao.get(menuId);
		menu.setHref(updateMenu.getHref());
		menu.setIcon(updateMenu.getIcon());
		menu.setMenuName(updateMenu.getMenuName());
		menu.setMenuCode(updateMenu.getMenuCode());
		menu.setSort(updateMenu.getSort());
		menu.setStatus(updateMenu.getStatus());
		menu.setRemarks(updateMenu.getRemarks());
		menu.setUpdateBy(userName);
		menu.setUpdateTime(new Date());
		menuDao.update(menu);
	}
	@Override
	public void updateStatus(Integer menuId) {
		Menu menu = menuDao.get(menuId);
		if (menu != null && StatusEnum.HIDDEN.getStatus().equals(menu.getStatus())) {
			// 显示该目录
			menu.setStatus(StatusEnum.SHOW.getStatus());
			menuDao.update(menu);
		} else if (menu != null && StatusEnum.SHOW.getStatus().equals(menu.getStatus())) {
			// 冻结该目录及其及目录
			List<Integer> menuIds = new ArrayList<>();
			menuIds.add(menuId);
			menuIds = getMenuIds(menuIds, menuId);
			for(Integer id : menuIds) {
				Menu menu2 = menuDao.get(id);
				menu2.setStatus(StatusEnum.HIDDEN.getStatus());
				menuDao.update(menu2);
			}
		}
	}

	@Override
	public List<Menu> list1() {
		List<Menu> menus = new ArrayList<>();
		//  查询一级目录
		List<Menu> parentMenus = menuDao.findByType1(MenuTypeEnum.FIRST_MENU.getType(),1);
		// 查询二级目录
		List<Menu> childMenus = menuDao.findByType(MenuTypeEnum.SECOND_MENU.getType());
		// 查询操作目录
		List<Menu> handleMenus = menuDao.findByType(MenuTypeEnum.HANDLE_MENU.getType());

		for (Menu parentMenu : parentMenus) {// 遍历一级目录
			menus.add(parentMenu);
			for (Menu childMenu : childMenus) {// 遍历二级目录
				if (parentMenu.getMenuId() == childMenu.getParentId()) {
					menus.add(childMenu);
					for (Menu handleMenu : handleMenus) {// 遍历操作目录
						if (childMenu.getMenuId() == handleMenu.getParentId()) {
							menus.add(handleMenu);
						}
					}
				}
			}
		}
		return menus;
	}

	/**
	 * 根据目录ID查找目录ID的子目录
	 * @param menuId 目录ID
	 * @return List<Integer>
	 */
	private List<Integer> getMenuIds(List<Integer> menuIds, Integer menuId) {
		List<Menu> menus = menuDao.findByParentId(menuId);
		if (menus != null) {
			for (Menu childMenu : menus) {
				menuIds.add(childMenu.getMenuId());
				if (childMenu.getMenuType() == 0) {
					continue;
				}
				getMenuIds(menuIds, childMenu.getMenuId());
			}
		}
		return menuIds;
	}
}
