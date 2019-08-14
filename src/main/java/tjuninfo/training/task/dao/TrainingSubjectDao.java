package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.TrainingSubject;

import java.util.List;

/**
 * 培训科目数据层接口
 * @author 
 * @date 2018年5月16日
 */
public interface TrainingSubjectDao extends IBaseDao<TrainingSubject>{

	/**
	 * 分页查询培训科目
	 * @return
	 */
	List<TrainingSubject> findAll();

}
