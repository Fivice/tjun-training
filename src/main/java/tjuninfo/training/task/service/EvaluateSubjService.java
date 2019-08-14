package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.EvaluateScore;
import tjuninfo.training.task.entity.EvaluateSubj;
import tjuninfo.training.task.util.Page;

import java.util.List;


/**
 * 评价结果表业务层接口
 */
public interface EvaluateSubjService extends IBaseService<EvaluateSubj>{
    //根据班级id查询课程评价
    List<EvaluateSubj> findByClassId(Long classId);
}
