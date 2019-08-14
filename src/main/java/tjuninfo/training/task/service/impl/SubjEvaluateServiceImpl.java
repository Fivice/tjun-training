package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.SubjEvaluateDao;
import tjuninfo.training.task.entity.SubjEvaluate;
import tjuninfo.training.task.service.SubjEvaluateService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.SubjEvaluateVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 课程评价结果表业务层接口实现类
 */
@Service
public class SubjEvaluateServiceImpl extends BaseServiceImpl<SubjEvaluate> implements SubjEvaluateService {
	private SubjEvaluateDao subjEvaluateDao;

	@Resource
	public void setSysAuthorityDao(SubjEvaluateDao subjEvaluateDao) {
		this.subjEvaluateDao = subjEvaluateDao;
		this.dao = subjEvaluateDao;
	}

    @Override
    public Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> registerId) {
        Page page = subjEvaluateDao.findAllByRegisterId(pageSize,pageNumber, registerId);
        List<SubjEvaluateVo> subjEvaluateVos = page.getList();
        for(SubjEvaluateVo subjEvaluateVo : subjEvaluateVos){
            Long resultSum =subjEvaluateVo.getResultSum().longValue();
            BigDecimal resultCount=new BigDecimal(resultSum);
            BigDecimal var1 = subjEvaluateVo.getResult1().multiply(new BigDecimal("130"));
            BigDecimal var2 = subjEvaluateVo.getResult2().multiply(new BigDecimal("115"));
            BigDecimal var3 = subjEvaluateVo.getResult3().multiply(new BigDecimal("90"));
            BigDecimal var4 = subjEvaluateVo.getResult4().multiply(new BigDecimal("75"));
            BigDecimal var5 = subjEvaluateVo.getResult5().multiply(new BigDecimal("60"));
            BigDecimal varSum = var1.add(var2).add(var3).add(var4).add(var5);
            BigDecimal varAverage = varSum.divide(resultCount,2,BigDecimal.ROUND_HALF_UP);
            subjEvaluateVo.setResult(varAverage);
//            subjEvaluateVo.setResult((subjEvaluateVo.getResult1().multiply(new BigDecimal("130")).add(subjEvaluateVo.getResult2().multiply(new BigDecimal("115"))).add(subjEvaluateVo.getResult3().multiply(new BigDecimal("90"))).add(subjEvaluateVo.getResult4().multiply(new BigDecimal("75"))).add(subjEvaluateVo.getResult5().multiply(new BigDecimal("60")))).divide(resultCount));
        }
        return page;
    }

    @Override
    public void saveSubj(SubjEvaluate se) {
        subjEvaluateDao.save(se);
    }
}
