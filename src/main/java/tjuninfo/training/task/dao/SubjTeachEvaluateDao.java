package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.SubjEvaluate;
import tjuninfo.training.task.entity.SubjTeachEvaluate;
import tjuninfo.training.task.util.Page;

import java.util.List;

/**
 * 课程评价结果表数据层接口
 */
public interface SubjTeachEvaluateDao extends IBaseDao<SubjTeachEvaluate> {
    //根据报道ID到评价结果表中所有信息
    Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> registerId);
}

	
	
