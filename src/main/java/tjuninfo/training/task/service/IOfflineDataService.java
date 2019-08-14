package tjuninfo.training.task.service;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.service.IBaseService;
import tjuninfo.training.task.entity.OfflineData;
import tjuninfo.training.task.result.CmsResult;

import java.text.ParseException;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/29 10:28
 * @Description:就餐脱机数据业务层接口
 */
public interface IOfflineDataService extends IBaseService<OfflineData> {
    /**
     * 分页查询
     * @param bt
     * @param area
     * @param month
     * @return
     */
    public List<OfflineData> getinfo(BTView<OfflineData> bt,String area,String month,String time,String status);

    /**
     * 人脸数据分页查询
     * @param bt
     * @param area
     * @param month
     * @param time
     * @param status
     * @return
     */
    public List<OfflineData> getFaceInfo(BTView<OfflineData> bt,String area,String month,String time,String status);

    /**
     * 脱机数据存储
     * @param number
     * @param day
     * @return
     */
    public CmsResult offlineSave(String number, String day) throws ParseException;

    /**
     * 判断餐点（1早餐；2中餐；3晚餐）
     * @param time
     * @return
     */
    Integer dinner (String  time) throws ParseException;

}
