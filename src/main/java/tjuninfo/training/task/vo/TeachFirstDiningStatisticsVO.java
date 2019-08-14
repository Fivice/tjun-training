package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 教师首日就餐统计
 * @Description:
 */
public class TeachFirstDiningStatisticsVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**主键 编码**/
//    private Integer id;

    /** 就餐地点**/
    private String diningPlace;

    /**早餐安排**/
    private BigDecimal dBreakfast;

    /**早餐实到**/
    private BigDecimal rBreakfast;

//    /**早餐实到金额**/
//    private BigDecimal rBreakfastMoney;

    /**中餐安排**/
    private BigDecimal dLunch;

    /**中餐实到**/
    private BigDecimal rLunch;

//    /**中餐实到金额**/
//    private BigDecimal rLunchMoney;

    /**晚餐安排**/
    private BigDecimal dDinner;

    /**晚餐实到**/
    private BigDecimal rDinner;

//    /**晚餐实到金额**/
//    private BigDecimal rDinnerMoney;

//    /**实到合计金额**/
//    private BigDecimal rCountMoney;
//
//    /**应到合计金额**/
//    private BigDecimal dCountMoney;
//
//    /**就餐率**/
//    private BigDecimal diningRate;


    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    public BigDecimal getdBreakfast() {
        return dBreakfast;
    }

    public void setdBreakfast(BigDecimal dBreakfast) {
        this.dBreakfast = dBreakfast;
    }

    public BigDecimal getrBreakfast() {
        return rBreakfast;
    }

    public void setrBreakfast(BigDecimal rBreakfast) {
        this.rBreakfast = rBreakfast;
    }

    public BigDecimal getdLunch() {
        return dLunch;
    }

    public void setdLunch(BigDecimal dLunch) {
        this.dLunch = dLunch;
    }

    public BigDecimal getrLunch() {
        return rLunch;
    }

    public void setrLunch(BigDecimal rLunch) {
        this.rLunch = rLunch;
    }

    public BigDecimal getdDinner() {
        return dDinner;
    }

    public void setdDinner(BigDecimal dDinner) {
        this.dDinner = dDinner;
    }

    public BigDecimal getrDinner() {
        return rDinner;
    }

    public void setrDinner(BigDecimal rDinner) {
        this.rDinner = rDinner;
    }
}
