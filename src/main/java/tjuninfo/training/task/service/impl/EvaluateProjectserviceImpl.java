package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.EvaluateProjectDao;
import tjuninfo.training.task.entity.EvaluateProject;
import tjuninfo.training.task.service.EvaluateProjectService;
import javax.annotation.Resource;
import java.util.List;

/**
 * 评价项目维护业务层接口实现类
 */
@Service
public class EvaluateProjectserviceImpl extends BaseServiceImpl<EvaluateProject> implements EvaluateProjectService {

    private EvaluateProjectDao EvaluateProjectDao;
    @Resource
    public void setEvaluateProjectDao(EvaluateProjectDao EvaluateProjectDao) {
        this.EvaluateProjectDao = EvaluateProjectDao;
        this.dao = EvaluateProjectDao;
    }

    @Override
    /**
     * 根据项目选项来查找
     */
    public List findEvaluateProjectList(Integer largeClass) {
        List<EvaluateProject> list = EvaluateProjectDao.findAll(largeClass);
        return list;
    }

    @Override
    public List<EvaluateProject> findEvaluateProjectList(BTView<EvaluateProject> bt, Integer largeClass) {
        return EvaluateProjectDao.findEvaluateProjectList(bt,largeClass);
    }

    @Override
    /**
     * 保存信息
     */
    public void saveOrUpdate(EvaluateProject ep) {
        EvaluateProjectDao.saveOrUpdate(ep);
    }

    @Override
    /**
     * 根据ID删除信息
     */
    public void deleteByNid(int id) {
        EvaluateProjectDao.deleteByNid(id);
    }

    @Override
    public List<EvaluateProject> findAll() {
        List<EvaluateProject> list = EvaluateProjectDao.getAll();
        return list;
    }
}
