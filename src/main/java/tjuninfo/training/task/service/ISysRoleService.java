package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.SysRole;

import java.util.List;

/**
 * 角色业务层接口
 * @author shenxianyan
 * @date 2018年5月23日
 */
public interface ISysRoleService extends IBaseService<SysRole> {
	/**
	 * 获取系统目录列表
	 * @return list
	 */
	List<SysRole> list();
	/**
	 * 根据ID查询角色
	 * @param roleId
	 * @return
	 */
	public SysRole get(Long roleId);
	/**
	 * 分页查询角色列表
	 * @param bt
	 * @param param
	 * @return
	 */
	public List<SysRole> findSysRolePage(BTView<SysRole> bt,Object[] param);
	/**
	 * 更新角色
	 */
	public void updateRole(SysRole s);
	
	 void saveOrUpdate(SysRole s);
	 /**
	  * 通过roleId删除
	  * @param roleId
	  */
	void deleteByRoleId(Long roleId);

	//根据角色名称获取角色
	List<SysRole> getUsers(String roleName);

	//根据级别名称和级别id查询级别信息
	public SysRole getByRoleValue(String roleValue, String roleId);
	
	
}
