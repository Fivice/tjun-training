package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/*
评价项目维护表实体类
 */
@Entity
@Table(name = "evaluate_subj")
public class EvaluateSubj extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 课程
     */
    @Column(name = "subject")
    private String subject;

    /**
     * 班级id
     */
    @Column(name = "class_id")
    private Long classId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
