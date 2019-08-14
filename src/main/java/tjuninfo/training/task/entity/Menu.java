package tjuninfo.training.task.entity;

import java.util.Date;

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
 * 菜单表的实体类
 * @author shenxianyan
 * @date 2018年5月17日
 */
@Entity
@Table(name = "sys_menu")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;
    /**
     * 父级编号
     */
    @Column(name = "parent_id")
    private Integer parentId;
    /**
     * 权限类型 1=菜单/2=功能/3=子功能/0=操作
     */
    @Column(name = "menu_type")
    private Integer menuType;
    /**
     * 权限代码
     */
    @Column(name = "menu_code")
	private String menuCode;
    /**
     * 权限名称
     */
    @Column(name = "menu_name")
	private String menuName;
    /**
     * 排序
     */
    @Column(name = "sort")
	private Integer sort;
    /**
     * 链接地址
     */
    @Column(name = "href")
	private String href;
    /**
     * 图标名称
     */
    @Column(name = "icon")
	private String icon;
    /**
     * 状态 0=隐藏/1=显示
     */
    @Column(name = "status")
	private Integer status;
    /**
     * 权限标识
     */
    @Column(name = "permission")
	private String permission;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
	private Date createTime;
    /**
     * 创建者
     */
    @Column(name = "create_by")
	private String createBy;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
	private Date updateTime;
    /**
     * 更新者
     */
    @Column(name = "update_by")
	private String updateBy;
    /**
     * 备注信息
     */
    @Column(name = "remarks")
	private String remarks;
    
    public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    

}
