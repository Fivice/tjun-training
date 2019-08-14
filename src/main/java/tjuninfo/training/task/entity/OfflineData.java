package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * 就餐脱机数据的实体类
 *
 * @author shenxianyan
 * @date 2018年5月16日
 */
@Entity
@Table(name = "offline_data")
public class OfflineData extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 流水号
     **/
    @Column(name = "number", length = 30, unique = true)
    private String number;

    /**
     * 就餐地点
     **/
    @Column(name = "area", length = 30)
    private String area;

    /**
     * 日期
     **/
    @Column(name = "day")
    private String day;

    /**
     * 时间（1早餐；2中餐；3晚餐）
     **/
    @Column(name = "time")
    private Integer time;

    /**
     * 姓名
     **/
    @Column(name = "name")
    private String name;

    /**
     * 身份证
     **/
    @Column(name = "id_card")
    private String idCard;

    /**
     * 手机号
     **/
    @Column(name = "tel", length = 30)
    private String tel;

    /**
     * 单位
     **/
    @Column(name = "unit", length = 30)
    private String unit;

    /**
     * 上传者/接收者
     **/
    @Column(name = "uploader", length = 64)
    private String uploader;
    /**
     * 备注
     **/
    @Column(name = "remarks", length = 64)
    private String remarks;
    /**
     * 数据存储情况（2.失败、1成功、0未接收）
     **/
    @Column(name = "status")
    private Integer status;

    /**
     * 设备编号
     **/
    @Column(name = "equ_number", length = 64)
    private String deviceSn;

    /**
     * 类别（1教师、2学员）
     **/
    @Column(name = "o_category", length = 64)
    private Integer category;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }
}
