package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.EvaluateScore;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.EvaluateScoreVo;
import tjuninfo.training.task.vo.EvaluationResultVO;
import tjuninfo.training.task.vo.OverallEvaluationVO;

import java.util.List;


/**
 * 评价结果表业务层接口
 */
public interface EvaluateScoreService extends IBaseService<EvaluateScore>{
    //根据报道ID到评价结果表中所有不含建议的信息
    Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> id);
    List<EvaluateScoreVo> findAllByRegisterIdTest(List<Integer> id);
    //根据报道ID到评价结果表中所有只含建议的信息
    Page findAllByRegisterId1(int pageSize, int pageNumber, List<Integer> registerId);

    void saveJudge(EvaluateScore es);

    OverallEvaluationVO getByRegisterId(List<Integer> registerId);

    /**
     * 变换传入List结构为可用的
     * @param list
     * @return
     */
    public List<EvaluationResultVO> transformList(List<EvaluateScoreVo> list);
}
