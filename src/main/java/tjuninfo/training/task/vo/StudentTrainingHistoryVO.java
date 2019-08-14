package tjuninfo.training.task.vo;

import java.io.Serializable;

public class StudentTrainingHistoryVO implements Serializable {
//    班级名称
    private String className;
//    主办单位
    private String hostUnit;
//    办班时间
    private String startStopTime;
//    开班天数
    private Integer days;
//    学时
    private Double classHours;

    public StudentTrainingHistoryVO(String className, String hostUnit, String startStopTime, Integer days, Double classHours) {
        this.className = className;
        this.hostUnit = hostUnit;
        this.startStopTime = startStopTime;
        this.days = days;
        this.classHours = classHours;
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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Double getClassHours() {
        return classHours;
    }

    public void setClassHours(Double classHours) {
        this.classHours = classHours;
    }
}
