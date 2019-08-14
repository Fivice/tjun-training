package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.ClassInfoForDiningPrepVO;
import tjuninfo.training.task.vo.ClassInfoForDiningStatisticsVO;
import tjuninfo.training.task.vo.ClassInfoForTeaDinListVo;
import tjuninfo.training.task.vo.ClassVo;

import java.util.List;


/**
 * 学生信息表业务层接口
 * @author CJ
 * @date 2018年9月19日
 */
public interface ClassInfoService extends IBaseService<ClassInfo>{
	//分页查询
	public Pages getList(int pageSize, int pageIndex,String plan,String classNumber,
						 String startStopTime,String className,String teacherName,String userId,String time,String regPlace,String order,String entryStartTime,String evaluateStopTime,String sort, String sortOrder);

    /**
     * 查询获取正在开班的班级信息
     * @param pageSize
     * @param pageIndex
     * @param plan
     * @param classNumber
     * @param startStopTime
     * @param className
     * @param teacherName
     * @param userId
     * @param regPlace
     * @return
     */
	public List<ClassInfo> getClassInfoList(int pageSize, int pageIndex,String plan,String classNumber,
                         String startStopTime,String className,String teacherName,String userId,String time,String regPlace);

    /**
     * 转换List到Page
     * @param pageSize
     * @param pageIndex
     * @param list
     * @return
     */
	public Page<ClassInfo> transListToPage(int pageSize,int pageIndex,List list);
	//根据班级编号查询班级信息
	public ClassInfo getByclassNumber(String classNumber,String classInfoId);
	public ClassInfo getByclassNumber(String classNumber);
    public List<ClassInfo> list();
	public List<ClassInfo> getByUserId(Integer userId);


	//根据日期,培训类型，计划内外查询班级列表
	public List<ClassInfo> findClassInfosByDate(String date,String type,String plan);

	List<ClassInfo> findList(Integer siId);//查询学员培训记录信息

	List<ClassVo> findList(BTView<ClassVo> bt, Integer siId);//分页；根据学员ID查找培训记录

	List<ClassInfo> findAll();

//    List<ClassInfo> findAllByStartStopTime(String startDay, String endDay,String );

	//分页
	Pages findAllByStartStopTime(int pageSize, int pageIndex,String startDay, String endDay, String classNumber, String className, String trainingType, String plan,Integer useId,String place);

	Pages classList(int pageSize, int pageIndex,String startStopTime, String classNumber, String className, String trainingType, String plan, Integer useId,String place);
	/**
	 * 根据时间和学员id拿到学员所在班级id
	 * @param siId
	 * @param time
	 * @return
	 */
	ClassInfo getClassIfo(Integer siId,String time);
	//根据食堂名称到班级信息表中查找所有班级的信息
	List<ClassInfo> byDiningPlace(String eatPlace);

	public Page<ClassInfoForDiningStatisticsVO> getClassInfo(int pageIndex, int pageSize, String startStopTime, String diningPlace,Integer userId);

	//分页查询备餐
	public Page<ClassInfoForDiningPrepVO>  findPrepList(int pageSize, int pageIndex, String today, String className);

	//查询班级编号最大值
	public String findMaxClassNumber();

	//根据班级id查询班级信息
	public ClassInfo findClassInfoByClassId(String classId);

	//分页查询教师就餐班级概览
	public ClassInfoForTeaDinListVo getTeachDinProfile(int pageSize, int pageIndex, String classNumber,
													   String startStopTime, String className, String teacherName,
													   String userId, String orderName, String orderBy);
	//判断是否存在一条记录
	Boolean  ifExest (Long id);
}
