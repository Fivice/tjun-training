package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IBasicParametersDao;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.service.IBasicParametersService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 院校基本参数表业务层接口实现类1
 * @author
 */
@Service
public class BasicParametersServiceImpl extends BaseServiceImpl<BasicParameters> implements IBasicParametersService{
    private IBasicParametersDao basicParametersDao;
    @Resource
    public void setSysAuthorityDao(IBasicParametersDao basicParametersDao) {
        this.basicParametersDao = basicParametersDao;
        this.dao = basicParametersDao;
    }

    @Override
    public List<BasicParameters> list() {
        return basicParametersDao.getAll();
    }
}