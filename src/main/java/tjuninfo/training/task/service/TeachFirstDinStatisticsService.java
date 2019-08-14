package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.TeachDiningDataStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachFirstDiningStatisticsVO;

import java.util.List;

/**
 * @Auther: win7
 * @Description:教师就餐首日统计业务层接口
 */
public interface TeachFirstDinStatisticsService extends IBaseService<TeachFirstDiningStatisticsVO> {

  /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
  /*流水号教师首日餐统计*/
    List<TeachFirstDiningStatisticsVO> count(String startDay, String endDay);

  /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
  /*刷脸教师首日餐统计*/
    List<TeachFirstDiningStatisticsVO> count2(String startDay, String endDay);

    TeachDiningDataStatisticsVO getTeachFirstDayDiningStatistics(String diningPlace, String[] classIdArr, String startTime, String endTime);
    List<TeachDiningDataStatisticsVO> getTeachDiningFirstDayStatisticsList(String diningPlace, String[] classIdArr, String startTime, String endTime);
}
