package tjuninfo.training.task.dao.impl;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.TeachDiningDao;
import tjuninfo.training.task.dto.TeachDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.StuFirstDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningRecordVO;
import tjuninfo.training.task.vo.TeachDiningVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/17 11:58
 * @Description:
 */
@Repository
public class TeachDiningDaoImpl extends BaseDaoImpl<TeachDining> implements TeachDiningDao {
    public TeachDiningDaoImpl() {
        super(TeachDining.class);
    }

    @Override
    public List<TeachDining> findTeachDiningList(String schoolName,String number,String month,String arranger) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String d = sdf.format(date);
        StringBuffer sql = new StringBuffer("SELECT DISTINCT number,teacher as teacherName,arranger,class_id as classId,area FROM teach_dining t where 1=1");
        if (schoolName != null && !schoolName.equals("")) {
            sql.append(" and t.area = " + "'" + schoolName + "'");
        }
        if (number != null && !number.equals("")) {
            sql.append( " and t.number = " + number);
        }
        if (month != null && !month.equals("")) {
            sql.append( " and t.day like "+ "'%" + month + "%'");
        }
        if (arranger != null && !arranger.equals("")) {
            sql.append(" and t.arranger = "+ "'" + arranger + "'") ;
        }
        sql.append(" order by t.time DESC ") ;
        List<TeachDining> list = super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDining.class)).list();
        return list;
    }

    @Override
    public List<TeachDining> findTeachDiningList(BTView<TeachDining> bt, String schoolName, String number, String month, String arranger) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String d = sdf.format(date);
        StringBuffer sql = new StringBuffer("SELECT DISTINCT number,teacher as teacherName,userName as arranger,class_id as classId,area,time FROM teach_dining t JOIN sys_user s ON t.arranger = s.user_id JOIN class_info c ON t.class_id = c.id");
        if (schoolName != null && !schoolName.equals("")) {
            sql.append(" and t.area = " + "'" + schoolName + "'");
        }
        if (number != null && !number.equals("")) {
            sql.append(" and t.number = " + "'" + number + "'") ;
        }
        if (month != null && !month.equals("")) {
            sql.append(" and c.start_stop_time like "+ "'%" + month + "%'") ;
        }
        if (arranger != null && !arranger.equals("")) {
            sql.append(" and t.arranger = "+ "'" + arranger + "'") ;
        }
        sql.append(" order by t.time DESC ");
        StringBuffer sqlTotal = new StringBuffer("SELECT count(DISTINCT number) FROM teach_dining t JOIN class_info c ON t.class_id = c.id where 1=1");
        if (schoolName != null && !schoolName.equals("")) {
            sqlTotal.append( " and t.area = " + "'" + schoolName + "'");
        }
        if (number != null && !number.equals("")) {
            sqlTotal.append(" and t.number = " + "'" + number + "'") ;
        }
        if (month != null && !month.equals("")) {
            sqlTotal.append(" and c.start_stop_time like "+ "'%" + month + "%'");
        }
        if (arranger != null && !arranger.equals("")) {
            sqlTotal.append(" and t.arranger = "+ "'" + arranger + "'");
        }
        sqlTotal.append(" order by t.time DESC ") ;
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDining.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public List<TeachDining> findTeachDiningList1(BTView<TeachDining> bt, String schoolName, String number, String month, String arranger,Integer classId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String d = sdf.format(date);
        StringBuffer sql = new StringBuffer("SELECT DISTINCT number,teacher as teacherName,userName as arranger,class_id as classId,area,time FROM teach_dining t JOIN sys_user s ON t.arranger = s.user_id JOIN class_info c ON t.class_id = c.id");
        if (schoolName != null && !schoolName.equals("")) {
            sql.append(" and t.area = " + "'" + schoolName + "'");
        }
        if (number != null && !number.equals("")) {
            sql.append(" and t.number = " + "'" + number + "'") ;
        }
        if (month != null && !month.equals("")) {
            sql.append(" and t.time like "+ "'%" + month + "%'") ;
        }
        if (arranger != null && !arranger.equals("")) {
            sql.append(" and t.arranger = "+ "'" + arranger + "'") ;
        }
        if (classId != 0 && !classId.equals("")) {
            sql.append(" and t.class_id = "+ classId) ;
        }
        sql.append(" order by t.time DESC ");
        StringBuffer sqlTotal = new StringBuffer("SELECT count(DISTINCT number) FROM teach_dining t JOIN class_info c ON t.class_id = c.id where 1=1");
        if (schoolName != null && !schoolName.equals("")) {
            sqlTotal.append( " and t.area = " + "'" + schoolName + "'");
        }
        if (number != null && !number.equals("")) {
            sqlTotal.append(" and t.number = " + "'" + number + "'") ;
        }
        if (month != null && !month.equals("")) {
            sqlTotal.append(" and t.time like "+ "'%" + month + "%'");
        }
        if (arranger != null && !arranger.equals("")) {
            sqlTotal.append(" and t.arranger = "+ "'" + arranger + "'");
        }
        if (classId != 0 && !classId.equals("")) {
            sqlTotal.append(" and t.class_id = "+ classId) ;
        }
        sqlTotal.append(" order by t.time DESC ") ;
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDining.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }


    @Override
    public List<TeachDining> findTeachDiningList(String number) {
        String sql ="SELECT number,teacher as teacherName,class_id as classId,arranger,time,area,`day`,breakfast,lunch,dinner FROM teach_dining WHERE 1=1 AND number ="+number+"";
        List<TeachDining> list = super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TeachDining.class)).list();
        return list;
    }


    @Override
    public List<TeachDining> findDiningList(String number) {
        String sql ="SELECT id,number,`day`,breakfast,lunch,dinner FROM teach_dining WHERE 1=1 AND number ="+number+" AND `day` is not NULL ";
        List<TeachDining> list = super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TeachDining.class)).list();
        return list;
    }

    @Override
    public List<TeachDining> findDiningList(BTView<TeachDining> bt, String number,String time) {
        String sqlTotal ="SELECT count(*) FROM teach_dining WHERE 1=1 AND number ='"+number+"' AND time ='"+time+"' AND `day` is not NULL ";
        String sql ="SELECT id,number,`day`,breakfast,lunch,dinner FROM teach_dining WHERE 1=1 AND number ="+number+" AND time ='"+time+"' AND `day` is not NULL ";
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TeachDining.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public List<TeachDining> findTeachDiningList(String number, String time) {
        String sql ="SELECT number,teacher as teacherName,class_id as classId,arranger,area,time,`day`,breakfast,lunch,dinner FROM teach_dining WHERE 1=1 AND number ='"+number+"' AND time ='"+time+"'  ";
        List<TeachDining> list = super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TeachDining.class)).list();
        return list;
    }

    @Override
    public boolean ifDing(String number, String teacherName, String time, String day, Integer dinner) {
        boolean r = false; day = day.substring(0,10);
        StringBuffer sql =new StringBuffer("SELECT * from teach_dining t where t.time='"+time+"' and t.number='"
                +number+"' and t.teacher = '"+teacherName+"' and  t.`day` ='"+day+"'");
        if(dinner ==1){//早餐
            sql.append("and t.breakfast = 1 ");
        }else if(dinner ==2){//中餐
            sql.append("and t.lunch = 1");
        }else if(dinner ==3) {//晚餐
            sql.append("and t.dinner = 1");
        }
        List<Object> list = this.getSession().createSQLQuery(sql.toString()).getResultList();
        if(list.size()>0){
            r = true;
        }
        return r;
    }

    @Override
    public List<TeachDining> byTime(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList) {
        List<Integer> classId = new ArrayList<Integer>();
        StringBuffer sql = new StringBuffer("from TeachDining t where 1=1");
        if(classInfoList.size()>0){
            for(ClassInfo classInfo : classInfoList){
                classId.add(classInfo.getId().intValue());
            }
            if(classInfoList.size() > 1){
                sql.append(" and (t.classId = "+classId.get(0)+"");
                for(int i = 1 ; i<classId.size(); i++){
                    sql.append( " or t.classId = "+classId.get(i)+" ");
                }
                sql.append(")");
            }else {
                sql.append(" and (t.classId = "+classId.get(0)+")");
            }
        }
        if (classDining.getBreakfast() != null) {
            sql.append("and t.breakfast = " + 1);
        }
        if (classDining.getLunch() != null) {
            sql.append("and t.lunch = " + 1);
        }
        if (classDining.getDinner() != null) {
            sql.append("and t.dinner = " + 1);
        }
        sql.append("and t.day = " +"'"+nowDate+"'");
        return getSession().createQuery(sql.toString()).list();
    }

    @Override
    public List<TeachDiningVO> byTime2(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList) {
        List<Integer> classId = new ArrayList<Integer>();
        StringBuffer sql = new StringBuffer("select t.id as id, t.class_id as classId,\n" +
                "        count(1=1) as countB  from teach_dining t where 1=1");
        if(classInfoList.size()>0){
            for(ClassInfo classInfo : classInfoList){
                classId.add(classInfo.getId().intValue());
            }
            if(classInfoList.size() > 1){
                sql.append(" and (t.class_id = "+classId.get(0)+"");
                for(int i = 1 ; i<classId.size(); i++){
                    sql.append( " or t.class_id = "+classId.get(i)+" ");
                }
                sql.append(")");
            }else {
                sql.append(" and (t.class_id = "+classId.get(0)+")");
            }
        }
        if (classDining.getBreakfast() != null) {
            sql.append("and t.breakfast = " + 1);
        }
        if (classDining.getLunch() != null) {
            sql.append("and t.lunch = " + 1);
        }
        if (classDining.getDinner() != null) {
            sql.append("and t.dinner = " + 1);
        }
        sql.append(" and t.day = " +"'"+nowDate+"'");
        sql.append(" group by t.class_id ");
        return super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningVO.class)).list();
    }

    @Override
    public List<TeachDining> findTeachDinList(Integer id) {
        String sql ="SELECT number,teacher as teacherName,class_id as classId,area,`day`,breakfast,lunch,dinner FROM teach_dining WHERE 1=1 AND class_id ="+id+"";
        List<TeachDining> list = super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TeachDining.class)).list();
        return list;
    }

    @Override
    public void deleteByNid(String number,String time) {
        String sql ="delete from teach_dining where number='"+number+"' and time='"+time+"' ";
        super.getSession().createSQLQuery(sql).executeUpdate();
//        super.getSession().createSQLQuery("delete from teach_dining where number=:number")
//                .setParameter("number", number).executeUpdate();
    }

    @Override
    public void updateByNumber(String teacherName,Integer classId,String area,String number,String time) {
        String sql ="UPDATE teach_dining SET teacher = '"+teacherName+"',class_id = '"+classId+"',area = '"+area+"' where number='"+number+"' and time = '"+time+"' ";
        super.getSession().createSQLQuery(sql).executeUpdate();
//        super.getSession().createSQLQuery("UPDATE teach_dining SET teacher = '"+teacherName+"',class_id = '"+classId+"',area = '"+area+"' where number=:number ")
//                .setParameter("number", number).executeUpdate();
    }

    @Override
    public void deleteByNumber(String number,String time) {
        String sql ="delete from teach_dining where number='"+number+"' and time='"+time+"' AND `day` is not NULL";
        super.getSession().createSQLQuery(sql).executeUpdate();
//        super.getSession().createSQLQuery("delete from teach_dining where number=:number AND `day` is not NULL ")
//                .setParameter("number", number).executeUpdate();
    }

    @Override
    public TeachDiningDataForDiningStatisticsDTO getTeachDining(String diningPlace, String classId, String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT td.class_id AS classId,td.area AS diningPlace,SUM(td.breakfast =1) AS breakfastEatCounts,SUM(td.lunch=1) AS lunchEatCounts,SUM(td.dinner=1) AS dinnerEatCounts " +
                "FROM teach_dining td WHERE 1=1 ");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND td.area = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND td.class_id = ").append(classId);
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND `day`>= '").append(startTime).append("'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND `day`<= '").append(endTime).append("'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningDataForDiningStatisticsDTO.class));

        return (TeachDiningDataForDiningStatisticsDTO) query.uniqueResult();
    }

    @Override
    public TeachDiningDataForDiningStatisticsDTO getTeachFirstDayDining(String diningPlace, String classId,String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT td.class_id AS classId,td.area AS diningPlace,SUM(td.breakfast =1) AS breakfastEatCounts,SUM(td.lunch=1) AS lunchEatCounts,SUM(td.dinner=1) AS dinnerEatCounts " +
                "FROM teach_dining td INNER JOIN class_info ci ON ci.id = td.class_id AND td.`day` = LEFT(ci.start_stop_time,10) ");
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND td.class_id = '").append(classId).append("'");
        }
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND td.area = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND td.`day`>= '").append(startTime).append("'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND td.`day`<= '").append(endTime).append("'");
        }
        Query query =getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningDataForDiningStatisticsDTO.class));
        return (TeachDiningDataForDiningStatisticsDTO) query.uniqueResult();
    }

    @Override
    public List<TeachDining> TeachDiningPrepList(String classNum, String time,Integer b,Integer l,Integer d) {
        StringBuffer hql =new StringBuffer("from TeachDining c where 1=1") ;
        if (classNum!=null && !classNum.equals("")){
            hql.append(" and c.classId="+ "'" + classNum.trim() + "'");
        }
        if (time!=null && !time.equals("")){
            hql.append(" and c.day="+ "'" + time.trim() + "'");
        }
        if (b!=null && !b.equals("")){
            hql.append(" and c.breakfast="+b);
        }
        if (l!=null && !l.equals("")){
            hql.append(" and c.lunch="+l);
        }
        if (d!=null && !d.equals("")){
            hql.append(" and c.dinner="+ d);
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public long getCountByClassIdFromTeachDining(long classId,String startStopTime) {
        StringBuffer hql =new StringBuffer("select count(distinct number)  from TeachDining t where 1=1") ;
            if (classId <= 0) {//非法id
                return -1;
            } else {
                hql.append(" and t.classId = ").append(classId);
            }
            if (startStopTime != null && !startStopTime.equals("")) {
            hql.append( " and t.time like "+ "'%" + startStopTime + "%'");
            }
        long count =  (long)getSession().createQuery(hql.toString()).uniqueResult();
        return count;
    }

    @Override
    public Pages teachDiningList1(int pageSize, int pageIndex, String schoolName, String number, String month,Integer classId) {
        StringBuffer hql =new StringBuffer("select count(distinct number)from TeachDining t where 1=1 ") ;

        if (schoolName != null && !schoolName.equals("")) {
            hql.append(" and t.area = " + "'" + schoolName + "'");
        }
        if (number != null && !number.equals("")) {
            hql.append(" and t.number = " + "'" + number + "'") ;
        }
        if (month != null && !month.equals("")) {
            hql.append(" and t.time like "+ "'%" + month + "%'") ;
        }
        if (classId != 0 && !classId.equals("")) {
            hql.append(" and t.classId = "+ classId) ;
        }
        hql.append(" ORDER BY t.day ASC");
        Query query = getSession().createQuery(hql.toString());
        Pages pages = new Pages(pageIndex, pageSize, query);

        return pages;
    }
}
