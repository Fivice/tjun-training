package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 单位实体类
* @author 
 */
@Entity
@Table(name = "unit_information")
public class Unit extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键 单位编码**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Integer areaId;

    /**上级单位编码**/
    @Column(name = "sjarea_id")
    private Integer sjareaId;
    
    /**
     * 排序
     */
    @Column(name = "sort")
	private Integer sort;
    
    /**
     * 单位类型 1=省/2=市/3=县
     */
    @Column(name = "area_type")
    private Integer areaType;

	/**单位名称**/
    @Column(name = "area_name")
    private String areaName;

	/**联系人**/
	@Column(name = "contacts")
	private String contacts;

	/**联系方式**/
	@Column(name = "contacts_tel")
	private String contactsTel;

	/**邮箱**/
	@Column(name = "email")
	private String email;

	/**邮箱编码**/
	@Column(name = "postal_code")
	private String postalCode;

	/**地址**/
	@Column(name = "address")
	private String address;

	/**状态 1 在用 2 删除**/
	@Column(name = "status")
	private Integer status;

	/**备注**/
	@Column(name = "remarks")
	private String remarks;

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
