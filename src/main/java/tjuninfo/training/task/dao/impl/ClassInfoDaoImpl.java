package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ClassInfoDao;
import tjuninfo.training.task.dto.ClassInfoForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.ClassVo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 班级信息表数据层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Repository
public class ClassInfoDaoImpl extends BaseDaoImpl<ClassInfo> implements ClassInfoDao {

	public ClassInfoDaoImpl(){
		super(ClassInfo.class);
	}

	@Override
	public Pages getList(int pageSize, int pageIndex,String plan,String classNumber,
						 String startStopTime,String className,String teacherName,String userId,String time,String regPlace,String order,String entryStartTime, String evaluateStopTime,String sort, String sortOrder) {
		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1") ;
		if (plan!=null && !plan.equals("")){
			hql.append(" and c.plan="+Integer.parseInt(plan.trim()));
		}
		if (classNumber!=null && !classNumber.equals("")){
			hql.append(" and c.classNumber like "+ "'%" + classNumber.trim() + "%'");
		}
		if (startStopTime!=null && !startStopTime.equals("")){
			hql.append(" and c.startStopTime like "+ "'%" + startStopTime.trim() + "%'");
		}
		if (className!=null && !className.equals("")){
			hql.append(" and c.className like "+ "'%" + className.trim() + "%'");
		}
		if (teacherName!=null && !teacherName.equals("")){
			hql.append(" and c.teacherName like "+ "'%" + teacherName.trim() + "%'");
		}
		if (userId!=null && !userId.equals("")){
			hql.append(" and c.userId="+Integer.parseInt(userId.trim()));
		}
        if (time!=null && !time.equals("")){
            //hql.append(" and c.startStopTime>="+ "'" + time.trim() + "'");
			hql.append(" and c.startStopTime like"+ "'" + time.trim() + "%'");
        }
		if (regPlace!=null && !regPlace.equals("")){
			hql.append(" and c.regPlace="+ "'" + regPlace.trim() + "'");
		}
		if (entryStartTime!=null && !entryStartTime.equals("")){
			hql.append(" and c.entryStartTime like "+ "'%" + entryStartTime.trim() + "%'");
		}
		if (evaluateStopTime!=null && !evaluateStopTime.equals("")){
			hql.append(" and c.evaluateStopTime like "+ "'%" + evaluateStopTime.trim() + "%'");
		}
		if (order!=null && !order.equals("")){
			hql.append(" ORDER BY c.entryStartTime ASC");
		}else if (StringUtils.isNotBlank(sort)&&StringUtils.isNotBlank(sortOrder)){
			hql.append(" ORDER BY c.").append(sort).append(" ").append(sortOrder);
		}else {
			//hql.append(" ORDER BY c.updateDate DESC ");
			hql.append(" ORDER BY c.classNumber DESC ");
		}

		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageIndex, pageSize, query);

		return pages;
	}

	@Override
	public List<ClassInfo> getClassInfoList(String plan, String classNumber, String startStopTime, String className, String teacherName, String userId, String time, String regPlace) {
		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1") ;
		if (plan!=null && !plan.equals("")){
			hql.append(" and c.plan="+Integer.parseInt(plan.trim()));
		}
		if (classNumber!=null && !classNumber.equals("")){
			hql.append(" and c.classNumber like "+ "'%" + classNumber.trim() + "%'");
		}
		if (startStopTime!=null && !startStopTime.equals("")){
			hql.append(" and c.startStopTime like "+ "'%" + startStopTime.trim() + "%'");
		}
		if (className!=null && !className.equals("")){
			hql.append(" and c.className like "+ "'%" + className.trim() + "%'");
		}
		if (teacherName!=null && !teacherName.equals("")){
			hql.append(" and c.teacherName like "+ "'%" + teacherName.trim() + "%'");
		}
		if (userId!=null && !userId.equals("")){
			hql.append(" and c.userId="+Integer.parseInt(userId.trim()));
		}
		if (time!=null && !time.equals("")){
			hql.append(" and c.startStopTime>="+ "'" + time.trim() + "'");
		}
		if (regPlace!=null && !regPlace.equals("")){
			hql.append(" and c.regPlace="+ "'" + regPlace.trim() + "'");
		}
//		hql.append(" ORDER BY c.updateDate DESC ");
		hql.append(" ORDER BY c.classNumber DESC ");
		Query query = getSession().createQuery(hql.toString());
		return query.list();
	}

	@Override
	public ClassInfo getByclassNumber(String classNumber,String classInfoId) {
		StringBuffer hql =new StringBuffer("select new ClassInfo(id) from ClassInfo c where c.classNumber="+ "'" + classNumber.trim() + "'");


		if (classInfoId!=null && !classInfoId.equals("")){
			hql.append(" and c.id != "+ "'" + Integer.parseInt(classInfoId) + "'");
		}
		Query query = getSession().createQuery(hql.toString());
		return (ClassInfo) query.uniqueResult();
	}

	@Override
	public ClassInfo getByclassNumber(String classNumber) {
		StringBuffer hql =new StringBuffer("select new ClassInfo(id) from ClassInfo c where c.classNumber="+ "'" + classNumber.trim() + "'");
		Query query = getSession().createQuery(hql.toString());
		return (ClassInfo) query.uniqueResult();
	}

	@Override
	public List<ClassInfo> getByUserId(Integer userId) {
		List<ClassInfo> list=null;
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String nowDate = sdf.format(date);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM");
		String nextDate =sdff.format(cal.getTime());
		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1 ") ;
		if (userId!=null && !userId.equals("")){
			hql.append(" and c.userId="+userId);
		}
		hql.append("and evaluate_stop_time like '" + "%" + nowDate.trim() + "%" + "' or evaluate_stop_time like '" + "%" + nextDate.trim() + "%" + "'");*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String nowDate = sdf.format(date);//当前日期

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);
		String nextDate =sdf.format(c.getTime());//后7天日期

		c.add(Calendar.DAY_OF_MONTH, -9);
		String yDate =sdf.format(c.getTime());
		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1 ") ;
		if (userId!=null && !userId.equals("")){
			hql.append(" and c.userId="+userId);
		}
		hql.append(" and '"+nextDate +"' > c.entryStartTime and '"+yDate+"' < c.evaluateStopTime");

//		StringBuffer hql =new StringBuffer("SELECT * from class_info c where c.userId = "+userId+" and '"+nextDate
//				+"' > c.entry_start_time and '"+yDate+"' < c.evaluate_stop_time") ;
		System.out.println(hql);
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public List<ClassInfo> list() {
		List<ClassInfo> list=null;
	/*	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String nowDate = sdf.format(date);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM");
		String nextDate =sdff.format(cal.getTime());
		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1 ") ;
		hql.append("and entry_start_time like '" + "%" + nowDate.trim() + "%" + "' or entry_start_time like '" + "%" + nextDate.trim() + "%" + "'");
		return getSession().createQuery(hql.toString()).list();*///日期获取不准确
		StringBuffer sql = new StringBuffer("SELECT ci.* FROM class_info ci WHERE STR_TO_DATE(RIGHT(ci.start_stop_time,10),'%Y-%m-%d')>NOW() ");
		sql.append(" GROUP BY ci.id");
		list =getSession().createSQLQuery(sql.toString()).addEntity(ClassInfo.class).list();
		return list ;
	}

	@Override
	public List<ClassInfo> findList(Integer siId) {
		String sql = "SELECT class_name as className,host_unit as hostUnit,start_stop_time as startStopTime,day_num as dayNum,time_num as timeNum  FROM class_info WHERE id IN" +
				"(SELECT DISTINCT class_id FROM register where si_id = "+siId+")";
		List<ClassInfo> list = super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ClassInfo.class)).list();
		return list;
	}

	@Override
	public List<ClassVo> findList(BTView<ClassVo> bt, Integer siId) {
		String sqlTotal = "SELECT count(id)  FROM class_info WHERE id IN" +
				"(SELECT DISTINCT class_id FROM register where si_id = "+siId+")";
		String sql = "SELECT c.id as id,c.class_name as className,c.host_unit as hostUnit,c.start_stop_time as startStopTime,c.day_num as dayNum,c.time_num as timeNum  FROM class_info c WHERE c.id IN" +
				"(SELECT DISTINCT class_id FROM register where si_id = "+siId+")";
		bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal).uniqueResult().toString()));
		return  super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ClassVo.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
	}

	@Override
    public List<ClassInfo> findClassInfosByDate(String date,String type,String plan) {
        /*SELECT * FROM class_info c WHERE c.id in (SELECT r.class_id FROM register r WHERE r.pay='1' ) AND c.start_stop_time LIKE '%2018-10%'*/
		/*StringBuffer hql =new StringBuffer("select new ClassInfo(id,dayNum) from ClassInfo c WHERE c.id in (SELECT r.classId FROM Register r)");*/
        /*StringBuffer hql =new StringBuffer("select new ClassInfo(id,dayNum) from ClassInfo c WHERE c.id in (SELECT r.classId FROM Register r WHERE r.pay='1' )");*/
		StringBuffer hql =new StringBuffer("select new ClassInfo(id,dayNum) from ClassInfo c WHERE 1=1");
        if(date!=null && !date.equals("")){
            /*hql.append(" AND c.startStopTime LIKE '" + "%" + date.trim() + "%" + "'");*/
			String[] days=date.trim().split("至");
			hql.append(" AND c.startStopTime BETWEEN"+ "'" + days[0].trim() + "'"+" and "+ "'" + days[1].trim() + "' ");
        }
        if(type!=null && !type.equals("")){
            hql.append(" AND c.typeId = '" +Integer.parseInt(type.trim()) +"'");
        }
        if(plan!=null && !plan.equals("")){
            hql.append(" AND c.plan = '" +Integer.parseInt(plan.trim()) +"'");
        }
        Query query = getSession().createQuery(hql.toString());
        return  query.list();
    }


	@Override
	public Pages findAllByStartStopTime(int pageSize, int pageIndex,String startDay, String endDay, String classNumber, String className, String trainingType, String plan,Integer useId,String place) {
		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1") ;
		if (useId != null && !useId.equals("")){
			hql.append("and c.userId = "+Integer.valueOf(useId));
		}
		if (classNumber != null && !classNumber.equals("")){
			hql.append("and c.classNumber like "+"'%"+classNumber+"%'");
		}
		if (className != null && !className.equals("")){
			hql.append("and c.className like "+"'%"+className+"%'");
		}
		if (trainingType != null && !trainingType.equals("")){
			hql.append("and c.typeId = "+Integer.valueOf(trainingType));
		}
		if (plan != null && !plan.equals("")){
			hql.append("and c.plan = "+Integer.valueOf(plan));
		}
		if (startDay!=null && !startDay.equals("")){
			hql.append("and c.startStopTime BETWEEN"+ "'" + startDay + "'"+" and "+ "'" + endDay + "' ");
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(place)){
			hql.append(" AND c.regPlace = '").append(place).append("'");
		}
		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageIndex, pageSize, query);
		return pages;
	}

	@Override
	public Pages classList(int pageSize, int pageIndex, String startStopTime, String classNumber, String className, String trainingType, String plan, Integer useId, String place) {
		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1") ;
		if (useId != null && !useId.equals("")){
			hql.append(" and c.userId = "+Integer.valueOf(useId));
		}
		if (classNumber != null && !classNumber.equals("")){
			hql.append(" and c.classNumber like "+"'%"+classNumber+"%'");
		}
		if (className != null && !className.equals("")){
			hql.append(" and c.className like "+"'%"+className+"%'");
		}
		if (trainingType != null && !trainingType.equals("")){
			hql.append(" and c.typeId = "+Integer.valueOf(trainingType));
		}
		if (plan != null && !plan.equals("")){
			hql.append(" and c.plan = "+Integer.valueOf(plan));
		}
		if (startStopTime!=null && !startStopTime.equals("")){
			hql.append(" and c.startStopTime like '%"+startStopTime+"%'");
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(place)){
			hql.append(" AND c.regPlace = '").append(place).append("'");
		}
		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageIndex, pageSize, query);
		return pages;
	}

	@Override
	public ClassInfo getClassIfo(Integer siId, String time) {
		//根据时间和学员id拿到学员所在班级id  (拿取就餐安排表和报名表的班级id交集)
		StringBuffer sql = new StringBuffer("SELECT * from class_info o where o.id = " +
				"(SELECT c.class_room from class_dining c where c.`day` = '"+time+"' " +
				"and c.class_room in(SELECT r.class_id as id from register r where r.si_id ='"+siId+"'))");
		ClassInfo c = (ClassInfo) this.getSession().createSQLQuery(sql.toString()).addEntity(ClassInfo.class).uniqueResult();//判断身份证是学员时查出有结果集
		return c;
	}

	@Override
	public List<ClassInfo> byDiningPlace(String eatPlace) {
		StringBuffer sql = new StringBuffer("from ClassInfo c where 1=1");
		if (eatPlace != null && !eatPlace.equals("")){
			sql.append("and c.diningPlace = "+"'"+eatPlace+"'");
		}
		return  getSession().createQuery(sql.toString()).list();
	}

//	@Override
//	public List<ClassInfo> findAllByStartStopTime(String startDay, String endDay) {
//		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1") ;
//		if (startDay!=null && !startDay.equals("")){
//			hql.append("and c.startStopTime BETWEEN"+ "'" + startDay + "'"+" and "+ "'" + endDay + "' ");
//		}
//		return getSession().createQuery(hql.toString()).list();
//	}

	/**
	 *
	 * wubin 添加，如有修改请通知
	 */
	@Override
	public Page<ClassInfoForDiningStatisticsDTO> getClassInfo(int pageIndex, int pageSize, String startStopTime, String diningPlace,Integer userId) {
		String[] timeArr;
		String startTime,stopTime;
		boolean startTimeFlag,stopTimeFlag;
		Page<ClassInfoForDiningStatisticsDTO> page = new Page<>();
		StringBuffer sql = new StringBuffer("SELECT ci.id AS classId,ci.class_number AS classNumber,ci.class_name AS className,ci.dining_place diningPlace,ci.planned_number AS plannedNumber From class_info ci WHERE 1=1");

		//判断时间段字符串不为空
		if (StringUtils.isNotBlank(startStopTime)){
			timeArr = startStopTime.split(" - ");
			//		时间格式验证
			String el = "\\d{4}-\\d{2}-\\d{2}";
			Pattern pattern = Pattern.compile(el);
		//判断字符串符合"yyyy-MM-dd - yyyy-MM-dd"的格式要求
			if (timeArr.length == 2){
				startTime = timeArr[0];
				stopTime = timeArr[1];
				Matcher startTimeMatcher = pattern.matcher(startTime);
				Matcher stopTimeMatcher = pattern.matcher(stopTime);
				startTimeFlag = startTimeMatcher.matches();
				stopTimeFlag = stopTimeMatcher.matches();
				if (startTimeFlag&&stopTimeFlag){
					sql.append(" NOT IN (LEFT(ci.start_stop_time,10)>'").append(stopTime).append("' OR RIGHT(ci.start_stop_time,10)<'").append(startTime).append("')");
				}
			}
		}
		//如果就餐地点字符串不为空
		if (StringUtils.isNotBlank(diningPlace)){
			sql.append(" AND ci.dining_place = '").append(diningPlace).append("' ");
		}
		if (userId!=null){
			sql.append(" AND ci.user_id = ").append(userId);
		}
		Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(ClassInfoForDiningStatisticsDTO.class));
		int count = query.list().size();
		query.setFirstResult((pageIndex-1)*pageSize);
		query.setMaxResults(pageSize);
		List<ClassInfoForDiningStatisticsDTO> list = query.list();

		page.setList(list);
		page.setPageSize(pageSize);
		page.setPageNo(pageIndex);
		page.setTotalRecords(count);
		return page;
	}
	@Override
	public Page<ClassInfoForDiningStatisticsDTO> findPrepList(int pageSize, int pageIndex, String today, String diningPlace) {
		Page<ClassInfoForDiningStatisticsDTO> page = new Page<>();
		StringBuffer sql = new StringBuffer("SELECT ci.id AS classId,ci.class_number AS classNumber,ci.class_name AS className,ci.dining_place diningPlace,ci.planned_number AS plannedNumber From class_info ci WHERE 1=1");


		if(today!= null && !today.equals("")){
			sql.append(" NOT IN (LEFT(ci.start_stop_time,10)>'").append(today).append("' OR RIGHT(ci.start_stop_time,10)<'").append(today).append("')");
			/*hql.append(" and LEFT(c.startStopTime,10)<'"+today+"'");
			hql.append(" and RIGHT(c.startStopTime,10)>'"+today+"'");*/
		}
		//如果就餐地点字符串不为空
		if (StringUtils.isNotBlank(diningPlace)){
			sql.append(" AND ci.dining_place = '").append(diningPlace).append("' ");
		}


		Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(ClassInfoForDiningStatisticsDTO.class));

		int count = query.list().size();
		query.setFirstResult((pageIndex-1)*pageSize);
		query.setMaxResults(pageSize);
		List<ClassInfoForDiningStatisticsDTO> list = query.list();

		page.setList(list);
		page.setPageSize(pageSize);
		page.setPageNo(pageIndex);
		page.setTotalRecords(count);

		return page;
	}

	@Override
	public String findMaxClassNumber() {
		String l = (String)getSession().createQuery("select MAX(c.classNumber) from ClassInfo c").uniqueResult();
		return l;
	}


	public Pages getTeachDinProfile(int pageSize, int pageIndex, String classNumber,
							 String startStopTime, String className, String teacherName,
							 String userId,String orderName,String orderBy){
		StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1");
		if(classNumber != null && !("").equals(classNumber)){
			hql.append(" and c.classNumber like " + "'%").append(classNumber).append("%'");
		}
		if(className != null && !("").equals(className)){
			hql.append(" and c.className like " + "'%").append(className).append("%'");
		}
		if(teacherName != null && !("").equals(teacherName)){
			hql.append(" and c.teacherName like " + "'%").append(teacherName).append("%'");
		}
		if(startStopTime != null && !("").equals(startStopTime)){
			hql.append(" and c.startStopTime like " + "'%").append(startStopTime).append("%'");
		}
		if( userId!=null && !userId.equals("")){
			hql.append(" and c.userId = ").append(userId);
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(orderName)&& org.apache.commons.lang3.StringUtils.isNotBlank(orderBy)){
			hql.append(" ORDER BY c.").append(orderName).append(" ").append(orderBy);
		}
		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageIndex, pageSize, query);
		return pages;
	}


	@Override
	public ClassInfo findClassInfoByClassId(String classId) {
		StringBuffer sql = new StringBuffer("select new ClassInfo(className,trainingExpense,interScaleFee,singleRoomCharge,regPlace,otherCharges,dayNum,increaseDay) from ClassInfo c where 1=1");
		if (classId != null && !classId.equals("")){
			sql.append("and c.id = "+Long.valueOf(classId));
		}
		return (ClassInfo) getSession().createQuery(sql.toString()).uniqueResult();
	}

}
