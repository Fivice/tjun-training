package tjuninfo.training.task.util;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class IdGen {

    private static long code=1;//全局静态变量，（必须static修饰）每调用一次方法自增1
	
	/**
     * 带-的UUID
     *
     * @return 36位的字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
