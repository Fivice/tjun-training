package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.TeacherInfo;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 教师信息数据层接口
 * @author 
 * @date 2018年5月16日
 */
public interface TeacherInfoDao extends IBaseDao<TeacherInfo>{

	/**
	 * 分页查询教师信息
	 * @return
	 */
	Pages findAll(int pageSize, int pageIndex, String subject, String tiName, String tiDepartment);

	/**
	 * 获取当天有就餐安排的教师
	 * @return
	 */
	List<TeacherInfo> getTodayDining();

	//根据身份证和教师id查询教师信息
	public TeacherInfo getBysiIDNumber(String siIDNumber, String tiId);

	/**
	 * 查询当天有就餐安排的教师，并判断idCard是否在其中
	 * @param time
	 * @param idCard
	 * @return
	 */
	boolean ifExist(String time,String idCard,Integer dinner);
	/**
	 * 查询是否是教师
	 * @param idCard
	 * @return
	 */
	boolean ifExist2(String idCard);
}
