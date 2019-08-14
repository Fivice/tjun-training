package tjuninfo.training.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.DiningStatisticsConstantCode;
import tjuninfo.training.task.dao.ClassDiningDao;
import tjuninfo.training.task.dto.ClassDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.dto.StuDiningRecordStatisticsDTO;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.StuDiningDataStatisticsVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/29 10:28
 * @Description:就餐安排业务层接口实现类
 */
@Service
public class ClassDiningServiceImpl extends BaseServiceImpl<ClassDining> implements ClassDiningService{

    private ClassDiningDao classDiningDao;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private StuDinRecordService stuDinRecordService;
    @Autowired
    private IBasicParametersService iBasicParametersService;
    @Resource
    public void setSysAuthorityDao(ClassDiningDao classDiningDao) {
        this.classDiningDao = classDiningDao;
        this.dao = classDiningDao;
    }


    @Override
    //查询所有的班级就餐信息
    public List<ClassDining> findClassDiningList() {
        List<ClassDining> list = classDiningDao.findAll();
        return list;
    }

    @Override
    //根据班级编号查询相应的班级就餐信息
    public List<ClassDining> findClassDiningList(String classNum) {
        List<ClassDining> list = classDiningDao.findClassDiningList(classNum);
        return list;
    }

    @Override
    public List<ClassDining> findClassDiningList(String classNum, String time) {
        return classDiningDao.findClassDiningList(classNum,time);
    }

    @Override
    //根据id号删除信息
    public void deleteByNid(int id) {
        classDiningDao.deleteById(id);
    }

    @Override
    //保存
    public void saveOrUpdate(ClassDining cd) {
        classDiningDao.saveOrUpdate(cd);
    }

    @Override
    public void deleteByClassNumber(String classNumber) {
        classDiningDao.deleteByClassNumber(classNumber);
    }

    @Override
    public Pages findClassDiningList(int pageSize, int pageNumber, String s) {
        return classDiningDao.findClassDiningList(pageSize,pageNumber,s);
    }

    @Override
    public ClassDining findMax(String classId) {
        return classDiningDao.findMax(classId);
    }

    @Override
    public String timeMax(ClassDining c) {
        String timeMax = c.getDay();//获取安排的最后一天
        //给时间加上时分秒
        String  timeMax2 = null;
        //取安排的最后一餐的时间
        if(c.getDinner() == 1){//最后一餐是晚餐 （1:就餐； 2：未就餐）
            timeMax2 = timeMax +" 23:59:59";
        }else if(c.getLunch() == 1){//最后一餐是中餐
            timeMax2 = timeMax +" 15:00:00";
        }else if(c.getBreakfast() == 1){//最后一餐是早餐
            timeMax2 = timeMax +" 10:00:00";
        }else {//最后一天没有就餐安排
            timeMax2 = timeMax +" 00:00:00";
        }
        return timeMax2;
    }

    @Override
    public boolean ifDing(String classId, String day, Integer dinner) {
        return classDiningDao.ifDing(classId,day,dinner);
    }

    @Override
    public List<ClassDining> byTime(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList) {
        return classDiningDao.byTime(nowDate,classDining,classInfoList);
    }

    @Override
    public List<ClassDining> byClassID(Long id) {
        return classDiningDao.byClassID(id);
    }

    @Override
    public ClassDiningDataForDiningStatisticsDTO getClassDining(String diningPlace, String classId,String startTime,String endTime) {
        return classDiningDao.getClassDining(diningPlace,classId,startTime,endTime);
    }

    @Override
    public StuDiningDataStatisticsVO getStuDiningStatisticsData(String diningPlace, String[] classIdArr, String startTime, String endTime) {
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
        //通过班级Id查看这段时间就餐安排早中晚次数
        for (String classId: classIdArr
             ) {
            ClassInfo classInfo = classInfoService.get(Long.parseLong(classId));
            //查看实际就餐安排
            StuDiningRecordStatisticsDTO stuDiningRecordStatisticsDTO = stuDinRecordService.getStuDiningRecordStatistics(diningPlace,classId,startTime,endTime);
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

            //班级应到就餐费用
            BigDecimal classTotalMoney;
            BigDecimal classBreakfastTotalMoney;
            BigDecimal classLunchTotalMoney;
            BigDecimal classDinnerTotalMoney;
            BigDecimal classBreakfastEatCount,classLunchEatCount,classDinnerEatCount;//该班级应该就餐人次
            //查询班级就餐地某段时间内早中晚就餐次数
            ClassDiningDataForDiningStatisticsDTO classDiningDataForDiningStatisticsDTO = getClassDining(diningPlace,classId,startTime,endTime);

            BigInteger stuWillDining = registerService.getClassDiningCount(diningPlace,classId);
            //早餐应到人次
            classBreakfastEatCount = classDiningDataForDiningStatisticsDTO.getBreakfastEatCounts() == null?(new BigDecimal(0)):(classDiningDataForDiningStatisticsDTO.getBreakfastEatCounts().multiply(new BigDecimal(stuWillDining)));
            //午餐应到人次
            classLunchEatCount = classDiningDataForDiningStatisticsDTO.getLunchEatCounts() == null?(new BigDecimal(0)):(classDiningDataForDiningStatisticsDTO.getLunchEatCounts().multiply(new BigDecimal(stuWillDining)));
            //晚餐应到人次
            classDinnerEatCount = classDiningDataForDiningStatisticsDTO.getDinnerEatCounts() == null?(new BigDecimal(0)):(classDiningDataForDiningStatisticsDTO.getDinnerEatCounts().multiply(new BigDecimal(stuWillDining)));

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
    public List<StuDiningDataStatisticsVO> getStuDiningStatisticsDataList(String diningPlace, String[] classIdArr, String startTime, String endTime) {
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
                list.add(getStuDiningStatisticsData(place,classIdArr,startTime,endTime));
            }
        }else{
            list.add(getStuDiningStatisticsData(diningPlace,classIdArr,startTime,endTime));
        }
        return list;
    }

    @Override
    public ClassDiningDataForDiningStatisticsDTO getClassFirstDayDining(String diningPlace, String classId) {
        return classDiningDao.getClassFirstDayDining(diningPlace,classId);
    }

    @Override
    public Pages findClassDiningListByClassId(int pageSize, int pageNumber, String classId) {
        return classDiningDao.findClassDiningListByClassId(pageSize,pageNumber,classId);
    }
}
