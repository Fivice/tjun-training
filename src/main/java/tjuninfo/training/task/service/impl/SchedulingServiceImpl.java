package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.SchedulingDao;
import tjuninfo.training.task.entity.Scheduling;
import tjuninfo.training.task.service.SchedulingService;
import tjuninfo.training.task.util.Pages;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师信息业务层接口实现类
 * @author
 * @date 2018年5月18日
 */
@Service
public class SchedulingServiceImpl extends BaseServiceImpl<Scheduling> implements SchedulingService {
	private SchedulingDao schedulingDao;
	@Resource
	public void setSysAuthorityDao(SchedulingDao schedulingDao) {
		this.schedulingDao = schedulingDao;
		this.dao = schedulingDao;
	}


	@Override
	public List<Scheduling> list() {
		return schedulingDao.getAll();
	}

	@Override
	public List<Scheduling> findSchedulingList(String day,String className,String id) {
		return schedulingDao.findAll(day,className,id);
	}

	@Override
	public void deleteById(Long id) {
		schedulingDao.deleteByPK(id);
	}

	@Override
	public void saveScheduling(Scheduling scheduling) {
		schedulingDao.save(scheduling);
	}

	@Override
	public Scheduling selectById(Long id) {
		return schedulingDao.get(id);
	}

	@Override
	public Pages findSchList(int pageSize, int pageIndex, String day, String className, String id) {
		return schedulingDao.findSchList(pageSize,pageIndex,day,className,id);
	}
	@Override
	public List<Scheduling> findEvaSchList(String id,int evaluate) {
		return schedulingDao.findEvaSchList(id,evaluate);
	}

	@Override
	public List<String> findList(String id) {
		return schedulingDao.findList(id);
	}


}
