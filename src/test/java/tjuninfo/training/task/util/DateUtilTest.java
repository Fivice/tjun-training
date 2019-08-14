package tjuninfo.training.task.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-hibernate.xml"})
@Transactional(transactionManager = "transactionManager")
public class DateUtilTest {
    @Test
    public void stringToDate() {
//        Date date = DateUtil.StringToDate("2018-10-16","yyyy-mm-dd");
//        System.out.println(date);
        String dateString = "2018-10-13 至 2018-10-16";
        int days = DateUtil.getIntervalDays(dateString);
    }
}