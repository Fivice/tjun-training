package tjuninfo.training.task.dao.impl;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.TeachDinFaceRecordDao;
import tjuninfo.training.task.entity.TeachDiningFaceRecord;
import tjuninfo.training.task.entity.TeachDiningRecord;
import tjuninfo.training.task.entity.TeacherDiningRegister;
import tjuninfo.training.task.vo.TeachDiningFaceRecordVO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/12/7 14:40
 * @Description:
 */
@Repository
public class TeachDinFaceRecordDaoImpl extends BaseDaoImpl<TeachDiningFaceRecord> implements TeachDinFaceRecordDao {

    public TeachDinFaceRecordDaoImpl() {
        super(TeachDiningFaceRecord.class);
    }

    @Override
    public List<TeachDiningFaceRecordVO> findFaceList(BTView<TeachDiningFaceRecordVO> btView, String schoolName, Integer classRoom, String className, String month) {
            StringBuffer sql = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String d = sdf.format(date);
            sql = new StringBuffer("SELECT a.ti_id AS teacherId, a.ti_name AS teacherName,a.class_id as classId,a.class_number AS classNumber,a.class_name AS className,a.dining_place AS diningPlace," +
                    " a.`DAY` AS day,a.breakfast AS breakfast,a.lunch AS lunch,a.dinner AS dinner,b.rate AS rate,a.sb AS countB,a.sl AS countL," +
                    " a.sd AS countD FROM ( SELECT  ti.ti_id, ti.ti_name,c.class_name,c.class_number,c.id as class_id,t. DAY,t.breakfast,t.lunch,t.dinner," +
                    " t.dining_place,SUM(t.breakfast = 1) as sb,SUM(t.lunch = 1) as sl,SUM(t.dinner = 1) as sd FROM  teach_dining_face_record t" +
                    " JOIN teacher_information ti ON t.teacher_id = ti.ti_id JOIN class_info c ON t.class_id = c.id GROUP BY ti.ti_id,t.class_id having t.`day` LIKE '%" + month + "%') a" +
                    " JOIN (SELECT tdr_id,teacher_id,class_id,(sum(tt.breakfast = 1) + sum(tt.lunch = 1) + sum(tt.dinner = 1)) AS rate FROM" +
                    " teacher_dining_for_scene tt JOIN teacher_dining_register td ON tt.tdr_id = td.id WHERE tt.dining_date <= '"+d+"'" +
                    " GROUP BY teacher_id,tdr_id) b ON a.ti_id = b.teacher_id where 1 = 1 and a.class_id = b.class_id");
            if (schoolName != null && !schoolName.equals("")) {
                sql.append(" and a.dining_place = " + "'" + schoolName + "'");
            }
            if (classRoom != null && !classRoom.equals("")) {
                sql.append(" and a.class_id = " + "'" + classRoom + "'");
            }
            if (className != null && !className.equals("")) {
                sql.append(" and a.class_name like " + "'%" + className + "%'");
            }

            StringBuffer sqlTotal = null;
            sqlTotal = new StringBuffer("SELECT count(*) FROM ( SELECT  ti.ti_id, ti.ti_name,c.class_name,c.class_number,c.id as class_id,t. DAY,t.breakfast,t.lunch,t.dinner," +
                    " t.dining_place,SUM(t.breakfast = 1),SUM(t.lunch = 1),SUM(t.dinner = 1) FROM  teach_dining_face_record t" +
                    " JOIN teacher_information ti ON t.teacher_id = ti.ti_id JOIN class_info c ON t.class_id = c.id GROUP BY ti.ti_id,t.class_id having t.`day` LIKE '%" + month + "%') a" +
                    " JOIN (SELECT tdr_id,teacher_id,class_id,(sum(tt.breakfast = 1) + sum(tt.lunch = 1) + sum(tt.dinner = 1)) AS rate FROM" +
                    " teacher_dining_for_scene tt JOIN teacher_dining_register td ON tt.tdr_id = td.id WHERE tt.dining_date <= '"+d+"'" +
                    " GROUP BY teacher_id,tdr_id) b ON a.ti_id = b.teacher_id where 1 = 1 and a.class_id = b.class_id");
            if (schoolName != null && !schoolName.equals("")) {
                sqlTotal.append(" and a.dining_place = " + "'" + schoolName + "'");
            }
            if (classRoom != null && !classRoom.equals("")) {
                sqlTotal.append(" and a.class_id = " + "'" + classRoom + "'");
            }
            if (className != null && !className.equals("")) {
                sqlTotal.append(" and a.class_name like " + "'%" + className + "%'");
            }
            btView.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
            return super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningFaceRecordVO.class))
                    .setFirstResult((btView.getPageNumber() - 1) * btView.getPageSize()).setMaxResults(btView.getPageSize()).getResultList();

        }

    @Override
    public List<TeachDiningFaceRecordVO> findList(BTView<TeachDiningFaceRecordVO> btView, String teacherId,Integer classId) {
        String sqlTotal = "SELECT count(*) FROM teach_dining_face_record WHERE teacher_id = '" + teacherId + "' AND class_id = " + classId + " ORDER BY `DAY` ASC";
        String sql = "SELECT `DAY` as day,breakfast,lunch,dinner FROM teach_dining_face_record WHERE teacher_id = '" + teacherId + "' AND class_id = " + classId + " ORDER BY `DAY` ASC";
        btView.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal).uniqueResult().toString()));
        return super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TeachDiningFaceRecordVO.class))
                .setFirstResult((btView.getPageNumber() - 1) * btView.getPageSize()).setMaxResults(btView.getPageSize()).getResultList();
    }
    @Override
    public List<TeachDiningFaceRecordVO> findFaceList(BTView<TeachDiningFaceRecordVO> btView, String schoolName, Integer classRoom, String className, Integer userId, String month) {
        StringBuffer sql = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String d = sdf.format(date);
        sql = new StringBuffer("SELECT a.ti_id AS teacherId, a.ti_name AS teacherName,a.authorizer_id AS authorizerId,a.class_id as classId,a.class_number AS classNumber,a.class_name AS className,a.dining_place AS diningPlace," +
                " a.`DAY` AS day,a.breakfast AS breakfast,a.lunch AS lunch,a.dinner AS dinner,b.rate AS rate,a.sb AS countB,a.sl AS countL," +
                " a.sd AS countD FROM ( SELECT  ti.ti_id, ti.ti_name,c.class_name,c.class_number,c.id as class_id,t.authorizer_id,t. DAY,t.breakfast,t.lunch,t.dinner," +
                " t.dining_place,SUM(t.breakfast = 1) as sb,SUM(t.lunch = 1) as sl,SUM(t.dinner = 1) as sd FROM  teach_dining_face_record t" +
                " JOIN teacher_information ti ON t.teacher_id = ti.ti_id JOIN class_info c ON t.class_id = c.id WHERE t.authorizer_id = '"+userId+"' GROUP BY ti.ti_id,t.class_id having t.`day` LIKE '%" + month + "%') a" +
                " JOIN (SELECT tdr_id,teacher_id,class_id,(sum(tt.breakfast = 1) + sum(tt.lunch = 1) + sum(tt.dinner = 1)) AS rate FROM" +
                " teacher_dining_for_scene tt JOIN teacher_dining_register td ON tt.tdr_id = td.id WHERE tt.dining_date <= '"+d+"'" +
                " GROUP BY teacher_id,tdr_id) b ON a.ti_id = b.teacher_id where 1 = 1 and a.class_id = b.class_id");
        if (schoolName != null && !schoolName.equals("")) {
            sql.append(" and a.dining_place = " + "'" + schoolName + "'");
        }
        if (classRoom != null && !classRoom.equals("")) {
            sql.append(" and a.class_id = " + "'" + classRoom + "'");
        }
        if (className != null && !className.equals("")) {
            sql.append(" and a.class_name like " + "'%" + className + "%'");
        }

        StringBuffer sqlTotal = null;
        sqlTotal = new StringBuffer("SELECT count(*) FROM ( SELECT  ti.ti_id, ti.ti_name,c.class_name,c.class_number,c.id as class_id,t. DAY,t.breakfast,t.lunch,t.dinner," +
                " t.dining_place,t.authorizer_id,SUM(t.breakfast = 1),SUM(t.lunch = 1),SUM(t.dinner = 1) FROM  teach_dining_face_record t" +
                " JOIN teacher_information ti ON t.teacher_id = ti.ti_id JOIN class_info c ON t.class_id = c.id WHERE t.authorizer_id = '"+userId+"' GROUP BY ti.ti_id,t.class_id having t.`day` LIKE '%" + month + "%') a" +
                " JOIN (SELECT tdr_id,teacher_id,class_id,(sum(tt.breakfast = 1) + sum(tt.lunch = 1) + sum(tt.dinner = 1)) AS rate FROM" +
                " teacher_dining_for_scene tt JOIN teacher_dining_register td ON tt.tdr_id = td.id WHERE tt.dining_date <= '"+d+"'" +
                " GROUP BY teacher_id,tdr_id) b ON a.ti_id = b.teacher_id where 1 = 1 and a.class_id = b.class_id");
        if (schoolName != null && !schoolName.equals("")) {
            sqlTotal.append(" and a.dining_place = " + "'" + schoolName + "'");
        }
        if (classRoom != null && !classRoom.equals("")) {
            sqlTotal.append(" and a.class_id = " + "'" + classRoom + "'");
        }
        if (className != null && !className.equals("")) {
            sqlTotal.append(" and a.class_name like " + "'%" + className + "%'");
        }
        btView.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal.toString()).uniqueResult().toString()));
        return super.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningFaceRecordVO.class))
                .setFirstResult((btView.getPageNumber() - 1) * btView.getPageSize()).setMaxResults(btView.getPageSize()).getResultList();

    }

    @Override
    public TeachDiningFaceRecord findByTime(String time, Integer teacher) {
        StringBuffer sql = new StringBuffer("SELECT * from teach_dining_face_record t where" +
                " t.teacher_id = "+ teacher +" and t.`day` like '"+time+"'");
        TeachDiningFaceRecord t = (TeachDiningFaceRecord) this.getSession().createSQLQuery(sql.toString())
                .addEntity(TeachDiningFaceRecord.class).uniqueResult();
        return t;
    }

    @Override
    public TeacherDiningRegister findClass(String time, Integer teacher) {
        StringBuffer sql = new StringBuffer("SELECT a.* from teacher_dining_register a LEFT join teacher_dining_for_scene b" +
                " on a.id = b.tdr_id where b.dining_date = '"+time+"' and a.teacher_id = "+teacher);
        TeacherDiningRegister t = (TeacherDiningRegister) this.getSession().createSQLQuery(sql.toString())
                .addEntity(TeacherDiningRegister.class).uniqueResult();
        return t;
    }

    @Override
    public List<TeachDiningFaceRecord> getTeachDiningFaceRecord(int teacherId, int classId) {
        StringBuffer hql = new StringBuffer("FROM TeachDiningFaceRecord tdf WHERE tdf.teacherId = "+teacherId+" AND tdf.classId = "+classId);
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }
}
