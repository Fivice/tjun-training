package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.EvaluateScore;
import tjuninfo.training.task.entity.Register;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.EvaluateScoreVo;

import java.util.List;

/**
 * 评价结果表数据层接口
 */
public interface EvaluateScoreDao extends IBaseDao<EvaluateScore> {
    //根据报道ID到评价结果表中所有不含建议的信息
    Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> id);
    List<EvaluateScoreVo> findAllByRegisterIdTest(List<Integer> id);
    //根据报道ID到评价结果表中所有只含建议的信息
    Page findAllByRegisterId1(int pageSize, int pageNumber, List<Integer> id);

    List<EvaluateScoreVo> getByRegisterId(List<Integer> registerId,Integer id);
}

	
	
