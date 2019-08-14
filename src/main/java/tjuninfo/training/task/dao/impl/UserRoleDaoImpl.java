package tjuninfo.training.task.dao.impl;


import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IUserRoleDao;
import tjuninfo.training.task.entity.UserRole;

import java.util.List;
/**
 * 角色数据层接口实现类
 * @author shenxianyan
 * @date 2018年5月29日
 */
@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements IUserRoleDao {
	public UserRoleDaoImpl() {
		super(UserRole.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<UserRole> listByUserId(Integer userId) {
		@SuppressWarnings("unchecked")
		List<UserRole> list = super.getSession().createCriteria(UserRole.class,"ur").add(Restrictions.eq("ur.userId", userId)).list();
		return list;
	}

	@Override
	public Long getByUserId(Integer userId) {
		Long roleId =null;
		UserRole ur =null;
		try {
			String sql = "SELECT * from sys_user_role o where user_id = "+userId;
			List<UserRole> list = super.getSession().createSQLQuery(sql).addEntity(UserRole.class).getResultList();
			if(list.size()!=0) {
				 ur = list.get(0);
			}
			
			if(ur!=null) {
				roleId = ur.getRoleId();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roleId;
	}

	@Override
	public Long getCount(String loginAccount) {
		String sql = "SELECT COUNT(*) from sys_user u where  u.state !=2  and login_account =  "+loginAccount;
		return (Long)getSession().createQuery(sql.toString()).uniqueResult();
	}

	@Override
	public UserRole getUserRoleByUserId(Integer userId) {
		String hql =" from UserRole u where 1=1 ";
		if(userId!= null && !userId.equals("")){
			hql += " and u.userId = "+userId;
		}


		return (UserRole) getSession().createQuery(hql).uniqueResult();
	}


}
