package tjuninfo.training.task.dao;

import java.util.List;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.dto.RoleMenuDTO;
import tjuninfo.training.task.entity.RoleMenu;
import tjuninfo.training.task.vo.RoleMenuVo2;
/**
 * 角色数据层接口
 * @author shenxianyan
 * @date 2018年5月16日
 */
public interface IRoleMenuDao extends IBaseDao<RoleMenu>{
	/**
	 * 通过用户id查找菜单
	 * @param userId
	 * @return List<Menu>
	 */
	public List<RoleMenuDTO> findMeunByUserId(Integer userId);
	
	public List<RoleMenuDTO> findMeunByUserId1(Integer userId);
	
	public void doDel(Long roleId);
	public List<RoleMenuVo2> findMenuIdByRoleId(Long roleId);
	
}
