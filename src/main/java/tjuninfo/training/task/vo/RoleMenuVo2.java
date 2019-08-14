package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigInteger;


public class RoleMenuVo2 implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigInteger roleMenuId;
	private BigInteger roleId;
	private BigInteger menuId;
	public BigInteger getRoleMenuId() {
		return roleMenuId;
	}
	public void setRoleMenuId(BigInteger roleMenuId) {
		this.roleMenuId = roleMenuId;
	}
	public BigInteger getRoleId() {
		return roleId;
	}
	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}
	public BigInteger getMenuId() {
		return menuId;
	}
	public void setMenuId(BigInteger menuId) {
		this.menuId = menuId;
	}

}
