package tjuninfo.training.task.vo;

import java.io.Serializable;

/**
 * 接收短信的手机号 和 发送短信后的返回状态码
 */
public class MsgReturnStatusVO implements Serializable {
    //接收短信的手机号
    private String phone;
    //短信发送后返回的状态码
    private String returnCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
}
