package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @Auther: win7
 * @Date: 2018/10/27 13:20
 * @Description:
 */
public class TeachCardVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**班级id*/
    private BigInteger classId;

    /**班级编号*/
    private String classNumber;

    /**班级名称*/
    private String className;

    /**主办单位*/
    private String hostUnit;

    /**承办单位*/
    private String organizerUnit;

    /**班主任*/
    private String userName;

    /**办班时间*/
    private String startTime;

    /**天数*/
    private Integer dayNum;

    /**可回收时间*/
    private String recoverTime;

    /**办班时间段*/
    private String startStopTime;

    public BigInteger getClassId() {
        return classId;
    }

    public void setClassId(BigInteger classId) {
        this.classId = classId;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
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

    public String getOrganizerUnit() {
        return organizerUnit;
    }

    public void setOrganizerUnit(String organizerUnit) {
        this.organizerUnit = organizerUnit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getStartStopTime() {
        return startStopTime;
    }

    public void setStartStopTime(String startStopTime) {
        this.startStopTime = startStopTime;
    }
}
