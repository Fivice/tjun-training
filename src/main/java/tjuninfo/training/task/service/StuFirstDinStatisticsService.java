package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuDiningDataStatisticsVO;
import tjuninfo.training.task.vo.StuFirstDiningStatisticsVO;

import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:37
 * @Description:学员首日餐业务层接口
 */
public interface StuFirstDinStatisticsService extends IBaseService<StuFirstDiningStatisticsVO> {

  /*根据时间统计每个就餐地点的首日就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
  Page count(int pageSize, int pageIndex, String startDay, String endDay);

  StuDiningDataStatisticsVO stuFirstDayStatistics(String diningPlace,String[] classIdArr);

  List<StuDiningDataStatisticsVO> getStuFirstDayDiningStatisticsList(String diningPlace, String[] classIdArr);
}
