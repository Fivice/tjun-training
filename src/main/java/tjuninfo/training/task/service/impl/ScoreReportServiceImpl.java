package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ScoreReportDao;
import tjuninfo.training.task.entity.ScoreReport;
import tjuninfo.training.task.service.ScoreReportService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师信息业务层接口实现类
 * @author
 * @date 2018年5月18日
 */
@Service
public class ScoreReportServiceImpl extends BaseServiceImpl<ScoreReport> implements ScoreReportService {
	private ScoreReportDao scoreReportDao;
	@Resource
	public void setSysAuthorityDao(ScoreReportDao scoreReportDao) {
		this.scoreReportDao = scoreReportDao;
		this.dao = scoreReportDao;
	}


	@Override
	public List<ScoreReport> findScoreReportList(String month,String teacherName) {
		return scoreReportDao.findAll(month,teacherName);
	}

	@Override
	public List<ScoreReport> findScoreReportList2(Long classId) {
		return scoreReportDao.findAll2(classId);
	}

	@Override
	public void deleteById(Integer id) {
		scoreReportDao.deleteByPK(id);
	}

	@Override
	public void saveTeacherInfo(ScoreReport scoreReport) {
		scoreReportDao.save(scoreReport);
	}

	@Override
	public ScoreReport selectById(Integer id) {
		return scoreReportDao.get(id);
	}

	@Override
	public List<ScoreReport> list() {
		return scoreReportDao.getAll();
	}

	@Override
	public void deleteByClassId(Long classId) {
		scoreReportDao.deleteByClssId(classId);
	}

	@Override
	public ScoreReport selectScoreReport(String id, String classId) {
		return scoreReportDao.selectScoreReport(id, classId);
	}

}
