package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Auther: win7
 * @Date: 2018/10/12 10:40
 * @Description:教师就餐记录表实体类VO
 */
public class TeachDiningVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**主键 id**/
    private Integer id;

    /** 班级id**/
    private Integer classId;

    /**就餐次数**/
    private BigInteger countB;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public BigInteger getCountB() {
        return countB;
    }

    public void setCountB(BigInteger countB) {
        this.countB = countB;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

}
