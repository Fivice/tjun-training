package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.TeachDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachFirstDiningStatisticsVO;

import java.util.List;

/**
 * 教师首日就餐统计数据层接口
 * @author 
 */
public interface ITeachFirstDiningStatisticsDao extends IBaseDao<TeachFirstDiningStatisticsVO> {

    /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
    /*流水号教师首日餐统计*/
    List<TeachFirstDiningStatisticsVO> count(String startDay, String endDay);

    /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
    /*刷脸教师首日餐统计*/
    List<TeachFirstDiningStatisticsVO> count2(String startDay, String endDay);
}

	
	
