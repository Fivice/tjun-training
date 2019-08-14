package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.EvaluateProject;
import java.util.List;

/**
 * 评价项目维护业务层接口
 */
public interface EvaluateProjectService extends IBaseService<EvaluateProject>{

 /**
  * 根据项目选项来查找
  */
 List findEvaluateProjectList(Integer largeClass);

 /**分页*/
 List<EvaluateProject> findEvaluateProjectList(BTView<EvaluateProject> bt, Integer largeClass);

 /**
  * 保存信息
  */
 void saveOrUpdate(EvaluateProject ep);

 /**
  * 根据ID删除信息
  */
 void deleteByNid(int id);

 List<EvaluateProject> findAll();

}
