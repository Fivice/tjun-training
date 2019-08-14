package tjuninfo.training.task.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.EvaluateProjectDao;
import tjuninfo.training.task.entity.EvaluateProject;
import tjuninfo.training.task.entity.StuDiningRecord;

import java.util.List;

/**
 * 评价项目维护数据层接口实现类
 */
@Repository
public class EvaluateProjectDaoImpl extends BaseDaoImpl<EvaluateProject> implements EvaluateProjectDao {

    public EvaluateProjectDaoImpl() {
        super(EvaluateProject.class);
    }

    @Override
    /*根据项目选项来查找*/
    public List<EvaluateProject> findAll(Integer largeClass) {
        List<EvaluateProject> List = super.getSession().createCriteria(EvaluateProject.class, "e").add(Restrictions.eq("e.largeClass", largeClass)).list();
        return List;
    }

    @Override
    /*根据id删除*/
    public void deleteByNid(int id) {
        super.getSession().createSQLQuery("delete from evaluate_project where id=:id")
                .setParameter("id", id).executeUpdate();
    }

    @Override
    public List<EvaluateProject> findEvaluateProjectList(BTView<EvaluateProject> bt, Integer largeClass) {
        String sqlTotal = "SELECT count(*) FROM evaluate_project WHERE large_class = '"+largeClass+"'";
        String sql = "SELECT id,type,project,score,e_remark as eRemark,large_class as largeClass  FROM evaluate_project WHERE large_class = '"+largeClass+"'";
        bt.setTotal(Long.parseLong(this.getSession().createSQLQuery(sqlTotal).uniqueResult().toString()));
        return  super.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(EvaluateProject.class))
                .setFirstResult((bt.getPageNumber()-1)*bt.getPageSize()).setMaxResults(bt.getPageSize()).getResultList();
    }
}
