package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.entity.Unit;

import java.util.List;


/**
 * 院校基本参数表业务层接口1
 * @author 
 */
public interface  IBasicParametersService extends IBaseService<BasicParameters>{


    List<BasicParameters> list();
}
