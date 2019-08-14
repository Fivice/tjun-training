package tjuninfo.training.task.vo;

import java.io.Serializable;

public class MessageFromClassInfoVO implements Serializable {

    private String classNumber;     //班级编号
    private String className;		// 班级名称
    private String hostUnit;		// 主办单位（手写）
    private String startStopTime;		// 办班时间段
    private String teacherName;		// 班主任名称
    private String phoneNumber;           //联系方式
    private String regPlace;		// 报到地点
    private String entryStartTime;		// 报名开始时间
    private String hotelPlace;		// 住宿地点
    private String diningPlace;		// 就餐地点
    private Double trainingExpense;		// 培训费
    private Double otherCharges;		// 其它费用

    public MessageFromClassInfoVO(String classNumber, String className, String hostUnit, String startStopTime, String teacherName, String phoneNumber, String regPlace, String entryStartTime, String hotelPlace, String diningPlace, Double trainingExpense, Double otherCharges) {
        this.classNumber = classNumber;
        this.className = className;
        this.hostUnit = hostUnit;
        this.startStopTime = startStopTime;
        this.teacherName = teacherName;
        this.phoneNumber = phoneNumber;
        this.regPlace = regPlace;
        this.entryStartTime = entryStartTime;
        this.hotelPlace = hotelPlace;
        this.diningPlace = diningPlace;
        this.trainingExpense = trainingExpense;
        this.otherCharges = otherCharges;
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

    public String getRegPlace() {
        return regPlace;
    }

    public void setRegPlace(String regPlace) {
        this.regPlace = regPlace;
    }

    public String getEntryStartTime() {
        return entryStartTime;
    }

    public void setEntryStartTime(String entryStartTime) {
        this.entryStartTime = entryStartTime;
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

    public Double getTrainingExpense() {
        return trainingExpense;
    }

    public void setTrainingExpense(Double trainingExpense) {
        this.trainingExpense = trainingExpense;
    }

    public Double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    @Override
    public String toString() {
        return "班级编号：'" + classNumber + '\'' +
                ", 班级名称：'" + className + '\'' +
                ", 主办单位：'" + hostUnit + '\'' +
                ", 培训时间：'" + startStopTime + '\'' +
                ", 班主任：'" + teacherName + '\'' +
                ", 联系方式：'" + phoneNumber + '\'' +
                ", 报到地点：'" + regPlace + '\'' +
                ", 报到时间：'" + entryStartTime + '\'' +
                ", 住宿地点：'" + hotelPlace + '\'' +
                ", 就餐地点：'" + diningPlace + '\'' +
                ", 培训费：" + trainingExpense +
                ", 其他费用：" + otherCharges;
    }
}
