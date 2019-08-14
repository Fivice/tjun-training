package tjuninfo.training.task.service.impl;


import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.ISysRoleDao;
import tjuninfo.training.task.entity.SysRole;
import tjuninfo.training.task.service.ISysRoleService;

import javax.annotation.Resource;
import java.util.List;
/**
 * 角色业务层接口实现类
 * @author shenxianyan
 * @date 2018年5月23日
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements ISysRoleService {
	private ISysRoleDao sysRoleDao;
	@Resource
	public void setemployeeDao(ISysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
		this.dao = sysRoleDao; 
	}
	
	@Override
	public List<SysRole> list() {
		List<SysRole> list = sysRoleDao.findAll();
		return list;
	}

	@Override
	public SysRole get(Long roleId) {
		// TODO Auto-generated method stub
		return sysRoleDao.get(roleId);
	}

	@Override
	public List<SysRole> findSysRolePage(BTView<SysRole> bt, Object[] param) {
		List<SysRole> list=sysRoleDao.getinfo(bt);
		return list;
	}

	@Override
	public void saveOrUpdate(SysRole s) {
		sysRoleDao.saveOrUpdate(s);
		
	}

	@Override
	public void updateRole(SysRole s) {
		SysRole updateSysRole = sysRoleDao.get(s.getRoleId());
		updateSysRole.setRoleValue(s.getRoleValue());
//		updateSysRole.setRoleKey(s.getRoleKey());
		updateSysRole.setDescription(s.getDescription());
		
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		sysRoleDao.deleteByRoleId(roleId);
		
	}

	@Override
	public List<SysRole> getUsers(String roleName) {
		return sysRoleDao.getUsers(roleName);
	}

	@Override
	public SysRole getByRoleValue(String roleValue, String roleId) {
		return sysRoleDao.getByRoleValue(roleValue,roleId);
	}


}
