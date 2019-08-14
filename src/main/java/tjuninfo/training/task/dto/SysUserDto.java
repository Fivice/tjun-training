package tjuninfo.training.task.dto;

import tjuninfo.training.task.vo.DepartmentVo;

import java.io.Serializable;

/**
 * 用户vo
 * @author shenxianyan
 * @date 2018年6月5日
 */
public class SysUserDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键**/
    private Integer userId;


    /**用户真实姓名**/
    private String userName;

    /**手机号**/
    private String telephone;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
