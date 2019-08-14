package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * @Auther: win7
 * @Date: 2018/10/17 11:19
 * @Description:教师就餐安排实体类
 */

@Entity
@Table(name = "teach_dining")
public class TeachDining extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 流水号
     **/
    @Column(name ="number")
    private String number;

    /**
     * 教师姓名
     **/
    @Column(name = "teacher")
    private String teacherName;

    /**
     * 安排人id
     **/
    @Column(name = "arranger")
    private String arranger;

    @Transient
    private String className;

    /**
     * 班级ID
     **/
    @Column(name = "class_id")
    private Integer classId;

    /**
     * 就餐地点
     **/
    @Column(name ="area")
    private String area;

    /**
     * 系统时间 安排时间  流水号绑定教师信息时间
     **/
    @Column(name ="time")
    private String time;

    /**
     * 日期
     **/
    @Column(name ="day")
    private String day;

    /**
     * 早餐
     **/
    @Column(name ="breakfast")
    private Integer breakfast;

    /**
     * 午餐
     **/
    @Column(name ="lunch")
    private Integer lunch;

    /**
     * 晚餐
     **/
    @Column(name ="dinner")
    private Integer dinner;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getArranger() {
        return arranger;
    }

    public void setArranger(String arranger) {
        this.arranger = arranger;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getLunch() {
        return lunch;
    }

    public void setLunch(Integer lunch) {
        this.lunch = lunch;
    }

    public Integer getDinner() {
        return dinner;
    }

    public void setDinner(Integer dinner) {
        this.dinner = dinner;
    }
}
