package tjuninfo.training.task.service.impl;

import org.springframework.stereotype.Service;
import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IBasicParametersDao;
import tjuninfo.training.task.dao.IStuDiningStatisticsDao;
import tjuninfo.training.task.entity.BasicParameters;
import tjuninfo.training.task.entity.StuDiningRecord;
import tjuninfo.training.task.service.StuDinRecordService;
import tjuninfo.training.task.service.StuDinStatisticsService;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.vo.StuDiningRecordVO;
import tjuninfo.training.task.vo.StuDiningStatisticsVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/30 16:38
 * @Description:就餐统计业务层接口实现类
 */
@Service
public class StuDinStatisticsServiceImpl extends BaseServiceImpl<StuDiningStatisticsVO> implements StuDinStatisticsService {

    private IStuDiningStatisticsDao stuDiningStatisticsDao;
    @Resource
    public void setDao(IStuDiningStatisticsDao stuDiningStatisticsDao) {
        this.stuDiningStatisticsDao = stuDiningStatisticsDao;
        this.dao = stuDiningStatisticsDao;
    }

    @Override
    public Page count(int pageSize, int pageNumber, String startDay, String endDay) {
        return stuDiningStatisticsDao.count(pageSize,pageNumber,startDay,endDay);
    }

}
