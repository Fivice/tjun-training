package tjuninfo.training.task.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.DepartmentDao;
import tjuninfo.training.task.dao.ISysRoleDao;
import tjuninfo.training.task.dao.ISysUserDao;
import tjuninfo.training.task.dao.IUserRoleDao;
import tjuninfo.training.task.dto.SysUserDto;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.entity.SysRole;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.service.ISysUserService;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.DepartmentVo;
import tjuninfo.training.task.vo.SysUserVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户service层接口实现类
 * @author shenxianyan
 * @date 2018年5月17日
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements ISysUserService {
	
	private ISysUserDao sysUserDao;
	@Resource
	public void setSysUserDao(ISysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
		this.dao = sysUserDao; 
	}
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private IUserRoleDao userRoleDao;
	@Autowired
	private ISysRoleDao roleDao;
	@Override
	public List<SysUserVo> findSysUserPage(BTView<SysUser> bt, Object[] param) {
		List<SysUser> ulist=sysUserDao.getinfo(bt);
		List<SysUserVo> list = new ArrayList<>(); 
		for(SysUser u :ulist) {
			SysUserVo uVo = new SysUserVo();
			//级别
				String rolename = null;
				Long roleId = userRoleDao.getByUserId(u.getUserId());
				if(roleId!=null) {
					SysRole role = roleDao.get(roleId);
					rolename = role.getRoleValue();
				}
			//部门
			try {
				Department d=u.getDepartment();
				DepartmentVo dvo=new DepartmentVo();
				if(d!=null) {
					dvo.setAreaId(d.getAreaId());
					String areaName = d.getAreaName();
					if(d.getSjareaId()!=null&&d.getSjareaId()!=1) {
						Department sjd =departmentDao.get(d.getSjareaId());
						areaName = areaName.concat(" / "+sjd.getAreaName());
					}
					dvo.setAreaName(areaName);
					uVo.setDepartmentVo(dvo);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			uVo.setUserId(u.getUserId());
			uVo.setRole(rolename);
			uVo.setLoginAccount(u.getLoginAccount());
			uVo.setRegisterTime(u.getRegisterTime());
			uVo.setState(u.getState());
			uVo.setBirthday(u.getBirthday());
			uVo.setSex(u.getSex());
			uVo.setTelephone(u.getTelephone());
			uVo.setUserName(u.getUserName());
			uVo.setEmail(u.getEmail());
			uVo.setUserType(u.getUserType());
			list.add(uVo);
		}
		return list;
	}
	
	public List<SysUser> transitionSysUserState(BTView<SysUser> bt, Integer userId , Object[] param){
		String hql = "from SysUser";
		Map<String, String> orderMap = null;
		if(bt.getSortName()!=null && !bt.getSortName().equals("")){
			orderMap = new HashMap<String, String>();
			orderMap.put(bt.getSortName(), bt.getSortOrder());
		}
		sysUserDao.transitionStateByUserId(userId,param,orderMap);
		return sysUserDao.findByPage(hql, bt, param, orderMap);
	}
	@Override
	public void updateUser(SysUser user) {
		SysUser updateUser = sysUserDao.get(user.getUserId());
		updateUser.setLoginAccount(user.getLoginAccount());
		updateUser.setState(user.getState());
	}
	
	 
	public void updateRoles(Integer userId, String roleId) {
		
		  sysUserDao.updateUserRoles(userId,roleId);
		  
	}
	@Override
	public SysUser findUser(Integer userId) {
		return sysUserDao.get(userId);
	}

	@Override
	public List<SysUser> findUserList() {
		return sysUserDao.findUserList();
	}

	@Override
	public List<SysUser> getUserList(String loginAccount, String userName,String departmentName) {
		return sysUserDao.getUserList(loginAccount,userName,departmentName);
	}

	@Override
	public List<SysUserDto> findByRoleId(Integer roleId) {
		return sysUserDao.findByRoleId(roleId);
	}

	@Override
	public Pages getUserList(int pageSize, int pageNumber, String loginAccount, String userName, String departmentName) {
		return sysUserDao.getUserList(pageSize,pageNumber,loginAccount,userName,departmentName);
	}

	@Override
	public List<SysUser> findUserListByName(String userName) {
		return sysUserDao.findUserListByName(userName);
	}


}
