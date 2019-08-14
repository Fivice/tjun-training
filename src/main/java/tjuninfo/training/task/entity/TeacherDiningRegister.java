package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @description 教师就餐注册表实体
 * @author Fivice
 * @date 2018年12月5日11:16:30
 */
@Entity
@Table(name = "teacher_dining_register")
public class TeacherDiningRegister extends BaseEntity {
    //主键id
    private BigInteger id;
    //教师id
    private Integer teacherId;
    //班级id
    private BigInteger classId;
    //教师就餐注册时间
    private String regTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Column(name = "teacher_id")
    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Column(name = "class_id")
    public BigInteger getClassId() {
        return classId;
    }

    public void setClassId(BigInteger classId) {
        this.classId = classId;
    }

    @Column(name = "reg_time")
    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }
}
