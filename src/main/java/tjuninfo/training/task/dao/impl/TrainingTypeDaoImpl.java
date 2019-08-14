package tjuninfo.training.task.dao.impl;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.TrainingTypeDao;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.entity.TrainingType;

import java.util.List;

/*基本培训维护数据层接口实现类*/
@Repository
public class TrainingTypeDaoImpl extends BaseDaoImpl<TrainingType> implements TrainingTypeDao {

    public TrainingTypeDaoImpl() {
        super(TrainingType.class);
    }

    /*查找方法*/
    public List<TrainingType> findAll() {
        List<TrainingType> List = super.getSession().createCriteria(TrainingType.class).list();
        return List;
    }

    @Override
    public List<TrainingType> findTrainingTypeList(BTView<TrainingType> bt) {
        String sqlTotal ="SELECT count(*) from training_type where 1=1";
        String sql ="SELECT id,type,t_explan as tExplan,remark from training_type where 1=1";
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(TrainingType.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }

    /*根据id删除*/
    public void deleteByNid(int id){
        super.getSession().createSQLQuery("delete from training_type where id=:id")
                .setParameter("id", id).executeUpdate();

    }

    @Override
    public List<TrainingType> findListByTypeName(String typeName) {
        StringBuffer hql =new StringBuffer("from TrainingType c where 1=1") ;
        if (typeName!=null && !typeName.equals("")){
            hql.append(" and c.type="+"'"+typeName.trim()+"'");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public List<TrainingType> checkType(String type) {
        StringBuffer hql =new StringBuffer("from TrainingType c where 1=1") ;
        if (type!=null && !type.equals("")){
            hql.append(" and c.type="+"'"+type.trim()+"'");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

}
