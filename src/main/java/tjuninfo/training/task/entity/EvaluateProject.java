package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/*
评价项目维护表实体类
 */
@Entity
@Table(name = "evaluate_project")
public class EvaluateProject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 区域编码
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 评价分类
     */
    @Column(name = "type")
    private String type;

    /**
     * 评价项目
     */
    @Column(name = "project")
    private String project;

    /**
     * 满分分值
     */
    @Column(name = "score")
    private String score;

    /**
     * 评价大项
     */
    @Column(name = "large_class")
    private Integer largeClass;

    /**
     * 备注
     */
    @Column(name = "e_remark")
    private String eRemark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getLargeClass() {
        return largeClass;
    }

    public void setLargeClass(Integer largeClass) {
        this.largeClass = largeClass;
    }

    public String geteRemark() {
        return eRemark;
    }

    public void seteRemark(String eRemark) {
        this.eRemark = eRemark;
    }
}
