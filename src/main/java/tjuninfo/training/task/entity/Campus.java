package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 校区表的实体
 */
@Entity
@Table(name = "campus")
public class Campus extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**主键校区编码**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**校区名称**/
    @Column(name = "name")
    private String schoolName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

}
