package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuFirstDiningStatisticsVO;

import java.util.List;

/**
 * 学生就餐统计数据层接口
 * @author 
 */
public interface IStuFirstDiningStatisticsDao extends IBaseDao<StuFirstDiningStatisticsVO> {

    /*根据时间统计每个就餐地点的首日就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
    Page count(int pageSize, int pageIndex, String startDay, String endDay);
}

	
	
