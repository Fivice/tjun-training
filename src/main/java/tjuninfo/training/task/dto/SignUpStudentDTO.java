package tjuninfo.training.task.dto;

import tjuninfo.training.task.entity.Student;

import java.io.Serializable;

public class SignUpStudentDTO implements Serializable {

    private Student student;    //学员信息

    private Double otherCharges;//
    private Double trainingExpense;
    private Double scaleFeeTotal;
    private Double foodTotal;
    private String hotelPlace;  //就餐地点
    private String dining;     // 是否就餐信息
    private Integer hotel;      // 是否住宿信息
    private String pay;        // 是否交费信息
    private String reportTime;  // 报道时间
    private String place;       //报道地点
    private String number;      //流水号

    public SignUpStudentDTO() {}

    public SignUpStudentDTO(Student student, Double otherCharges, Double trainingExpense, Double scaleFeeTotal, Double foodTotal, String hotelPlace, String dining, Integer hotel, String pay, String reportTime, String place, String number) {
        this.student = student;
        this.otherCharges = otherCharges;
        this.trainingExpense = trainingExpense;
        this.scaleFeeTotal = scaleFeeTotal;
        this.foodTotal = foodTotal;
        this.hotelPlace = hotelPlace;
        this.dining = dining;
        this.hotel = hotel;
        this.pay = pay;
        this.reportTime = reportTime;
        this.place = place;
        this.number = number;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public String getHotelPlace() {
        return hotelPlace;
    }

    public void setHotelPlace(String hotelPlace) {
        this.hotelPlace = hotelPlace;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
