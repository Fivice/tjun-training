package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.StudentCardDao;
import tjuninfo.training.task.entity.StudentCard;

import java.util.List;


/**
 * 学生信息表数据层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Repository
public class StudentCardDaoImpl extends BaseDaoImpl<StudentCard> implements StudentCardDao {

	public StudentCardDaoImpl(){
		super(StudentCard.class);
	}


	@Override
	public List<StudentCard> list() {
		List<StudentCard> stuCardList = super.getSession().createCriteria(StudentCard.class).list();
		return stuCardList;
	}

	@Override
	public List<StudentCard> findListBy(String number) {
		StringBuffer hql =new StringBuffer("from StudentCard c where 1=1");
		if(number!=null && !number.equals("")){
			hql.append(" AND c.number = '" + number.trim() + "'");
		}
		Query query = getSession().createQuery(hql.toString());
		return  query.list();
	}

	@Override
	public List<StudentCard> findByStudentIdAndRegisterTime(String siId, String registerTime) {
		StringBuffer hql =new StringBuffer("from StudentCard c where 1=1");
		if(siId!=null && !siId.equals("")){
			hql.append(" AND c.studentId = " + Integer.parseInt(siId.trim()));
		}
		if(siId!=null && !siId.equals("")){
			hql.append(" AND c.registerTime = '" + registerTime.trim() + "'");
		}
		Query query = getSession().createQuery(hql.toString());
		return  query.list();
	}
}
