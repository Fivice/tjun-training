package tjuninfo.training.task.vo;


import java.io.Serializable;

public class TrainRecordVO implements Serializable {

    private Long id;

//    private String projectNumber;		// 项目编号

    protected String classNumber;		// 班级编号

    private String className;		// 班级名称

    private String subject;            //培训课程

    private String hostUnit;		// 主办单位（手写）

    private String startStopTime;		// 办班时间段

    private Integer dayNum;		// 培训天数

    private Double timeNum;		// 学时

    private String name;//学生姓名

    private String idCard;//学生身份证

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
