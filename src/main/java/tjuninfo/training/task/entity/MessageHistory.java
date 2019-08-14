package tjuninfo.training.task.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "message_history")

public class MessageHistory implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 班级id
     */
    @Column(name = "classId",nullable = false)
    private Long classId;

    /**
     * 短信发送的手机号
     */
    @Column(name = "phone",nullable = false)
    private String phone;

    /**
     * 发送时间
     */
    @Column(name = "send_time",nullable = false)
    private Date sendTime;

    /**
     * 短信发送用户id
     */
    @Column(name = "user",nullable = false)
    private Integer user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
