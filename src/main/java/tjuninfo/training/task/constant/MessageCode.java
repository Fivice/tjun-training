package tjuninfo.training.task.constant;

public class MessageCode {

    //发送短信用户id
    public static final String UID = "tjuninfo";
    //发送短信用户短信密钥
    public static final String KEY = "d41d8cd98f00b204e980";
    //发送短信请求地址
    public static final String POST_URL = "http://gbk.api.smschinese.cn";
    //发送短信使用的编码模式
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=gbk";
    //发送短信后接受返回值使用的编码模式
    public static final String RESULT_TYPE = "gbk";

}
