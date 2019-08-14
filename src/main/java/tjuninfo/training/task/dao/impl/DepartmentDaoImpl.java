package tjuninfo.training.task.dao.impl;


import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.DepartmentDao;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.entity.Menu;
/**
 * 部门表数据层接口实现类
 * @author 
 * @date 2018年5月23日
 */
@Repository
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {

	public DepartmentDaoImpl(){
		super(Department.class);
	}


	@Override
	public List<Department> findByType(Integer areaType) {
		List<Department> list= super.getSession().createCriteria(Department.class, "d").add(Restrictions.eq("d.areaType",areaType)).add(Restrictions.eq("d.state",1)).addOrder(Order.asc("d.sort")).list();
		return list;
	}


	@Override
	public List<Department> findBySjareaId(Integer sjareaId) {
		List<Department> lists =super.getSession().createCriteria(Department.class, "d").add(Restrictions.eq("d.sjareaId",sjareaId)).list();
		return lists;
	}

	@Override
	public List<Department> findList() {
		StringBuffer hql =new StringBuffer("from Department c WHERE c.state=1");
		Query query = getSession().createQuery(hql.toString());
		return  query.list();
	}


}
