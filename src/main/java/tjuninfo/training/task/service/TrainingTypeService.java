package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.TrainingType;

import java.util.List;

/*基本培训维护业务层接口*/
public interface TrainingTypeService extends IBaseService<TrainingType> {

    /*查询数据库数据*/
    List<TrainingType> findTrainingTypeList();
    /*分页查找*/
    List<TrainingType> findTrainingTypeList(BTView<TrainingType> bt);


    /*根据id删除*/
    void deleteByNid(int id);

    /*保存*/
    void saveOrUpdate(TrainingType tt);

    /*根据名称查找*/
    public List<TrainingType> findListByTypeName(String typeName);

    /*根据l类型查找*/
    List<TrainingType> checkType(String type);
}
