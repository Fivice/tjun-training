package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @description 教师就餐安排表(给人脸识别绑定使用)
 * @author Fivice
 * @date 2018年12月5日11:18:28
 */
@Entity
@Table(name = "teacher_dining_for_scene")
public class TeacherDiningForScene extends BaseEntity {
    //主键
    private int id;
    //教师就餐注册id
    private BigInteger teacherDiningRegId;
    //安排人id(谁安排的)
    private String arrangeId;
    //就餐地点
    private String diningPlace;
    //就餐日期
    private String diningDate;
    //早餐
    private int breakfast;
    //中餐
    private int lunch;
    //晚餐
    private int dinner;
    //记录添加日期
    private String recordTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "tdr_id")
    public BigInteger getTeacherDiningRegId() {
        return teacherDiningRegId;
    }

    public void setTeacherDiningRegId(BigInteger teacherDiningRegId) {
        this.teacherDiningRegId = teacherDiningRegId;
    }

    @Column(name = "arrange_id")
    public String getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(String arrangeId) {
        this.arrangeId = arrangeId;
    }

    @Column(name = "dining_palce")
    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    @Column(name = "dining_date")
    public String getDiningDate() {
        return diningDate;
    }

    public void setDiningDate(String diningDate) {
        this.diningDate = diningDate;
    }

    @Column(name = "breakfast")
    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }

    @Column(name = "lunch")
    public int getLunch() {
        return lunch;
    }

    public void setLunch(int lunch) {
        this.lunch = lunch;
    }

    @Column(name = "dinner")
    public int getDinner() {
        return dinner;
    }

    public void setDinner(int dinner) {
        this.dinner = dinner;
    }

    @Column(name = "record_time")
    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
