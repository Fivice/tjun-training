package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.EvaluateProject;
import java.util.List;

/**
 * 评价项目维护数据层接口
 */
public interface EvaluateProjectDao extends IBaseDao<EvaluateProject> {
     /*根据项目选项来查找*/
    List<EvaluateProject> findAll(Integer largeClass);

    /*根据id删除*/
    void deleteByNid(int id);

    List<EvaluateProject> findEvaluateProjectList(BTView<EvaluateProject> bt, Integer largeClass);

}
