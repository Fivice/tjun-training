package tjuninfo.training.task.dao;

import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.UserRole;

import java.util.List;
/**
 * 角色用户接口
 * @author shenxianyan
 * @date 2018年5月28日
 */
public  interface IUserRoleDao extends IBaseDao<UserRole>{
	public List<UserRole> listByUserId(Integer userId);
	 
	//根据userId查找roleid
	public Long getByUserId(Integer userId);
	
	/**
	 * 根据用户名获取相同用户名的条数
	 * @param loginAccount
	 * @return
	 */
	Long getCount(String loginAccount);

    UserRole getUserRoleByUserId(Integer userId);
}


