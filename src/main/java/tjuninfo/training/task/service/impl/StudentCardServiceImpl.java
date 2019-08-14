package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.StudentCardDao;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.StudentCard;
import tjuninfo.training.task.service.StudentCardService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学生信息表业务层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Service
public class StudentCardServiceImpl extends BaseServiceImpl<StudentCard> implements StudentCardService {
	private StudentCardDao studentCardDao;
	@Resource
	public void setSysAuthorityDao(StudentCardDao studentCardDao) {
		this.studentCardDao = studentCardDao;
		this.dao = studentCardDao;
	}
	@Override
	public List<StudentCard> list() {
		return studentCardDao.list();
	}

	@Override
	public List<StudentCard> findListBy(String number) {
		return studentCardDao.findListBy(number);
	}

	@Override
	public List<StudentCard> findByStudentIdAndRegisterTime(String siId, String registerTime) {
		return studentCardDao.findByStudentIdAndRegisterTime(siId,registerTime);
	}


}
