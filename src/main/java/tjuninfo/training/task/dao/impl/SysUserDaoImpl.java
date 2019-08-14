package tjuninfo.training.task.dao.impl;


import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ISysUserDao;
import tjuninfo.training.task.dto.SysUserDto;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.util.Pages;

import java.util.List;
import java.util.Map;

/**
 * 用户数据层接口实现类
 * @author shenxianyan
 * @date 2018年5月29日
 */
@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements ISysUserDao {

	public SysUserDaoImpl() {
		super(SysUser.class);
	}
	@Override
	public SysUser getLoginUser(SysUser user) {
		// TODO 用户登录
		String[] pn = {"loginAccount"};
		String[] pv = {user.getLoginAccount()};
		return super.getByProerties(pn, pv, null);
	}
	@Override
	public void transitionStateByUserId( Integer userId, Object[] param,Map<String, String> orderMap) {
	
		if (userId != null) {
			 
			new StringBuffer("UPDATE "+this.entityClass.getName()+" SET state = 1 where user_id = " + userId +" and state =0");
			 
			new StringBuffer("UPDATE "+this.entityClass.getName()+" SET state = 0 where user_id = " + userId+" and state =1");
	
			 		 
		}
	}
	@Override
	public List<SysUser> getinfo(BTView<SysUser> bt) {
			bt.setTotal(Long.parseLong(this.getSession()
					.createCriteria(SysUser.class,"s")
					.add(Restrictions.not(Expression.eq("s.state", 2)))
					.addOrder(Order.desc("s.registerTime"))
					.setProjection(Projections.rowCount()).uniqueResult().toString()));
			return this.getSession().createCriteria(SysUser.class,"s")
					.add(Restrictions.not(Expression.eq("s.state", 2)))
					.addOrder(Order.desc("s.registerTime"))
					 .setFirstResult((bt.getPageNumber() - 1) * bt.getPageSize()).setMaxResults(bt.getPageSize())
			.list();
	}
	
	 
	public void updateUserRoles(Integer userId, String roleId) {
		if (userId != null) {	
			super.getSession().createSQLQuery("DELETE  FROM sys_user_role  where  user_id = :userId " ).setParameter("userId", userId).executeUpdate();

				super.getSession().createSQLQuery("INSERT INTO sys_user_role  (user_id,role_id) VALUES (:userId ,:roleId )" ).setParameter("userId", userId).setParameter("roleId",roleId).executeUpdate();

		}
		 
	}

	@Override
	public List<SysUser> findUserList() {
		StringBuffer hql =new StringBuffer("from SysUser c where 1=1") ;
		Query query = getSession().createQuery(hql.toString());
		return query.list();
	}

	@Override
	public List<SysUser> getUserList(String loginAccount, String userName,String departmentName) {
		StringBuffer hql =new StringBuffer("from SysUser s where 1=1") ;
		if (loginAccount!=null && !loginAccount.equals("")){
			hql.append(" and s.loginAccount like "+ "'%" + loginAccount + "%'");
		}
		if (userName!=null && !userName.equals("")){
			hql.append(" and s.userName like "+ "'%" + userName + "%'");
		}
		if(departmentName !=null && !departmentName.equals("")){
			System.out.println(Integer.parseInt(departmentName)+"1111");
			hql.append("and s.department.areaId = "+ Integer.parseInt(departmentName));
			System.out.println("ssss");
		}
		hql.append("and s.state !="+2);
		hql.append(" ORDER BY s.registerTime DESC ");
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public List<SysUserDto> findByRoleId(Integer roleId) {

		String sql = "SELECT user_id as userId,userName as userName,telephone as telephone FROM sys_user s WHERE s.user_id in(SELECT c.user_id FROM sys_user_role c WHERE 1=1 AND c.role_id="+roleId+") AND s.state=1 ORDER BY CONVERT (s.userName USING gbk) COLLATE gbk_chinese_ci ASC";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(SysUserDto.class));
		List<SysUserDto> list = query.list();
		return list;
	}

	@Override
	public Pages getUserList(int pageSize, int pageNumber, String loginAccount, String userName, String departmentName) {
		StringBuffer hql =new StringBuffer("from SysUser s where 1=1") ;
		if (loginAccount!=null && !loginAccount.equals("")){
			hql.append(" and s.loginAccount like "+ "'%" + loginAccount + "%'");
		}
		if (userName!=null && !userName.equals("")){
			hql.append(" and s.userName like "+ "'%" + userName + "%'");
		}
		if(departmentName !=null && !departmentName.equals("")){
			System.out.println(Integer.parseInt(departmentName)+"1111");
			hql.append("and s.department.areaId = "+ Integer.parseInt(departmentName));
			System.out.println("ssss");
		}
		hql.append("and s.state !="+2);
		hql.append(" ORDER BY s.registerTime DESC ");
		Query query = getSession().createQuery(hql.toString());
		Pages pages = new Pages(pageNumber, pageSize, query);
		return pages;
	}

	@Override
	public List<SysUser> findUserListByName(String userName) {
		StringBuffer hql =new StringBuffer("from SysUser s where 1=1") ;
		if (userName!=null && !userName.equals("")){
			hql.append(" and s.userName = "+ "'" + userName + "'");
		}
		Query query = getSession().createQuery(hql.toString());
		return query.list();
	}
}
