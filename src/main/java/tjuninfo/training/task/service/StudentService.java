package tjuninfo.training.task.service;

import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.Student;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.Pages;


/**
 * 学生信息表业务层接口
 * @author CJ
 * @date 2018年9月19日
 */
public interface StudentService extends IBaseService<Student>{
	//分页查询
	public Page queryForPage(int currentPage, int pageSize, Student student);
	public Pages getList(int pageSize, int pageIndex,String unitId,String unitName,String siIDNumber);
	public Student findByNumber(String siIDNumber);

	//根据身份证和学生id查询学生信息
	public Student getBysiIDNumber(String siIDNumber,String siId);

	/**
	 * 文件上传
	 * @param file
	 * @param path
	 * @return
	 */
	String uploadFiles (MultipartFile file,String path);
	/**
	 * 查询当天有就餐安排的学生，并判断idCard是否在其中
	 * @param time
	 * @param idCard
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
