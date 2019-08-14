package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 教师信息的实体类
 * @author wt
 * @date 2018年5月16日
 */
@Entity
@Table(name = "teacher_information")
public class TeacherInfo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ti_id")
    private Integer tiId;

    /**姓名**/
    @Column(name = "ti_name" ,length = 64)
    private String tiName;

    /**手机号**/
    @Column(name = "phone_number" ,length = 11)
    private String phoneNumber;

    /**工作单位**/
    @Column(name = "ti_department" ,length = 255)
    private String tiDepartment;

    /*授课科目*/
    @Column(name = "subject")
    private String subject;

    /*民族*/
    @Column(name = "ethnic_group")
    private String ethnicGroup;

    /*身份证号*/
    @Column(name = "si_ID_number")
    private String siIDNumber;

    /*人脸匹对照*/
    @Column(name = "scenePicture")
    private String scenePicture;

    /*证件照*/
    @Column(name = "photo")
    private String photo;

    /*邮箱*/
    @Column(name = "email")
    private String email;

    /*备注*/
    @Column(name = "remarks")
    private String remarks;

    /*性别 0：男 1：女*/
    @Column(name = "sex")
    private String sex;

    @Column(name = "create_by")
    private String createBy;		// 创建者
    @Column(name = "create_date")
    private String createDate;		// 创建时间
    @Column(name = "update_by")
    private String updateBy;		// 更新者
    @Column(name = "update_date")
    private String updateDate;		// 更新时间

    public TeacherInfo(){

    }

    public Integer getTiId() {
        return tiId;
    }

    public void setTiId(Integer tiId) {
        this.tiId = tiId;
    }

    public String getTiName() {
        return tiName;
    }

    public void setTiName(String tiName) {
        this.tiName = tiName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTiDepartment() {
        return tiDepartment;
    }

    public void setTiDepartment(String tiDepartment) {
        this.tiDepartment = tiDepartment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public String getSiIDNumber() {
        return siIDNumber;
    }

    public void setSiIDNumber(String siIDNumber) {
        this.siIDNumber = siIDNumber;
    }

    public String getScenePicture() {
        return scenePicture;
    }

    public void setScenePicture(String scenePicture) {
        this.scenePicture = scenePicture;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
