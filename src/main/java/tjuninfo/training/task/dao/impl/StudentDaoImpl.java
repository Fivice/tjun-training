package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.StudentDao;
import tjuninfo.training.task.entity.Student;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 学生信息表数据层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Repository
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {

	public StudentDaoImpl(){
		super(Student.class);
	}


	@Override
	public List<Student> queryForPage(int currentPage, int pageSize, Student student) {
		List<Student> entitylist=null;
		StringBuffer hql =new StringBuffer("from Student c where 1=1") ;
		hql.append(" ORDER BY c.updateDate DESC ");
		Query query = getSession().createQuery(hql.toString());
		if(currentPage!=0&&pageSize!=0){
			query.setFirstResult(currentPage);
			query.setMaxResults(pageSize);
		}

		entitylist = query.list();

		return entitylist;
	}

	@Override
	public Pages getList(int pageSize, int pageIndex,String unitId,String unitName,String siIDNumber) {
		StringBuffer hql =new StringBuffer("from Student c where 1=1") ;
		/*if(null!=unitId && !unitId.equals("")){
			hql.append(" and c.siUnitId = "+unitId.trim());
		}else {*/
			if(null!=unitName && !unitName.equals("")){
				hql.append(" and c.unitName like "+"'%"+unitName.trim()+"%'");
			}
		/*}*/
		if(null!=siIDNumber && !siIDNumber.equals("")){
			hql.append(" and c.siIDNumber = "+"'"+siIDNumber.trim()+"'");
		}
		hql.append(" ORDER BY c.updateDate DESC ");
		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageIndex, pageSize, query);

		return pages;
	}

	@Override
	public Student findByNumber(String siIDNumber) {
		StringBuffer hql =new StringBuffer("from Student c where 1=1") ;
		if(null!=siIDNumber && !siIDNumber.equals("")){
			hql.append(" and c.siIDNumber = "+"'"+siIDNumber.trim()+"'");
		}
		Query query = getSession().createQuery(hql.toString());
		return (Student) query.uniqueResult();
	}

	@Override
	public Student getBysiIDNumber(String siIDNumber, String siId) {
		StringBuffer hql =new StringBuffer("select new Student(siId) from Student c where c.siIDNumber="+ "'" + siIDNumber + "'");
		if (siId!=null && !siId.equals("")){
			hql.append(" and c.siId != "+ "'" + Integer.parseInt(siId.trim()) + "'");
		}
		Query query = getSession().createQuery(hql.toString());
		return (Student) query.uniqueResult();
	}

	@Override
	public boolean ifExist(String time, String idCard,Integer dinner) {
		boolean r = false;

		StringBuffer sql =new StringBuffer("SELECT cc.* from (SELECT s.si_id,s.si_ID_number " +
				"from student_information s  RIGHT join (SELECT r.si_id as id from register r " +
				"where r.dining = 1 and r.class_id in (SELECT c.class_room from class_dining c " +
				"where c.`day` like '"+time+"'");
		if(dinner==1){//1早2中3晚
			sql.append(" and c.breakfast=1");
		}else if(dinner==2){
			sql.append(" and c.lunch=1");
		}else if(dinner==3){
			sql.append(" and c.dinner=1");
		}
		sql.append(" )) as lj on  s.si_id =lj.id) cc where cc.si_ID_number = '"+idCard+"'");
		List<Object> list = this.getSession().createSQLQuery(sql.toString()).getResultList();
		if(list.size()>0){
			r = true;
		}
		return r;
	}

	@Override
	public boolean ifExist2(String idCard) {
		boolean r = false;
		StringBuffer sql =new StringBuffer("select * from  student_information  c where c.si_ID_number="+ "'" + idCard + "'");
		List<Object> list = this.getSession().createSQLQuery(sql.toString()).getResultList();
		if(list.size()>0){
			r = true;
		}
		return r;
	}

	@Override
	public Student findStudentById(String id) {
		StringBuffer sql = new StringBuffer("select new Student(siName,siIDNumber) from Student c where 1=1");
		if (id != null && !id.equals("")){
			sql.append("and c.id = "+Integer.parseInt(id));
		}
		return (Student) getSession().createQuery(sql.toString()).uniqueResult();
	}

}
