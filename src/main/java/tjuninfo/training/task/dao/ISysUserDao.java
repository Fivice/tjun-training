package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.dto.SysUserDto;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.util.Pages;

import java.util.List;
import java.util.Map;

/**
 * 用户数据层接口
 * @author shenxianyan
 * @date 2018年5月16日
 */
public interface ISysUserDao extends IBaseDao<SysUser>{
	
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public SysUser getLoginUser(SysUser user);
	
	/**
	 * 转换状态 启用和禁用
//	 * @param conditionName		WHERE子句条件的属性数组名称
//	 * @param conditionValue	WHERE子句条件的属性数组值
//	 * @param propertyName		UPDATE子句属性数组名称
//	 * @param propertyValue		UPDATE子句属性数组值
	 */
	public void transitionStateByUserId( Integer userId, Object[] param,Map<String, String> orderMap);
	
	/**
	 * 分页
	 * @return
	 */
	public List<SysUser> getinfo(BTView<SysUser> bt) ;

	public void updateUserRoles(Integer userId, String roleId);

	public List<SysUser> findUserList();

	//根据登录账号，真实姓名，部门名称查找用户，且展示以注册时间倒叙展示
    List<SysUser> getUserList(String loginAccount, String userName,String departmentName);

    //根据角色id查询用户
	public List<SysUserDto> findByRoleId(Integer roleId);

    Pages getUserList(int pageSize, int pageNumber, String loginAccount, String userName, String departmentName);

	//根据真实姓名查询用户集合
	List<SysUser> findUserListByName(String userName);
}
