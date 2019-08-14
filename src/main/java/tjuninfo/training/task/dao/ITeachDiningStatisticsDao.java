package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningStatisticsVO;

import java.util.List;

/**
 * 教师就餐统计数据层接口
 * @author 
 */
public interface ITeachDiningStatisticsDao extends IBaseDao<TeachDiningStatisticsVO> {

    /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
    /*流水号*/
    List<TeachDiningStatisticsVO> count( String startDay, String endDay);
    /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
    /*人脸识别*/
    List<TeachDiningStatisticsVO> count2(String startDay, String endDay);
}

	
	
