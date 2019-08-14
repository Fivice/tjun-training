package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.dto.SysUserDto;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.vo.SysUserVo;

import java.util.List;

/**
 * 用户service层接口
 * @author shenxianyan
 * @date 2018年5月23日
 */
public interface ISysUserService extends IBaseService<SysUser> {
	/**
	 * 分页查询
//	 * @param hql
	 * @param bt
	 * @param param
	 * @return
	 */
	public List<SysUserVo> findSysUserPage(BTView<SysUser> bt,Object[] param);
	/**
	 * 更新用户
	 * @param user
	 */
	public void updateUser(SysUser user);
	/**
	 * 分页
	 * @param bt
	 * @param userId
	 * @param param
	 * @return
	 */
	public List<SysUser> transitionSysUserState(BTView<SysUser> bt,Integer userId,Object[] param);
	public void updateRoles(Integer userId, String roleId);
	
	public SysUser findUser(Integer userId);

	public List<SysUser> findUserList();
	//根据登录账号，真实姓名，部门名称查找用户，且展示以注册时间倒叙展示
    List<SysUser> getUserList(String loginAccount, String userName,String departmentName);
	//根据角色id查询用户
	public List<SysUserDto> findByRoleId(Integer roleId);

    Pages getUserList(int pageSize, int pageNumber, String loginAccount, String userName, String departmentName);
	//根据真实姓名查询用户集合
	List<SysUser> findUserListByName(String userName);
}
