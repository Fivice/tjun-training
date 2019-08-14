package tjuninfo.training.task.dao.impl;


import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ISysRoleDao;
import tjuninfo.training.task.entity.SysRole;

import java.util.List;

/**
 * 角色数据层接口实现类
 * @author shenxianyan
 * @date 2018年5月29日
 */
@Repository
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements ISysRoleDao {
	public SysRoleDaoImpl(){
		super(SysRole.class);
	}

	@Override
	public List<SysRole> findAll() {
		List<SysRole> roleList = super.getSession().createCriteria(SysRole.class).list();
		return roleList;
	}
	@Override
	public List<SysRole> getinfo(BTView<SysRole> bt) {
		if(bt.getSearchText()!=null&&!bt.getSearchText().equals("")) {
			bt.setTotal(Long.parseLong(this.getSession().createCriteria(SysRole.class, "r")
					.add(Restrictions.or(Restrictions.or(Restrictions.like("r.roleKey", "%"+bt.getSearchText()+"%"),Restrictions.like("r.roleValue", "%"+bt.getSearchText()+"%"))))
					.setProjection(Projections.rowCount()).uniqueResult().toString()));
			return  this.getSession().createCriteria(SysRole.class,"r")
					.add(Restrictions.or(Restrictions.or(Restrictions.like("r.roleKey", "%"+bt.getSearchText()+"%"),Restrictions.like("r.roleValue", "%"+bt.getSearchText()+"%"))))
					 .setFirstResult((bt.getPageNumber() - 1) * bt.getPageSize()).setMaxResults(bt.getPageSize())
			.list();
		}else {
			bt.setTotal(Long.parseLong(this.getSession().createCriteria(SysRole.class, "r")
					.setProjection(Projections.rowCount()).uniqueResult().toString()));
			return  this.getSession().createCriteria(SysRole.class,"r")
					 .setFirstResult((bt.getPageNumber() - 1) * bt.getPageSize()).setMaxResults(bt.getPageSize())
			.list();
		}
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		super.getSession().createSQLQuery("delete from sys_role where role_id=:roleId")
		.setParameter("roleId", roleId).executeUpdate();
		
	}

	@Override
	public List<SysRole> getUsers(String roleName) {
		StringBuffer hql =new StringBuffer("from SysRole s where 1=1") ;
		if (roleName!=null && !roleName.equals("")){
			hql.append(" and s.roleValue = "+ "'" + roleName.trim() + "'");
		}
		return getSession().createQuery(hql.toString()).list();
	}

	@Override
	public SysRole getByRoleValue(String roleValue, String roleId) {
		StringBuffer hql =new StringBuffer(" from SysRole s where s.roleValue="+ "'" + roleValue + "'");
		if (roleId!=null && !roleId.equals("")){
			hql.append(" and s.roleId != "+  Long.parseLong(roleId.trim()) );
		}
		Query query = getSession().createQuery(hql.toString());
		return (SysRole) query.uniqueResult();
	}
}
