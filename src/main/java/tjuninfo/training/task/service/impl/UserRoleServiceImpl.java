package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IUserRoleDao;
import tjuninfo.training.task.entity.UserRole;
import tjuninfo.training.task.service.IUserRoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户service层接口实现类
 * @author shenxianyan
 * @date 2018年5月17日
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {
	
	private IUserRoleDao userRoleDao;
	@Resource
	public void setUserRoleDao(IUserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
		this.dao = userRoleDao; 
	}
 
	public List<UserRole> getRoleIdByUserId(Integer userId) {
		// TODO Auto-generated method stub
		 List<UserRole> list = userRoleDao.listByUserId(userId);	
	     return list;
	}

	@Override
	public Long getCount(String loginAccount) {
		Long count= userRoleDao.getCount(loginAccount);
		return count;
	}

	@Override
	public UserRole getUserRoleByUserId(Integer userId) {
		UserRole userRole  = userRoleDao.getUserRoleByUserId(userId);
		return userRole;
	}


}
