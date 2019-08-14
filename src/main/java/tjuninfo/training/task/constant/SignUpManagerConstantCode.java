package tjuninfo.training.task.constant;

import java.util.List;
import java.util.Map;

public class SignUpManagerConstantCode {
    //空支付条件
    public static final int FAIL_PAY =0;
    //已支付条件
    public static final int IS_PAY =1;
    //未支付条件
    public static final int NO_PAY =2;

    //登录人 不是班主任时的userId
    public static final int NOT_HEADMASTER =0;

    //登录人 班主任
    public static final String HEAD_MASTER ="班主任";

    //学员已经报到
    public static final String IS_REGISTER = "1";
    //学员未报到
    public static final String IS_NOT_REGISTER = "2";
    //学员已报名
    public static final String IS_SIGN = "3";
    //学员未报名
    public static final String IS_NOT_SIGN ="4";

    //报名成功
    public static final String REGISTER_SUCCESS = "3";

    /*学员状态码status*/
    public static final String STUDENT_STATUS_VALID = "0";
    public static final String STUDENT_STATUS_INVALID = "1";

    /*学员评价状态evaluate*/
    public static final int STUDENT_EVALUATE = 1;//已评价
    public static final int STUDENT_NOT_EVALUATE = 0;//未评价

}
