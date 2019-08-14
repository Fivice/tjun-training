package tjuninfo.training.task.vo;

import java.io.Serializable;
/**
 * 菜单vo
 * @author win7
 *
 */
public class MenuVo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer menuId;
	private Integer parentId;


	private String menuName;

	
	public MenuVo(Integer menuId, Integer parentId, String menuName) {
		super();
		this.menuId = menuId;
		this.parentId = parentId;
		
		this.menuName = menuName;
	}
	public MenuVo(Integer menuId) {
		super();
		this.menuId = menuId;
		
	}


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


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	
}
