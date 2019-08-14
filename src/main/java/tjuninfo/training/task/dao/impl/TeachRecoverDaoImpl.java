package tjuninfo.training.task.dao.impl;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.TeachRecoverDao;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.vo.StudentCardVO;
import tjuninfo.training.task.vo.TeachCardVO;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/25 10:42
 * @Description:
 */
@Repository
public class TeachRecoverDaoImpl extends BaseDaoImpl<TeacherCard> implements TeachRecoverDao {

    public TeachRecoverDaoImpl() {
        super(TeacherCard.class);
    }

    @Override
    public List<TeachCardVO> findList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = sdf.format(date);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH,-2);
        Date m = c.getTime();
        String t = sdf.format(m);
        String sql = "SELECT c.id as classId,class_number as classNumber,class_name as className,host_unit as hostUnit,organizer_unit as organizerUnit,start_stop_time as startStopTime,day_num as dayNum,userName as userName FROM class_info c " +
                "JOIN sys_user s ON c.user_id = s.user_id  where '"+t+"' < evaluate_stop_time < '"+d+"' AND c.state is null";
        List<TeachCardVO> list = super.getSession().createSQLQuery(sql)
                .setResultTransformer(Transformers.aliasToBean(TeachCardVO.class)).list();
        return list;
    }
    @Override
    public List<TeachCardVO> findList(BTView<TeachCardVO> bt,Integer userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.add(Calendar.DAY_OF_MONTH,1);
        Date date = c1.getTime();
        String d = sdf.format(date);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH,-2);
        Date m = c.getTime();
        String t = sdf.format(m);
        StringBuffer sqlTotal =new StringBuffer("SELECT  count(*) FROM class_info c JOIN sys_user s ON c.user_id = s.user_id   where LTRIM(evaluate_stop_time) BETWEEN '"+t+"'and '"+d+"' AND (c.state2 is null or c.state2 = '')") ;
        StringBuffer sql = new StringBuffer("SELECT c.id as classId,class_number as classNumber,class_name as className,host_unit as hostUnit,organizer_unit as organizerUnit,start_stop_time as startTime,day_num as dayNum,userName as userName ,evaluate_stop_time as recoverTime FROM class_info c " +
                "JOIN sys_user s ON c.user_id = s.user_id  where LTRIM(evaluate_stop_time) BETWEEN '"+t+"'and '"+d+"' AND (c.state2 is null or c.state2 = '')");
        if(userId!=null&&!userId.equals("")){
            sqlTotal.append(" and s.user_id =" + userId);
            sql.append(" and s.user_id =" + userId);
        }
        System.out.println(sql);
        System.out.println(sqlTotal);
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachCardVO.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public List<TeacherCard> findNum(String number) {
        StringBuffer sql=new StringBuffer("SELECT id,number,teacher as teacherName,time FROM teacher_card WHERE number ='"+number+"'");
        List<TeacherCard> list = super.getSession().createSQLQuery(sql.toString())
                .setResultTransformer(Transformers.aliasToBean(TeacherCard.class)).list();
        return list;
    }

    @Override
    public void updateByNumber(String number, String teacherName,String time) {
        super.getSession().createSQLQuery("UPDATE teacher_card SET teacher = '"+teacherName+"',time = '"+time+"'  where number=:number")
                .setParameter("number", number).executeUpdate();
    }

    @Override
    public List<TeacherCard> findListBy(String number) {
        StringBuffer hql =new StringBuffer("from TeacherCard c where 1=1");
        if(number!=null && !number.equals("")){
            hql.append(" AND c.number = '" + number.trim() + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        return  query.list();
    }

    @Override
    public TeacherCard findBynumber(String number) {
        TeacherCard t=null;
        StringBuffer sql =new StringBuffer("SELECT * from teacher_card  t  where t.number = '"+number+"'");
        List<TeacherCard>  list = this.getSession().createSQLQuery(sql.toString()).addEntity(TeacherCard.class).getResultList();
        if(list.size()>0){
            t = list.get(0);
        }
        return t;
    }

    @Override
    public List<TeachDining> findByTimeNum(String number, String teacherName, String time) {
        StringBuffer sql =new StringBuffer("SELECT * from teach_dining t where t.time='"+time+"' and t.number='"+number+"' and t.teacher = '"+teacherName+"'");
        List<TeachDining>  list = this.getSession().createSQLQuery(sql.toString()).addEntity(TeachDining.class).getResultList();
        return list;
    }

    @Override
    public TeachDining findMax(String number, String teacherName, String time) {
        StringBuffer sql =new StringBuffer("SELECT t.* from teach_dining t where t.time='"+time+"' and t.number='"+number +"' and t.teacher = '"+teacherName+"' " +
                "and t.`day` in (SELECT max(t1.`day`) from teach_dining t1 where  t1.time='"+time+"' and t1.number='"+number+"' and t1.teacher = '"+teacherName+"');");
        return (TeachDining) this.getSession().createSQLQuery(sql.toString()).addEntity(TeachDining.class).uniqueResult();
    }

    @Override
    public Long byTimeAndClassId(String nowDate, ClassDining classDining,Integer classID) {
        StringBuffer hql =new StringBuffer("SELECT COUNT(1) from TeachDiningRecord t where 1=1") ;
        hql.append("and t.teacherDay = " + "'"+nowDate+"'");
        if (classDining.getBreakfast() != null) {
            hql.append("and t.breakfast = " + 1);
        }
        if (classDining.getLunch() != null) {
            hql.append("and t.lunch = " + 1);
        }
        if (classDining.getDinner() != null) {
            hql.append("and t.dinner = " + 1);
        }
        if(classID != null){
            hql.append("and t.classRoom = " + classID);
        }
        long count = (long) getSession().createQuery(hql.toString()).uniqueResult();
        return count;
    }

    @Override
    public List<TeachDining> findByClass(Long id) {
//        StringBuffer sql =new StringBuffer("SELECT * from teach_dining t where t.class_id="+id+" GROUP BY t.number");
        StringBuffer sql =new StringBuffer("SELECT t.* from teach_dining t LEFT join  teacher_card c  on t.number = c.number where t.time = c.time and t.class_id="+id+" GROUP BY t.number");
        List<TeachDining>  list = this.getSession().createSQLQuery(sql.toString()).addEntity(TeachDining.class).getResultList();
        return list;
    }

    @Override
    public void recoverTeachList(String number) {
        super.getSession().createSQLQuery("UPDATE teacher_card SET teacher = NULL,time = NULL where number=:number")
                .setParameter("number", number).executeUpdate();
    }

    @Override
    public void recoverClassList(BigInteger classId) {
        super.getSession().createSQLQuery("UPDATE teacher_card SET teacher = NULL,time = NULL where number in\n" +
                "(SELECT DISTINCT number FROM register r JOIN class_info c ON r.class_id = c.id AND  c.id =:classId) ")
                .setParameter("classId", classId).executeUpdate();
    }

    @Override
    public TeacherCard findByNum(String number) {
        StringBuffer hql =new StringBuffer("from TeacherCard t where t.number='" +number+ "' ");
        TeacherCard teacherCard = (TeacherCard)getSession().createQuery(hql.toString()).uniqueResult();
        return teacherCard;
    }


}
