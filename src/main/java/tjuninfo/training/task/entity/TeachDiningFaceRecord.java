package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * @Description:教师人脸识别实际就餐记录实体类
 */

@Entity
@Table(name = "teach_dining_face_record")
public class TeachDiningFaceRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 教师ID
     **/
    @Column(name = "teacher_id")
    private Integer teacherId;

    /**
     * 班级id
     **/
    @Column(name = "class_id")
    private Integer classId;

    /**
     * 就餐地点
     **/
    @Column(name = "dining_place")
    private String diningPlace;

    /**
     * 就餐日期
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
     * 授权者编号
     **/
    @Column(name = "authorizer_id")
    private Integer authorizerId;

    public Integer getId() {
        return id;
    }

    public TeachDiningFaceRecord() {
    }

    public Integer getAuthorizerId() {
        return authorizerId;
    }

    public void setAuthorizerId(Integer authorizerId) {
        this.authorizerId = authorizerId;
    }

    public TeachDiningFaceRecord(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
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