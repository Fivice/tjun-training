package tjuninfo.training.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import tjuninfo.training.support.BaseEntity;

/**
 * 角色的实体类
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;
//	/*
//	 * 角色编码(英文+数字)
//	 */
//	@Column(name = "role_key")
//	private String roleKey;
	/*
	 * 角色名称（中文名）
	 */
	@Column(name = "role_value")
	private String roleValue;
	/*
	 * 创建时间   
	 */
	@Column(name = "create_time")
	private String createTime;
	/*
	 * 角色备注
	 */
	@Column(name = "description")
	private String description;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
