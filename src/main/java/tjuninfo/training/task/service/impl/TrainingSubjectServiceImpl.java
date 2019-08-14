package tjuninfo.training.task.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.impl.TrainingSubjectDaoImpl;
import tjuninfo.training.task.entity.TrainingSubject;
import tjuninfo.training.task.service.TrainingSubjectService;

import java.util.List;

/**
 * 教师信息业务层接口实现类
 * @author
 * @date 2018年5月18日
 */
@Service
public class TrainingSubjectServiceImpl extends BaseServiceImpl<TrainingSubject> implements TrainingSubjectService {
	@Autowired
	private TrainingSubjectDaoImpl trainingSubjectDao;


	@Override
	public List<TrainingSubject> findTsList() {
		return trainingSubjectDao.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		trainingSubjectDao.deleteByPK(id);
	}

	@Override
	public void saveTeacherInfo(TrainingSubject ts) {
		trainingSubjectDao.save(ts);
	}

	@Override
	public TrainingSubject selectById(Integer id) {
		return trainingSubjectDao.get(id);
	}

	@Override
	public void updateTeacherInfo(TrainingSubject ts) {
		TrainingSubject updateTs = trainingSubjectDao.get(ts.getTsId());
		updateTs.setTsName(ts.getTsName());


	}
}
