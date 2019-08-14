package tjuninfo.training.task.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TeachDiningRecordForDiningStatisticsDTO implements Serializable {
    private String diningPlace;
    private BigDecimal breakfastEatCountReal;
    private BigDecimal lunchEatCountReal;
    private BigDecimal dinnerEatCountReal;

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    public BigDecimal getBreakfastEatCountReal() {
        return breakfastEatCountReal;
    }

    public void setBreakfastEatCountReal(BigDecimal breakfastEatCountReal) {
        this.breakfastEatCountReal = breakfastEatCountReal;
    }

    public BigDecimal getLunchEatCountReal() {
        return lunchEatCountReal;
    }

    public void setLunchEatCountReal(BigDecimal lunchEatCountReal) {
        this.lunchEatCountReal = lunchEatCountReal;
    }

    public BigDecimal getDinnerEatCountReal() {
        return dinnerEatCountReal;
    }

    public void setDinnerEatCountReal(BigDecimal dinnerEatCountReal) {
        this.dinnerEatCountReal = dinnerEatCountReal;
    }
}
