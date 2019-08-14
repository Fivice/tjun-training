package tjuninfo.training.task.service;

import java.util.List;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.dto.RoleMenuDTO;
import tjuninfo.training.task.entity.RoleMenu;
import tjuninfo.training.task.vo.RoleMenuVO;
import tjuninfo.training.task.vo.RoleMenuVo2;


/**
 * 角色目录关联表 业务逻辑层接口  
 * @author shenxianyan
 * @date 2018年5月28日
 */
public interface IRoleMenuService extends IBaseService<RoleMenu> {
	/**
	 * 根据管理员ID查找系统目录
	 * @param userId 管理员ID
	 * @param status 状态
	 * @return List<RoleMenuVO>
	 */
	List<RoleMenuVO> listByUserId(Integer userId);
	/**
	 * 根据管理员ID查找系统第三级目录
	 * @param userId 管理员ID
	 * @param status 状态
	 * @return List<RoleMenuDTO>
	 */
	List<RoleMenuDTO> listByUserId1(Integer userId);
	/**
	 * 根据ID查询RoleMenu对象
	 * @param roleId
	 * @return
	 */
	public void doDel(Long roleId);
	/**
	  * 通过roleId删除
	  * @param roleMenuId
	  */
	
	void add(RoleMenu roleMenu);
	
	public List<RoleMenuVo2> findMenuIdByRoleId(Long roleId);
	
	
}
