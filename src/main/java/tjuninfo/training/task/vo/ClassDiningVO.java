package tjuninfo.training.task.vo;

import java.io.Serializable;

public class ClassDiningVO implements Serializable {

    private String day;          //就餐日期
    private String breakfast;		// 早餐就餐标准
    private String lunch;		// 中餐就餐标准
    private String dinner;		// 晚餐就餐标准
    private Integer isbreakfast; //早餐是否安排就餐
    private Integer islunch; //中餐是否安排就餐
    private Integer isdinner; //晚餐是否安排就餐

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public Integer getIsbreakfast() {
        return isbreakfast;
    }

    public void setIsbreakfast(Integer isbreakfast) {
        this.isbreakfast = isbreakfast;
    }

    public Integer getIslunch() {
        return islunch;
    }

    public void setIslunch(Integer islunch) {
        this.islunch = islunch;
    }

    public Integer getIsdinner() {
        return isdinner;
    }

    public void setIsdinner(Integer isdinner) {
        this.isdinner = isdinner;
    }
}
