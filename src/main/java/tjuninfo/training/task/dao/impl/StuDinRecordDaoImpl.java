package tjuninfo.training.task.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.StuDinRecordDao;
import tjuninfo.training.task.dto.StuDiningRecordStatisticsDTO;
import tjuninfo.training.task.entity.StuDiningRecord;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.StuDiningRecordVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:42
 * @Description:就餐统计数据层接口实现类
 */
@Repository
public class StuDinRecordDaoImpl extends BaseDaoImpl<StuDiningRecord> implements StuDinRecordDao {

    public StuDinRecordDaoImpl() {
        super(StuDiningRecord.class);
    }


    @Override
    /**根据学员ID编号查看学员就餐详情**/
    public List<StuDiningRecord> findAll(Integer student) {
        return super.getSession().createCriteria(StuDiningRecord.class, "s").add(Restrictions.eq("s.student", student)).addOrder(Order.asc("s.day")).list();
    }

    @Override
    public List<StuDiningRecordVO> findSum(String area, Integer classRoom, String className, Integer userId,Integer roleId,String month) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = new Date();
//            String d = sdf.format(date);
            String sql = null;
//            if(roleId == 3){
//                sql = "SELECT qq.studentId as studentId,qq.classId as classId,qq.className as className,qq.classNumber as classNumber,qq.countB as countB,qq.countL as countL,qq.countD as countD,qq.diningPlace as diningPlace,qq.studentName as studentName,qq.idCard as idCard,ssss.rate as rate from (" +
//                        "SELECT DISTINCT d.student AS studentId,d.class_room AS classId,SUM(d.breakfast = 1) AS countB,SUM(d.lunch = 1) AS countL,SUM(d.dinner = 1) AS countD,s.si_name AS studentName," +
//                        "s.si_ID_number AS idCard,c.class_number AS classNumber,c.class_name AS className,c.dining_place AS  diningPlace FROM stud_dining_record d JOIN student_information s ON d.student = s.si_id" +
//                        " JOIN class_info c ON d.class_room = c.id GROUP BY d.student) qq JOIN ( SELECT class_room,(SUM(breakfast = 1)+SUM(lunch = 1)+SUM(dinner = 1)) as rate  from " +
//                        "class_dining where `day` < '"+d+"' AND class_room in (SELECT d.class_room FROM stud_dining_record d GROUP BY d.student) GROUP BY class_room) ssss ON qq.classId = ssss.class_room";
//            }else {
//                sql = "SELECT qq.studentId as studentId,qq.classId as classId,qq.className as className,qq.classNumber as classNumber,qq.countB as countB,qq.countL as countL,qq.countD as countD,qq.diningPlace as diningPlace,qq.studentName as studentName,qq.idCard as idCard,ssss.rate as rate from (" +
//                        "SELECT DISTINCT d.student AS studentId,d.class_room AS classId,SUM(d.breakfast = 1) AS countB,SUM(d.lunch = 1) AS countL,SUM(d.dinner = 1) AS countD,s.si_name AS studentName," +
//                        "s.si_ID_number AS idCard, c.class_number AS classNumber,c.class_name AS className,c.dining_place AS  diningPlace FROM stud_dining_record d" +
//                        " JOIN student_information s ON d.student = s.si_id JOIN class_info c ON d.class_room = c.id AND d.class_room IN (SELECT  id FROM class_info c  WHERE c.user_id = '" + userId + "')" +
//                        "GROUP BY d.student) qq JOIN ( SELECT class_room,(SUM(breakfast = 1)+SUM(lunch = 1)+SUM(dinner = 1)) as rate  from " +
//                        "class_dining where `day` < '"+d+"' AND class_room in (SELECT d.class_room FROM stud_dining_record d GROUP BY d.student) GROUP BY class_room) ssss" +
//                        " ON qq.classId = ssss.class_room";
//            }
//            if (area != null && !area.equals("")) {
//                sql += " and qq.diningPlace = " +"'"+ area+"'";
//            }
//            if (classRoom != null && !classRoom.equals("")) {
//                sql += " and qq.classId = " + classRoom;
//            }
//            if (className != null && !className.equals("")) {
//                sql += " and qq.className like "+ "'%" + className + "%'";
//            }
            List<StuDiningRecordVO> list = super.getSession().createSQLQuery(sql)
                    .setResultTransformer(Transformers.aliasToBean(StuDiningRecordVO.class)).list();
            return list;
        }

    @Override
    public List<StuDiningRecordVO> findSum(BTView<StuDiningRecordVO> bt,String area, Integer classRoom, String className,String studentName,String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = sdf.format(date);
        StringBuffer sql = new StringBuffer(
            "SELECT qq.studentId as studentId,qq.classId as classId,qq.className as className,qq.classNumber as classNumber,qq.countB as countB,qq.countL as countL,qq.countD as countD,qq.diningPlace as diningPlace,qq.studentName as studentName,qq.idCard as idCard,ssss.rate as rate from (" +
                    "SELECT DISTINCT d.`day`,d.student AS studentId,d.class_room AS classId,SUM(d.breakfast = 1) AS countB,SUM(d.lunch = 1) AS countL,SUM(d.dinner = 1) AS countD,s.si_name AS studentName," +
                    "s.si_ID_number AS idCard,c.class_number AS classNumber,c.class_name AS className,c.dining_place AS  diningPlace FROM stud_dining_record d JOIN student_information s ON d.student = s.si_id" +
                    " JOIN class_info c ON d.class_room = c.id GROUP BY d.student,d.class_room HAVING d.`day` LIKE '%" + month + "%') qq JOIN ( SELECT class_room,(SUM(breakfast = 1)+SUM(lunch = 1)+SUM(dinner = 1)) as rate  from " +
                    "class_dining where `day` <= '"+d+"' AND class_room in (SELECT d.class_room FROM stud_dining_record d GROUP BY d.student,d.class_room) GROUP BY class_room) ssss ON qq.classId = ssss.class_room");
        if (area != null && !area.equals("")) {
            sql.append(" and qq.diningPlace = " +"'"+ area+"'") ;
        }
        if (classRoom != null && !classRoom.equals("")) {
            sql.append(" and qq.classId = " + "'" + classRoom + "'") ;
        }
        if (className != null && !className.equals("")) {
            sql.append(" and qq.className like "+ "'%" + className + "%'") ;
        }
        if (studentName != null && !studentName.equals("")) {
            sql.append(" and qq.studentName like "+ "'%" + studentName + "%'") ;
        }

        StringBuffer sqlTotal = new StringBuffer(
            "SELECT count(*) from (" +
                    "SELECT DISTINCT d.`day`,d.student AS studentId,d.class_room AS classId,SUM(d.breakfast = 1) AS countB,SUM(d.lunch = 1) AS countL,SUM(d.dinner = 1) AS countD,s.si_name AS studentName," +
                    "s.si_ID_number AS idCard,c.class_number AS classNumber,c.class_name AS className,c.dining_place AS  diningPlace FROM stud_dining_record d JOIN student_information s ON d.student = s.si_id" +
                    " JOIN class_info c ON d.class_room = c.id GROUP BY d.student,d.class_room HAVING d.`day` LIKE '%" + month + "%') qq JOIN ( SELECT class_room,(SUM(breakfast = 1)+SUM(lunch = 1)+SUM(dinner = 1)) as rate  from " +
                    "class_dining where `day` <= '"+d+"' AND class_room in (SELECT d.class_room FROM stud_dining_record d GROUP BY d.student,d.class_room) GROUP BY class_room) ssss ON qq.classId = ssss.class_room");
        if (area != null && !area.equals("")) {
            sqlTotal.append(" and qq.diningPlace = " +"'"+ area+"'") ;
        }
        if (classRoom != null && !classRoom.equals("")) {
            sqlTotal.append(" and qq.classId = " + "'" + classRoom + "'") ;
        }
        if (className != null && !className.equals("")) {
            sqlTotal.append(" and qq.className like "+ "'%" + className + "%'") ;
        }
        if (studentName != null && !studentName.equals("")) {
            sqlTotal.append(" and qq.studentName like "+ "'%" + studentName + "%'") ;
        }
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(StuDiningRecordVO.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public List<StuDiningRecordVO> findSum(BTView<StuDiningRecordVO> bt,String area, Integer classRoom, String className,String studentName, Integer userId,String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = sdf.format(date);
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT qq.studentId as studentId,qq.classId as classId,qq.className as className,qq.classNumber as classNumber,qq.countB as countB,qq.countL as countL,qq.countD as countD,qq.diningPlace as diningPlace,qq.studentName as studentName,qq.idCard as idCard,ssss.rate as rate from (" +
                "SELECT DISTINCT d.`day`,d.student AS studentId,d.class_room AS classId,SUM(d.breakfast = 1) AS countB,SUM(d.lunch = 1) AS countL,SUM(d.dinner = 1) AS countD,s.si_name AS studentName," +
                "s.si_ID_number AS idCard, c.class_number AS classNumber,c.class_name AS className,c.dining_place AS  diningPlace FROM stud_dining_record d" +
                " JOIN student_information s ON d.student = s.si_id JOIN class_info c ON d.class_room = c.id AND d.class_room IN (SELECT  id FROM class_info c  WHERE c.user_id = '" + userId + "')" +
                "GROUP BY d.student,d.class_room HAVING d.`day` LIKE '%" + month + "%') qq JOIN ( SELECT day,class_room,(SUM(breakfast = 1)+SUM(lunch = 1)+SUM(dinner = 1)) as rate  from " +
                "class_dining where `day` <= '"+d+"' AND class_room in (SELECT d.class_room FROM stud_dining_record d GROUP BY d.student) GROUP BY class_room) ssss" +
                " ON qq.classId = ssss.class_room");
        if (area != null && !area.equals("")) {
            sql.append(" and qq.diningPlace = " +"'"+ area+"'");
        }
        if (classRoom != null && !classRoom.equals("")) {
            sql.append( " and qq.classId = " + "'" + classRoom + "'");
        }
        if (className != null && !className.equals("")) {
            sql.append(" and qq.className like "+ "'%" + className + "%'");
        }
        if (studentName != null && !studentName.equals("")) {
            sql.append(" and qq.studentName like "+ "'%" + studentName + "%'") ;
        }
        StringBuilder sqlTotal = new StringBuilder();
        sqlTotal.append( "SELECT count(*) from (" +
                "SELECT DISTINCT d.`day`,d.student AS studentId,d.class_room AS classId,SUM(d.breakfast = 1) AS countB,SUM(d.lunch = 1) AS countL,SUM(d.dinner = 1) AS countD,s.si_name AS studentName," +
                "s.si_ID_number AS idCard, c.class_number AS classNumber,c.class_name AS className,c.dining_place AS  diningPlace FROM stud_dining_record d" +
                " JOIN student_information s ON d.student = s.si_id JOIN class_info c ON d.class_room = c.id AND d.class_room IN (SELECT  id FROM class_info c  WHERE c.user_id = '" + userId + "')" +
                "GROUP BY d.student,d.class_room HAVING d.`day` LIKE '%" + month + "%') qq JOIN ( SELECT day,class_room,(SUM(breakfast = 1)+SUM(lunch = 1)+SUM(dinner = 1)) as rate  from " +
                "class_dining where `day` <= '"+d+"' AND class_room in (SELECT d.class_room FROM stud_dining_record d GROUP BY d.student) GROUP BY class_room) ssss" +
                " ON qq.classId = ssss.class_room") ;
        if (area != null && !area.equals("")) {
            sqlTotal.append( " and qq.diningPlace = " +"'"+ area+"'");
        }
        if (classRoom != null && !classRoom.equals("")) {
            sqlTotal.append( " and qq.classId = " + "'" + classRoom + "'");
        }
        if (className != null && !className.equals("")) {
            sqlTotal.append( " and qq.className like "+ "'%" + className + "%'");
        }
        if (studentName != null && !studentName.equals("")) {
            sqlTotal.append(" and qq.studentName like "+ "'%" + studentName + "%'") ;
        }
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(StuDiningRecordVO.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public List<StuDiningRecord> findList(BTView<StuDiningRecord> bt, int student,Integer classId) {
        StringBuilder sqlTotal=new StringBuilder().append("SELECT count(*) FROM stud_dining_record WHERE student = '"+student+"' AND class_room = " + classId + " ORDER BY `day` ASC");
        StringBuilder sql=new StringBuilder()
                .append( "SELECT day,breakfast,lunch,dinner FROM stud_dining_record WHERE student = '"+student+"' AND class_room = " + classId + " ORDER BY `day` ASC");
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(StuDiningRecord.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public StuDiningRecord findByTime(String time,Integer student, Integer classRoom) {
        StuDiningRecord sdr = new StuDiningRecord();
        //数据库中可能有当天的就餐记录 也可能没有
//        String sql = "SELECT s.* from stud_dining_record s where s.`day` = '2018-10-25'and s.student = 20 and s.class_room = 6;";
        StringBuilder sql=new StringBuilder()
                .append("SELECT s.* from stud_dining_record s where s.`day` = '"+ time +"'and s.student = "+student+" and s.class_room = "+classRoom);
        List<StuDiningRecord> list = this.getSession().createSQLQuery(sql.toString()).addEntity(StuDiningRecord.class).getResultList();
        if(list.size()>0){
            sdr = list.get(0);
        }else {
            sdr = null;
        }
        return sdr;
    }

    @Override
    public List<StuDiningRecord> findCount(String classId,String type) {
        StringBuffer hql =new StringBuffer("select new StuDiningRecord(id) from StuDiningRecord c where 1=1");

        if (classId!=null && !classId.equals("")){
            hql.append(" And c.classRoom="+ Integer.valueOf(classId.trim()));
        }
        //早餐就餐数
        if (type!=null && type.equals("0")){
            hql.append(" And c.breakfast=1");
        }
        //中餐就餐数
        if (type!=null && type.equals("1")){
            hql.append(" And c.lunch = 1");
        }
        //晚餐就餐数
        if (type!=null && type.equals("2")){
            hql.append(" And c.dinner = 1");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public StuDiningRecordStatisticsDTO getStuDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT sdr.area AS diningPlace, SUM(sdr.breakfast=1) AS breakfastEatCount,SUM(sdr.lunch=1) AS lunchEatCount,SUM(sdr.dinner=1) AS dinnerEatCount FROM stud_dining_record sdr");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" WHERE sdr.area = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND sdr.`day` >= '").append(startTime).append("'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND sdr.`day` <='").append(endTime).append("'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND sdr.class_room = ").append(classId);
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(StuDiningRecordStatisticsDTO.class));
        return (StuDiningRecordStatisticsDTO) query.uniqueResult();
    }

    @Override
    public StuDiningRecordStatisticsDTO getStuDiningFirstDayRecordStatistics(String diningPlace, String classId) {
        StringBuffer sql = new StringBuffer("SELECT SUM(sdr.breakfast = 1) AS breakfastEatCount,SUM(sdr.lunch = 1) AS lunchEatCount,SUM(sdr.dinner =1) AS dinnerEatCount,ci.dining_place AS diningPlace " +
                "FROM stud_dining_record sdr INNER JOIN class_info ci ON sdr.class_room = ci.id AND sdr.`day` = LEFT(ci.start_stop_time,10) ");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND ci.dining_place = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND sdr.class_room = '").append(classId).append("'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(StuDiningRecordStatisticsDTO.class));

        return (StuDiningRecordStatisticsDTO) query.uniqueResult();
    }
}
