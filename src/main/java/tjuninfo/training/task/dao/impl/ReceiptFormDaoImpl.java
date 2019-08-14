package tjuninfo.training.task.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IReceiptFormDao;
import tjuninfo.training.task.entity.ReceiptForm;

import java.util.List;

/**
 * @description 回执单表
 * @author Fivice
 * @date 2018年11月9日11:42:05
 */
@Repository
public class ReceiptFormDaoImpl extends BaseDaoImpl<ReceiptForm> implements IReceiptFormDao{

    public ReceiptFormDaoImpl() {
        super(ReceiptForm.class);
    }

    @Override
    public long saveReceiptForm(ReceiptForm entity) {

        return (long) getSessionFactory().openSession().save(entity);
        //super.save(entity);

    }

    @Override
    public long judgeStudentIsInReceiptForm(String siId, String classId) {
        StringBuffer hql = new StringBuffer("SELECT COUNT(*) FROM ReceiptForm r WHERE 1=1");
        if (!("").equals(siId)&&siId!=null){
            hql.append(" And  r.siId = "+siId);
        }
        if (!("").equals(classId)&&classId!=null){
            hql.append(" AND r.classId ="+classId);
        }
        Query query =getSession().createQuery(hql.toString());

        return (long) query.uniqueResult();
    }

    @Override
    public List getStudentInfoListByReceiptForm(String siUnitId,String classId) {
        StringBuffer hql = new StringBuffer("SELECT si FROM Student si INNER JOIN ReceiptForm rf ON " +
                "si.siId = rf.siId");
        if (!("").equals(siUnitId)&&siUnitId!= null){
            hql.append(" AND si.siUnitId = \'"+siUnitId.trim()+"\'");
        }
        if (!("").equals(classId)&&classId!= null){
            hql.append(" AND rf.classId = \'"+classId.trim()+"\'");
        }
        Query query = getSession().createQuery(hql.toString());

        return query.list();
    }
}
