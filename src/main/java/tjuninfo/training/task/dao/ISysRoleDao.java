package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.SysRole;

import java.util.List;
/**
 * 角色数据层接口
 * @author 
 * @date 2018年5月16日
 */
public interface ISysRoleDao extends IBaseDao<SysRole>{
	
	/**
	 * 查询所有角色
	 * @return List<SysRole>
	 */
	public List<SysRole> findAll();
	/**
	 * 分页查询角色
	 * @return
	 */
	public List<SysRole> getinfo(BTView<SysRole> bt) ;
	
	public void deleteByRoleId(Long roleId);
	//根据角色名称获取角色
	List<SysRole> getUsers(String roleName);
	//根据级别名称和级别id查询教师信息
	public SysRole getByRoleValue(String roleValue, String roleId);

}
