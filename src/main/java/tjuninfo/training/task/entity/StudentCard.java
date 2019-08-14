package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * @Auther: win7
 * @Date: 2018/10/25 15:45
 * @Description:学员流水号表实体类
 */
@Entity
@Table(name = "student_card")
public class StudentCard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**学员流水号*/
    @Column(name = "number")
    private String number;

    /**学员ID*/
    @Column(name = "student_id")
    private Integer studentId;

    /**学员报名时间*/
    @Column(name = "register_time")
    private String registerTime;

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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
}
