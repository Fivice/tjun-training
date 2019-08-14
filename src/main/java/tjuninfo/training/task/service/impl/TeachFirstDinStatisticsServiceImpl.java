package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.DiningStatisticsConstantCode;
import tjuninfo.training.task.dao.ITeachDiningStatisticsDao;
import tjuninfo.training.task.dao.ITeachFirstDiningStatisticsDao;
import tjuninfo.training.task.dto.TeachDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.dto.TeachDiningRecordForDiningStatisticsDTO;
import tjuninfo.training.task.dto.TeachSenseDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.TeachDiningDataStatisticsVO;
import tjuninfo.training.task.vo.TeachFirstDiningStatisticsVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:38
 * @Description:教师就餐首日统计业务层接口实现类
 */
@Service
public class TeachFirstDinStatisticsServiceImpl extends BaseServiceImpl<TeachFirstDiningStatisticsVO> implements TeachFirstDinStatisticsService {
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
    private ITeachFirstDiningStatisticsDao teachFirstDiningStatisticsDao;
    @Resource
    public void setDao(ITeachFirstDiningStatisticsDao teachFirstDiningStatisticsDao) {
        this.teachFirstDiningStatisticsDao = teachFirstDiningStatisticsDao;
        this.dao = teachFirstDiningStatisticsDao;
    }


    @Override
    /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
    /*流水号教师首日餐统计*/
    public List<TeachFirstDiningStatisticsVO> count(String startDay, String endDay) {
        return teachFirstDiningStatisticsDao.count(startDay,endDay);
    }


    @Override
    /*根据时间统计每个就餐地点的就餐安排和就餐时间次数，金额，实到合计，应到合计并算出就餐率*/
    /*刷脸教师首日餐统计*/
    public List<TeachFirstDiningStatisticsVO> count2(String startDay, String endDay) {
        return teachFirstDiningStatisticsDao.count2(startDay,endDay);
    }

    @Override
    public TeachDiningDataStatisticsVO getTeachFirstDayDiningStatistics(String diningPlace, String[] classIdArr, String startTime, String endTime) {
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
        for (String classId:classIdArr
             ) {
            ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));
            //教师应到首日就餐费用
            BigDecimal teachTotalMoney;
            BigDecimal teachBreakfastTotalMoney;
            BigDecimal teachLunchTotalMoney;
            BigDecimal teachDinnerTotalMoney;
            BigDecimal teachBreakfastEatCount,teachLunchEatCount,teachDinnerEatCount;//该班级教师应该就餐人次
            /*
            * 查询班级教师在就餐地首日餐就餐次数
            * */
            //刷卡就餐安排首日就餐查询
            TeachDiningDataForDiningStatisticsDTO teachDiningDataForDiningStatisticsDTO = teachDiningService.getTeachFirstDayDining(diningPlace,classId,startTime,endTime);
            //人脸绑定就餐安排首日就餐查询
            TeachSenseDiningDataForDiningStatisticsDTO teachSenseDiningDataForDiningStatisticsDTO = iTeacherDiningForSceneService.getTeachFirstDayDining(diningPlace,classId,startTime,endTime);
            //将刷卡和人脸就餐首日就餐人次相加
            teachBreakfastEatCount = (teachDiningDataForDiningStatisticsDTO.getBreakfastEatCounts() == null?(new BigDecimal(0)):(teachDiningDataForDiningStatisticsDTO.getBreakfastEatCounts())).add(teachSenseDiningDataForDiningStatisticsDTO.getBreakfastEatCounts() == null?(new BigDecimal(0)):(teachSenseDiningDataForDiningStatisticsDTO.getBreakfastEatCounts()));
            teachLunchEatCount = (teachDiningDataForDiningStatisticsDTO.getLunchEatCounts() == null?(new BigDecimal(0)):(teachDiningDataForDiningStatisticsDTO.getLunchEatCounts())).add(teachSenseDiningDataForDiningStatisticsDTO.getLunchEatCounts() == null?(new BigDecimal(0)):(teachSenseDiningDataForDiningStatisticsDTO.getLunchEatCounts()));
            teachDinnerEatCount = (teachDiningDataForDiningStatisticsDTO.getDinnerEatCounts() == null?(new BigDecimal(0)):(teachDiningDataForDiningStatisticsDTO.getDinnerEatCounts())).add(teachSenseDiningDataForDiningStatisticsDTO.getDinnerEatCounts() == null?(new BigDecimal(0)):(teachSenseDiningDataForDiningStatisticsDTO.getDinnerEatCounts()));
            //计算教师首日就餐费用
            teachBreakfastTotalMoney = teachBreakfastEatCount.multiply(new BigDecimal(classInfo.getBreakfast()));
            teachLunchTotalMoney = teachLunchEatCount.multiply(new BigDecimal(classInfo.getLunch()));
            teachDinnerTotalMoney = teachDinnerEatCount.multiply(new BigDecimal(classInfo.getDinner()));
            teachTotalMoney = teachBreakfastTotalMoney.add(teachLunchTotalMoney).add(teachDinnerTotalMoney);
            //累加各个班级教师在某一个就餐地的首日应到就餐人次
            breakfastEatCount = breakfastEatCount.add(teachBreakfastEatCount);
            lunchEatCount = lunchEatCount.add(teachLunchEatCount);
            dinnerEatCount = dinnerEatCount.add(teachDinnerEatCount);
            //累加每个班级下绑定的教师就餐费用
            totalMoney = totalMoney.add(teachTotalMoney);

            //计算实际就餐费用
            TeachDiningRecordForDiningStatisticsDTO tdr = teachDinRecordService.getTeachFirstDayDiningRecordStatistics(diningPlace,classId,startTime,endTime);
            TeachDiningRecordForDiningStatisticsDTO tsdr = iTeacherDiningForSceneService.getTeachFirstDayDiningRecordStatistics(diningPlace,classId,startTime,endTime);
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
    public List<TeachDiningDataStatisticsVO> getTeachDiningFirstDayStatisticsList(String diningPlace, String[] classIdArr, String startTime, String endTime) {
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
                list.add(getTeachFirstDayDiningStatistics(place,classIdArr,startTime,endTime));
            }
        }else{
            list.add(getTeachFirstDayDiningStatistics(diningPlace,classIdArr,startTime,endTime));
        }
        return list;
    }
}
