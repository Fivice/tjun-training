package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.DiningStatisticsConstantCode;
import tjuninfo.training.task.dao.ITeachDiningStatisticsDao;
import tjuninfo.training.task.dto.TeachDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.dto.TeachDiningRecordForDiningStatisticsDTO;
import tjuninfo.training.task.dto.TeachSenseDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.TeachDiningDataStatisticsVO;
import tjuninfo.training.task.vo.TeachDiningStatisticsVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:教师就餐统计业务层接口实现类
 */
@Service
public class TeachDinStatisticsServiceImpl extends BaseServiceImpl<TeachDiningStatisticsVO> implements TeachDinStatisticsService {

    private ITeachDiningStatisticsDao teachDiningStatisticsDao;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private TeachDiningService teachDiningService;
    @Autowired
    private ITeacherDiningForSceneService iTeacherDiningForSceneService;
    @Autowired
    private TeachDinRecordService teachDinRecordService;
    @Autowired
    private IBasicParametersService iBasicParametersService;
    @Resource
    public void setDao(ITeachDiningStatisticsDao teachDiningStatisticsDao) {
        this.teachDiningStatisticsDao = teachDiningStatisticsDao;
        this.dao = teachDiningStatisticsDao;
    }

    @Override
    public List<TeachDiningStatisticsVO> count(String startDay, String endDay) {
        return teachDiningStatisticsDao.count(startDay,endDay);
    }

    @Override
    public List<TeachDiningStatisticsVO> count2(String startDay, String endDay) {
        return teachDiningStatisticsDao.count2(startDay,endDay);
    }

    @Override
    public TeachDiningDataStatisticsVO getTeachDiningStatisticsData(String diningPlace, String[] classIdArr, String startTime, String endTime) {
        //应到就餐人次
        BigDecimal breakfastEatCount = new BigDecimal(0),lunchEatCount = new BigDecimal(0),dinnerEatCount = new BigDecimal(0);
        //应到合计金额
        BigDecimal totalMoney = new BigDecimal(0);
        //实到人次
        BigDecimal breakfastEatCountReal = new BigDecimal(0),lunchEatCountReal = new BigDecimal(0),dinnerEatCountReal = new BigDecimal(0);
        //实际产生费用
        BigDecimal breakfastEatMoneyReal = new BigDecimal(0),lunchEatMoneyReal = new BigDecimal(0),dinnerEatMoneyReal = new BigDecimal(0);
        if (classIdArr.length==0){
            return null;
        }
        for (String classId: classIdArr
             ) {
            ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));

            //计算实际就餐费用
            TeachDiningRecordForDiningStatisticsDTO tdr = teachDinRecordService.getTeachDiningRecordStatistics(diningPlace,classId,startTime,endTime);
            TeachDiningRecordForDiningStatisticsDTO tsdr = iTeacherDiningForSceneService.getTeachDiningRecordStatistics(diningPlace,classId,startTime,endTime);
            //实际就餐人次
            breakfastEatCountReal = breakfastEatCountReal.add(tdr.getBreakfastEatCountReal()==null?(new BigDecimal(0)):(tdr.getBreakfastEatCountReal())).add(tsdr.getBreakfastEatCountReal()==null?(new BigDecimal(0)):(tsdr.getBreakfastEatCountReal()));
            lunchEatCountReal = lunchEatCountReal.add(tdr.getLunchEatCountReal()==null?(new BigDecimal(0)):(tdr.getLunchEatCountReal())).add(tsdr.getLunchEatCountReal()==null?(new BigDecimal(0)):(tsdr.getLunchEatCountReal()));
            dinnerEatCountReal = dinnerEatCountReal.add(tdr.getDinnerEatCountReal()==null?(new BigDecimal(0)):(tdr.getDinnerEatCountReal())).add(tsdr.getDinnerEatCountReal()==null?(new BigDecimal(0)):(tsdr.getDinnerEatCountReal()));

            //实际产生费用
            BigDecimal teachBreakFastMoneyReal = ((tdr.getBreakfastEatCountReal()==null?(new BigDecimal(0)):(tdr.getBreakfastEatCountReal())).add(tsdr.getBreakfastEatCountReal()==null?(new BigDecimal(0)):(tsdr.getBreakfastEatCountReal()))).multiply(new BigDecimal(Long.parseLong(classInfo.getBreakfast())));
            breakfastEatMoneyReal = breakfastEatMoneyReal.add(teachBreakFastMoneyReal);

            BigDecimal teachLunchEatMoneyReal = ((tdr.getLunchEatCountReal()==null?(new BigDecimal(0)):(tdr.getLunchEatCountReal())).add(tsdr.getLunchEatCountReal()==null?(new BigDecimal(0)):(tsdr.getLunchEatCountReal()))).multiply(new BigDecimal(Long.parseLong(classInfo.getLunch())));
            lunchEatMoneyReal = lunchEatMoneyReal.add(teachLunchEatMoneyReal);

            BigDecimal teachDinnerEatMoneyReal = ((tdr.getDinnerEatCountReal()==null?(new BigDecimal(0)):(tdr.getDinnerEatCountReal())).add(tsdr.getDinnerEatCountReal()==null?(new BigDecimal(0)):(tsdr.getDinnerEatCountReal()))).multiply(new BigDecimal(Long.parseLong(classInfo.getDinner())));
            dinnerEatMoneyReal = dinnerEatMoneyReal.add(teachDinnerEatMoneyReal);
            //教师应到就餐费用
            BigDecimal teachTotalMoney;
            BigDecimal teachBreakfastTotalMoney;
            BigDecimal teachLunchTotalMoney;
            BigDecimal teachDinnerTotalMoney;
            BigDecimal teachBreakfastEatCount,teachLunchEatCount,teachDinnerEatCount;//该班级教师应该就餐人次
            //查询班级教师在就餐地某段时间内早中晚就餐次数
            TeachDiningDataForDiningStatisticsDTO teachDiningDataForDiningStatisticsDTO = teachDiningService.getTeachDining(diningPlace,classId,startTime,endTime);
            TeachSenseDiningDataForDiningStatisticsDTO teachSenseDiningDataForDiningStatisticsDTO = iTeacherDiningForSceneService.getTeachDining(diningPlace,classId,startTime,endTime);
            teachBreakfastEatCount = (teachDiningDataForDiningStatisticsDTO.getBreakfastEatCounts() == null?(new BigDecimal(0)):(teachDiningDataForDiningStatisticsDTO.getBreakfastEatCounts())).add(teachSenseDiningDataForDiningStatisticsDTO.getBreakfastEatCounts() == null?(new BigDecimal(0)):(teachSenseDiningDataForDiningStatisticsDTO.getBreakfastEatCounts()));
            teachLunchEatCount = (teachDiningDataForDiningStatisticsDTO.getLunchEatCounts() == null?(new BigDecimal(0)):(teachDiningDataForDiningStatisticsDTO.getLunchEatCounts())).add(teachSenseDiningDataForDiningStatisticsDTO.getLunchEatCounts() == null?(new BigDecimal(0)):(teachSenseDiningDataForDiningStatisticsDTO.getLunchEatCounts()));
            teachDinnerEatCount = (teachDiningDataForDiningStatisticsDTO.getDinnerEatCounts() == null?(new BigDecimal(0)):(teachDiningDataForDiningStatisticsDTO.getDinnerEatCounts())).add(teachSenseDiningDataForDiningStatisticsDTO.getDinnerEatCounts() == null?(new BigDecimal(0)):(teachSenseDiningDataForDiningStatisticsDTO.getDinnerEatCounts()));

            teachBreakfastTotalMoney = teachBreakfastEatCount.multiply(new BigDecimal(classInfo.getBreakfast()));
            teachLunchTotalMoney = teachLunchEatCount.multiply(new BigDecimal(classInfo.getLunch()));
            teachDinnerTotalMoney = teachDinnerEatCount.multiply(new BigDecimal(classInfo.getDinner()));
            teachTotalMoney = teachBreakfastTotalMoney.add(teachLunchTotalMoney).add(teachDinnerTotalMoney);
            //累加各个班级教师在某一个就餐地的应到就餐人次
            breakfastEatCount = breakfastEatCount.add(teachBreakfastEatCount);
            lunchEatCount = lunchEatCount.add(teachLunchEatCount);
            dinnerEatCount = dinnerEatCount.add(teachDinnerEatCount);
            totalMoney = totalMoney.add(teachTotalMoney);
        }
        TeachDiningDataStatisticsVO teachDiningDataStatisticsVO = new TeachDiningDataStatisticsVO(diningPlace,
                breakfastEatCount,
                lunchEatCount,
                dinnerEatCount,
                totalMoney,
                breakfastEatCountReal,
                breakfastEatMoneyReal,
                lunchEatCountReal,
                lunchEatMoneyReal,
                dinnerEatCountReal,
                dinnerEatMoneyReal
        );
        return teachDiningDataStatisticsVO;
    }

    @Override
    public List<TeachDiningDataStatisticsVO> getTeachDiningStatisticsDataList(String diningPlace, String[] classIdArr, String startTime, String endTime) {
        List<TeachDiningDataStatisticsVO> list = new ArrayList<>();
        if (DiningStatisticsConstantCode.ALL_PLACE.equals(diningPlace)){
            //获取全部地区列表
            List<BasicParameters> basicParametersList = iBasicParametersService.list();
            BasicParameters basicParameters = basicParametersList.get(0);
            String diningPlaceStr = basicParameters.getEatPlace();
            String[] diningPlaceArr = diningPlaceStr.split(",");

            //循环查询每个地区在指定条件下的就餐情况
            for (String place: diningPlaceArr
            ) {
                list.add(getTeachDiningStatisticsData(place,classIdArr,startTime,endTime));
            }
        }else{
            list.add(getTeachDiningStatisticsData(diningPlace,classIdArr,startTime,endTime));
        }
        return list;
    }
}
