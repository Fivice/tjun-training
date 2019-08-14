package tjuninfo.training.task.vo;

import java.io.Serializable;

public class DepartmentVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**主键 区域编码**/
    private Integer areaId;

    /**上级区域编码**/
    private Integer sjareaId;
    
    /**排序**/
	private Integer sort;
    
    /**
     * 权限类型 1=学校/2=院系
     */
    private Integer areaType;

	/**区域名称**/
    private String areaName;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getSjareaId() {
		return sjareaId;
	}

	public void setSjareaId(Integer sjareaId) {
		this.sjareaId = sjareaId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
    
}
