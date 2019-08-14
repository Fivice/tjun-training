package tjuninfo.training.task.vo;

import java.io.Serializable;

public class PayInfoForDiningAndHotelChangeVO implements Serializable {
    /**
     * 住宿费
     */
    private double hotelFee;
    /**
     * 就餐费
     */
    private double diningFee;

    public PayInfoForDiningAndHotelChangeVO(double hotelFee, double diningFee) {
        this.hotelFee = hotelFee;
        this.diningFee = diningFee;
    }

    public double getHotelFee() {
        return hotelFee;
    }

    public void setHotelFee(double hotelFee) {
        this.hotelFee = hotelFee;
    }

    public double getDiningFee() {
        return diningFee;
    }

    public void setDiningFee(double diningFee) {
        this.diningFee = diningFee;
    }
}
