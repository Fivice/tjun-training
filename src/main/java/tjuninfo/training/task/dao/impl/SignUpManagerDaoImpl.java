package tjuninfo.training.task.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ISignUpManagerDao;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.util.Pages;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class SignUpManagerDaoImpl extends BaseDaoImpl<ClassInfo> implements ISignUpManagerDao {

    public SignUpManagerDaoImpl(){
        super(ClassInfo.class);
    }

    @Override
    public Pages getList(int pageSize, int pageIndex, String status, String searchContent, String startStopTime) {
        StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1") ;
        if (status!=null && !status.equals("")){
            hql.append(" and c.status=").append(Integer.parseInt(status));
        }
        if (searchContent!=null && !searchContent.equals("")){
            hql.append(" and c.className like " + "'%").append(searchContent).append("%'");
        }
        if (searchContent!=null && !searchContent.equals("")){
            hql.append(" and c.teacherName like " + "'%").append(searchContent).append("%'");
        }
        if (searchContent!=null && !searchContent.equals("")){
            hql.append(" and c.hostUnit like " + "'%").append(searchContent).append("%'");
        }
        if (startStopTime!=null && !startStopTime.equals("")){
            hql.append(" and c.startStopTime like " + "'%").append(startStopTime).append("%'");
        }
        hql.append(" ORDER BY c.updateDate DESC ");

        Query query = getSession().createQuery(hql.toString());
        Pages pages = new Pages(pageIndex, pageSize, query);

        return pages;
    }

    @Override
    public long getCountByClassIdFromRegister(long classId) {
        StringBuffer hql =new StringBuffer("select count(*) from Register r where 1=1") ;
        if(classId <= 0){//非法id
            return -1;
        }else{
            hql.append(" and r.classId = ").append(classId);
        }
        long count = (long) getSession().createQuery(hql.toString()).uniqueResult();
        return count;
    }


    @Override
    public ClassInfo getClassInfoByClassId(long classId) {
        StringBuffer hql = new StringBuffer("From ClassInfo c Where 1=1");
        if (classId <=0){//非法id
            return null;
        }else{
            hql.append(" AND c.id= ").append(classId);
        }
        Query query = getSession().createQuery(hql.toString());
        ClassInfo classInfo = (ClassInfo) query.uniqueResult();
//        getSession().close();
        return classInfo;
    }

    @Override
    public Pages getClassInfoList(int pageSize, int pageIndex, String classNumber, String className, String teacherName, String startStopTime, int userId,String regPlace,String entryStartTime,String orderName,String orderBy) {
        StringBuffer hql =new StringBuffer("from ClassInfo c where 1=1");
        if(classNumber != null && !("").equals(classNumber)){
            hql.append(" and c.classNumber like " + "'%").append(classNumber).append("%'");
        }
        if(className != null && !("").equals(className)){
            hql.append(" and c.className like " + "'%").append(className).append("%'");
        }
        if(teacherName != null && !("").equals(teacherName)){
            hql.append(" and c.teacherName like " + "'%").append(teacherName).append("%'");
        }
        if(startStopTime != null && !("").equals(startStopTime)){
            hql.append(" and c.startStopTime like " + "'%").append(startStopTime).append("%'");
        }
        if( userId != 0){
            hql.append(" and c.userId = ").append(userId);
        }
        if (StringUtils.isNotBlank(regPlace)){
            hql.append(" and c.regPlace = '").append(regPlace).append("'");
        }
        if (StringUtils.isNotBlank(entryStartTime)){
            hql.append(" and c.entryStartTime like '").append(entryStartTime).append("%'");
        }
        if (StringUtils.isNotBlank(orderName)&&StringUtils.isNotBlank(orderBy)){
            hql.append(" ORDER BY c.").append(orderName).append(" ").append(orderBy);
        }
        Query query = getSession().createQuery(hql.toString());
        Pages pages = new Pages(pageIndex, pageSize, query);
        return pages;
    }

    @Override
    public boolean judgeUserIsRole(long roleId, String roleValue) {
        StringBuffer hql = new StringBuffer("SELECT sr.roleValue FROM SysRole sr WHERE sr.roleId = \'" +roleId+"\'");
        Query query = getSession().createQuery(hql.toString());
        String value = (String) query.uniqueResult();
        if(roleValue.equals(value)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public UserRole getUserRole(int userId) {
        StringBuffer hql = new StringBuffer("FROM UserRole ur WHERE ur.userId = " +userId);
        Query query = getSession().createQuery(hql.toString());
        return (UserRole) query.uniqueResult();
    }

    @Override
    public boolean judgeStudentHadPay(int studentId) {
        StringBuffer hql = new StringBuffer("Select r.pay FROM Register r WHERE r.siId = "+studentId);
        Query query = getSession().createQuery(hql.toString());
        String pay = (String) query.uniqueResult();
        if ("1".equals(pay)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean judgeStudentIdAndClassIdInRegister(int studentId, long classId) {
        StringBuffer hql = new StringBuffer("SELECT count(*) FROM Register r WHERE r.siId = "+studentId+" AND r.classId = "+classId);
        Query query = getSession().createQuery(hql.toString());
        long regCount = (long) query.uniqueResult();
        if(regCount == 0){
            return false;
        }else{
            return true;
        }

    }

    @Override
    public boolean studentPayConfirm(int studentId,long classId,int pay) {
        if(pay == 1||pay ==2){
            StringBuffer hql = new StringBuffer("UPDATE Register r SET pay = "+pay+" WHERE r.siId = "+studentId+" AND r.classId = "+classId);
            Query query = getSession().createQuery(hql.toString());
            int ret = query.executeUpdate();
            return ret==1?true:false;
        }
        return false;
    }

    @Override
    public boolean studentPayConfirm(int studentId, long classId, int pay, double trainingExpense, double otherCharges, double hotelFee, double diningFee) {
        if (pay == 1||pay == 2){
            StringBuffer hql = new StringBuffer("UPDATE Register r SET " +
                    "pay = "+pay+",trainingExpense ="+trainingExpense+",otherCharges ="+otherCharges+",scaleFeeTotal ="+hotelFee+",foodTotal ="+diningFee+" WHERE r.siId = "+studentId+" AND r.classId = "+classId);
            Query query = getSession().createQuery(hql.toString());
            int ret = query.executeUpdate();
            return ret==1?true:false;
        }
        return false;
    }

    @Override
    public List<Unit> getFatherUnitList() {
        StringBuffer hql = new StringBuffer("FROM Unit ui WHERE ui.status = 1 AND ui.areaType =1");
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public List<Unit> getChildUnitList(Integer fatherId) {
        if (fatherId <= 0){
            return null;
        }
        StringBuffer hql = new StringBuffer("FROM Unit ui WHERE ui.status = 1 AND ui.sjareaId ="+fatherId);
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public Register getRegisterInfo(long classId) {
        StringBuffer hql = new StringBuffer("FROM Register r WHERE r.classId = "+classId);
        Query query = getSession().createQuery(hql.toString());
//        query.setMaxResults(1);
        List list =query.list();
        return (Register) list.get(0);
    }

    @Override
    public Register getStudentRegisterInfo(Integer siId, long classId) {
        StringBuffer hql = new StringBuffer("FROM Register r WHERE r.classId = "+classId+" AND r.siId = "+siId);
        Query query = getSession().createQuery(hql.toString());
        return (Register) query.uniqueResult();
    }

    @Override
    public Student getStudentInfo(Integer siId) {
        StringBuffer hql = new StringBuffer("FROM Student s WHERE s.siId = "+siId);
        Query query = getSession().createQuery(hql.toString());
        return (Student) query.uniqueResult();
    }

    @Override
    public List<Register> getStudentTrainingHistoryList(Integer siId) {
        StringBuffer hql = new StringBuffer("FROM Register r WHERE r.siId = "+siId);
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public List<ClassDining> getClassDining(long classId) {
        if (classId<=0){//非法Id
            return null;
        }
        StringBuffer hql = new StringBuffer("From ClassDining cd WHERE cd.classRoom ="+classId);
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }

    @Override
    public List<StudentCard> getStudentCardInfo(String number) {
        StringBuffer hql = new StringBuffer("From StudentCard sc Where 1=1 ");
        if (!("").equals(number)&&number != null){
            hql.append(" AND sc.number = ").append("\'").append(number).append("\'");
        }
        Query query = getSession().createQuery(hql.toString());
        return query.list();
    }
}
