package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.dto.ClassInfoForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.ClassVo;

import java.util.List;

/**
 * 学生信息表数据层接口
 * @author CJ
 * @date 2018年9月19日
 */
public interface ClassInfoDao extends IBaseDao<ClassInfo> {
	//分页查询
	public Pages getList(int pageSize, int pageIndex,
						 String plan,String classNumber,String startStopTimer,
						                                    //教师id                    //报名地点      //排序       //报名时间
						 String className,String teacherName,String userId,String time,String regPlace,String order,String entryStartTime,String evaluateStopTime,String sort, String sortOrder);
	//分页查询
	public List<ClassInfo> getClassInfoList(String plan,String classNumber,String startStopTime,
						                                    //教师id                    //报名地点
						 String className,String teacherName,String userId,String time,String regPlace);

	//根据班级编号查询班级信息
	public ClassInfo getByclassNumber(String classNumber,String classInfoId);

	public ClassInfo getByclassNumber(String classNumber);

	public List<ClassInfo> getByUserId(Integer userId);

	public List<ClassInfo> list();

	List<ClassInfo> findList(Integer siId);//查询学员培训记录信息

	List<ClassVo> findList(BTView<ClassVo> bt, Integer siId);


	//根据日期,培训类型，计划内外查询班级列表
	public List<ClassInfo> findClassInfosByDate(String date,String type,String plan);


	//根据开班时间和结束时间查找班级
//    List<ClassInfo> findAllByStartStopTime(String startDay, String endDay);

	Pages findAllByStartStopTime(int pageSize, int pageIndex,String startDay, String endDay, String classNumber, String className, String trainingType, String plan, Integer useId,String place);

	/**
	 * 根据条件查询班级列表
	 * @param pageSize
	 * @param pageIndex
	 * @param startStopTime
	 * @param classNumber
	 * @param className
	 * @param trainingType
	 * @param plan
	 * @param useId
	 * @param place
	 * @return
	 */
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

	/**
	 * 通过时间段和就餐地点查询：在此地点就餐的班级和与时间有交集的班级
	 * @param startStopTime
	 * @param diningPlace
	 * @return
	 */
	Page<ClassInfoForDiningStatisticsDTO> getClassInfo(int pageIndex, int pageSize, String startStopTime, String diningPlace,Integer userId);

	//查询备餐
	Page<ClassInfoForDiningStatisticsDTO>  findPrepList(int pageSize, int pageIndex, String today, String diningPlace);

	//查询班级编号最大值
	public String findMaxClassNumber();


	//分页查询教师就餐班级概览
	public Pages getTeachDinProfile(int pageSize, int pageIndex, String classNumber,
							  String startStopTime, String className, String teacherName,
							  String userId,String orderName,String orderBy);


	//根据班级id查询班级信息
	public ClassInfo findClassInfoByClassId(String classId);

}

	
	
