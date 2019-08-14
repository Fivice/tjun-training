package tjuninfo.training.task.dao.impl;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ITeacherDiningRegisterDao;
import tjuninfo.training.task.entity.TeacherDiningRegister;

import java.math.BigInteger;

@Repository
public class TeacherDiningRegisterDaoImpl extends BaseDaoImpl<TeacherDiningRegister> implements ITeacherDiningRegisterDao {
    public TeacherDiningRegisterDaoImpl() {
        super(TeacherDiningRegister.class);
    }

    @Override
    public TeacherDiningRegister getTeacherBindClassInfo(int teacherId) {
        StringBuffer hql = new StringBuffer("select tdr.id AS id,tdr.teacher_id AS teacherId,tdr.class_id AS classId,tdr.reg_time AS regTime  FROM teacher_dining_register tdr WHERE tdr.teacher_id = "+teacherId+" AND tdr.class_id IN" +
                " (SELECT ci.id FROM class_info ci WHERE STR_TO_DATE(RIGHT(ci.start_stop_time,10),'%Y-%m-%d')>NOW())");
        Query query = getSession().createSQLQuery(hql.toString());

        return (TeacherDiningRegister) query.setResultTransformer(Transformers.aliasToBean(TeacherDiningRegister.class)).uniqueResult();
    }

    @Override
    public TeacherDiningRegister getTeacherDiningRegister(int teacherId, long classId) {
        StringBuffer hql = new StringBuffer("FROM TeacherDiningRegister tdr WHERE tdr.teacherId = "+teacherId+" AND tdr.classId = "+classId);
        Query query = getSession().createQuery(hql.toString());

        return (TeacherDiningRegister) query.uniqueResult();
    }
}
