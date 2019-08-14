package tjuninfo.training.task.service;

import java.util.List;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.Menu;

/**
 * 菜单表业务层接口
 * @author shenxianyan
 * @date 2018年5月17日
 */
public interface IMenuService extends IBaseService<Menu>{
	/**
	 * 获取系统目录列表
	 * @return list
	 */
	List<Menu> list();
	/**
	 * 根据id获取菜单
	 * @param menuId
	 * @return
	 */
	Menu getByMenuId(Integer menuId);
	/**
	 * 添加更新信息
	 * @param menu
	 * @param userName
	 * @return
	 */
	void updateMenu(Menu menu, String userName,Integer menuId);
	/**
	 * 更新目录状态
	 * @param menuId
	 * @return
	 */
	void updateStatus(Integer menuId);

	/**
	 * 根据状态获取系统目录列表
	 * @return list
	 */
	List<Menu> list1();
}
