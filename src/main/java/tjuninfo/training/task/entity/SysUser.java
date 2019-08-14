package tjuninfo.training.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import tjuninfo.training.support.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 用户的实体类
 *
 * @author shenxianyan
 * @date 2018年5月16日
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 登录账号
     **/
    @Column(name = "login_account", length = 30, unique = true)
    private String loginAccount;

    /**
     * 登录密码
     **/
    @NotNull(message = "buhhh")
    @Column(nullable = false, name = "login_pass", length = 65)
    private String loginPass;

    /**
     * 头像
     **/
    @Column(name = "user_head", length = 30)
    private String userHead;

    /**
     * 状态
     **/
    @Column(name = "state")
    private Integer state;

    /**
     * 用户真实姓名
     **/
    @Column(name = "userName")
    private String userName;

    /**
     * 手机号
     **/
    @Column(name = "telephone")
    private String telephone;

    /**
     * 生日
     **/
    @Column(name = "birthday")
    private Integer birthday;

    /**
     * 性别
     **/
    @Column(name = "sex")
    private Integer sex;

    /**
     * 注册时间
     **/
    @Column(name = "register_time", length = 30)
    private String registerTime;

    /**
     * 注册时分配的角色
     **/
    @Column(name = "role_id", length = 30)
    private Long role;

    /**
     * 上级用户类型
     **/
    @Column(name = "sup_user_type", length = 64)
    private String supType;


    /**
     * 用户类型
     **/
    @Column(name = "user_type", length = 64)
    private String userType;

    /**
     * 邮箱
     **/
    @Column(name = "email", length = 64)
    private String email;

    /**
     * 部门
     **/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_id", referencedColumnName = "area_id")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private Department department;

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupType() {
        return supType;
    }

    public void setSupType(String supType) {
        this.supType = supType;
    }
}
