package tjuninfo.training.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 成绩证书实体类
* @author 
 * @date 2018年5月27日
 */
@Entity
@Table(name = "score_report")
public class ScoreReport extends BaseEntity{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**主键**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	/**
	 * 关联班级id
	 */

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="class_id",referencedColumnName = "id")
    @Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    private ClassInfo classInfo;

	/**
	 * 关联报名学员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="student_id",referencedColumnName = "id")
	@Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private Register register;


	/**成绩**/
	@Column(name = "mark")
	private String mark;

	/**证书名称**/
	@Column(name = "report_name")
	private String reportName;

	/**证书编号**/
	@Column(name = "report_number")
	private String reportNumber;

	/**备注**/
	@Column(name = "remark")
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClassInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
