package tjuninfo.training.task.dao;

import tjuninfo.training.support.BTView;
import tjuninfo.training.support.dao.IBaseDao;
import tjuninfo.training.task.entity.OfflineData;

import java.util.List;

/**
 * 用户数据层接口
 * @author shenxianyan
 * @date 2018年5月16日
 */
public interface IOfflineDataDao extends IBaseDao<OfflineData>{


	/**
	 * 分页
	 * @return
	 */
	public List<OfflineData> getinfo(BTView<OfflineData> bt,String area,String month,String time,String status) ;

	/**
	 * 人脸分页
	 * @return
	 */
	public List<OfflineData> getFaceInfo(BTView<OfflineData> bt,String area,String month,String time,String status) ;

}
