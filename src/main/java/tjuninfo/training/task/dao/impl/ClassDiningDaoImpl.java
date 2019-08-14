package tjuninfo.training.task.dao.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ClassDiningDao;
import tjuninfo.training.task.dto.ClassDiningDataForDiningStatisticsDTO;
import tjuninfo.training.task.entity.ClassDining;
import tjuninfo.training.task.entity.ClassInfo;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.ClassDiningVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: win7
 * @Date: 2018/9/29 10:31
 * @Description:就餐安排管理数据层接口实现类
 */
@Repository
public class ClassDiningDaoImpl extends BaseDaoImpl<ClassDining> implements ClassDiningDao {


    public ClassDiningDaoImpl() {
        super(ClassDining.class);
    }


    @Override
    //查询所有的班级就餐信息
    public List<ClassDining> findAll() {
        List<ClassDining> list = super.getSession().createCriteria(ClassDining.class).list();
        return list;
    }

    @Override
    //根据班级编号查询相应的班级就餐信息
    public List<ClassDining> findClassDiningList(String classNum) {
        return super.getSession().createCriteria(ClassDining.class, "c").add(Restrictions.eq("c.classRoom",classNum)).addOrder(Order.asc("c.day")).list();
    }

    @Override
    public List<ClassDining> findClassDiningList(String classNum, String time) {
        StringBuffer hql =new StringBuffer("from ClassDining c where 1=1") ;
        if (classNum!=null && !classNum.equals("")){
            hql.append(" and c.classRoom="+ "'" + classNum.trim() + "'");
        }
        if (time!=null && !time.equals("")){
            hql.append(" and c.day="+ "'" + time.trim() + "'");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    //根据id号删除信息
    public void deleteById(int id) {
        super.getSession().createSQLQuery("delete from class_dining where id=:id")
                .setParameter("id", id).executeUpdate();
    }

    @Override
    public void deleteByClassNumber(String classNumber) {
        super.getSession().createSQLQuery("delete from class_dining where class_room=:classNumber")
                .setParameter("classNumber", classNumber).executeUpdate();
    }

    @Override
    public Pages findClassDiningList(int pageSize, int pageNumber, String s) {
        StringBuffer hql =new StringBuffer("from ClassDining c where 1=1") ;
        if (s != null && !s.equals("")){
            hql.append("and c.classRoom = "+Long.valueOf(s));
        }
        hql.append(" ORDER BY c.day  ");
        Query query = getSession().createQuery(hql.toString());
        Pages pages = new Pages(pageNumber, pageSize, query);
        return pages;
    }

    @Override
    public ClassDining findMax(String classId) {
        StringBuffer sql =new StringBuffer(" SELECT c.*from class_dining c where  c.class_room = '"+ classId +"' and c.`day` in(select max(c1.`day`) from class_dining c1 where c1.class_room = '"+classId+"')");
        return (ClassDining) this.getSession().createSQLQuery(sql.toString()).addEntity(ClassDining.class).uniqueResult();
    }

    @Override
    public boolean ifDing(String classId, String day, Integer dinner) {
        boolean r = false; day = day.substring(0,10);
        StringBuffer sql =new StringBuffer("SELECT * from class_dining c where c.class_room = '"+classId+"' and c.`day` = '"+day+"'");
        if(dinner ==1){//早餐
            sql.append("and c.breakfast = 1 ");
        }else if(dinner ==2){//中餐
            sql.append("and c.lunch = 1");
        }else if(dinner ==3) {//晚餐
            sql.append("and c.dinner = 1");
        }
        List<Object> list = this.getSession().createSQLQuery(sql.toString()).getResultList();
        if(list.size()>0){
            r = true;
        }
        return r;
    }

    @Override
    public List<ClassDining> byTime(String nowDate, ClassDining classDining, List<ClassInfo> classInfoList) {
        List<Integer> classId = new ArrayList<Integer>();
        StringBuffer sql = new StringBuffer("from ClassDining c where 1=1");
        if(classInfoList.size()>0){
            for(ClassInfo classInfo : classInfoList){
                classId.add(classInfo.getId().intValue());
            }
            if(classInfoList.size() > 1){
                sql.append(" and (c.classRoom = "+classId.get(0)+"");
                for(int i = 1 ; i<classId.size(); i++){
                    sql.append( " or c.classRoom = "+classId.get(i)+" ");
                }
                sql.append(")");
            }else {
                sql.append(" and (c.classRoom = "+classId.get(0)+")");
            }
        }
            if (classDining.getBreakfast() != null) {
                sql.append("and c.breakfast = " + 1);
            }
            if (classDining.getLunch() != null) {
                sql.append("and c.lunch = " + 1);
            }
            if (classDining.getDinner() != null) {
                sql.append("and c.dinner = " + 1);
            }
        sql.append("and c.day = " +"'"+nowDate+"'");
        return getSession().createQuery(sql.toString()).list();
    }

    @Override
    public List<ClassDining> byClassID(Long id) {
        StringBuffer sql = new StringBuffer("from ClassDining c where 1=1");
        if (id != null && !id.equals("")){
            sql.append("and c.classRoom = "+Long.valueOf(id));
        }
        return getSession().createQuery(sql.toString()).list();
    }

    @Override
    public ClassDiningDataForDiningStatisticsDTO getClassDining(String diningPlace, String classId,String startTime,String endTime) {

        StringBuffer sql = new StringBuffer("SELECT cd.class_room AS classId,SUM(cd.breakfast = 1) AS breakfastEatCounts,SUM(cd.lunch = 1) AS lunchEatCounts,SUM(cd.dinner =1) AS dinnerEatCounts,ci.dining_place AS diningPlace " +
                "FROM class_dining cd INNER JOIN class_info ci ON cd.class_room = ci.id");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND ci.dining_place = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND cd.class_room = ").append(classId);
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND `day`>= '").append(startTime).append("'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND `day`<= '").append(endTime).append("'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(ClassDiningDataForDiningStatisticsDTO.class));

        return (ClassDiningDataForDiningStatisticsDTO) query.uniqueResult();
    }

    @Override
    public ClassDiningDataForDiningStatisticsDTO getClassFirstDayDining(String diningPlace, String classId) {
        StringBuffer sql = new StringBuffer("SELECT cd.class_room AS classId,SUM(cd.breakfast = 1) AS breakfastEatCounts,SUM(cd.lunch = 1) AS lunchEatCounts,SUM(cd.dinner =1) AS dinnerEatCounts,ci.dining_place AS diningPlace " +
                "FROM class_dining cd INNER JOIN class_info ci ON cd.class_room = ci.id AND cd.`day` = LEFT(ci.start_stop_time,10)");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND ci.dining_place = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND cd.class_room = '").append(classId).append("'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(ClassDiningDataForDiningStatisticsDTO.class));
        return (ClassDiningDataForDiningStatisticsDTO) query.uniqueResult();
    }

    @Override
    public Pages findClassDiningListByClassId(int pageSize, int pageNumber, String classId) {
        StringBuffer hql = new StringBuffer("SELECT c.day as day,c.breakfast as isbreakfast,c.lunch as islunch,c.dinner as isdinner,(SELECT n.breakfast FROM ClassInfo as n WHERE n.id="+Long.valueOf(classId)+") as breakfast,(SELECT n.lunch FROM ClassInfo as n WHERE n.id="+Long.valueOf(classId)+") as lunch,(SELECT n.dinner FROM ClassInfo as n WHERE n.id="+Long.valueOf(classId)+") as dinner from ClassDining as c where c.classRoom = "+Long.valueOf(classId));
        hql.append(" ORDER BY c.day  ");
        Query query = getSession().createQuery(hql.toString());
        query.setResultTransformer(new AliasToBeanResultTransformer(
                ClassDiningVO.class));

        Pages pages = new Pages(pageNumber, pageSize, query);
        return pages;
    }
}

