package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * @Auther: win7
 * @Date: 2018/10/12 10:22
 * @Description:教师就餐记录表实体类
 */

@Entity
@Table(name = "teach_dining_record")
public class TeachDiningRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 教师姓名
     **/
    @Column(name = "teacher")
    private String teacherName;

    /**
     * 班级id
     **/
    @Column(name ="class_room")
    private Integer classRoom;

    /**
     * 区域
     **/
    @Column(name = "area")
    private String area;

    /**
     * 日期
     **/
    @Column(name = "t_day")
    private String teacherDay;

    /**
     * 早餐
     **/
    @Column(name = "breakfast")
    private Integer breakfast;

    /**
     * 中餐
     **/
    @Column(name = "lunch")
    private Integer lunch;

    /**
     * 晚餐
     **/
    @Column(name = "dinner")
    private Integer dinner;

    /**
     * 流水号
     **/
    @Column(name = "num")
    private String num;

    /**
     * 授权者编号
     **/
    @Column(name = "authorizer_id")
    private Integer authorizerId;

    /**
     * 系统时间 安排时间  流水号绑定教师信息时间
     **/
    @Column(name ="time")
    private String time;
    /**
     * 最近一次就餐时间点
     **/
    @Column(name ="time_dinner")
    private String timeDinner;

    public String getTime() {
        return time;
    }

    public TeachDiningRecord() {
    }

    public TeachDiningRecord(Integer id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeDinner() {
        return timeDinner;
    }

    public void setTimeDinner(String timeDinner) {
        this.timeDinner = timeDinner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(Integer classRoom) {
        this.classRoom = classRoom;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTeacherDay() {
        return teacherDay;
    }

    public void setTeacherDay(String teacherDay) {
        this.teacherDay = teacherDay;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public Integer getAuthorizerId() {
        return authorizerId;
    }

    public void setAuthorizerId(Integer authorizerId) {
        this.authorizerId = authorizerId;
    }
}
