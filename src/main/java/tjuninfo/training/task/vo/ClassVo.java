package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author win7
 * @title: ClassVo
 * @projectName tjun-training
 * @description: TODO
 * @date 2019/6/6上午 9:24
 */
public class ClassVo implements Serializable {

    private BigInteger id;

    private String className;		// 班级名称

    private String hostUnit;		// 主办单位（手写）

    private String startStopTime;		// 办班时间段

    private Integer dayNum;		// 培训天数

    private Double timeNum;		// 学时

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHostUnit() {
        return hostUnit;
    }

    public void setHostUnit(String hostUnit) {
        this.hostUnit = hostUnit;
    }

    public String getStartStopTime() {
        return startStopTime;
    }

    public void setStartStopTime(String startStopTime) {
        this.startStopTime = startStopTime;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Double getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(Double timeNum) {
        this.timeNum = timeNum;
    }
}
