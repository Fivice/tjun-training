package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.constant.ApiCode;
import tjuninfo.training.task.dao.StuDinRecordDao;
import tjuninfo.training.task.dto.StuDiningRecordStatisticsDTO;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.StuDiningRecord;
import tjuninfo.training.task.entity.Student;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.StuDinRecordService;
import tjuninfo.training.task.vo.StuDiningRecordVO;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:38
 * @Description:就餐统计业务层接口实现类
 */
@Service
public class StuDinRecordServiceImpl extends BaseServiceImpl<StuDiningRecord> implements StuDinRecordService {

    private StuDinRecordDao stuDinRecordDao;

    @Resource
    public void setDao(StuDinRecordDao stuDinRecordDao) {
        this.stuDinRecordDao = stuDinRecordDao;
        this.dao = stuDinRecordDao;
    }


    @Override
    /**根据学员ID编号查看学员就餐详情**/
    public List<StuDiningRecord> findList(Integer student) {
        List<StuDiningRecord> list = stuDinRecordDao.findAll(student);
        return list;
    }

    @Override
    /**查看学员就餐统计**/
    public List<StuDiningRecordVO> findSum(String area, Integer classRoom, String className, Integer userId, Integer roleId, String month) {
        List<StuDiningRecordVO> list = stuDinRecordDao.findSum(area, classRoom, className, userId, roleId, month);
        return list;
    }

    @Override
    public List<StuDiningRecordVO> findSum(BTView<StuDiningRecordVO> bt, String area, Integer classRoom, String className,String studentName, String month) {
        return stuDinRecordDao.findSum(bt, area, classRoom, className, studentName, month);
    }

    @Override
    public List<StuDiningRecordVO> findSum(BTView<StuDiningRecordVO> bt, String area, Integer classRoom, String className,String studentName, Integer userId, String month) {
        return stuDinRecordDao.findSum(bt, area, classRoom, className, studentName, userId, month);
    }

    @Override
    public List<StuDiningRecord> findList(BTView<StuDiningRecord> bt, int student,Integer classId) {
        return stuDinRecordDao.findList(bt, student,classId);
    }

    @Override
    public StuDiningRecord findByTime(String time, Integer student, Integer classRoom) {
        return stuDinRecordDao.findByTime(time, student, classRoom);
    }

    @Override
    public List<StuDiningRecord> findCount(String classId, String type) {
        return stuDinRecordDao.findCount(classId, type);
    }

    @Override
    public CmsResult saveData(String time, Student s, StuDiningRecord sdr, ClassInfo c) throws ParseException {
        CmsResult cmsResult = null;
        String time2 = time.substring(0, 10);
        //判断餐点
        String timePD12 = time.substring(11, 13);
        time = time.substring(11);
        String zhtimePD = " 15:00:00";//中餐
        String ztimePD = " 10:00:00";//早餐
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        if ((dateFormat.parse(time).getTime()) >= (dateFormat.parse(zhtimePD).getTime())) {//晚餐
            if (sdr != null) {//该天 此流水号有就餐记录
                if (sdr.getDinner() == 2) {//该时间段 未就餐
                    sdr.setDinner(1);
                    stuDinRecordDao.update(sdr);//按时间段存储就餐信息
                    cmsResult = new CmsResult(ApiCode.SUCCESS);
                } else {
                    cmsResult = new CmsResult(ApiCode.FAILED_limit);//每人每餐只能刷一次
                }
            } else {//该天 此流水号没有就餐记录
                StuDiningRecord sNew = new StuDiningRecord();
                sNew.setDinner(1);
                sNew.setArea(c.getDiningPlace());
                sNew.setBreakfast(2);
                sNew.setLunch(2);
                sNew.setClassRoom(c.getId().intValue());
                sNew.setDay(time2);
                sNew.setStudent(s.getSiId());
                stuDinRecordDao.save(sNew);   //按时间段存储就餐信息
                cmsResult = new CmsResult(ApiCode.SUCCESS);
            }
        } else if ((dateFormat.parse(time).getTime()) >= (dateFormat.parse(ztimePD).getTime()) || timePD12.equals("12")) {//中餐
            if (sdr != null) {//该天 此流水号有就餐记录
                if (sdr.getLunch() == 2) {//该时间段 未就餐
                    sdr.setLunch(1);
                    stuDinRecordDao.update(sdr);//按时间段存储就餐信息
                    cmsResult = new CmsResult(ApiCode.SUCCESS);
                } else {
                    cmsResult = new CmsResult(ApiCode.FAILED_limit);//每人每餐只能刷一次
                }
            } else {//该天 此流水号没有就餐记录
                StuDiningRecord sNew = new StuDiningRecord();
                sNew.setDinner(2);
                sNew.setArea(c.getDiningPlace());
                sNew.setBreakfast(2);
                sNew.setLunch(1);
                sNew.setClassRoom(c.getId().intValue());
                sNew.setDay(time2);
                sNew.setStudent(s.getSiId());
                stuDinRecordDao.save(sNew);   //按时间段存储就餐信息
                cmsResult = new CmsResult(ApiCode.SUCCESS);
            }
        } else {//早餐
            if (sdr != null) {//该天 此流水号有就餐记录
                if (sdr.getBreakfast() == 2) {//该时间段 未就餐
                    sdr.setBreakfast(1);
                    stuDinRecordDao.update(sdr);//按时间段存储就餐信息
                    cmsResult = new CmsResult(ApiCode.SUCCESS);
                } else {
                    cmsResult = new CmsResult(ApiCode.FAILED_limit);//每人每餐只能刷一次
                }
            } else {//该天 此流水号没有就餐记录
                StuDiningRecord sNew = new StuDiningRecord();
                sNew.setDinner(2);
                sNew.setArea(c.getDiningPlace());
                sNew.setBreakfast(1);
                sNew.setLunch(2);
                sNew.setClassRoom(c.getId().intValue());
                sNew.setDay(time2);
                sNew.setStudent(s.getSiId());
                stuDinRecordDao.save(sNew);   //按时间段存储就餐信息
                cmsResult = new CmsResult(ApiCode.SUCCESS);
            }
        }
        return cmsResult;
    }

    @Override
    public StuDiningRecordStatisticsDTO getStuDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        return stuDinRecordDao.getStuDiningRecordStatistics(diningPlace,classId,startTime,endTime);
    }

    @Override
    public StuDiningRecordStatisticsDTO getStuDiningFirstDayRecordStatistics(String diningPlace, String classId) {
        return stuDinRecordDao.getStuDiningFirstDayRecordStatistics(diningPlace,classId);
    }
}
