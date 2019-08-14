package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.SchedulingDao;
import tjuninfo.training.task.entity.Scheduling;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 日程安排数据层接口实现类
 * @author
 * @date 2018年5月29日
 */
@Repository
public class SchedulingDaoImpl extends BaseDaoImpl<Scheduling> implements SchedulingDao {
	public SchedulingDaoImpl(){
		super(Scheduling.class);
	}

	public List<Scheduling> findAll(String day,String className,String id) {
		List<Scheduling> list=null;
		StringBuffer hql =new StringBuffer(" from Scheduling s where 1=1 ");
			if(day != null && !day.equals("")){
				hql.append(" and s.day =" + "'"+day+"'");
			}
			if(className!= null && !className.equals("")){
				hql.append(" and s.classroom =" + "'"+ className +"'");
			}
			if(id!= null && !id.equals("")){
				hql.append(" and s.classInfo.id =" + "'"+ Integer.parseInt(id) +"'");
			}
		hql.append(" ORDER BY s.day ASC");
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public Pages findSchList(int pageSize, int pageIndex, String day, String className, String id) {
		StringBuffer hql =new StringBuffer("from Scheduling s where 1=1") ;
		if(day!= null && !day.equals("")){
			hql.append("and s.day =" + "'"+day+"'");
		}
		if(className != null && !className.equals("")){
			hql.append(" and s.classroom like " + "'%"+ className +"%'");
		}
		if(id != null && !id.equals("")){
			hql.append( "and s.classInfo.id =" + "'"+ Integer.parseInt(id) +"'");
		}
		hql.append(" ORDER BY s.day ASC");
		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageIndex, pageSize, query);

		return pages;
	}

	public List<Scheduling> findEvaSchList(String id,int evaluate) {
		List<Scheduling> list=null;
		StringBuffer hql =new StringBuffer(" from Scheduling s where 1=1 ");

		if(id!= null && !id.equals("")){
			hql.append(" and s.classInfo.id =" + "'"+ Integer.parseInt(id) +"'");
		}
		hql.append(" and s.evaluate =" + "'"+ evaluate +"'");
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public List<String> findList(String id) {
		StringBuffer hql =new StringBuffer("SELECT scheduling_content AS schContent FROM scheduling WHERE class_id = "+id+" ") ;
		return getSession().createSQLQuery(hql.toString()).list();
	}

}
