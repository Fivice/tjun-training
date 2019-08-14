package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName StuDiningStatisticsDataVO
 * @date 2019/1/30 20:50
 **/
public class StuDiningStatisticsDataVO implements Serializable {

    /** 就餐地点**/
    private String diningPlace;

    /**早餐安排**/
    private BigDecimal breakfastEatCount;

    /**早餐实到**/
    private BigDecimal breakfastEatCountReal;

    /**早餐实到金额**/
    private BigDecimal breakfastMoneyReal;

    /**中餐安排**/
    private BigDecimal lunchEatCount;

    /**中餐实到**/
    private BigDecimal lunchEatCountReal;

    /**中餐实到金额**/
    private BigDecimal lunchMoneyReal;

    /**晚餐安排**/
    private BigDecimal dinnerEatCount;

    /**晚餐实到**/
    private BigDecimal dinnerEatCountReal;

    /**晚餐实到金额**/
    private BigDecimal dinnerMoneyReal;

    /**实到合计金额**/
    private BigDecimal rCountMoney;

    /**应到合计金额**/
    private BigDecimal dCountMoney;

    public StuDiningStatisticsDataVO(String diningPlace, BigDecimal breakfastEatCount, BigDecimal breakfastEatCountReal, BigDecimal breakfastMoneyReal, BigDecimal lunchEatCount, BigDecimal lunchEatCountReal, BigDecimal lunchMoneyReal, BigDecimal dinnerEatCount, BigDecimal dinnerEatCountReal, BigDecimal dinnerMoneyReal, BigDecimal rCountMoney, BigDecimal dCountMoney) {
        this.diningPlace = diningPlace;
        this.breakfastEatCount = breakfastEatCount;
        this.breakfastEatCountReal = breakfastEatCountReal;
        this.breakfastMoneyReal = breakfastMoneyReal;
        this.lunchEatCount = lunchEatCount;
        this.lunchEatCountReal = lunchEatCountReal;
        this.lunchMoneyReal = lunchMoneyReal;
        this.dinnerEatCount = dinnerEatCount;
        this.dinnerEatCountReal = dinnerEatCountReal;
        this.dinnerMoneyReal = dinnerMoneyReal;
        this.rCountMoney = rCountMoney;
        this.dCountMoney = dCountMoney;
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

    public BigDecimal getBreakfastEatCountReal() {
        return breakfastEatCountReal;
    }

    public void setBreakfastEatCountReal(BigDecimal breakfastEatCountReal) {
        this.breakfastEatCountReal = breakfastEatCountReal;
    }

    public BigDecimal getBreakfastMoneyReal() {
        return breakfastMoneyReal;
    }

    public void setBreakfastMoneyReal(BigDecimal breakfastMoneyReal) {
        this.breakfastMoneyReal = breakfastMoneyReal;
    }

    public BigDecimal getLunchEatCount() {
        return lunchEatCount;
    }

    public void setLunchEatCount(BigDecimal lunchEatCount) {
        this.lunchEatCount = lunchEatCount;
    }

    public BigDecimal getLunchEatCountReal() {
        return lunchEatCountReal;
    }

    public void setLunchEatCountReal(BigDecimal lunchEatCountReal) {
        this.lunchEatCountReal = lunchEatCountReal;
    }

    public BigDecimal getLunchMoneyReal() {
        return lunchMoneyReal;
    }

    public void setLunchMoneyReal(BigDecimal lunchMoneyReal) {
        this.lunchMoneyReal = lunchMoneyReal;
    }

    public BigDecimal getDinnerEatCount() {
        return dinnerEatCount;
    }

    public void setDinnerEatCount(BigDecimal dinnerEatCount) {
        this.dinnerEatCount = dinnerEatCount;
    }

    public BigDecimal getDinnerEatCountReal() {
        return dinnerEatCountReal;
    }

    public void setDinnerEatCountReal(BigDecimal dinnerEatCountReal) {
        this.dinnerEatCountReal = dinnerEatCountReal;
    }

    public BigDecimal getDinnerMoneyReal() {
        return dinnerMoneyReal;
    }

    public void setDinnerMoneyReal(BigDecimal dinnerMoneyReal) {
        this.dinnerMoneyReal = dinnerMoneyReal;
    }

    public BigDecimal getrCountMoney() {
        return rCountMoney;
    }

    public void setrCountMoney(BigDecimal rCountMoney) {
        this.rCountMoney = rCountMoney;
    }

    public BigDecimal getdCountMoney() {
        return dCountMoney;
    }

    public void setdCountMoney(BigDecimal dCountMoney) {
        this.dCountMoney = dCountMoney;
    }
}
