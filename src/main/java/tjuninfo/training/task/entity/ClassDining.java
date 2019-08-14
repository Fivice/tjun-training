package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * @Auther: win7
 * @Date: 2018/9/29 09:50
 * @Description:班级就餐管理实体类
 */
@Entity
@Table(name = "class_dining")
public class ClassDining extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**主键 **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**班级id **/
    @Column(name = "class_room")
    private String classRoom;

    /**日期 **/
    @Column(name = "day")
    private String day;

    /**早餐 **/
    @Column(name = "breakfast")
    private Integer breakfast;

    /**中餐 **/
    @Column(name = "lunch")
    private Integer lunch;

    /**晚餐 **/
    @Column(name = "dinner")
    private Integer dinner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
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
