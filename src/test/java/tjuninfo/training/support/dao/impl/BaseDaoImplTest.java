package tjuninfo.training.support.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.Student;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-hibernate.xml")
public class BaseDaoImplTest {

    @Test
    public void findByPage() {
    }
}