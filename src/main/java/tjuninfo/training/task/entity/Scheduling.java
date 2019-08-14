package tjuninfo.training.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import tjuninfo.training.support.BaseEntity;
import tjuninfo.training.task.util.excel.annotation.ExcelField;

import javax.persistence.*;

/**
 * 日程安排实体类
* @author 
 * @date 2018年5月27日
 */
@Entity
@Table(name = "scheduling")
public class Scheduling extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduling_id")
    private Long schId;

    /**日期**/
    @Column(name = "day")
    private String day;

	/**时间段**/
	@Column(name = "time")
	private String time;

	/**课程或内容**/
	@Column(name = "scheduling_content")
	private String schContent;

    /**授课形式**/
    @Column(name = "teaching_form")
    private String teachingForm;


	//    /**教师id**/
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="teacher_id",referencedColumnName = "ti_id")
//    @Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
//    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
//    private Teacher teacher;

	/**教师**/

	@Column(name = "teacher")
	private String teacher;

    /**教师所属中心**/

    @Column(name = "teacher_dep")
    private String teacherDep;

    /*上课地点*/
	@Column(name = "classroom")
    private String classroom;

	/**评测（ 0：不评测，1：评测 ）**/
	@Column(name = "evaluation")
	private int evaluate;

    /**评测（ 0：不评测，1：评测 ）*/
    @Transient
    @ExcelField(title = "是否参评",align = 2, sort = 3)
    private String evaName;





    /**班级**/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="class_id",referencedColumnName = "id")
	@Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private ClassInfo classInfo;

	public Long getSchId() {
		return schId;
	}

	public void setSchId(Long schId) {
		this.schId = schId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSchContent() {
		return schContent;
	}

	public void setSchContent(String schContent) {
		this.schContent = schContent;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public ClassInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}

    public String getEvaName() {
        return evaName;
    }

    public void setEvaName(String evaName) {
        this.evaName = evaName;
    }

	public String getTeachingForm() {
		return teachingForm;
	}

	public void setTeachingForm(String teachingForm) {
		this.teachingForm = teachingForm;
	}

    public String getTeacherDep() {
        return teacherDep;
    }

    public void setTeacherDep(String teacherDep) {
        this.teacherDep = teacherDep;
    }
}
