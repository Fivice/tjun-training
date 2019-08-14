package tjuninfo.training.task.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.TeachDinRecordDao;
import tjuninfo.training.task.dto.TeachDiningRecordForDiningStatisticsDTO;
import tjuninfo.training.task.entity.TeachDiningFaceRecord;
import tjuninfo.training.task.entity.TeachDiningRecord;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;
import tjuninfo.training.task.vo.TeachDiningRecordVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/12 11:21
 * @Description:
 */
@Repository
public class TeachDinRecordDaoImpl extends BaseDaoImpl<TeachDiningRecord> implements TeachDinRecordDao {

    public TeachDinRecordDaoImpl() {
        super(TeachDiningRecord.class);
    }

    @Override
    public List<TeachDiningRecordVO> findSum(String area, Integer classRoom, String className, Integer userId, Integer roleId) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String d = sdf.format(date);
        String sql = null;
//        if(roleId == 3){
//            sql = "SELECT qq.teacherName as teacherName,qq.classId as classId,qq.countB as countB,qq.countL as countL,qq.countD as countD,qq.num as num," +
//                    "qq.classNumber as classNumber,qq.className,qq.diningPlace as diningPlace,qq.authorizerId,ssss.rate as rate FROM" +
//                    "(SELECT  aa.teacherName as teacherName,aa.classId AS classId,aa.countB as countB,aa.countL as countL,aa.countD as countD,aa.num AS num," +
//                    "aa.authorizerId as authorizerId,c.class_number AS classNumber, c.class_name AS className, c.dining_place AS diningPlace  FROM   " +
//                    " (SELECT  t.teacher AS teacherName,t.class_room AS classId,SUM(t.breakfast = 1) as countB,SUM(t.lunch = 1) as countL,SUM(t.dinner = 1) as countD," +
//                    "t.num AS num,t.authorizer_id as authorizerId  FROM teach_dining_record t  group by  t.NUM) as aa  JOIN  class_info c  ON aa.classId = c.id  ) qq" +
//                    " JOIN (SELECT class_room,(SUM(breakfast = 1) + SUM(lunch = 1) + SUM(dinner = 1)) AS rate FROM class_dining WHERE `day` < '"+d+"'" +
//                    " AND class_room IN (SELECT t.class_room FROM teach_dining_record t  GROUP BY t.num)GROUP BY class_room) ssss ON qq.classId = ssss.class_room";
//        }else {
//            sql = "SELECT qq.teacherName as teacherName,qq.classId as classId,qq.countB as countB,qq.countL as countL,qq.countD as countD,qq.num as num," +
//                    "qq.classNumber as classNumber,qq.className,qq.diningPlace as diningPlace,qq.authorizerId,ssss.rate as rate FROM" +
//                    "(SELECT  aa.teacherName as teacherName,aa.classId AS classId,aa.countB as countB,aa.countL as countL,aa.countD as countD,aa.num AS num," +
//                    "aa.authorizerId as authorizerId,c.class_number AS classNumber, c.class_name AS className, c.dining_place AS diningPlace  FROM   " +
//                    " (SELECT  t.teacher AS teacherName,t.class_room AS classId,SUM(t.breakfast = 1) as countB,SUM(t.lunch = 1) as countL,SUM(t.dinner = 1) as countD," +
//                    "t.num AS num,t.authorizer_id as authorizerId  FROM teach_dining_record t  group by  t.NUM) as aa  JOIN  class_info c  ON aa.classId = c.id AND aa.authorizerId = '" + userId + "' ) qq" +
//                    " JOIN (SELECT class_room,(SUM(breakfast = 1) + SUM(lunch = 1) + SUM(dinner = 1)) AS rate FROM class_dining WHERE `day` < '"+d+"'" +
//                    " AND class_room IN (SELECT t.class_room FROM teach_dining_record t  GROUP BY t.num)GROUP BY class_room) ssss ON qq.classId = ssss.class_room";
//        }
//
//        if (area != null && !area.equals("")) {
//            sql += " and qq.diningPlace = " + "'" + area + "'";
//        }
//        if (classRoom != null && !classRoom.equals("")) {
//            sql += " and qq.classId = " + classRoom;
//        }
//        if (className != null && !className.equals("")) {
//            sql += " and qq.className like "+ "'%" + className + "%'";
//        }
        List<TeachDiningRecordVO> list = super.getSession().createSQLQuery(sql)
                .setResultTransformer(Transformers.aliasToBean(TeachDiningRecordVO.class)).list();
        return list;
    }

    @Override
    public List<TeachDiningRecordVO> findSum(BTView<TeachDiningRecordVO> bt, String area, Integer classRoom, String className, String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = sdf.format(date);
        StringBuffer sql = null;
        sql = new StringBuffer("SELECT qq.teacherName as teacherName,qq.classId as classId,qq.countB as countB,qq.countL as countL,qq.countD as countD,qq.num as num," +
                "qq.classNumber as classNumber,qq.className,qq.diningPlace as diningPlace,qq.authorizerId,ssss.rate as rate FROM" +
                "(SELECT  aa.teacherName as teacherName,aa.classId AS classId,aa.countB as countB,aa.countL as countL,aa.countD as countD,aa.num AS num," +
                "aa.authorizerId as authorizerId,c.class_number AS classNumber, c.class_name AS className, c.dining_place AS diningPlace  FROM   " +
                " (SELECT  t.t_day,t.teacher AS teacherName,t.class_room AS classId,SUM(t.breakfast = 1) as countB,SUM(t.lunch = 1) as countL,SUM(t.dinner = 1) as countD," +
                "t.num AS num,t.authorizer_id as authorizerId  FROM teach_dining_record t  group by  t.NUM,t.class_room having t.t_day LIKE '%" + month + "%') as aa  JOIN  class_info c  ON aa.classId = c.id  ) qq" +
                " JOIN (SELECT class_id,(SUM(breakfast = 1) + SUM(lunch = 1) + SUM(dinner = 1)) AS rate FROM teach_dining WHERE `day` <= '" + d + "'" +
                " AND class_id IN (SELECT t.class_room FROM teach_dining_record t  GROUP BY t.num,t.class_room)GROUP BY class_id) ssss ON qq.classId = ssss.class_id");
        if (area != null && !area.equals("")) {
            sql.append(" and qq.diningPlace = " + "'" + area + "'");
        }
        if (classRoom != null && !classRoom.equals("")) {
            sql.append(" and qq.classId = " + "'" + classRoom + "'");
        }
        if (className != null && !className.equals("")) {
            sql.append(" and qq.className like " + "'%" + className + "%'");
        }

        StringBuffer sqlTotal = null;
        sqlTotal = new StringBuffer("SELECT count(*) FROM" +
                "(SELECT  aa.teacherName as teacherName,aa.classId AS classId,aa.countB as countB,aa.countL as countL,aa.countD as countD,aa.num AS num," +
                "aa.authorizerId as authorizerId,c.class_number AS classNumber, c.class_name AS className, c.dining_place AS diningPlace  FROM   " +
                " (SELECT  t.t_day,t.teacher AS teacherName,t.class_room AS classId,SUM(t.breakfast = 1) as countB,SUM(t.lunch = 1) as countL,SUM(t.dinner = 1) as countD," +
                "t.num AS num,t.authorizer_id as authorizerId  FROM teach_dining_record t  group by  t.NUM,t.class_room having t.t_day LIKE '%" + month + "%') as aa  JOIN  class_info c  ON aa.classId = c.id  ) qq" +
                " JOIN (SELECT class_id,(SUM(breakfast = 1) + SUM(lunch = 1) + SUM(dinner = 1)) AS rate FROM teach_dining WHERE `day` <= '" + d + "'" +
                " AND class_id IN (SELECT t.class_room FROM teach_dining_record t  GROUP BY t.num,t.class_room)GROUP BY class_id) ssss ON qq.classId = ssss.class_id");
        if (area != null && !area.equals("")) {
            sqlTotal.append(" and qq.diningPlace = " + "'" + area + "'");
        }
        if (classRoom != null && !classRoom.equals("")) {
            sqlTotal.append(" and qq.classId = " + "'" + classRoom + "'");
        }
        if (className != null && !className.equals("")) {
            sqlTotal.append(" and qq.className like " + "'%" + className + "%'");
        }
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningRecordVO.class))
                .setFirstResult((bt.getPageNumber() - 1) * bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public List<TeachDiningRecordVO> findSum(BTView<TeachDiningRecordVO> bt, String area, Integer classRoom, String className, Integer userId, String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = sdf.format(date);
        StringBuffer sql = null;
        sql = new StringBuffer("SELECT qq.teacherName as teacherName,qq.classId as classId,qq.countB as countB,qq.countL as countL,qq.countD as countD,qq.num as num," +
                "qq.classNumber as classNumber,qq.className,qq.diningPlace as diningPlace,qq.authorizerId,ssss.rate as rate FROM" +
                "(SELECT  aa.teacherName as teacherName,aa.classId AS classId,aa.countB as countB,aa.countL as countL,aa.countD as countD,aa.num AS num," +
                "aa.authorizerId as authorizerId,c.class_number AS classNumber, c.class_name AS className, c.dining_place AS diningPlace  FROM   " +
                " (SELECT  t.t_day,t.teacher AS teacherName,t.class_room AS classId,SUM(t.breakfast = 1) as countB,SUM(t.lunch = 1) as countL,SUM(t.dinner = 1) as countD," +
                "t.num AS num,t.authorizer_id as authorizerId  FROM teach_dining_record t  group by  t.NUM,t.class_room having t.t_day LIKE '%" + month + "%') as aa  JOIN  class_info c  ON aa.classId = c.id AND aa.authorizerId = '" + userId + "' ) qq" +
                " JOIN (SELECT class_id,(SUM(breakfast = 1) + SUM(lunch = 1) + SUM(dinner = 1)) AS rate FROM teach_dining WHERE `day` <= '" + d + "'" +
                " AND class_id IN (SELECT t.class_room FROM teach_dining_record t  GROUP BY t.num,t.class_room)GROUP BY class_id) ssss ON qq.classId = ssss.class_id");
        if (area != null && !area.equals("")) {
            sql.append(" and qq.diningPlace = " + "'" + area + "'");
        }
        if (classRoom != null && !classRoom.equals("")) {
            sql.append(" and qq.classId = " + "'" + classRoom + "'");
        }
        if (className != null && !className.equals("")) {
            sql.append(" and qq.className like " + "'%" + className + "%'");
        }

        StringBuffer sqlTotal = null;
        sqlTotal = new StringBuffer("SELECT count(*) FROM" +
                "(SELECT  aa.teacherName as teacherName,aa.classId AS classId,aa.countB as countB,aa.countL as countL,aa.countD as countD,aa.num AS num," +
                "aa.authorizerId as authorizerId,c.class_number AS classNumber, c.class_name AS className, c.dining_place AS diningPlace  FROM   " +
                " (SELECT  t.t_day,t.teacher AS teacherName,t.class_room AS classId,SUM(t.breakfast = 1) as countB,SUM(t.lunch = 1) as countL,SUM(t.dinner = 1) as countD," +
                "t.num AS num,t.authorizer_id as authorizerId  FROM teach_dining_record t  group by  t.NUM,t.class_room having t.t_day LIKE '%" + month + "%') as aa  JOIN  class_info c  ON aa.classId = c.id AND aa.authorizerId = '" + userId + "' ) qq" +
                " JOIN (SELECT class_id,(SUM(breakfast = 1) + SUM(lunch = 1) + SUM(dinner = 1)) AS rate FROM teach_dining WHERE `day` <= '" + d + "'" +
                " AND class_id IN (SELECT t.class_room FROM teach_dining_record t  GROUP BY t.num,t.class_room)GROUP BY class_id) ssss ON qq.classId = ssss.class_id");

        if (area != null && !area.equals("")) {
            sqlTotal.append(" and qq.diningPlace = " + "'" + area + "'");
        }
        if (classRoom != null && !classRoom.equals("")) {
            sqlTotal.append(" and qq.classId = " + "'" + classRoom + "'");
        }
        if (className != null && !className.equals("")) {
            sqlTotal.append(" and qq.className like " + "'%" + className + "%'");
        }
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningRecordVO.class))
                .setFirstResult((bt.getPageNumber() - 1) * bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public List<TeachDiningRecord> findAll(String num) {
        return super.getSession().createCriteria(TeachDiningRecord.class, "t").add(Restrictions.eq("t.num", num)).addOrder(Order.asc("t.teacherDay")).list();
    }

    @Override
    public List<TeachDiningRecord> findList(BTView<TeachDiningRecord> bt, String num,Integer classId) {
        String sqlTotal = "SELECT count(*) FROM teach_dining_record WHERE num = '" + num + "' AND class_room = " + classId + " ORDER BY t_day ASC";
        String sql = "SELECT t_day as teacherDay,breakfast,lunch,dinner FROM teach_dining_record WHERE num = '" + num + "' AND class_room = " + classId + " ORDER BY t_day ASC";
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal).uniqueResult().toString()));
        return super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TeachDiningRecord.class))
                .setFirstResult((bt.getPageNumber() - 1) * bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public TeachDiningRecordVO sumByClassId(Long classId) {
        StringBuffer hql = new StringBuffer("SELECT SUM(breakfast=1) as countB ,SUM(lunch=1) as countL,SUM(dinner=1) as countD from teach_dining_record r where 1=1");
//        hql.append(" and r.classId = "+classId);
//		hql.append(" and r.foodTotal <> "+0);
        TeachDiningRecordVO count = (TeachDiningRecordVO) getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningRecordVO.class)).uniqueResult();
        System.out.println(count);
        return count;
    }

    @Override
    public List<TeachDiningRecord> findAllByClassId(Long classId) {
        StringBuffer hql = new StringBuffer("from TeachDiningRecord r where 1=1");
        if (classId != null && !classId.equals("")) {
            hql.append(" and r.classRoom = " + classId);
        }
        return getSession().createQuery(hql.toString()).list();
    }

    @Override
    public List<TeachDiningFaceRecord> faceByClassId(Long id) {
        StringBuffer hql = new StringBuffer("from TeachDiningFaceRecord r where 1=1");
        if (id != null && !id.equals("")) {
            hql.append(" and r.classId = " + id);
        }
        return getSession().createQuery(hql.toString()).list();
    }

    @Override
    public TeachDiningRecord findByTime(String time, String day, String number, String teacherName) {
        TeachDiningRecord t = null;
        StringBuffer sql = new StringBuffer("SELECT t.* from teach_dining_record t where  " +
                " t.num='" + number + "' and t.teacher = '" + teacherName + "' and t.t_day='" + day + "' and t.time = '" + time + "'");
        List<TeachDiningRecord> list = this.getSession().createSQLQuery(sql.toString()).addEntity(TeachDiningRecord.class).getResultList();
        if (list.size() > 0) {
            t = list.get(0);
        }
        return t;
    }

    @Override
    public List<TeachDiningRecord> findCount(String classId, String type) {
        StringBuffer hql = new StringBuffer("select new TeachDiningRecord(id) from TeachDiningRecord c where 1=1");

        if (classId != null && !classId.equals("")) {
            hql.append(" And c.classRoom=" + Integer.valueOf(classId.trim()));
        }
        //早餐就餐数
        if (type != null && type.equals("0")) {
            hql.append(" And c.breakfast=1");
        }
        //中餐就餐数
        if (type != null && type.equals("1")) {
            hql.append(" And c.lunch = 1");
        }
        //晚餐就餐数
        if (type != null && type.equals("2")) {
            hql.append(" And c.dinner = 1");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public List<TeachDiningFaceRecord> findCount2(String classId, String type) {
        StringBuffer hql = new StringBuffer("select new TeachDiningFaceRecord(id) from TeachDiningFaceRecord c where 1=1");

        if (classId != null && !classId.equals("")) {
            hql.append(" And c.classId=" + Integer.valueOf(classId.trim()));
        }
        //早餐就餐数
        if (type != null && type.equals("0")) {
            hql.append(" And c.breakfast=1");
        }
        //中餐就餐数
        if (type != null && type.equals("1")) {
            hql.append(" And c.lunch = 1");
        }
        //晚餐就餐数
        if (type != null && type.equals("2")) {
            hql.append(" And c.dinner = 1");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public TeachDiningRecordForDiningStatisticsDTO getTeachDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT tdr.area AS diningPlace,SUM(tdr.breakfast=1) AS breakfastEatCountReal,SUM(tdr.lunch=1) AS lunchEatCountReal,SUM(tdr.dinner=1) AS dinnerEatCountReal FROM teach_dining_record tdr WHERE 1=1");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND tdr.area = '"+diningPlace+"'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND tdr.class_room = '"+classId+"'");
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND tdr.t_day>='"+startTime+"'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND tdr.t_day<='"+endTime+"'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningRecordForDiningStatisticsDTO.class));
        return (TeachDiningRecordForDiningStatisticsDTO) query.uniqueResult();
    }

    @Override
    public TeachDiningRecordForDiningStatisticsDTO getTeachFirstDayDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT tdr.area AS diningPlace,SUM(tdr.breakfast=1) AS breakfastEatCountReal,SUM(tdr.lunch=1) AS lunchEatCountReal,SUM(tdr.dinner=1) AS dinnerEatCountReal " +
                "FROM teach_dining_record tdr INNER JOIN class_info ci ON tdr.class_room = ci.id AND tdr.t_day = LEFT(ci.start_stop_time,10) ");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND tdr.area = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND tdr.class_room = '").append(classId).append("'");
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND tdr.t_day >= '").append(startTime).append("'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND tdr.t_day <= '").append(endTime).append("'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningRecordForDiningStatisticsDTO.class));

        return (TeachDiningRecordForDiningStatisticsDTO) query.uniqueResult();
    }
}
