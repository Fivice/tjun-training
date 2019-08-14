package tjuninfo.training.task.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.EvaluateScoreDao;
import tjuninfo.training.task.entity.EvaluateScore;
import tjuninfo.training.task.service.EvaluateProjectService;
import tjuninfo.training.task.service.EvaluateScoreService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.EvaluateScoreVo;
import tjuninfo.training.task.vo.EvaluationProjectVO;
import tjuninfo.training.task.vo.EvaluationResultVO;
import tjuninfo.training.task.vo.OverallEvaluationVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 报到信息表业务层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Service
public class EvaluateScoreServiceImpl extends BaseServiceImpl<EvaluateScore> implements EvaluateScoreService {
	private EvaluateScoreDao evaluateScoreDao;

	@Resource
	public void setSysAuthorityDao(EvaluateScoreDao evaluateScoreDao) {
		this.evaluateScoreDao = evaluateScoreDao;
		this.dao = evaluateScoreDao;
	}



	@Override
	public Page findAllByRegisterId(int pageSize, int pageNumber, List<Integer> id) {
		Page page = evaluateScoreDao.findAllByRegisterId(pageSize,pageNumber,id);
		List<EvaluateScoreVo> evaluateScoreVoList = page.getList();
		for(EvaluateScoreVo evaluateScoreVo : evaluateScoreVoList){
			Long resultSum =evaluateScoreVo.getResultSum().longValue();
			BigDecimal resultSum1=new BigDecimal(resultSum);
			evaluateScoreVo.setResult((evaluateScoreVo.getResult1().multiply(new BigDecimal("130")).add(evaluateScoreVo.getResult2().multiply(new BigDecimal("115"))).add(evaluateScoreVo.getResult3().multiply(new BigDecimal("90"))).add(evaluateScoreVo.getResult4().multiply(new BigDecimal("75"))).add(evaluateScoreVo.getResult5().multiply(new BigDecimal("60")))).divide(resultSum1,2,BigDecimal.ROUND_HALF_UP));
		}
		return page;
	}
    @Override
    public List<EvaluateScoreVo> findAllByRegisterIdTest(List<Integer> id) {
        return evaluateScoreDao.findAllByRegisterIdTest(id);
    }

    @Override
    public Page findAllByRegisterId1(int pageSize, int pageNumber,List<Integer> registerId) {
        return evaluateScoreDao.findAllByRegisterId1(pageSize,pageNumber,registerId);
    }

	@Override
	public void saveJudge(EvaluateScore es) {
		evaluateScoreDao.save(es);
	}

	@Override
	public OverallEvaluationVO getByRegisterId(List<Integer> registerId) {
		//培训质量满意程度
		List<EvaluateScoreVo> evaluateScoreVoList = evaluateScoreDao.getByRegisterId(registerId,1);
		//综合服务满意程度
		List<EvaluateScoreVo> evaluateScoreVoList2 = evaluateScoreDao.getByRegisterId(registerId,2);
		OverallEvaluationVO overallEvaluationVO = new OverallEvaluationVO();
		Integer result = null;
		Double res4 = 0.0;
		if(evaluateScoreVoList.size()>0){
			Double res = 0.0;
			for(EvaluateScoreVo evaluateScoreVo : evaluateScoreVoList){
				Double res2 = 0.0;
				result = Integer.parseInt(evaluateScoreVo.getSuggestResult());
				if(result==1){
					res2= 130.0;
				}else if(result==2){
					res2 =115.0;
				}else if(result==3){
					res2 =90.0;
				}else if(result==4){
					res2 =75.0;
				}else if(result==5){
					res2 =60.0;
				}
				res = res2+res4;
				res4 = res;
			}
			BigDecimal d1 = new BigDecimal (res);//45除以7=6.428571428571429
			BigDecimal d2 = new BigDecimal (evaluateScoreVoList.size());
			BigDecimal r= d1.divide(d2,2,BigDecimal.ROUND_UP);//得到的结果就是6.42858，直接进位
			overallEvaluationVO.setQualityResult(r.doubleValue());
		}else {
			overallEvaluationVO.setQualityResult(0.00);
		}
		Integer result2 = null;
		Double res = 0.0;
		if(evaluateScoreVoList2.size()>0){
			Double res3 = 0.0;
			for(EvaluateScoreVo evaluateScoreVo2 : evaluateScoreVoList2){
				Double res2 = 0.0;
				result2 = Integer.parseInt(evaluateScoreVo2.getSuggestResult());
				if(result2==1){
					res2 = 130.0;
				}else if(result2==2){
					res2 =115.0;
				}else if(result2==3){
					res2 =90.0;
				}else if(result2==4){
					res2 =75.0;
				}else if(result2==5){
					res2 =60.0;
				}

				res = res2+res3;
				res3 = res;
			}
			BigDecimal d1 = new BigDecimal (res);
			BigDecimal d2 = new BigDecimal (evaluateScoreVoList2.size());
			BigDecimal r= d1.divide(d2,2,BigDecimal.ROUND_UP);//得到的结果就是6.42858，直接进位
			overallEvaluationVO.setComprehensiveResult(r.doubleValue());
//			overallEvaluationVO.setComprehensiveResult(res/evaluateScoreVoList2.size());
		}else {
			overallEvaluationVO.setComprehensiveResult(0.00);
		}
		return overallEvaluationVO;
	}

    @Override
    public List<EvaluationResultVO> transformList(List<EvaluateScoreVo> list) {
	    List<String> type = new ArrayList<>();
	    List<String> project = new ArrayList<>();
	    List<EvaluationProjectVO> evaluationProjectVOList = new ArrayList<>();
        List<EvaluationResultVO> evaluationResultVOList = new ArrayList<>();

        for (EvaluateScoreVo temp: list
             ) {
            if (!project.contains(temp.getProject())){
                project.add(temp.getProject());
            }
        }
        for (EvaluateScoreVo temp: list
        ) {
            if (!type.contains(temp.getType())){
                type.add(temp.getType());
            }
        }

        for (String temp:project
             ) {
            List<String> suggests = new ArrayList<>();
            for (EvaluateScoreVo evo:list
                 ) {
                //找出list中project为和temp等值的suggests
                if (temp.equals(evo.getProject())){
                    suggests.add(evo.getSuggestResult());
                }
            }
            EvaluationProjectVO evaluationProjectVO = new EvaluationProjectVO();
            evaluationProjectVO.setProject(temp);
            evaluationProjectVO.setSuggests(suggests);
            evaluationProjectVOList.add(evaluationProjectVO);
        }

        //循环遍历Type找出其中
        for (String temp:type
             ) {
            List<EvaluationProjectVO> newList = new ArrayList<>();
            //循环遍历evaluationProjectVOList，找出其中父类型和temp一致的evaluationProjectVO插入新的List中

            for (EvaluationProjectVO epvo:evaluationProjectVOList
                 ) {
                if (temp.equals(findType(epvo.getProject(),list))){
                    newList.add(epvo);
                }
            }
            //将temp和新的list打包成新的对象插入到evaluationResultVOList中
            EvaluationResultVO evaluationResultVO = new EvaluationResultVO();
            evaluationResultVO.setType(temp);
            evaluationResultVO.setProject(newList);
            evaluationResultVOList.add(evaluationResultVO);
        }
        return evaluationResultVOList;
    }
    private String findType(String project,List<EvaluateScoreVo> list){
        String type = null;
        for (EvaluateScoreVo esvo:list
             ) {
            if (project.equals(esvo.getProject())){
                type = esvo.getType();
            }
        }
	    return type;
    }
}
