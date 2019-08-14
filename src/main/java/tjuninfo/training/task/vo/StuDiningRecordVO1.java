package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: win7
 * @Date: 2018/10/9 10:57
 * @Description:学员就餐记录表实体类VO
 */
public class StuDiningRecordVO1 implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 班级名称
     **/
    private String className;

    /**
     * 已到人数
     */
    private Integer arrived;

    /**
     * 应到人数
     */
    private Integer reached;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getArrived() {
        return arrived;
    }

    public void setArrived(Integer arrived) {
        this.arrived = arrived;
    }

    public Integer getReached() {
        return reached;
    }

    public void setReached(Integer reached) {
        this.reached = reached;
    }
}