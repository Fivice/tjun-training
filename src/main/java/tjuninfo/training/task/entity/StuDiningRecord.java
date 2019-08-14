package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * @Auther: win7
 * @Date: 2018/9/30 15:15
 * @Description:学员就餐记录表实体类
 */

@Entity
@Table(name = "stud_dining_record")
public class StuDiningRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 学员ID
     **/
    @Column(name = "student")
    private Integer student;

    /**
     * 班级ID
     **/
    @Column(name = "class_room")
    private Integer classRoom;

    /**
     * 区域:就餐地点
     **/
    @Column(name = "area")
    private String area;

    /**
     * 日期
     **/
    @Column(name = "day")
    private String day;

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
     * 最近一次就餐时间点
     **/
    @Column(name ="time_dinner")
    private String timeDinner;

    public String getTimeDinner() {
        return timeDinner;
    }

    public void setTimeDinner(String timeDinner) {
        this.timeDinner = timeDinner;
    }

    public Integer getId() {
        return id;
    }

    public StuDiningRecord() {
    }

    public StuDiningRecord(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudent() {
        return student;
    }

    public void setStudent(Integer student) {
        this.student = student;
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
