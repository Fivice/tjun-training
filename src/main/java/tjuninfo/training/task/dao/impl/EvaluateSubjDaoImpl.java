package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.EvaluateScoreDao;
import tjuninfo.training.task.dao.EvaluateSubjDao;
import tjuninfo.training.task.entity.EvaluateScore;
import tjuninfo.training.task.entity.EvaluateSubj;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.EvaluateScoreVo;

import java.util.List;


/**
 * 评价结果表数据层接口实现类
 */
@Repository
public class EvaluateSubjDaoImpl extends BaseDaoImpl<EvaluateSubj> implements EvaluateSubjDao {

	public EvaluateSubjDaoImpl(){
		super(EvaluateSubj.class);
	}


	@Override
	public List<EvaluateSubj> findByClassId(Long classId) {
		StringBuffer hql =new StringBuffer("from EvaluateSubj c where 1=1") ;
		if (null!=classId){
			hql.append(" and c.classId="+classId);
		}
		Query query = getSession().createQuery(hql.toString());
		return query.list();
	}
}
