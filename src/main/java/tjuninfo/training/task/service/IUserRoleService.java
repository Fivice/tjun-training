package tjuninfo.training.task.service;

import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.UserRole;

import java.util.List;

public interface IUserRoleService extends IBaseService<UserRole> {
	/**
	 * 根据用户id得到角色id
	 * @param userId
	 * @return
	 */
	List<UserRole> getRoleIdByUserId(Integer userId);
	/**
	 * 根据用户名获取相同用户名的条数
	 * @param loginAccount
	 * @return
	 */
	Long getCount(String loginAccount);

	//根据用户id查找用户--级别对象
	public  UserRole getUserRoleByUserId(Integer userId);
}
