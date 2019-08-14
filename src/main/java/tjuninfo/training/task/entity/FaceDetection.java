package tjuninfo.training.task.entity;

import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;

/**
 * @author wubin
 * @version 1.0.0
 * @description TDOO
 * @ClassName FaceDetection
 * @date 2019/1/15 23:27
 **/
@Entity
@Table(name = "face_detection")
public class FaceDetection extends BaseEntity {
    /**主键**/
    private int deviceId;
    /**机器识别码**/
    private String deviceSn;
    /**身份证ID**/
    private String idNumber;
    /**注册地点**/
    private String registerArea;
    /**更新时间**/
    private String updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Column(name = "device_sn")
    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    @Column(name = "id_number")
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Column(name = "register_area")
    public String getRegisterArea() {
        return registerArea;
    }

    public void setRegisterArea(String registerArea) {
        this.registerArea = registerArea;
    }

    @Column(name = "update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
