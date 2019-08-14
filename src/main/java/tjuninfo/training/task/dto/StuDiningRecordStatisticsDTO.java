package tjuninfo.training.task.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName StuDiningRecordStatisticsDTO
 * @date 2019/1/30 23:59
 **/
public class StuDiningRecordStatisticsDTO implements Serializable {
    private String diningPlace;
    private BigDecimal breakfastEatCount;
    private BigDecimal lunchEatCount;
    private BigDecimal dinnerEatCount;

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
}
