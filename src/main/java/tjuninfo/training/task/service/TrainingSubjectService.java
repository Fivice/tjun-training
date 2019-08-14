package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.TrainingSubject;

import java.util.List;

/**
 * 培训科目业务层接口
 * @author
 * @date 2018年5月17日
 */
public interface TrainingSubjectService extends IBaseService<TrainingSubject>{
    //查询所有列表
    public List<TrainingSubject> findTsList();

    //根据id删除数据
    public  void deleteById(Integer id);

    //添加数据
    public  void saveTeacherInfo(TrainingSubject ts);

    //根据id查询数据
    public  TrainingSubject selectById(Integer ts);

    //更新数据
    public void updateTeacherInfo(TrainingSubject ts);
}
