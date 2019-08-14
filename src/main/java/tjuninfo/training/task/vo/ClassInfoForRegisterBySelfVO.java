package tjuninfo.training.task.vo;

import java.io.Serializable;

public class ClassInfoForRegisterBySelfVO implements Serializable {

    private Long id;

    private String projectNumber;		// 项目编号

    protected String classNumber;		// 班级编号

    private String className;		// 班级名称

    private String siUnitId;       //单位id

    private String hostUnit;		// 主办单位（手写）

    private String teacherName;		// 班主任名称

    private String phoneNumber;           //联系方式

    private Long schedulingId;		// 日程安排

    private Integer typeId;		// 培训类型

    private String typeName;		// 培训类型名称

    private Double trainingExpense;		// 培训费

    private String regPlace;		// 报名地点

    private Double otherCharges;		// 其它费用

    private String hotelPlace;		// 住宿地点

    private String diningPlace;		// 就餐地点

    private String startStopTime;		// 办班时间段

    private Double increaseDay;		// 食宿增加天数

    private String fileUrl;		// 相关文件地址

    private Integer evaluate;		// 评价  0：参评，1：不参评

    private String evaluateStartTime;		// 评价开始时间

    private String evaluateStopTime;		// 评价结束时间

    public ClassInfoForRegisterBySelfVO(Long id, String projectNumber, String classNumber, String className, String siUnitId, String hostUnit, String teacherName, String phoneNumber, Long schedulingId, Integer typeId, String typeName, Double trainingExpense, String regPlace, Double otherCharges, String hotelPlace, String diningPlace, String startStopTime, Double increaseDay, String fileUrl, Integer evaluate, String evaluateStartTime, String evaluateStopTime) {
        this.id = id;
        this.projectNumber = projectNumber;
        this.classNumber = classNumber;
        this.className = className;
        this.siUnitId = siUnitId;
        this.hostUnit = hostUnit;
        this.teacherName = teacherName;
        this.phoneNumber = phoneNumber;
        this.schedulingId = schedulingId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.trainingExpense = trainingExpense;
        this.regPlace = regPlace;
        this.otherCharges = otherCharges;
        this.hotelPlace = hotelPlace;
        this.diningPlace = diningPlace;
        this.startStopTime = startStopTime;
        this.increaseDay = increaseDay;
        this.fileUrl = fileUrl;
        this.evaluate = evaluate;
        this.evaluateStartTime = evaluateStartTime;
        this.evaluateStopTime = evaluateStopTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
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

    public String getSiUnitId() {
        return siUnitId;
    }

    public void setSiUnitId(String siUnitId) {
        this.siUnitId = siUnitId;
    }

    public String getHostUnit() {
        return hostUnit;
    }

    public void setHostUnit(String hostUnit) {
        this.hostUnit = hostUnit;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Long schedulingId) {
        this.schedulingId = schedulingId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getTrainingExpense() {
        return trainingExpense;
    }

    public void setTrainingExpense(Double trainingExpense) {
        this.trainingExpense = trainingExpense;
    }

    public String getRegPlace() {
        return regPlace;
    }

    public void setRegPlace(String regPlace) {
        this.regPlace = regPlace;
    }

    public Double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public String getHotelPlace() {
        return hotelPlace;
    }

    public void setHotelPlace(String hotelPlace) {
        this.hotelPlace = hotelPlace;
    }

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    public String getStartStopTime() {
        return startStopTime;
    }

    public void setStartStopTime(String startStopTime) {
        this.startStopTime = startStopTime;
    }

    public Double getIncreaseDay() {
        return increaseDay;
    }

    public void setIncreaseDay(Double increaseDay) {
        this.increaseDay = increaseDay;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Integer evaluate) {
        this.evaluate = evaluate;
    }

    public String getEvaluateStartTime() {
        return evaluateStartTime;
    }

    public void setEvaluateStartTime(String evaluateStartTime) {
        this.evaluateStartTime = evaluateStartTime;
    }

    public String getEvaluateStopTime() {
        return evaluateStopTime;
    }

    public void setEvaluateStopTime(String evaluateStopTime) {
        this.evaluateStopTime = evaluateStopTime;
    }
}
