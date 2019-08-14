package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class StuDiningDataStatisticsVO implements Serializable {
    //就餐地点
    private String diningPlace;
    //早餐应到人次
    private BigDecimal breakfastEatCount;
    //中餐应到人次
    private BigDecimal lunchEatCount;
    //晚餐应到人次
    private BigDecimal dinnerEatCount;
    //应到人次合计费用
    private BigDecimal totalMoney;
    //早餐实际就餐人次
    private BigDecimal breakfastEatCountReal;
    //早餐实际就餐费用
    private BigDecimal breakfastEatMoneyReal;
    //中餐实际就餐人次
    private BigDecimal lunchEatCountReal;
    //中餐实际就餐费用
    private BigDecimal lunchEatMoneyReal;
    //晚餐实际就餐人次
    private BigDecimal dinnerEatCountReal;
    //晚餐实际就餐费用
    private BigDecimal dinnerEatMoneyReal;

    public StuDiningDataStatisticsVO(String diningPlace, BigDecimal breakfastEatCount, BigDecimal lunchEatCount, BigDecimal dinnerEatCount, BigDecimal totalMoney, BigDecimal breakfastEatCountReal, BigDecimal breakfastEatMoneyReal, BigDecimal lunchEatCountReal, BigDecimal lunchEatMoneyReal, BigDecimal dinnerEatCountReal, BigDecimal dinnerEatMoneyReal) {
        this.diningPlace = diningPlace;
        this.breakfastEatCount = breakfastEatCount;
        this.lunchEatCount = lunchEatCount;
        this.dinnerEatCount = dinnerEatCount;
        this.totalMoney = totalMoney;
        this.breakfastEatCountReal = breakfastEatCountReal;
        this.breakfastEatMoneyReal = breakfastEatMoneyReal;
        this.lunchEatCountReal = lunchEatCountReal;
        this.lunchEatMoneyReal = lunchEatMoneyReal;
        this.dinnerEatCountReal = dinnerEatCountReal;
        this.dinnerEatMoneyReal = dinnerEatMoneyReal;
    }

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    public BigDecimal getBreakfastEatCount() {
        return breakfastEatCount;
    }

    public void setBreakfastEatCount(BigDecimal breakfastEatCount) {
        this.breakfastEatCount = breakfastEatCount;
    }

    public BigDecimal getLunchEatCount() {
        return lunchEatCount;
    }

    public void setLunchEatCount(BigDecimal lunchEatCount) {
        this.lunchEatCount = lunchEatCount;
    }

    public BigDecimal getDinnerEatCount() {
        return dinnerEatCount;
    }

    public void setDinnerEatCount(BigDecimal dinnerEatCount) {
        this.dinnerEatCount = dinnerEatCount;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getBreakfastEatCountReal() {
        return breakfastEatCountReal;
    }

    public void setBreakfastEatCountReal(BigDecimal breakfastEatCountReal) {
        this.breakfastEatCountReal = breakfastEatCountReal;
    }

    public BigDecimal getBreakfastEatMoneyReal() {
        return breakfastEatMoneyReal;
    }

    public void setBreakfastEatMoneyReal(BigDecimal breakfastEatMoneyReal) {
        this.breakfastEatMoneyReal = breakfastEatMoneyReal;
    }

    public BigDecimal getLunchEatCountReal() {
        return lunchEatCountReal;
    }

    public void setLunchEatCountReal(BigDecimal lunchEatCountReal) {
        this.lunchEatCountReal = lunchEatCountReal;
    }

    public BigDecimal getLunchEatMoneyReal() {
        return lunchEatMoneyReal;
    }

    public void setLunchEatMoneyReal(BigDecimal lunchEatMoneyReal) {
        this.lunchEatMoneyReal = lunchEatMoneyReal;
    }

    public BigDecimal getDinnerEatCountReal() {
        return dinnerEatCountReal;
    }

    public void setDinnerEatCountReal(BigDecimal dinnerEatCountReal) {
        this.dinnerEatCountReal = dinnerEatCountReal;
    }

    public BigDecimal getDinnerEatMoneyReal() {
        return dinnerEatMoneyReal;
    }

    public void setDinnerEatMoneyReal(BigDecimal dinnerEatMoneyReal) {
        this.dinnerEatMoneyReal = dinnerEatMoneyReal;
    }
}
