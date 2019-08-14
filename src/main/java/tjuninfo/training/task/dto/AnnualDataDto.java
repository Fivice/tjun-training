package tjuninfo.training.task.dto;

import java.io.Serializable;

/**
 * 用户vo
 * @author shenxianyan
 * @date 2018年6月5日
 */
public class AnnualDataDto implements Serializable{
	

	/**主键**/
    private Integer id;

    /**月份**/
    private String month;

    /**班级数**/
    private Integer classNum;

    /**培训人数**/
    private Integer studentNum;

    /**培训天数**/
    private Integer dayNum;

    /**培训费用**/
    private Double trainingExpense;

    /**住宿费用**/
    private Double scaleFeeTotal;

    /**就餐费用**/
    private Double foodTotal;

    /**其它费用**/
    private Double otherCharges;

    /**就餐结余**/
    private Double balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getDayNum() {

        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
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

    public Double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
