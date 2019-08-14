package tjuninfo.training.task.dao.impl;

import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ICampusDao;
import tjuninfo.training.task.dao.RecordChangeDao;
import tjuninfo.training.task.entity.Campus;
import tjuninfo.training.task.entity.RecordChange;

import java.util.List;

/**
 * 校区数据层接口实现层
 */
@Repository
public class RecordChangeDaoImpl extends BaseDaoImpl<RecordChange> implements RecordChangeDao {
    public RecordChangeDaoImpl() {
        super(RecordChange.class);
    }

    @Override
    public List<RecordChange> findAll(Long classId) {
        StringBuffer hql = new StringBuffer("from RecordChange r where 1=1");
        if(classId!= null && !classId.equals("")){
            hql.append(" and r.classId = "+ classId) ;
        }

        return getSession().createQuery(hql.toString()).list();
    }
}
