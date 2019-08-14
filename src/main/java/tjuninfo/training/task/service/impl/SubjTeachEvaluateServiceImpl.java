package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.SubjTeachEvaluateDao;
import tjuninfo.training.task.entity.SubjTeachEvaluate;
import tjuninfo.training.task.service.SubjTeachEvaluateService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.SubjTeachEvaluateVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 课程评价结果表业务层接口实现类
 */
@Service
public class SubjTeachEvaluateServiceImpl extends BaseServiceImpl<SubjTeachEvaluate> implements SubjTeachEvaluateService {
	private SubjTeachEvaluateDao subjTeachEvaluateDao;

	@Resource
	public void setSysAuthorityDao(SubjTeachEvaluateDao subjTeachEvaluateDao) {
		this.subjTeachEvaluateDao = subjTeachEvaluateDao;
		this.dao = subjTeachEvaluateDao;
	}

	@Override
	public Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> registerId) {
		Page page = subjTeachEvaluateDao.findAllByRegisterId(pageSize,pageNumber, registerId);
        List<SubjTeachEvaluateVo> subjTeachEvaluateVos = page.getList();
        for(SubjTeachEvaluateVo subjTeachEvaluateVo : subjTeachEvaluateVos){
            Long resultSum =subjTeachEvaluateVo.getResultSum().longValue();
            BigDecimal resultSum1=new BigDecimal(resultSum);
            subjTeachEvaluateVo.setResult((subjTeachEvaluateVo.getResult1().multiply(new BigDecimal("130")).add(subjTeachEvaluateVo.getResult2().multiply(new BigDecimal("115"))).add(subjTeachEvaluateVo.getResult3().multiply(new BigDecimal("90"))).add(subjTeachEvaluateVo.getResult4().multiply(new BigDecimal("75"))).add(subjTeachEvaluateVo.getResult5().multiply(new BigDecimal("60")))).divide(resultSum1,2,BigDecimal.ROUND_HALF_UP));
        }
        return page;
    }

    @Override
    public void saveTea(SubjTeachEvaluate st) {
        subjTeachEvaluateDao.save(st);
    }
}
