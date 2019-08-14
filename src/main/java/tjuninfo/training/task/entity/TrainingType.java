package tjuninfo.training.task.entity;


import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/*
 培训类型实体类
 */
@Entity
@Table(name = "training_type")
public class TrainingType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**主键 区域编码**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**培训类型名称*/
    @Column(name = "type")
    private String type;

    /**注释*/
    @Column(name = "t_explan")
    private String tExplan;

    /**备注*/
    @Column(name ="remark")
    private String remark;

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

    public String gettExplan() {
        return tExplan;
    }

    public void settExplan(String tExplan) {
        this.tExplan = tExplan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
