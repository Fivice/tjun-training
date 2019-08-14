package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.ScoreReport;

import java.util.List;

/**
 * 教师信息数据层接口
 * @author 
 * @date 2018年5月16日
 */
public interface ScoreReportDao extends IBaseDao<ScoreReport>{

	/**
	 * 分页查询教师信息
	 * @return
	 */
	List<ScoreReport> findAll(String month,String teacherName);

    List<ScoreReport> findAll2(Long classId);

    void deleteByClssId(Long classId);

	ScoreReport selectScoreReport(String id,String classId);
}
