package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.dto.TeachDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.dto.TeachSenseDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuDiningStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningDataStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningStatisticsVO;

import java.util.List;

/**
 * @Description:教师就餐统计业务层接口
 */
public interface TeachDinStatisticsService extends IBaseService<TeachDiningStatisticsVO> {

  /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
  /*流水号*/
  List<TeachDiningStatisticsVO> count(String startDay, String endDay);
  /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
  /*人脸识别*/
  List<TeachDiningStatisticsVO> count2(String startDay, String endDay);

  /**
   *
   * @param diningPlace
   * @param classIdArr
   * @param startTime
   * @param endTime
   * @return
   */
  public TeachDiningDataStatisticsVO getTeachDiningStatisticsData(String diningPlace, String[] classIdArr, String startTime, String endTime);

    /**
     *
     * @param diningPlace
     * @param classIdArr
     * @param startTime
     * @param endTime
     * @return
     */
  public List<TeachDiningDataStatisticsVO> getTeachDiningStatisticsDataList(String diningPlace, String[] classIdArr, String startTime, String endTime);

}
