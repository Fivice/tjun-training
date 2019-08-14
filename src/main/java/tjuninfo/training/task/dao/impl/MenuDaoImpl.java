package tjuninfo.training.task.dao.impl;


import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IMenuDao;
import tjuninfo.training.task.entity.Menu;
/**
 * 菜单表数据层接口实现类
 * @author shenxianyan
 * @date 2018年5月23日
 */
@Repository
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements IMenuDao {

	public MenuDaoImpl(){
		super(Menu.class);
	}

	@Override
	public List<Menu> findByType(Integer menuType) {
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<Menu> list = super.getSession().createCriteria(Menu.class, "m").add(Restrictions.eq("m.menuType", menuType)).addOrder(Order.asc("m.sort")).list();
		return list;
	}

	@Override
	public List<Menu> findAll() {
		List<Menu> list = super.getSession().createCriteria(Menu.class, "m").list();
		return list;
	}

	@Override
	public List<Menu> findByParentId(Integer parentId) {
		List<Menu> list = super.getSession().createCriteria(Menu.class, "m").add(Restrictions.eq("m.parentId", parentId)).addOrder(Order.asc("m.sort")).list();
		return list;
	}

	@Override
	public void updateStatusByIds(Integer menuId, Integer status) {
		// TODO Auto-generated method stub
//		super.getSession().createCriteria(Menu.class, "m").
	}

	@Override
	public List<Menu> findByType1(Integer menuType,Integer status) {
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<Menu> list = super.getSession().createCriteria(Menu.class, "m").add(Restrictions.eq("m.menuType", menuType)).add(Restrictions.eq("m.status", menuType)).addOrder(Order.asc("m.sort")).list();
		return list;
	}
}
