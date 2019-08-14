package tjuninfo.training.task.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ICampusDao;
import tjuninfo.training.task.dao.IClassroomDao;
import tjuninfo.training.task.entity.Campus;
import tjuninfo.training.task.entity.Classroom;

import java.util.List;

/**
 * 校区数据层接口实现层
 */
@Repository
public class CampusDaoImpl extends BaseDaoImpl<Campus> implements ICampusDao{
    public CampusDaoImpl() {
        super(Campus.class);
    }

    @Override
    public List<Campus> findBySchoolName(Integer schoolName) {
        StringBuffer hql =new StringBuffer("from Campus c where 1=1") ;
        if(schoolName!= null && !schoolName.equals("")){
            hql.append(" and c.schoolName = "+ schoolName );
        }

        return getSession().createQuery(hql.toString()).list();
    }

    @Override
    public List<Campus> findBySchoolName1(String school) {
        StringBuffer hql =new StringBuffer("from Campus c where 1=1") ;
        if(school!= null && !school.equals("")){
            hql.append(" and c.schoolName = "+ "'"+school+"'" );
        }

        return getSession().createQuery(hql.toString()).list();
    }

    @Override
    public List<Campus> getByIdAndSchoolName(String id, String schoolName) {
        StringBuffer hql =new StringBuffer("from Campus c where 1=1");

        if (schoolName!=null && !schoolName.equals("")){
            hql.append(" and c.schoolName= "+ "'" + schoolName.trim() + "'");
        }
        if (id!=null && !id.equals("")){
            hql.append(" and c.id != "+ "'" + Integer.parseInt(id.trim()) + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        return  query.list();
    }
}
