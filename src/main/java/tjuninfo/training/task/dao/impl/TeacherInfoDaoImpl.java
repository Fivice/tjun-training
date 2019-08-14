package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.TeacherInfoDao;
import tjuninfo.training.task.entity.TeacherInfo;
import tjuninfo.training.task.util.DateUtil;
import tjuninfo.training.task.util.Pages;

import java.util.Date;
import java.util.List;

/**
 * 教师信息数据层接口实现类
 * @author
 * @date 2018年5月29日
 */
@Repository
public class TeacherInfoDaoImpl extends BaseDaoImpl<TeacherInfo> implements TeacherInfoDao {
	public TeacherInfoDaoImpl(){
		super(TeacherInfo.class);
	}

	public Pages findAll(int pageSize, int pageIndex,String subject,String tiName,String tiDepartment) {
		StringBuffer hql =new StringBuffer("from TeacherInfo t where 1=1") ;
			if(subject!= null && !subject.equals("")){
				hql.append( " and t.subject like " + "'%"+ subject +"%'");
			}
			if(tiName != null && !tiName.equals("")){
				hql.append(" and t.tiName like " + "'%"+ tiName +"%'");
			}
			if(tiDepartment != null && !tiDepartment.equals("")){
				hql.append(" and t.tiDepartment like " + "'%"+ tiDepartment +"%'");
			}

		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageIndex, pageSize, query);

		return pages;
	}

	@Override
	public List<TeacherInfo> getTodayDining() {
		String currentTime = DateUtil.getDateTime(new Date()).substring(0,10);//获取当前日期
		StringBuffer sql = new StringBuffer("SELECT i.* from teacher_information i" +
				" where i.ti_id in  (SELECT r.teacher_id from teacher_dining_register r " +
				"where  r.id in(SELECT t.tdr_id from teacher_dining_for_scene t " +
				"where t.dining_date like '"+currentTime+"'))");
		List<TeacherInfo> list = this.getSession().createSQLQuery(sql.toString()).addEntity(TeacherInfo.class).getResultList();
		return list;
	}

	@Override
	public TeacherInfo getBysiIDNumber(String siIDNumber, String tiId) {
		StringBuffer hql =new StringBuffer(" from TeacherInfo t where t.siIDNumber="+ "'" + siIDNumber + "'");
		if (tiId!=null && !tiId.equals("")){
			hql.append(" and t.tiId != "+  Integer.parseInt(tiId.trim()) );
		}
		Query query = getSession().createQuery(hql.toString());
		return (TeacherInfo) query.uniqueResult();
	}

	@Override
	public boolean ifExist(String time, String idCard,Integer dinner) {
		boolean r = false;
		StringBuffer sql =new StringBuffer("SELECT cc.* from (SELECT i.ti_id,i.si_ID_number " +
				"from teacher_information i where i.ti_id in  (SELECT r.teacher_id from teacher_dining_register r " +
				"where  r.id in(SELECT t.tdr_id from teacher_dining_for_scene t where t.dining_date like '"+time+"' ");
		if(dinner==1){//1早2中3晚
			sql.append(" and t.breakfast=1");
		}else if(dinner==2){
			sql.append(" and t.lunch=1");
		}else if(dinner==3){
			sql.append(" and t.dinner=1");
		}
		sql.append(" ))) as cc where cc.si_ID_number = '"+idCard+"'");
		List<Object> list = this.getSession().createSQLQuery(sql.toString()).getResultList();
		if(list.size()>0){
			r = true;
		}
		return r;
	}

	@Override
	public boolean ifExist2(String idCard) {
		boolean r = false;
		StringBuffer sql =new StringBuffer("select * from teacher_information t where t.si_ID_number="+ "'" + idCard + "'");
		List<Object> list = this.getSession().createSQLQuery(sql.toString()).getResultList();
		if(list.size()>0){
			r = true;
		}
		return r;
	}

}
