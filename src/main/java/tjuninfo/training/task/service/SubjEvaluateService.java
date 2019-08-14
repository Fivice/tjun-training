package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.SubjEvaluate;
import tjuninfo.training.task.util.Page;

import java.util.List;


/**
 * 课程评价结果表业务层接口
 */
public interface SubjEvaluateService extends IBaseService<SubjEvaluate>{
    //根据报道ID到课程评价结果表中所有信息
    Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> registerId);

    //保存课程评价
    void saveSubj(SubjEvaluate se);
}
