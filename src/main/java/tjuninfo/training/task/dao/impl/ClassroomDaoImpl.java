package tjuninfo.training.task.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IClassroomDao;
import tjuninfo.training.task.entity.Classroom;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 区域及教室数据层接口实现层
 */
@Repository
public class ClassroomDaoImpl extends BaseDaoImpl<Classroom> implements IClassroomDao{
    public ClassroomDaoImpl() {
        super(Classroom.class);
    }

    @Override
    public List<Classroom> findByType(Integer type) {
        List<Classroom> list= super.getSession().createCriteria(Classroom.class, "c").add(Restrictions.eq("c.type",type)).addOrder(Order.asc("c.sort")).list();
        return list;
    }

    @Override
    public List<Classroom> getBySjId(Integer id) {
        return super.getSession().createCriteria(Classroom.class, "c").add(Restrictions.eq("c.sjId",id)).addOrder(Order.asc("c.sort")).list();
    }

    @Override
    public List<Classroom> findName() {
        List<Classroom> list=null;
        String hql = "from Classroom c where 1=1";
        hql+=" group by c.schoolName";
        return getSession().createQuery(hql).list();
    }

    @Override
    public List<Classroom> list(String name, String classType, String className) {
        List<Classroom> list=null;
        String hql = "from Classroom c where 1=1";
        if(name!= null && !name.equals("")){
            hql += " and c.schoolName = "+ "'"+name+"'" ;
        }
        if(classType!= null && !classType.equals("")){
            hql += " and c.classroomType = "+ classType ;
        }
        if(className != null && !className.equals("")){
            hql += " and c.className like " + "'%"+ className +"%'";
        }
        return getSession().createQuery(hql).list();
    }

    @Override
    public List<Classroom> getClassroomBySchool(Integer newSname) {
        List<Classroom> list=null;
        String hql = "from Classroom c where 1=1";
        if(newSname!= null && !newSname.equals("")){
            hql += " and c.schoolName = "+ newSname ;
        }
        hql += " and c.aState = 1";
        return getSession().createQuery(hql).list();
    }

    @Override
    public List<Classroom> getClassroomByClassId(String classId) {
        String hql = "from Classroom c where 1=1";
        if(classId!= null && !classId.equals("")){
            hql += " and c.campusId = "+ "'"+Integer.parseInt(classId)+"'" ;
        }
        hql += " and c.aState = 1";
        return getSession().createQuery(hql).list();
    }

    @Override
    public Pages list(int pageSize, int pageNumber, String schoolName, String classType, String className) {
        StringBuffer hql =new StringBuffer("from Classroom c where 1=1") ;
        if(schoolName!= null && !schoolName.equals("")){
            hql.append(" and c.schoolName = "+Integer.valueOf(schoolName)  ) ;
        }
        if(classType!= null && !classType.equals("")){
            hql.append(" and c.classroomType = "+ classType)  ;
        }
        if(className != null && !className.equals("")){
            hql.append(" and c.className like " + "'%"+ className +"%'");
        }
        hql.append("and c.aState = " +1 +" order by c.schoolName") ;
       Query query = getSession().createQuery(hql.toString());
        Pages pages = new Pages(pageNumber, pageSize, query);
        return pages;
    }

    @Override
    public List<Classroom> findByClassName(String className) {
        StringBuffer hql =new StringBuffer("from Classroom c where c.aState = 1");
        if(className!= null && !className.equals("")){
            hql.append(" and c.className = " + "'"+ className.trim() +"'");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public Classroom getByProp(String schoolId, String className, String classroomId) {
        StringBuffer hql =new StringBuffer("select new Classroom(id) from Classroom c where 1=1");

        if (schoolId!=null && !schoolId.equals("")){
            hql.append(" and c.schoolName= "+ "'" + Integer.parseInt(schoolId.trim()) + "'");
        }
        if (className!=null && !className.equals("")){
            hql.append(" and c.className= "+ "'" + className.trim() + "'");
        }
        if (classroomId!=null && !classroomId.equals("")){
            hql.append(" and c.id != "+ "'" + Integer.parseInt(classroomId.trim()) + "'");
        }
        hql.append(" and c.aState =1 ");
        Query query = getSession().createQuery(hql.toString());
        return (Classroom) query.uniqueResult();
    }
}
