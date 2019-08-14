package tjuninfo.training.task.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TeachSenseDiningDataForDiningStatisticsDTO implements Serializable {
    //    班级id
    private BigInteger classId;
    //    早餐就餐人次
    private BigDecimal breakfastEatCounts;
    //    午餐就餐人次
    private BigDecimal lunchEatCounts;
    //    晚餐就餐人次
    private BigDecimal dinnerEatCounts;
    //    就餐地点
    private String diningPlace;

    public BigInteger getClassId() {
        return classId;
    }

    public void setClassId(BigInteger classId) {
        this.classId = classId;
    }

    public BigDecimal getBreakfastEatCounts() {
        return breakfastEatCounts;
    }

    public void setBreakfastEatCounts(BigDecimal breakfastEatCounts) {
        this.breakfastEatCounts = breakfastEatCounts;
    }

    public BigDecimal getLunchEatCounts() {
        return lunchEatCounts;
    }

    public void setLunchEatCounts(BigDecimal lunchEatCounts) {
        this.lunchEatCounts = lunchEatCounts;
    }

    public BigDecimal getDinnerEatCounts() {
        return dinnerEatCounts;
    }

    public void setDinnerEatCounts(BigDecimal dinnerEatCounts) {
        this.dinnerEatCounts = dinnerEatCounts;
    }

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }
}
