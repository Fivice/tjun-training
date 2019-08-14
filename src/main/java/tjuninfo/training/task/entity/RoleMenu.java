package tjuninfo.training.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import tjuninfo.training.support.BaseEntity;
/**
 * 角色权限关联表 实体类   
 * @author shenxianyan
 * @date 2018年5月25日
 */
@Entity
@Table(name = "sys_role_menu")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleMenu extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色权限id(主键)
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_menu_id")
    private Long roleMenuId;
    /**
     * 角色id
     */
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name="role_id",referencedColumnName = "role_id")   
    @Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE} )
    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    private SysRole sysRole;
    /**
     * 权限id
     */
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name="menu_id",referencedColumnName = "menu_id")   
    @Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE } )
    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    private Menu menu;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Integer createTime;
    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Integer createBy;
    
	public Long getRoleMenuId() {
		return roleMenuId;
	}
	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}
	
	public SysRole getSysRole() {
		return sysRole;
	}
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}


}
