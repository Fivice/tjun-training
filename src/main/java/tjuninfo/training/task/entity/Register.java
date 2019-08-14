package tjuninfo.training.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 报到登记实体类
* @author 
 * @date 2018年5月27日
 */
@Entity
@Table(name = "register")
public class Register extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键**/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**学生id**/
    @Column(name = "si_id")
    private Integer siId;

	/**班级id**/
	@Column(name = "class_id")
	private Integer classId;

	@Column(name = "training_expense")
	private Double trainingExpense;		// 培训费

	@Column(name = "other_charges")
	private Double otherCharges;		// 其它费用

	@Column(name = "scaleFee_total")
	private Double scaleFeeTotal;		// 总住宿费用

	@Column(name = "food_total")
	private Double foodTotal;		// 总就餐费用

	/**是否住宿（0:标间，1:单间，2：不住宿）**/
	@Column(name = "hotel")
	private Integer hotel;

	@Column(name = "hotel_place")
	private String hotelPlace;		// 住宿地点

    /**是否就餐（1:就餐，2：不就餐）**/
    @Column(name = "dining")
    private String dining;

	/**是否缴费（1:已交，2：未交，3：国网商旅，4：统一转账）**/
	@Column(name = "pay")
	private String pay;

	/**学员流水号*/
	@Column(name = "number")
	private String number;

	/**学员是否已对课程评价  默认为0   0：未评价   1：已评价**/
	@Column(name = "status")
	private Integer status;

	@Transient
	private String PhotoStr;

	public String getPhotoStr() {
		return PhotoStr;
	}

	public void setPhotoStr(String photoStr) {
		PhotoStr = photoStr;
	}

	public Register(Integer id) {
		this.id = id;
	}

	public Register() {

	}

	/**报到时间**/
	@Column(name = "report_time")

	private String reportTime;

	/**报名地点**/
    @Column(name = "place")
    private String place;

    //早餐安排总次数
	@Transient
	private Integer breakfastTotal;
	//午餐安排总次数
	@Transient
	private Integer lunchTotal;
	//晚餐安排总次数
	@Transient
	private Integer dinnerTotal;

	public Integer getBreakfastTotal() {
		return breakfastTotal;
	}

	public String getHotelPlace() {
		return hotelPlace;
	}

	public void setHotelPlace(String hotelPlace) {
		this.hotelPlace = hotelPlace;
	}

	public void setBreakfastTotal(Integer breakfastTotal) {
		this.breakfastTotal = breakfastTotal;
	}

	public Integer getLunchTotal() {
		return lunchTotal;
	}

	public void setLunchTotal(Integer lunchTotal) {
		this.lunchTotal = lunchTotal;
	}

	public Integer getDinnerTotal() {
		return dinnerTotal;
	}

	public void setDinnerTotal(Integer dinnerTotal) {
		this.dinnerTotal = dinnerTotal;
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
    /*	@Column(name = "create_by")
	private String createBy;		// 创建者
	@Column(name = "create_date")
	private String createDate;		// 创建时间
	@Column(name = "update_by")
	private String updateBy;		// 更新者
	@Column(name = "update_date")
	private String updateDate;		// 更新时间
	@Column(name = "remarks")
	private String remarks;		// 备注信息*/

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getTrainingExpense() {
		return trainingExpense;
	}

	public void setTrainingExpense(Double trainingExpense) {
		this.trainingExpense = trainingExpense;
	}

	public Integer getId() {
		return id;
	}

	public Double getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiId() {
		return siId;
	}

	public void setSiId(Integer siId) {
		this.siId = siId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getHotel() {
		return hotel;
	}

	public void setHotel(Integer hotel) {
		this.hotel = hotel;
	}

	public String getDining() {
		return dining;
	}

	public void setDining(String dining) {
		this.dining = dining;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
