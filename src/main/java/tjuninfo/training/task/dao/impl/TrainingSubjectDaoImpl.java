package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.TrainingSubjectDao;
import tjuninfo.training.task.entity.TrainingSubject;

import java.util.List;

/**
 * 培训科目数据层接口实现类
 * @author
 * @date 2018年5月29日
 */
@Repository
public class TrainingSubjectDaoImpl extends BaseDaoImpl<TrainingSubject> implements TrainingSubjectDao {
	public TrainingSubjectDaoImpl(){
		super(TrainingSubject.class);
	}

	public List<TrainingSubject> findAll() {
		List<TrainingSubject> list=null;
		StringBuffer hql =new StringBuffer(" from TrainingSubject t where 1=1 ") ;
		try {
			Query query = getSession().createQuery(hql.toString());
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
