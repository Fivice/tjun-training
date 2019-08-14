package tjuninfo.training.task.dao.impl;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.StudentRecoverDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.StuDiningRecord;
import tjuninfo.training.task.entity.StudentCard;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.vo.StudentCardVO;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @Auther: win7
 * @Date: 2018/10/25 14:53
 * @Description:
 */
@Repository
public class StudentRecoverDaoImpl extends BaseDaoImpl<StudentCard> implements StudentRecoverDao {

    public StudentRecoverDaoImpl() {
        super(StudentCard.class);
    }

    @Override
    public void recoverStuList(String number) {
        super.getSession().createSQLQuery("UPDATE student_card SET student_id = NULL,register_time = NULL where number=:number")
                .setParameter("number", number).executeUpdate();
    }

    @Override
    public void recoverClassList(BigInteger classId) {
        super.getSession().createSQLQuery("UPDATE student_card SET student_id = NULL,register_time = NULL where number in\n" +
                "(SELECT DISTINCT number FROM register r JOIN class_info c ON r.class_id = c.id AND  c.id =:classId)")
                .setParameter("classId", classId).executeUpdate();
    }

    @Override
    public List<StudentCardVO> findList() {
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
        List<StudentCardVO> list = super.getSession().createSQLQuery(sql)
                .setResultTransformer(Transformers.aliasToBean(StudentCardVO.class)).list();
        return list;
    }

    @Override
    public List<StudentCardVO> findList(BTView<StudentCardVO> bt,Integer userId) {
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
        StringBuffer sqlTotal = new StringBuffer("SELECT  count(*) FROM class_info c JOIN sys_user s ON c.user_id = s.user_id  where LTRIM(c.evaluate_stop_time) BETWEEN '"+t+"' and '"+d+"' AND (c.state is null or c.state = '')") ;
        StringBuffer sql = new StringBuffer("SELECT c.id as classId,class_number as classNumber,class_name as className,host_unit as hostUnit,organizer_unit as organizerUnit,start_stop_time as startTime,day_num as dayNum,userName as userName ,evaluate_stop_time as recoverTime  FROM class_info c " +
                "JOIN sys_user s ON c.user_id = s.user_id  where LTRIM(c.evaluate_stop_time) BETWEEN '"+t+"' and '"+d+"' AND (c.state is null or c.state = '')");
        if(userId!=null&&!userId.equals("")){
            sqlTotal.append(" and s.user_id =" + userId);
            sql.append(" and s.user_id =" + userId);
        }
        System.out.println(sql);
        System.out.println(sqlTotal);
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(StudentCardVO.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    @Override
    public List<StuDiningRecord> findAllByClassId(Long classId) {
        StringBuffer hql =new StringBuffer("from StuDiningRecord s where 1=1") ;
        if (classId != null && !classId.equals("")) {
            hql.append(" and s.classRoom = "+ classId) ;
        }
        return  getSession().createQuery(hql.toString()).list();
    }

    @Override
    public StudentCard findCardBynumb(String number) {
        StudentCard s = new StudentCard();
        String  sql = "SELECT *from student_card c where number ='" +number+"'";
        List<StudentCard> list = this.getSession().createSQLQuery(sql).addEntity(StudentCard.class).getResultList();
        if(list.size()>0){
            s=list.get(0);
        }
        return s;
    }

    @Override
    public Long byTimeAndClassId(int i, String nowDate, ClassDining classDining) {
        StringBuffer hql =new StringBuffer("SELECT COUNT(1) from StuDiningRecord s where 1=1") ;
        hql.append("and s.classRoom = "+i);
        hql.append("and s.day = " + "'"+nowDate+"'");
        if (classDining.getBreakfast() != null) {
            hql.append("and s.breakfast = " + 1);
        }
        if (classDining.getLunch() != null) {
            hql.append("and s.lunch = " + 1);
        }
        if (classDining.getDinner() != null) {
            hql.append("and s.dinner = " + 1);
        }
        long count = (long) getSession().createQuery(hql.toString()).uniqueResult();
        return count;
    }

    @Override
    public List<StudentCard> findByClassId(Long classId) {
        StringBuffer sql =new StringBuffer("SELECT s.* from student_card s LEFT join  register r on r.number=s.number " +
                "where r.report_time = s.register_time and r.class_id = " + classId) ;
        return  getSession().createSQLQuery(sql.toString()).addEntity(StudentCard.class).getResultList();

    }
}
