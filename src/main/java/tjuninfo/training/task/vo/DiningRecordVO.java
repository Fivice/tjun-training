package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/10/9 10:57
 * @Description:学员就餐记录表实体类VO
 */
public class DiningRecordVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 学生就餐记录
     **/
    private List stuDiningRecordVO1;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getStuDiningRecordVO1() {
        return stuDiningRecordVO1;
    }

    public void setStuDiningRecordVO1(List stuDiningRecordVO1) {
        this.stuDiningRecordVO1 = stuDiningRecordVO1;
    }

}