package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.EvaluateScoreDao;
import tjuninfo.training.task.dao.EvaluateSubjDao;
import tjuninfo.training.task.entity.EvaluateScore;
import tjuninfo.training.task.entity.EvaluateSubj;
import tjuninfo.training.task.service.EvaluateScoreService;
import tjuninfo.training.task.service.EvaluateSubjService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.EvaluateScoreVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 报到信息表业务层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Service
public class EvaluateSubjServiceImpl extends BaseServiceImpl<EvaluateSubj> implements EvaluateSubjService {
	private EvaluateSubjDao evaluateSubjDao;
	@Resource
	public void setSysAuthorityDao(EvaluateSubjDao evaluateSubjDao) {
		this.evaluateSubjDao = evaluateSubjDao;
		this.dao = evaluateSubjDao;
	}

	@Override
	public List<EvaluateSubj> findByClassId(Long classId) {
		return evaluateSubjDao.findByClassId(classId);
	}
}
