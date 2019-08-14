package tjuninfo.training.task.dto;

import java.io.Serializable;
/**
 * 角色菜单dto
 * @author shenxianyan
 * @date 2018年5月28日
 */
public class RoleMenuDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 权限编号
     */
	private Integer menuId;
    /**
     * 父级编号
     */
	private Integer parentId;
    /**
     * 权限类型：1.菜单；2.功能；3.子功能；0.操作
     */
	private Integer menuType;
    /**
     * 权限名称
     */
	private String menuName;
    /**
     * 权限标识
     */
	private String permission;
    /**
     * 链接地址
     */
	private String href;
    /**
     * 图标名称
     */
	private String icon;

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
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
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
	
}
