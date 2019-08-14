package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.TrainingTypeDao;
import tjuninfo.training.task.entity.TeachDining;
import tjuninfo.training.task.entity.TrainingType;
import tjuninfo.training.task.service.TrainingTypeService;

import javax.annotation.Resource;
import java.util.List;

/*基本培训维护业务层接口实现类*/
@Service
public class TrainingTypeServiceImpl extends BaseServiceImpl<TrainingType> implements TrainingTypeService {

    private TrainingTypeDao trainingTypeDao;

    @Resource
    public void setTrainingTypeDao(TrainingTypeDao trainingTypeDao) {
        this.trainingTypeDao = trainingTypeDao;
        this.dao = trainingTypeDao;
    }

    /*查询数据库数据*/
    public List<TrainingType> findTrainingTypeList() {
        List<TrainingType> list = trainingTypeDao.findAll();
        return list;
    }

    @Override
    public List<TrainingType> findTrainingTypeList(BTView<TrainingType> bt) {
        return trainingTypeDao.findTrainingTypeList(bt);
    }

    /*根据id删除*/
    @Override
    public void deleteByNid(int id) {
        trainingTypeDao.deleteByNid(id);
    }

    /*保存*/
    @Override
    public void saveOrUpdate(TrainingType tt) {
        trainingTypeDao.saveOrUpdate(tt);
    }

    @Override
    public List<TrainingType> findListByTypeName(String typeName) {
        return trainingTypeDao.findListByTypeName(typeName);
    }

    @Override
    public List<TrainingType> checkType(String type) {
        return trainingTypeDao.checkType(type);
    }


}
