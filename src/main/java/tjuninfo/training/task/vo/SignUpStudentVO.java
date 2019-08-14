package tjuninfo.training.task.vo;

import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.entity.Student;

import java.io.Serializable;

public class SignUpStudentVO implements Serializable {
    private Student student;    //学生信息
    private ClassInfo classInfo;       //班级信息

//    private double totalCost;
    private String dining;     // 是否就餐信息
    private Integer hotel;      // 是否住宿信息
    private String pay;        // 是否交费信息
    private String reportTime;  // 报道时间
    private String place;       //报道地点
    private String hotelPlace;  //就餐地点
    private Double scaleFeeTotal;		// 总住宿费用
    private Double foodTotal;		// 总就餐费用
    private Double otherCharges;        // 其它费用
    private Double trainingExpense;		// 培训费
    private String number;      //流水号

    public SignUpStudentVO(Student student,ClassInfo classInfo, String dining, Integer hotel, String pay, String reportTime, String place, String hotelPlace, Double scaleFeeTotal, Double foodTotal, Double otherCharges, Double trainingExpense, String number) {
        this.student = student;
        this.classInfo = classInfo;
        this.dining = dining;
        this.hotel = hotel;
        this.pay = pay;
        this.reportTime = reportTime;
        this.place = place;
        this.hotelPlace = hotelPlace;
        this.scaleFeeTotal = scaleFeeTotal;
        this.foodTotal = foodTotal;
        this.otherCharges = otherCharges;
        this.trainingExpense = trainingExpense;
        this.number = number;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    public String getHotelPlace() {
        return hotelPlace;
    }

    public void setHotelPlace(String hotelPlace) {
        this.hotelPlace = hotelPlace;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDining() {
        return dining;
    }

    public void setDining(String dining) {
        this.dining = dining;
    }

    public Integer getHotel() {
        return hotel;
    }

    public void setHotel(Integer hotel) {
        this.hotel = hotel;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getScaleFeeTotal() {
        return scaleFeeTotal;
    }

    public void setScaleFeeTotal(Double scaleFeeTotal) {
        this.scaleFeeTotal = scaleFeeTotal;
    }

    public Double getFoodTotal() {
        return foodTotal;
    }

    public void setFoodTotal(Double foodTotal) {
        this.foodTotal = foodTotal;
    }

    public Double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Double getTrainingExpense() {
        return trainingExpense;
    }

    public void setTrainingExpense(Double trainingExpense) {
        this.trainingExpense = trainingExpense;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
