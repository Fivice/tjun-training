package tjuninfo.training.task.dao.impl;


import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ScoreReportDao;
import tjuninfo.training.task.entity.ScoreReport;

import java.util.List;

/**
 * 成绩证书数据层接口实现类
 * @author
 * @date 2018年5月29日
 */
@Repository
public class ScoreReportDaoImpl extends BaseDaoImpl<ScoreReport> implements ScoreReportDao {
	public ScoreReportDaoImpl(){
		super(ScoreReport.class);
	}

	public List<ScoreReport> findAll(String month,String teacherName) {
		StringBuffer hql =new StringBuffer(" from ScoreReport s where 1=1 ");
			if(month!= null && !month.equals("")){
				hql.append(" and s.classInfo.startStopTime like "+ "'%"+ month +"%'") ;
			}
			if(teacherName!= null && !teacherName.equals("")){
				hql.append(" and s.classInfo.teacherName = "+ "'"+teacherName+"'") ;
			}
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public List<ScoreReport> findAll2(Long classId) {
		StringBuffer hql = new StringBuffer(" from ScoreReport s where 1=1 ");
		if(classId!= null && !classId.equals("")){
			hql.append(" and s.classInfo.id = "+classId) ;
		}
		return getSession().createQuery(hql.toString()).list();

	}

	@Override
	public void deleteByClssId(Long classId) {
		super.getSession().createSQLQuery("delete from score_report where class_id=:classId")
				.setParameter("classId", classId).executeUpdate();
	}

	@Override
	public ScoreReport selectScoreReport(String id, String classId) {
		StringBuffer hql =new StringBuffer(" from ScoreReport s where 1=1 ");
		if(id!= null && !id.equals("")){
			hql.append(" and s.register.siId = "+Integer.parseInt(id));
		}
		if(classId!= null && !classId.equals("")){
			hql.append(" and s.classInfo.id = "+Long.parseLong(classId));
		}

		return (ScoreReport) getSession().createQuery(hql.toString()).uniqueResult();
	}

}
