package tjuninfo.training.task.dao.impl;



import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IBasicParametersDao;
import tjuninfo.training.task.entity.BasicParameters;


/**
 * 单位表数据层接口实现类
 * @author 
 * @date 2018年5月23日
 */
@Repository
public class BasicParametersDaoImpl extends BaseDaoImpl<BasicParameters> implements IBasicParametersDao {

	public BasicParametersDaoImpl() {
		super(BasicParameters.class);
	}

}