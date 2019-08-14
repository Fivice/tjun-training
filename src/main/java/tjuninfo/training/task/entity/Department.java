package tjuninfo.training.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import tjuninfo.training.support.BaseEntity;
/**
 * 部门实体类
* @author 
 * @date 2018年5月27日
 */
@Entity
@Table(name = "department")
public class Department extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键 区域编码**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Integer areaId;

    /**上级区域编码**/
    @Column(name = "sjarea_id")
    private Integer sjareaId;
    
    /**
     * 排序
     */
    @Column(name = "sort")
	private Integer sort;
    
    /**
     * 区域类型 1=学校/2=校区/3=院系/4=部门
     */
    @Column(name = "area_type")
    private Integer areaType;

	/**区域名称**/
    @Column(name = "area_name")
    private String areaName;

	/**区域状态**/
	@Column(name = "state")
	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	
	
}
