package tjuninfo.training.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import tjuninfo.training.support.BaseEntity;
/**
 * 用户角色关联表 实体类   
 * @author shenxianyan
 * @date 2018年5月27日
 */
@Entity
@Table(name = "sys_user_role")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserRole extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色用户id(主键)
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    
}
