package tjuninfo.training.task.service.impl;


import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.StudentDao;
import tjuninfo.training.task.entity.Student;
import tjuninfo.training.task.util.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 学生信息表业务层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl<Student> implements tjuninfo.training.task.service.StudentService {
	private StudentDao studentDao;
	@Resource
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
		this.dao = studentDao;
	}


	@Override
	public Page queryForPage(int currentPage, int pageSize, Student student) {
		Page page = new Page();
		//当前页开始记录
		int offset = page.countOffset(currentPage,pageSize);
		//分页查询结果集
		List<Student> list = studentDao.queryForPage(offset, pageSize,student);
		//总记录数
		/*int allRow = studentDao.queryForPage(0, 0,student).size();*/
		int allRow = studentDao.queryForPage(0, 0,student).size();

		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(allRow);
		page.setList(list);

		return page;
	}

	@Override
	public Pages getList(int pageSize, int pageIndex,String unitId,String unitName,String siIDNumber) {
		return studentDao.getList(pageSize,pageIndex,unitId,unitName,siIDNumber);
	}

	@Override
	public Student findByNumber(String siIDNumber) {
		return studentDao.findByNumber(siIDNumber);
	}

	@Override
	public Student getBysiIDNumber(String siIDNumber, String siId) {
		return studentDao.getBysiIDNumber(siIDNumber,siId);
	}

	@Override
	public String uploadFiles(MultipartFile file,String path) {
		String url="";
//		String path="/upload/studentInfo/";
		String uploadPath=path;
		File up = new File(uploadPath);
		if (!up.exists()) {
			up.mkdirs();
		}
		/*String fileName=file.getOriginalFilename();*/
		String ext = StringUtils.getExt(file.getOriginalFilename());
		String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat(RandomUtils.getRandom(6)).concat(".").concat(ext);
		url = path+fileName;
		File dfile = new File(url);
		try {
			file.transferTo(dfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fName=path+fileName;
		return fName;
	}

	@Override
	public boolean ifExist(String time, String idCard,Integer dinner) {
		return studentDao.ifExist(time,idCard,dinner);
	}

	@Override
	public boolean ifExist2(String idCard) {
		return studentDao.ifExist2(idCard);
	}

	@Override
	public Student findStudentById(String id) {
		return studentDao.findStudentById(id);
	}
}
