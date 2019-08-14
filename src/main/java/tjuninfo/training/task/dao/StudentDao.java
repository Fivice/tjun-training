package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.Student;
import tjuninfo.training.task.util.Pages;

import java.util.List;

/**
 * 学生信息表数据层接口
 * @author CJ
 * @date 2018年9月19日
 */
public interface StudentDao extends IBaseDao<Student> {
	//分页查询
	public List<Student> queryForPage(int currentPage, int pageSize, Student student);
	public Pages getList(int pageSize, int pageIndex,String unitId,String unitName,String siIDNumber);

	//根据身份证查询学生
	public Student findByNumber(String siIDNumber);

	//根据身份证和学生id查询学生信息
	public Student getBysiIDNumber(String siIDNumber,String siId);

	/**
	 * 查询当天有就餐安排的学生，并判断idCard是否在其中
	 * @param time
	 * @param idCard
	 * @param dinner
	 * @return
	 */
	boolean ifExist(String time,String idCard,Integer dinner);
	/**
	 * 查询是否是学生
	 * @param idCard
	 * @return
	 */
	boolean ifExist2(String idCard);

	public Student findStudentById(String id);
}

	
	
