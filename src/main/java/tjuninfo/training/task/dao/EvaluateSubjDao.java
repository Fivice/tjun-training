package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.EvaluateSubj;

import java.util.List;

/**
 * 评价结果表数据层接口
 */
public interface EvaluateSubjDao extends IBaseDao<EvaluateSubj> {
    //根据班级id查询课程评价
    List<EvaluateSubj> findByClassId(Long classId);
}

	
	
