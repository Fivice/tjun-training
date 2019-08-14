package tjuninfo.training.task.dao.impl;


import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.EvaluateScoreDao;
import tjuninfo.training.task.dao.RegisterDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.EvaluateProject;
import tjuninfo.training.task.entity.EvaluateScore;
import tjuninfo.training.task.entity.Register;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.EvaluateScoreVo;
import tjuninfo.training.task.vo.StuFirstDiningStatisticsVO;

import java.util.List;


/**
 * 评价结果表数据层接口实现类
 */
@Repository
public class EvaluateScoreDaoImpl extends BaseDaoImpl<EvaluateScore> implements EvaluateScoreDao {

	public EvaluateScoreDaoImpl(){
		super(EvaluateScore.class);
	}


	@Override
	public Page findAllByRegisterId(int pageSize,int pageNumber,List<Integer> id) {
		StringBuffer hql =new StringBuffer("select es.register_id as registerId,es.project_id as projectId,ep.project as project,ep.type as type, SUM(es.result=1) as result1, SUM(es.result=2) as result2,SUM(es.result=3) as result3,SUM(es.result=4) as result4,SUM(es.result=5) as result5,COUNT(es.result) as resultSum " +
                "FROM\n" +
                "\tevaluate_score es inner  JOIN evaluate_project ep ON es.project_id = ep.id and ep.large_class !=7 and ep.large_class !=8   ") ;
        if(id.size() > 1){
            hql.append(" and (es.register_id = "+id.get(0)+" ");
            for(int i = 1 ; i<id.size(); i++){
                hql.append( " or es.register_id = "+id.get(i)+" ");
            }
            hql.append(")");
        }else {
            hql.append(" and (es.register_id = "+id.get(0)+")");
        }
        hql.append(" GROUP BY es.project_id ");
        System.out.println(hql);
        Page<EvaluateScoreVo> page = new Page<>();
        //這裡需要注意的一點是返回的VO對象，如果有構造函數，則無參構造函數一定要是public
        Query query = getSession().createSQLQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(EvaluateScoreVo.class)).list().size();
        query.setFirstResult((pageNumber-1)*pageSize);
        query.setMaxResults(pageSize);
        List<EvaluateScoreVo> list = query.setResultTransformer(Transformers.aliasToBean(EvaluateScoreVo.class)).list();
        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageNumber);
        page.setTotalRecords(count);
        return page;
	}
    @Override
    public List<EvaluateScoreVo> findAllByRegisterIdTest(List<Integer> id) {
        StringBuffer hql =new StringBuffer("select es.register_id as registerId,es.project_id as projectId,ep.project as project,ep.type as type,es.result as suggestResult " +
                " FROM\n" +
                "\tevaluate_score es inner  JOIN evaluate_project ep ON es.project_id = ep.id and (ep.large_class =7 or ep.large_class =8) " );
        if(id.size() > 1){
            hql.append(" and (es.register_id = "+id.get(0)+"");
            for(int i = 1 ; i<id.size(); i++){
                hql.append( " or es.register_id = "+id.get(i)+" ");
            }
            hql.append(")");
        }else {
            hql.append(" and (es.register_id = "+id.get(0)+")");
        }
        hql.append("ORDER BY ep.type");
        Page<EvaluateScoreVo> page = new Page<>();
        //這裡需要注意的一點是返回的VO對象，如果有構造函數，則無參構造函數一定要是public
        Query query = getSession().createSQLQuery(hql.toString());

        List<EvaluateScoreVo> list = query.setResultTransformer(Transformers.aliasToBean(EvaluateScoreVo.class)).list();
        return list;
    }

    @Override
    public Page findAllByRegisterId1(int pageSize, int pageNumber, List<Integer> id) {
        StringBuffer hql =new StringBuffer("select es.register_id as registerId,es.project_id as projectId,ep.project as project,ep.type as type,es.result as suggestResult " +
                " FROM\n" +
                "\tevaluate_score es inner  JOIN evaluate_project ep ON es.project_id = ep.id and (ep.large_class =7 or ep.large_class =8) " );
            if(id.size() > 1){
                hql.append(" and (es.register_id = "+id.get(0)+"");
            for(int i = 1 ; i<id.size(); i++){
                hql.append( " or es.register_id = "+id.get(i)+" ");
            }
            hql.append(")");
        }else {
                hql.append(" and (es.register_id = "+id.get(0)+")");
            }
            hql.append("ORDER BY ep.type");
        Page<EvaluateScoreVo> page = new Page<>();
        //這裡需要注意的一點是返回的VO對象，如果有構造函數，則無參構造函數一定要是public
        Query query = getSession().createSQLQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(EvaluateScoreVo.class)).list().size();
        query.setFirstResult((pageNumber-1)*pageSize);
        query.setMaxResults(pageSize);
        List<EvaluateScoreVo> list = query.setResultTransformer(Transformers.aliasToBean(EvaluateScoreVo.class)).list();
        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageNumber);
        page.setTotalRecords(count);
        return page;
    }

    @Override
    public List<EvaluateScoreVo> getByRegisterId(List<Integer> registerId,Integer id) {
	    StringBuffer sql = new StringBuffer("select es.register_id as registerId,es.project_id as projectId,ep.project as project,ep.type as type,es.result as suggestResult " +
                " FROM " +
                " evaluate_score es inner  JOIN evaluate_project ep ON es.project_id = ep.id and ep.large_class =1 ");
        if (id == 1){
            sql.append(" and ep.id="+id);
        }
        if (id == 2){
            sql.append(" and ep.id="+id);
        }
	    if(registerId.size() > 1){
            sql.append(" and (es.register_id = "+registerId.get(0)+"");
            for(int i = 1 ; i<registerId.size(); i++){
                sql.append( " or es.register_id = "+registerId.get(i)+" ");
            }
            sql.append(")");
        }else {
            sql.append(" and (es.register_id = "+registerId.get(0)+")");
        }
	    Query query = getSession().createSQLQuery(sql.toString());
         List<EvaluateScoreVo> list = query.setResultTransformer(Transformers.aliasToBean(EvaluateScoreVo.class)).list();
         return list;

    }

}
