package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * @Auther: win7
 * @Date: 2018/10/25 10:25
 * @Description:教师证流水号表实体类
 */

@Entity
@Table(name = "teacher_card")
public class TeacherCard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**教师流水号*/
    @Column(name = "number")
    private String number;

    /**教师姓名*/
    @Column(name = "teacher")
    private String teacherName;

    /**流水号绑定时间*/
    @Column(name = "time")
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
