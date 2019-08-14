package tjuninfo.training.task.dao.impl;


import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.SubjEvaluateDao;
import tjuninfo.training.task.dao.SubjTeachEvaluateDao;
import tjuninfo.training.task.entity.SubjEvaluate;
import tjuninfo.training.task.entity.SubjTeachEvaluate;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.SubjEvaluateVo;
import tjuninfo.training.task.vo.SubjTeachEvaluateVo;

import java.util.List;


/**
 * 课程评价结果表数据层接口实现类
 */
@Repository
public class SubjTeachEvaluateDaoImpl extends BaseDaoImpl<SubjTeachEvaluate> implements SubjTeachEvaluateDao {

	public SubjTeachEvaluateDaoImpl(){
		super(SubjTeachEvaluate.class);
	}

	@Override
	public Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> registerId) {
		StringBuffer hql =new StringBuffer("select register_id as registerId,project_id as projectId,scheduling.scheduling_content as schedulingContent,scheduling.teacher as teacher, SUM(result=1) as result1, SUM(result=2) as result2,SUM(result=3) as result3,SUM(result=4) as result4,SUM(result=5) as result5,COUNT(result) as resultSum  \n" +
                "from subj_teach_evaluate INNER JOIN scheduling ON subj_teach_evaluate.project_id = scheduling.scheduling_id ") ;
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
		Page<SubjTeachEvaluateVo> page = new Page<>();
        Query query = getSession().createSQLQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(SubjTeachEvaluateVo.class)).list().size();
        query.setFirstResult((pageNumber-1)*pageSize);
        query.setMaxResults(pageSize);
        List<SubjTeachEvaluateVo> list = query.setResultTransformer(Transformers.aliasToBean(SubjTeachEvaluateVo.class)).list();
        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageNumber);
        page.setTotalRecords(count);
        return page;
    }
}
