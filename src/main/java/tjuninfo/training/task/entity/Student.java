package tjuninfo.training.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 部门实体类
* @author 
 * @date 2018年5月27日
 */
@Entity
@Table(name = "student_information")
public class Student extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "si_id")
    private Integer siId;

    /**学生姓名**/
    @Column(name = "si_name")
    private String siName;

	/**学生民族**/
	@Column(name = "ethnic_group")
	private String ethnicGroup;

	/**流水号**/
	@Column(name = "serial_number")
	private String serialNumber;

	/**单位**/
    @Column(name = "si_unitId")
	private Integer siUnitId;
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="si_unitId")
	@Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private Unit unit;*/

	/**单位名称**/
	@Column(name = "unit_name")
	private String unitName;

	/**部门**/
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="si_departmentId")
	@Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private Department department;*/
	@Column(name = "si_departmentId")
	private Integer siDepartmentId;

	/**部门名称**/
	@Column(name = "deparentmentName")
	private String deparentmentName;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**身份证号**/
    @Column(name = "si_ID_number")
    private String siIDNumber;

	/**证件照**/
	@Column(name = "photo")

	private String photo;

	/**手机号**/
	@Column(name = "si_phone_number")
	private String phoneNumber;

	/**状态**/
	@Column(name = "status")
	private String status;

	/**性别（0：男 1：女）**/
	@Column(name = "sex")
	private String sex;

	/**员工编号**/
	@Column(name = "si_number")
	private String siNumber;

	/**邮箱**/
	@Column(name = "email")
	private String email;

	/**工作岗位**/
	@Column(name = "post")
	private String post;

	/**职业技能等级**/
	@Column(name = "skill_level")
	private String skillLevel;

	/**地址**/
	@Column(name = "si_address")
	private String siAddress;

	/**
	 *  评价（ 0：已评价，1：未评价 ）
	 *  该字段作废
	 */
	@Column(name = "evaluate")
	private String evaluate;

	@Column(name = "scenePicture")
	private String scenePicture;

	@Column(name = "create_by")
	private String createBy;		// 创建者
	@Column(name = "create_date")
	private String createDate;		// 创建时间
	@Column(name = "update_by")
	private String updateBy;		// 更新者
	@Column(name = "update_date")
	private String updateDate;		// 更新时间
	@Column(name = "remarks")
	private String remarks;		// 备注信息

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}

	public Student() {
	}

	public Student(String siName, String siIDNumber) {
		this.siName = siName;
		this.siIDNumber = siIDNumber;
	}

	public String getScenePicture() {
		return scenePicture;
	}

	public void setScenePicture(String scenePicture) {
		this.scenePicture = scenePicture;
	}

	public Student(Integer siId) {
		this.siId = siId;
	}

	public Integer getSiId() {
		return siId;
	}

	public Integer getSiDepartmentId() {
		return siDepartmentId;
	}

	public void setSiDepartmentId(Integer siDepartmentId) {
		this.siDepartmentId = siDepartmentId;
	}

	public String getDeparentmentName() {
		return deparentmentName;
	}

	public void setDeparentmentName(String deparentmentName) {
		this.deparentmentName = deparentmentName;
	}

	public void setSiId(Integer siId) {
		this.siId = siId;
	}

	public String getSiName() {
		return siName;
	}

	public void setSiName(String siName) {
		this.siName = siName;
	}

	public Integer getSiUnitId() {
		return siUnitId;
	}

	public void setSiUnitId(Integer siUnitId) {
		this.siUnitId = siUnitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSiIDNumber() {
		return siIDNumber;
	}

	public void setSiIDNumber(String siIDNumber) {
		this.siIDNumber = siIDNumber;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSiNumber() {
		return siNumber;
	}

	public void setSiNumber(String siNumber) {
		this.siNumber = siNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getSiAddress() {
		return siAddress;
	}

	public void setSiAddress(String siAddress) {
		this.siAddress = siAddress;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
