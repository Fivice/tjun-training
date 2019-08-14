package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.TrainingType;

import java.util.List;

/*基本培训维护数据层接口*/
public interface TrainingTypeDao extends IBaseDao<TrainingType>{

    /*查找*/
    public List<TrainingType> findAll();

    List<TrainingType> findTrainingTypeList(BTView<TrainingType> bt);

    /*删除*/
    public void deleteByNid(int id);

    /*根据名称查找*/
    public List<TrainingType> findListByTypeName(String typeName);

    List<TrainingType> checkType(String type);
}
