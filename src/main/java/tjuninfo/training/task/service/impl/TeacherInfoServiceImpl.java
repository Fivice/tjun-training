package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.TeacherInfoDao;
import tjuninfo.training.task.entity.TeacherInfo;
import tjuninfo.training.task.service.TeacherInfoService;
import tjuninfo.training.task.util.Pages;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师信息业务层接口实现类
 * @author
 * @date 2018年5月18日
 */
@Service
public class TeacherInfoServiceImpl extends BaseServiceImpl<TeacherInfo> implements TeacherInfoService {
	private TeacherInfoDao teacherInfoDao;
	@Resource
	public void setSysAuthorityDao(TeacherInfoDao teacherInfoDao) {
		this.teacherInfoDao = teacherInfoDao;
		this.dao = teacherInfoDao;
	}


	@Override
	public Pages findTeacherInfoList(int pageSize, int pageIndex, String subject, String tiName, String tiDepartment) {
		return teacherInfoDao.findAll(pageSize,pageIndex,subject,tiName,tiDepartment);
	}

	@Override
	public void deleteById(Integer id) {
		teacherInfoDao.deleteByPK(id);
	}

	@Override
	public void saveTeacherInfo(TeacherInfo accessConCheck) {
		teacherInfoDao.save(accessConCheck);
	}

	@Override
	public TeacherInfo selectById(Integer id) {
		return teacherInfoDao.get(id);
	}

	@Override
	public List<TeacherInfo> list() {
		return teacherInfoDao.getAll();
	}

	@Override
	public List<TeacherInfo> getTodayDining() {

		return teacherInfoDao.getTodayDining();
	}

	@Override
	public TeacherInfo getBysiIDNumber(String siIDNumber, String tiId) {
		return teacherInfoDao.getBysiIDNumber(siIDNumber,tiId);
	}

	@Override
	public boolean ifExist(String time, String idCard,Integer dinner) {
		return teacherInfoDao.ifExist(time,idCard,dinner);
	}

	@Override
	public boolean ifExist2(String idCard) {
		return teacherInfoDao.ifExist2(idCard);
	}

}
