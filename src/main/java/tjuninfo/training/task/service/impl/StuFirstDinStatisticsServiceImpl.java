package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.DiningStatisticsConstantCode;
import tjuninfo.training.task.dao.IStuFirstDiningStatisticsDao;
import tjuninfo.training.task.dto.ClassDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.dto.SignUpStudentDTO;
import tjuninfo.training.task.dto.StuDiningRecordStatisticsDTO;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:38
 * @Description:学员首日餐业务层接口实现类
 */
@Service
public class StuFirstDinStatisticsServiceImpl extends BaseServiceImpl<StuFirstDiningStatisticsVO> implements StuFirstDinStatisticsService {

    @Autowired
    private ClassDiningService classDiningService;
    @Autowired
    private StuDinRecordService stuDinRecordService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private IBasicParametersService iBasicParametersService;
    private IStuFirstDiningStatisticsDao stuFirstDiningStatisticsDao;
    @Resource
    public void setDao(IStuFirstDiningStatisticsDao stuFirstDiningStatisticsDao) {
        this.stuFirstDiningStatisticsDao = stuFirstDiningStatisticsDao;
        this.dao = stuFirstDiningStatisticsDao;
    }


    @Override
    public Page count(int pageSize, int pageIndex, String startDay, String endDay) {
        return stuFirstDiningStatisticsDao.count(pageSize, pageIndex, startDay, endDay);
    }

    @Override
    public StuDiningDataStatisticsVO stuFirstDayStatistics(String diningPlace, String[] classIdArr) {
        //早餐应到人次
        BigDecimal breakfastEatCount = new BigDecimal(0);
        //中餐应到人次
        BigDecimal lunchEatCount = new BigDecimal(0);
        //晚餐应到人次
        BigDecimal dinnerEatCount = new BigDecimal(0);
        //应到人次合计费用
        BigDecimal totalMoney = new BigDecimal(0);
        //早餐实际就餐人次
        BigDecimal breakfastEatCountReal = new BigDecimal(0);
        //早餐实际就餐费用
        BigDecimal breakfastEatMoneyReal = new BigDecimal(0);
        //中餐实际就餐人次
        BigDecimal lunchEatCountReal = new BigDecimal(0);
        //中餐实际就餐费用
        BigDecimal lunchEatMoneyReal = new BigDecimal(0);
        //晚餐实际就餐人次
        BigDecimal dinnerEatCountReal = new BigDecimal(0);
        //晚餐实际就餐费用
        BigDecimal dinnerEatMoneyReal = new BigDecimal(0);
        for (String classId: classIdArr
             ) {
            //班级应到就餐费用
            BigDecimal classTotalMoney;
            BigDecimal classBreakfastTotalMoney;
            BigDecimal classLunchTotalMoney;
            BigDecimal classDinnerTotalMoney;
            BigDecimal classBreakfastEatCount,classLunchEatCount,classDinnerEatCount;
            /*
            * 获取班级首日餐安排统计
            * */
            ClassDiningDataForDiningStatisticsDTO classFirstDayDiningDTO= classDiningService.getClassFirstDayDining(diningPlace,classId);
            /*
            * 获取班级信息
            * */
            ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));
            /*
            * 计算该班级首日就餐人次
            * */
            //查询该班级报名后就餐人次
            BigInteger stuWillDining = registerService.getClassDiningCount(diningPlace,classId);
            //计算早餐该班级首日餐应到人次
            classBreakfastEatCount = ((classFirstDayDiningDTO.getBreakfastEatCounts()==null?(new BigDecimal(0)):(classFirstDayDiningDTO.getBreakfastEatCounts()).multiply(new BigDecimal(stuWillDining))));
            //计算中餐该班级首日餐应到人次
            classLunchEatCount = ((classFirstDayDiningDTO.getLunchEatCounts()==null?(new BigDecimal(0)):(classFirstDayDiningDTO.getLunchEatCounts()).multiply(new BigDecimal(stuWillDining))));
            //计算晚餐该班级首日餐应到人次
            classDinnerEatCount = ((classFirstDayDiningDTO.getDinnerEatCounts()==null?(new BigDecimal(0)):(classFirstDayDiningDTO.getDinnerEatCounts()).multiply(new BigDecimal(stuWillDining))));
            /*
            * 计算首日餐应到人次产生费用
            * */
            classBreakfastTotalMoney = classBreakfastEatCount.multiply(new BigDecimal(classInfo.getBreakfast()));
            classLunchTotalMoney = classLunchEatCount.multiply(new BigDecimal(classInfo.getLunch()));
            classDinnerTotalMoney = classDinnerEatCount.multiply(new BigDecimal(classInfo.getDinner()));
            //累加班级早中晚应该产生餐费
            classTotalMoney = classBreakfastTotalMoney.add(classLunchTotalMoney).add(classDinnerTotalMoney);
            //累加各个班级在某一个就餐地的应到就餐人次
            breakfastEatCount = breakfastEatCount.add(classBreakfastEatCount);
            lunchEatCount = lunchEatCount.add(classLunchEatCount);
            dinnerEatCount = dinnerEatCount.add(classDinnerEatCount);
            totalMoney = totalMoney.add(classTotalMoney);

            /*
            * 获取班级首日餐就餐记录统计
            * */
            StuDiningRecordStatisticsDTO stuDiningRecordStatisticsDTO = stuDinRecordService.getStuDiningFirstDayRecordStatistics(diningPlace,classId);
            //实际就餐人次累计
            breakfastEatCountReal = breakfastEatCountReal.add(stuDiningRecordStatisticsDTO.getBreakfastEatCount() == null?(new BigDecimal(0)):(stuDiningRecordStatisticsDTO.getBreakfastEatCount()));
            lunchEatCountReal = lunchEatCountReal.add(stuDiningRecordStatisticsDTO.getLunchEatCount() == null?(new BigDecimal(0)):(stuDiningRecordStatisticsDTO.getLunchEatCount()));
            dinnerEatCountReal = dinnerEatCountReal.add(stuDiningRecordStatisticsDTO.getDinnerEatCount() == null?(new BigDecimal(0)):(stuDiningRecordStatisticsDTO.getDinnerEatCount()));
            //实际就餐产生的费用

            BigDecimal classBreakFastMoneyReal = stuDiningRecordStatisticsDTO.getBreakfastEatCount() == null?(new BigDecimal(0)):(stuDiningRecordStatisticsDTO.getBreakfastEatCount().multiply(new BigDecimal(Long.parseLong(classInfo.getBreakfast()))));
            breakfastEatMoneyReal = breakfastEatMoneyReal.add(classBreakFastMoneyReal);

            BigDecimal classLunchMoneyReal = stuDiningRecordStatisticsDTO.getLunchEatCount() == null?(new BigDecimal(0)):(stuDiningRecordStatisticsDTO.getLunchEatCount().multiply(new BigDecimal(Long.parseLong(classInfo.getLunch()))));
            lunchEatMoneyReal = lunchEatMoneyReal.add(classLunchMoneyReal);

            BigDecimal classDinnerMoneyReal = stuDiningRecordStatisticsDTO.getDinnerEatCount() == null?(new BigDecimal(0)):(stuDiningRecordStatisticsDTO.getDinnerEatCount().multiply(new BigDecimal(Long.parseLong(classInfo.getDinner()))));
            dinnerEatMoneyReal = dinnerEatMoneyReal.add(classDinnerMoneyReal);
        }

        StuDiningDataStatisticsVO stuDiningDataStatisticsVO = new StuDiningDataStatisticsVO(
                diningPlace,
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
        return stuDiningDataStatisticsVO;
    }

    @Override
    public List<StuDiningDataStatisticsVO> getStuFirstDayDiningStatisticsList(String diningPlace, String[] classIdArr) {
        List<StuDiningDataStatisticsVO> list = new ArrayList<>();
        if (DiningStatisticsConstantCode.ALL_PLACE.equals(diningPlace)){
            //获取全部地区列表
            List<BasicParameters> basicParametersList = iBasicParametersService.list();
            BasicParameters basicParameters = basicParametersList.get(0);
            String diningPlaceStr = basicParameters.getEatPlace();
            String[] diningPlaceArr = diningPlaceStr.split(",");

            //循环查询每个地区在指定条件下的就餐情况
            for (String place: diningPlaceArr
            ) {
                list.add(stuFirstDayStatistics(place,classIdArr));
            }
        }else{
            list.add(stuFirstDayStatistics(diningPlace,classIdArr));
        }
        return list;
    }
}
