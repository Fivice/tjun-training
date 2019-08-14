package tjuninfo.training.task.dao;

import java.util.List;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.Menu;
/**
 * 菜单表数据层接口
 * @author shenxianyan
 * @date 2018年5月16日
 */
public interface IMenuDao extends IBaseDao<Menu> {
	
	/**
	 * 查询菜单
	 * @param bt	分页
	 * @param auth	菜单对象
	 * @return
	 *//*
	public List<Menu> findAuthority(BTView<Menu> bt, SysAuthority auth);*/
	/**
	 * 根据权限类型查找菜单
	 * @param menuType
	 * @return
	 */
	public List<Menu> findByType(Integer menuType);
	/**
	 * 查询所有菜单
	 * @return List<Menu>S
	 */
	public List<Menu> findAll();
	/**
	 * 根据父级id查找Menu
	 * @param parentId
	 * @return
	 */
	public List<Menu> findByParentId(Integer parentId);
	/**
	 * 更新目录状态,冻结目录及其及目录
	 * @param menuId目录ID
	 * @param status 目录状态
	 * @return
	 */
	public void updateStatusByIds(Integer menuId,Integer status);

	/**
	 * 根据状态类型查找菜单
	 * @param status
	 * @return
	 */
	public List<Menu> findByType1(Integer menuType,Integer status);
}
