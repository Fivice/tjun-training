
package tjuninfo.training.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import tjuninfo.training.support.BaseEntity;
import tjuninfo.training.task.util.excel.annotation.ExcelField;
import javax.persistence.*;


/**
 * 班级信息表Entity
 * @author CJ
 * @version 2018-09-25
 */
@Entity
@Table(name="class_info")
public class ClassInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**主键**/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "project_number")
	private String projectNumber;		// 项目编号
	@Column(name = "class_number")
	@ExcelField(title = "班级编号", align = 2, sort = 1)
	protected String classNumber;		// 班级编号
	@Column(name = "class_name")
	@ExcelField(title = "班级名称", align = 2, sort = 2)
	private String className;		// 班级名称
	/*@Column(name = "si_unitId")      // 主办单位（关联）
	private Integer siUnitId;*/
	@Column(name = "si_unitId")      // 主办单位（关联）
	private String siUnitId;
	@Column(name = "host_unit")
	@ExcelField(title = "主办单位", align = 2, sort = 9)
	private String hostUnit;		// 主办单位（手写）
	@Column(name = "organizer_unitId")      // 承办单位（关联）
	private Integer organizerUnitId;
	@Column(name = "organizer_unit")
	@ExcelField(title = "承办单位", align = 2, sort = 10)
	private String organizerUnit;		// 承办单位（手写）
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	@Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })*/
	@Column(name = "user_id")
	private Integer userId;		// 班主任
	@ExcelField(title = "联系人", align = 2, sort = 11)
	@Column(name = "teacherName")
	private String teacherName;		// 班主任名称
	@Column(name = "si_phone_number")
	private String phoneNumber;           //联系方式
	@Column(name = "scheduling_id")
	private Long schedulingId;		// 日程安排
	@Column(name = "area_id")
	private Integer areaId;		// 区域id
	@Column(name = "plan")
	private Integer plan;		// 0：计划内，1：计划外，2：非培训班
	@Transient
	@ExcelField(title = "计划类型",align = 2, sort = 3)
	private String planName;		// 0：计划内，1：计划外，2：非培训班
	@Column(name = "evaluate")
	private Integer evaluate;		// 评价  0：参评，1：不参评
	@Column(name = "subject")
	private String subject;            //评价课程

	@Transient
	@ExcelField(title = "是否评估", align = 2, sort = 14)
	private String evaluateName;
	/*@Transient
	@ExcelField(title = "人数", align = 2, sort = 8)
	private String count;*/
	@Column(name = "c_introduce")
	private String cintroduce;		// 简介
	@Column(name = "training_object")
	@ExcelField(title = "培训对象", align = 2, sort = 4)
	private String trainingObject;		// 培训对象
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="type_id")
	@Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	@ExcelField(title = "培训类型", value="trainingType.type" , align = 2, sort = 5)*/
	@Column(name = "type_id")
	private Integer typeId;		// 培训类型
	@ExcelField(title = "培训类型", align = 2, sort = 5)
	@Column(name = "typeName")
	private String typeName;		// 培训类型名称
	@ExcelField(title = "计划人数", align = 2, sort = 8)
	@Column(name = "planned_number")
	private Integer plannedNumber;		// 计划人数
	@Column(name = "training_expense")
	private Double trainingExpense;		// 培训费
	@Column(name = "inter_scale_fee")
	private Double interScaleFee;		// 标间费用
	@Column(name = "single_room_charge")
	private Double singleRoomCharge;		// 单间费用
	@Column(name = "reg_place")
	@ExcelField(title = "报名地点", align = 2, sort = 12)
	private String regPlace;		// 报名地点
	@Column(name = "other_charges")
	private Double otherCharges;		// 其它费用
	@Column(name = "expense_explanation")
	private String expenseExplanation;		// 费用说明
	@Column(name = "hotel_place")
	private String hotelPlace;		// 住宿地点
	@Column(name = "dining_place")
	private String diningPlace;		// 就餐地点
	@Column(name = "start_stop_time")
	private String startStopTime;		// 办班时间段
	@Column(name = "day_num")
	private Integer dayNum;		// 培训天数
	@Column(name = "time_num")
	private Double timeNum;		// 学时
	@Column(name = "increase_day")
	private Double increaseDay;		// 食宿增加天数
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="classroom")
	@Cascade( { org.hibernate.annotations.CascadeType.REFRESH } )
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })*/
	@Column(name = "classroom")
	private Integer classroom;		// 培训教室
	@Column(name = "classroomName")
	@ExcelField(title = "培训教室", align = 2, sort = 13)
	private String classroomName;		// 培训教室名称
	@Transient
	@ExcelField(title = "办班开始时间", align = 2, sort = 6)
	private String startTime;
	@Transient
	@ExcelField(title = "办班结束时间", align = 2, sort = 7)
	private String stopTime;
	@Column(name = "entry_start_time")
	private String entryStartTime;		// 报名开始时间
	@Column(name = "entry_stop_time")
	private String entryStopTime;		// 报名结束时间
	@Column(name = "evaluate_start_time")
	private String evaluateStartTime;		// 评价开始时间
	@Column(name = "evaluate_stop_time")
	private String evaluateStopTime;		// 评价结束时间
	@Column(name = "breakfast")
	private String breakfast;		// 早餐
	@Column(name = "lunch")
	private String lunch;		// 中餐
	@Column(name = "dinner")
	private String dinner;		// 晚餐
	@Column(name = "status")   //状态 0：开放 1：关闭
	private Integer status;
	@Column(name = "fileUrl")
	private String fileUrl;		// 相关文件地址
	@Column(name = "create_by")
	private String createBy;		// 创建者
	@Column(name = "create_date")
	private String createDate;		// 创建时间
	@Column(name = "update_by")
	private String updateBy;		// 更新者
	@Column(name = "update_date")
	private String updateDate;		// 更新时间
	@Column(name = "remarks")
	@ExcelField(title = "备注", align = 2, sort = 15)
	private String remarks;		// 备注信息
	@Column(name = "state")    //班级是否回收状态
	private String state;
	@Column(name = "state2")    //班级教师证是否回收状态
	private String state2;

	@Transient
	private Boolean administratorOrNot = false;//是否为超级管理员

	public Boolean getAdministratorOrNot() {
		return administratorOrNot;
	}

	public void setAdministratorOrNot(Boolean administratorOrNot) {
		this.administratorOrNot = administratorOrNot;
	}

	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}

	public String getEvaluateName() {
		return evaluateName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/*public String getCount() {

		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}*/

	public void setEvaluateName(String evaluateName) {

		this.evaluateName = evaluateName;
	}

	public String getState() {
		return state;

	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPlanName() {
		return planName;
	}

	public ClassInfo() {
	}

	public ClassInfo(Long id) {
		this.id = id;

	}

	public ClassInfo(Long id,Integer dayNum) {
		this.id = id;
		this.dayNum = dayNum;
	}

	public ClassInfo(String className, Double trainingExpense, Double interScaleFee, Double singleRoomCharge, String regPlace, Double otherCharges, Integer dayNum, Double increaseDay) {
		this.className = className;
		this.trainingExpense = trainingExpense;
		this.interScaleFee = interScaleFee;
		this.singleRoomCharge = singleRoomCharge;
		this.regPlace = regPlace;
		this.otherCharges = otherCharges;
		this.dayNum = dayNum;
		this.increaseDay = increaseDay;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public Double getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(Double timeNum) {
		this.timeNum = timeNum;
	}

	public Double getIncreaseDay() {
		return increaseDay;
	}

	public void setIncreaseDay(Double increaseDay) {
		this.increaseDay = increaseDay;
	}

	public String getDiningPlace() {
		return diningPlace;
	}

	public void setDiningPlace(String diningPlace) {
		this.diningPlace = diningPlace;
	}

	public Double getTrainingExpense() {

		return trainingExpense;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setTrainingExpense(Double trainingExpense) {
		this.trainingExpense = trainingExpense;
	}

	public Double getInterScaleFee() {
		return interScaleFee;
	}

	public void setInterScaleFee(Double interScaleFee) {
		this.interScaleFee = interScaleFee;
	}

	public Double getSingleRoomCharge() {
		return singleRoomCharge;
	}

	public void setSingleRoomCharge(Double singleRoomCharge) {
		this.singleRoomCharge = singleRoomCharge;
	}

	public Double getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(Double otherCharges) {
		this.otherCharges = otherCharges;
	}

	public String getExpenseExplanation() {
		return expenseExplanation;
	}

	public void setExpenseExplanation(String expenseExplanation) {
		this.expenseExplanation = expenseExplanation;
	}

	public Integer getOrganizerUnitId() {
		return organizerUnitId;
	}

	public void setOrganizerUnitId(Integer organizerUnitId) {
		this.organizerUnitId = organizerUnitId;
	}

	public String getOrganizerUnit() {
		return organizerUnit;
	}

	public void setOrganizerUnit(String organizerUnit) {
		this.organizerUnit = organizerUnit;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getProjectNumber() {

		return projectNumber;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	/*public Integer getSiUnitId() {
		return siUnitId;
	}

	public void setSiUnitId(Integer siUnitId) {
		this.siUnitId = siUnitId;
	}*/

	public String getSiUnitId() {
		return siUnitId;
	}

	public void setSiUnitId(String siUnitId) {
		this.siUnitId = siUnitId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getHostUnit() {
		return hostUnit;
	}

	public void setHostUnit(String hostUnit) {
		this.hostUnit = hostUnit;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getSchedulingId() {
		return schedulingId;
	}

	public void setSchedulingId(Long schedulingId) {
		this.schedulingId = schedulingId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getPlan() {
		return plan;
	}

	public void setPlan(Integer plan) {
		this.plan = plan;
	}

	public Integer getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Integer evaluate) {
		this.evaluate = evaluate;
	}

	public String getCintroduce() {
		return cintroduce;
	}

	public void setCintroduce(String cintroduce) {
		this.cintroduce = cintroduce;
	}

	public String getTrainingObject() {
		return trainingObject;
	}

	public void setTrainingObject(String trainingObject) {
		this.trainingObject = trainingObject;
	}


	public Integer getPlannedNumber() {
		return plannedNumber;
	}

	public void setPlannedNumber(Integer plannedNumber) {
		this.plannedNumber = plannedNumber;
	}

	public String getRegPlace() {
		return regPlace;
	}

	public void setRegPlace(String regPlace) {
		this.regPlace = regPlace;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getHotelPlace() {
		return hotelPlace;
	}

	public void setHotelPlace(String hotelPlace) {
		this.hotelPlace = hotelPlace;
	}

	public String getStartStopTime() {
		return startStopTime;
	}

	public void setStartStopTime(String startStopTime) {
		this.startStopTime = startStopTime;
	}

	public Integer getClassroom() {
		return classroom;
	}

	public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}

	public void setClassroom(Integer classroom) {
		this.classroom = classroom;
	}

	public String getEntryStartTime() {
		return entryStartTime;
	}

	public void setEntryStartTime(String entryStartTime) {
		this.entryStartTime = entryStartTime;
	}

	public String getEntryStopTime() {
		return entryStopTime;
	}

	public void setEntryStopTime(String entryStopTime) {
		this.entryStopTime = entryStopTime;
	}

	public String getEvaluateStartTime() {
		return evaluateStartTime;
	}

	public void setEvaluateStartTime(String evaluateStartTime) {
		this.evaluateStartTime = evaluateStartTime;
	}

	public String getEvaluateStopTime() {
		return evaluateStopTime;
	}

	public void setEvaluateStopTime(String evaluateStopTime) {
		this.evaluateStopTime = evaluateStopTime;
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}