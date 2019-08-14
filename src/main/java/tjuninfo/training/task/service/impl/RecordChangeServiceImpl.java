package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ClassInfoDao;
import tjuninfo.training.task.dao.RecordChangeDao;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.RecordChange;
import tjuninfo.training.task.service.ClassInfoService;
import tjuninfo.training.task.service.RecordChangeService;
import tjuninfo.training.task.util.Pages;

import javax.annotation.Resource;
import java.util.List;

/**
 * 班级信息表业务层接口实现类
 * @author CJ
 * @date 2018年9月19日
 */
@Service
public class RecordChangeServiceImpl extends BaseServiceImpl<RecordChange> implements RecordChangeService {
	private RecordChangeDao recordChangeDao;
	@Resource
	public void setSysAuthorityDao(RecordChangeDao recordChangeDao) {
		this.recordChangeDao = recordChangeDao;
		this.dao = recordChangeDao;
	}


	@Override
	public List<RecordChange> list(Long classId) {
		return recordChangeDao.findAll(classId);
	}

}
