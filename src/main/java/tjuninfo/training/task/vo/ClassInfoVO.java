package tjuninfo.training.task.vo;


import tjuninfo.training.task.entity.ClassInfo;

import java.io.Serializable;

public class ClassInfoVO implements Serializable {

    private Long id;

    private String projectNumber;		// 项目编号

    protected String classNumber;		// 班级编号

    private String className;		// 班级名称

    private String siUnitId;

    private String hostUnit;		// 主办单位（手写）

    private Integer organizerUnitId;

    private String organizerUnit;		// 承办单位（手写）

    private Integer userId;		// 班主任

    private String teacherName;		// 班主任名称

    private String phoneNumber;           //联系方式

    private Long schedulingId;		// 日程安排

    private Integer areaId;		// 区域id

    private Integer plan;		// 0：计划内，1：计划外，2：非培训班

    private String planName;		// 0：计划内，1：计划外，2：非培训班

    private Integer evaluate;		// 评价 0：已评价，1：未评价

    private String cintroduce;		// 简介

    private String trainingObject;		// 培训对象

    private Integer typeId;		// 培训类型

    private String typeName;		// 培训类型名称

    private Integer plannedNumber;		// 计划人数

    private Double trainingExpense;		// 培训费

    private Double interScaleFee;		// 标间费用

    private Double singleRoomCharge;		// 单间费用

    private String regPlace;		// 报名地点

    private Double otherCharges;		// 其它费用

    private String expenseExplanation;		// 费用说明

    private String hotelPlace;		// 住宿地点

    private String diningPlace;		// 就餐地点

    private String startStopTime;		// 办班时间段

    private Integer dayNum;		// 培训天数

    private Double timeNum;		// 学时

    private Double increaseDay;		// 食宿增加天数

    private Integer classroom;		// 培训教室

    private String classroomName;		// 培训教室名称

    private String entryStartTime;		// 报名开始时间

    private String entryStopTime;		// 报名结束时间

    private String evaluateStartTime;		// 评价开始时间

    private String evaluateStopTime;		// 评价结束时间

    private String breakfast;		// 早餐

    private String lunch;		// 中餐

    private String dinner;		// 晚餐

    private Integer status;

    private String fileUrl;		// 相关文件地址

    private String createBy;		// 创建者

    private String createDate;		// 创建时间

    private String updateBy;		// 更新者

    private String updateDate;		// 更新时间

    private String remarks;		// 备注信息

    public ClassInfoVO(ClassInfo classInfo) {
        this.id = classInfo.getId();
        this.projectNumber = classInfo.getProjectNumber();
        this.classNumber = classInfo.getClassNumber();
        this.className = classInfo.getClassName();
        this.siUnitId = classInfo.getSiUnitId();
        this.hostUnit = classInfo.getHostUnit();
        this.organizerUnitId = classInfo.getOrganizerUnitId();
        this.organizerUnit = classInfo.getOrganizerUnit();
        this.userId = classInfo.getUserId();
        this.teacherName = classInfo.getTeacherName();
        this.phoneNumber = classInfo.getPhoneNumber();
        this.schedulingId = classInfo.getSchedulingId();
        this.areaId = classInfo.getAreaId();
        this.plan = classInfo.getPlan();
        this.planName = classInfo.getPlanName();
        this.evaluate = classInfo.getEvaluate();
        this.cintroduce = classInfo.getCintroduce();
        this.trainingObject = classInfo.getTrainingObject();
        this.typeId = classInfo.getTypeId();
        this.typeName = classInfo.getTypeName();
        this.plannedNumber = classInfo.getPlannedNumber();
        this.trainingExpense = classInfo.getTrainingExpense();
        this.interScaleFee = classInfo.getInterScaleFee();
        this.singleRoomCharge = classInfo.getSingleRoomCharge();
        this.regPlace = classInfo.getRegPlace();
        this.otherCharges = classInfo.getOtherCharges();
        this.expenseExplanation = classInfo.getExpenseExplanation();
        this.hotelPlace = classInfo.getHotelPlace();
        this.diningPlace = classInfo.getDiningPlace();
        this.startStopTime = classInfo.getStartStopTime();
        this.dayNum = classInfo.getDayNum();
        this.timeNum = classInfo.getTimeNum();
        this.increaseDay = classInfo.getIncreaseDay();
        this.classroom = classInfo.getClassroom();
        this.classroomName = classInfo.getClassroomName();
        this.entryStartTime = classInfo.getEntryStartTime();
        this.entryStopTime = classInfo.getEntryStopTime();
        this.evaluateStartTime = classInfo.getEvaluateStartTime();
        this.evaluateStopTime = classInfo.getEvaluateStopTime();
        this.breakfast = classInfo.getBreakfast();
        this.lunch = classInfo.getLunch();
        this.dinner = classInfo.getDinner();
        this.status = classInfo.getStatus();
        this.fileUrl = classInfo.getFileUrl();
        this.createBy = classInfo.getCreateBy();
        this.createDate = classInfo.getCreateDate();
        this.updateBy = classInfo.getUpdateBy();
        this.updateDate = classInfo.getUpdateDate();
        this.remarks = classInfo.getRemarks();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
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

    public String getSiUnitId() {
        return siUnitId;
    }

    public void setSiUnitId(String siUnitId) {
        this.siUnitId = siUnitId;
    }

    public String getHostUnit() {
        return hostUnit;
    }

    public void setHostUnit(String hostUnit) {
        this.hostUnit = hostUnit;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
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

    public Integer getPlannedNumber() {
        return plannedNumber;
    }

    public void setPlannedNumber(Integer plannedNumber) {
        this.plannedNumber = plannedNumber;
    }

    public Double getTrainingExpense() {
        return trainingExpense;
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

    public String getRegPlace() {
        return regPlace;
    }

    public void setRegPlace(String regPlace) {
        this.regPlace = regPlace;
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

    public String getHotelPlace() {
        return hotelPlace;
    }

    public void setHotelPlace(String hotelPlace) {
        this.hotelPlace = hotelPlace;
    }

    public String getDiningPlace() {
        return diningPlace;
    }

    public void setDiningPlace(String diningPlace) {
        this.diningPlace = diningPlace;
    }

    public String getStartStopTime() {
        return startStopTime;
    }

    public void setStartStopTime(String startStopTime) {
        this.startStopTime = startStopTime;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
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

    public Integer getClassroom() {
        return classroom;
    }

    public void setClassroom(Integer classroom) {
        this.classroom = classroom;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
