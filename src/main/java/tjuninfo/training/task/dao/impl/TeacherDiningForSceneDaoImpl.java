package tjuninfo.training.task.dao.impl;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ITeacherDiningForSceneDao;
import tjuninfo.training.task.dto.*;
import tjuninfo.training.task.entity.TeacherDiningForScene;
import tjuninfo.training.task.util.Page;
import tjuninfo.training.task.util.StringUtils;

import javax.xml.transform.Transformer;
import java.math.BigInteger;
import java.util.List;

@Repository
public class TeacherDiningForSceneDaoImpl extends BaseDaoImpl<TeacherDiningForScene> implements ITeacherDiningForSceneDao {
    public TeacherDiningForSceneDaoImpl() {
        super(TeacherDiningForScene.class);
    }

    @Override
    public Page<TeacherInfoForSceneDTO> getTeacherInfoForScene(int pageSize, int pageIndex,String teacherName) {
        Page<TeacherInfoForSceneDTO> page = new Page<>();
        StringBuffer hql = new StringBuffer("Select * FROM (\n" +
                "select ti.ti_id AS teacherId, ti.ti_name AS teacherName,ti.si_ID_number AS idNumber,tdr.class_id AS classId from teacher_information ti LEFT JOIN\n" +
                " teacher_dining_register tdr on ti.ti_id = tdr.teacher_id AND tdr.class_id IN\n" +
                " (SELECT ci.id FROM class_info ci WHERE STR_TO_DATE(RIGHT(ci.start_stop_time,10),'%Y-%m-%d')>NOW() AND ci.dining_place IS NOT NULL AND ci.dining_place != '')\n" +
                ") temp WHERE 1=1 ");
        if (teacherName!=null&&!("").equals(teacherName)){
            hql.append("And teacherName LIKE '%").append(teacherName).append("%'");
        }
        Query query = getSession().createSQLQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(TeacherInfoForSceneDTO.class)).list().size();
        query.setFirstResult((pageIndex-1)*pageSize);
        query.setMaxResults(pageSize);

        List<TeacherInfoForSceneDTO> list = query.setResultTransformer(Transformers.aliasToBean(TeacherInfoForSceneDTO.class)).list();
        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageIndex);
        page.setTotalRecords(count);
        return page;
    }

    @Override
    public Page<ClassInfoForSceneDTO> getClassInfoForScene(int pageSize, int pageIndex,String className) {
        Page<ClassInfoForSceneDTO> page = new Page<>();
        StringBuffer hql = new StringBuffer("SELECT ci.id AS classId,ci.class_name AS className,ci.start_stop_time AS StartStopTime,CAST(GROUP_CONCAT(tdr.teacher_id SEPARATOR ',') AS CHAR) AS teacherId FROM\n" +
                " class_info ci LEFT JOIN teacher_dining_register tdr ON tdr.class_id = ci.id WHERE STR_TO_DATE(RIGHT(ci.start_stop_time,10),'%Y-%m-%d')>NOW() ");
        if (className !=null&&!("").equals(className)){
            hql.append(" AND ci.class_name LIKE '%").append(className).append("%'");
        }
        hql.append(" GROUP BY ci.id");
        Query query = getSession().createSQLQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(ClassInfoForSceneDTO.class)).list().size();
        query.setFirstResult((pageIndex-1)*pageSize);
        query.setMaxResults(pageSize);

        List<ClassInfoForSceneDTO> list = query.setResultTransformer(Transformers.aliasToBean(ClassInfoForSceneDTO.class)).list();
        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageIndex);
        page.setTotalRecords(count);
        return page;
    }

    @Override
    public Page<TeacherDiningForScene> getTeacherDiningForScene(int pageSize, int pageIndex, int teacherId, long classId) {
        Page<TeacherDiningForScene> page = new Page<>();
        StringBuffer hql = new StringBuffer(
                "Select tdfs.id AS id,tdfs.tdr_id AS teacherDiningRegId,tdfs.arrange_id AS arrangeId,tdfs.dining_palce AS diningPlace,tdfs.dining_date AS diningDate,tdfs.breakfast AS breakfast,tdfs.lunch AS lunch,tdfs.dinner AS dinner,tdfs.record_time AS recordTime " +
                "FROM teacher_dining_for_scene tdfs WHERE tdfs.tdr_id IN" +
                "( select tdr.id FROM teacher_dining_register tdr WHERE tdr.class_id = "+classId+" AND tdr.teacher_id = "+teacherId+" )");
        Query query = getSession().createSQLQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(TeacherDiningForScene.class)).list().size();

        query.setFirstResult((pageIndex-1)*pageSize);
        query.setMaxResults(pageSize);
        List<TeacherDiningForScene> list = query.setResultTransformer(Transformers.aliasToBean(TeacherDiningForScene.class)).list();
        page.setList(list);
        page.setTotalRecords(count);
        page.setPageNo(pageIndex);
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public TeacherDiningForScene getTeacherDining(BigInteger teacherDiningRegId, String diningDate) {
        StringBuffer hql = new StringBuffer("FROM TeacherDiningForScene tdfs WHERE tdfs.teacherDiningRegId = "+teacherDiningRegId);
        if (StringUtils.isNotBlank(diningDate)){
            hql.append(" AND tdfs.diningDate = \'").append(diningDate).append("\'");
        }
        Query query = getSession().createQuery(hql.toString());
        return (TeacherDiningForScene) query.uniqueResult();
    }

    @Override
    public List<TeacherDiningForScene> getTeacherDining(BigInteger teacherDiningRegId) {
        StringBuffer hql = new StringBuffer("FROM TeacherDiningForScene tdfs WHERE tdfs.teacherDiningRegId = "+teacherDiningRegId);
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public void deleteTeacherDiningForScene(long teacherDiningRegId) {
        StringBuffer hql = new StringBuffer("DELETE FROM teacher_dining_for_scene WHERE teacher_dining_for_scene.tdr_id = "+teacherDiningRegId);
        int query = getSession().createSQLQuery(hql.toString()).executeUpdate();
    }

    @Override
    public TeachSenseDiningDataForDiningStatisticsDTO getTeachDining(String diningPlace, String classId, String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT tdr.class_id AS classId,tdfs.dining_palce AS diningPlace,SUM(tdfs.breakfast=1) AS breakfastEatCounts,SUM(tdfs.lunch=1) AS lunchEatCounts,SUM(tdfs.dinner=1) AS dinnerEatCounts" +
                " FROM teacher_dining_for_scene tdfs INNER JOIN teacher_dining_register tdr ON tdfs.tdr_id = tdr.id");
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND tdr.class_id = ").append(classId);
        }
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND tdfs.dining_palce = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND tdfs.dining_date >='").append(startTime).append("'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND tdfs.dining_date<='").append(endTime).append("'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachSenseDiningDataForDiningStatisticsDTO.class));

        return (TeachSenseDiningDataForDiningStatisticsDTO) query.uniqueResult();
    }

    @Override
    public TeachDiningRecordForDiningStatisticsDTO getTeachDiningRecordStatistics(String diningPlace, String classId, String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT tdfr.dining_place AS diningPlace,SUM(tdfr.breakfast=1) AS breakfastEatCountReal,SUM(tdfr.lunch=1) AS lunchEatCountReal,SUM(tdfr.dinner=1) AS dinnerEatCountReal" +
                " FROM teach_dining_face_record tdfr WHERE 1=1");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND tdfr.dining_place = '"+diningPlace+"'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND tdfr.class_id = '"+classId+"'");
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND tdfr.`day`>='"+startTime+"'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND tdfr.`day`<='"+endTime+"'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningRecordForDiningStatisticsDTO.class));
        return (TeachDiningRecordForDiningStatisticsDTO) query.uniqueResult();
    }

    @Override
    public TeachSenseDiningDataForDiningStatisticsDTO getTeachFirstDayDining(String diningPlace, String classId,String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT tdr.class_id AS classId,tdfs.dining_palce AS diningPlace,SUM(tdfs.breakfast=1) AS breakfastEatCounts,SUM(tdfs.lunch=1) AS lunchEatCounts,SUM(tdfs.dinner=1) AS dinnerEatCounts " +
                "FROM teacher_dining_for_scene tdfs INNER JOIN");
        sql.append("(SELECT tdr.id AS tdr_id, tdr.class_id AS class_id,ci.start_stop_time AS start_stop_time FROM teacher_dining_register tdr INNER JOIN class_info ci ON tdr.class_id = ci.id)" +
                " tdr ON tdfs.tdr_id = tdr.tdr_id AND tdfs.dining_date = LEFT(tdr.start_stop_time,10) ");
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND tdr.class_id = '").append(classId).append("'");
        }
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND tdfs.dining_palce = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(startTime)){
            sql.append(" AND tdfs.dining_date >= '").append(startTime).append("'");
        }
        if (StringUtils.isNotBlank(endTime)){
            sql.append(" AND tdfs.dining_date <= '").append(endTime).append("'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachSenseDiningDataForDiningStatisticsDTO.class));
        return (TeachSenseDiningDataForDiningStatisticsDTO) query.uniqueResult();
    }

    @Override
    public TeachDiningRecordForDiningStatisticsDTO getTeachFirstDayDiningRecordStatistics(String diningPlace, String classId,String startTime, String endTime) {
        StringBuffer sql = new StringBuffer("SELECT tdfr.dining_place AS diningPlace,SUM(tdfr.breakfast=1) AS breakfastEatCountReal,SUM(tdfr.lunch=1) AS lunchEatCountReal,SUM(tdfr.dinner=1) AS dinnerEatCountReal" +
                " FROM teach_dining_face_record tdfr INNER JOIN class_info ci ON ci.id = tdfr.class_id AND tdfr.`day` = LEFT(ci.start_stop_time,10)");
        if (StringUtils.isNotBlank(diningPlace)){
            sql.append(" AND tdfr.dining_place = '").append(diningPlace).append("'");
        }
        if (StringUtils.isNotBlank(classId)){
            sql.append(" AND tdfr.class_id = '").append(classId).append("'");
        }
        Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(TeachDiningRecordForDiningStatisticsDTO.class));
        return (TeachDiningRecordForDiningStatisticsDTO) query.uniqueResult();
    }
}
