package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 区域及教室表的实体
 */
@Entity
@Table(name = "area_classroom")
public class Classroom extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**主键教室编码**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**校区名称**/
    @Column(name = "name")
    private Integer schoolName;

    /**教室名称**/
    @Column(name = "class_name")
    private String className;

    /**校区类型**/
    @Column(name = "type")
    private Integer type;

    /**教室类型**/
    @Column(name = "classroom_type")
    private Integer classroomType;

    /**容量**/
    @Column(name = "capacity")
    private Integer capacity;

    /**排序*/
    @Column(name = "sort")
    private Integer sort;

    /**备注*/
    @Column(name = "remarks")
    private String remarks;

    /**教室的状态 (1:正常(闲置)；2、禁用（在用）)*/
    @Column(name = "state")
    private Integer state;

    /**教室的状态 (1:正常；2、删除)*/
    @Column(name = "a_state")
    private Integer aState;

    public Integer getId() {
        return id;
    }

    public Classroom(Integer id) {
        this.id = id;
    }

    public Classroom() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(Integer schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getClassroomType() {
        return classroomType;
    }

    public void setClassroomType(Integer classroomType) {
        this.classroomType = classroomType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getaState() {
        return aState;
    }

    public void setaState(Integer aState) {
        this.aState = aState;
    }
}
