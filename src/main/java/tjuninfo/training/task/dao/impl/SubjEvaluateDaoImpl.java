package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.SubjEvaluateDao;
import tjuninfo.training.task.entity.SubjEvaluate;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.EvaluateScoreVo;
import tjuninfo.training.task.vo.SubjEvaluateVo;

import java.util.List;


/**
 * 课程评价结果表数据层接口实现类
 */
@Repository
public class SubjEvaluateDaoImpl extends BaseDaoImpl<SubjEvaluate> implements SubjEvaluateDao {

	public SubjEvaluateDaoImpl(){
		super(SubjEvaluate.class);
	}

    @Override
    public Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> registerId) {
        StringBuffer hql =new StringBuffer("select register_id as registerId,project_id as projectId,scheduling.scheduling_content as schedulingContent,scheduling.teacher as teacher, SUM(result=1) as result1, SUM(result=2) as result2,SUM(result=3) as result3,SUM(result=4) as result4,SUM(result=5) as result5,COUNT(result) as resultSum \n" +
                "from subj_evaluate INNER JOIN scheduling ON subj_evaluate.project_id = scheduling.scheduling_id ") ;
        if(registerId.size() > 1){
            hql.append(" and ( register_id = "+registerId.get(0)+" ");
            for(int i = 1 ; i<registerId.size(); i++){
                hql.append( " or register_id = "+registerId.get(i)+" ");
            }
            hql.append(")");
        }else {
            hql.append(" and (register_id = "+registerId.get(0)+")");
        }
        hql.append(" GROUP BY project_id ");
        Page<SubjEvaluateVo> page = new Page<>();
        Query query = getSession().createSQLQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(SubjEvaluateVo.class)).list().size();
        query.setFirstResult((pageNumber-1)*pageSize);
        query.setMaxResults(pageSize);
        List<SubjEvaluateVo> list = query.setResultTransformer(Transformers.aliasToBean(SubjEvaluateVo.class)).list();
        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageNumber);
        page.setTotalRecords(count);
        return page;
    }


}
