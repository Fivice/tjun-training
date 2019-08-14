package tjuninfo.training.task.dao.impl;


import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.IRoleMenuDao;
import tjuninfo.training.task.dto.RoleMenuDTO;
import tjuninfo.training.task.entity.RoleMenu;
import tjuninfo.training.task.vo.RoleMenuVo2;

/**
 * 角色菜单业务层实现类
 * @author shenxianyan
 * @date 2018年5月28日
 */
@Repository
public class RoleMenuDaoImpl extends BaseDaoImpl<RoleMenu> implements IRoleMenuDao {
	
	public RoleMenuDaoImpl(){
		super(RoleMenu.class);
	}
	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public List<RoleMenuDTO> findMeunByUserId(Integer userId) {
		StringBuilder sql=new StringBuilder("SELECT DISTINCT m.menu_id AS menuId,m.menu_type AS menuType,m.parent_id AS parentId, m.menu_name AS menuName, m.href AS href, m.icon AS icon"
				+ " FROM sys_menu m left join "
				+ "(select rm.menu_id from sys_role_menu rm left JOIN sys_user_role ur on rm.role_id= ur.role_id where ur.user_id=:userId) a"
				+ " on m.menu_id= a.menu_id where m.menu_id= a.menu_id and m.`status`=1 ORDER BY m.sort");
		List<RoleMenuDTO> list = super.getSession().createSQLQuery(sql.toString())
				.setParameter("userId", userId).setResultTransformer(Transformers.aliasToBean(RoleMenuDTO.class)).list();
		return list;
	}

	@Override
	public void doDel(Long roleId) {
		super.getSession().createSQLQuery("DELETE  FROM sys_role_menu  where role_id = :roleId " )
		.setParameter("roleId", roleId).executeUpdate();
	}
	@Override
	public List<RoleMenuDTO> findMeunByUserId1(Integer userId) {
		StringBuilder sql=new StringBuilder("SELECT  m.menu_id AS menuId,m.menu_type AS menuType,m.parent_id AS parentId, m.menu_name AS menuName, m.href AS href, m.icon AS icon"
				+ " FROM sys_menu m left join "
				+ "(select rm.menu_id from sys_role_menu rm left JOIN sys_user_role ur on rm.role_id= ur.role_id where ur.user_id=:userId) a"
				+ " on m.menu_id= a.menu_id where m.`status`=1  AND m.`menu_type` = 3 ORDER BY m.sort");
		List<RoleMenuDTO> list = super.getSession().createSQLQuery(sql.toString())
				.setParameter("userId", userId).setResultTransformer(Transformers.aliasToBean(RoleMenuDTO.class)).list();
		return list;
	}
	
	@Override
	public List<RoleMenuVo2> findMenuIdByRoleId(Long roleId) {
		StringBuilder sql=new StringBuilder("SELECT role_menu_id AS roleMenuId,menu_id AS menuId,role_id AS roleId FROM sys_role_menu WHERE role_id=:roleId");
		List<RoleMenuVo2> list = super.getSession().createSQLQuery(sql.toString())
				.setParameter("roleId", roleId).setResultTransformer(Transformers.aliasToBean(RoleMenuVo2.class)).list();
		System.out.println(list);
		return list;
	}
	
	
}
