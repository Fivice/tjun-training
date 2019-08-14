package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.StuDiningRecord;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuDiningRecordVO;
import tjuninfo.training.task.vo.StuDiningStatisticsVO;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:37
 * @Description:就餐统计业务层接口
 */
public interface StuDinStatisticsService extends IBaseService<StuDiningStatisticsVO> {

  /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
    Page count(int pageSize, int pageNumber, String startDay, String endDay);

}
