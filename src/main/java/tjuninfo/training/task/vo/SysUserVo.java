package tjuninfo.training.task.vo;

import java.io.Serializable;

/**
 * 用户vo
 * @author shenxianyan
 * @date 2018年6月5日
 */
public class SysUserVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键**/
    private Integer userId;

    /**登录账号**/
    private String loginAccount;

    /**登录密码**/
    private String loginPass;

    /**头像**/
    private String userHead;

    /**状态**/
    private Integer state;
    
    /**注册时间**/
    private String registerTime;

    /**部门**/
    private DepartmentVo departmentVo;
    
    /**级别**/
    private String role;
    
    /**用户真实姓名**/
    private String userName;

    /**手机号**/
    private String telephone;
    
    /**生日**/
    private Integer birthday;
    
    /**性别**/
    private Integer sex;

    private String userType;
    private String email;
    
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public DepartmentVo getDepartmentVo() {
		return departmentVo;
	}
	public void setDepartmentVo(DepartmentVo departmentVo) {
		this.departmentVo = departmentVo;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getUserId(){
        return userId;
    }
    public void setUserId(Integer userId){
        this.userId= userId;
    }
    public String getLoginAccount(){
        return loginAccount;
    }
    public void setLoginAccount(String loginAccount){
        this.loginAccount= loginAccount;
    }
    public String getLoginPass(){
        return loginPass;
    }
    public void setLoginPass(String loginPass){
        this.loginPass= loginPass;
    }
    public String getUserHead(){
        return userHead;
    }
    public void setUserHead(String userHead){
        this.userHead= userHead;
    }
    public String getRegisterTime(){
        return registerTime;
    }
    public void setRegisterTime(String registerTime){
        this.registerTime= registerTime;
    }
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
}
