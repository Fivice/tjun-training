package tjuninfo.training.task.dao.impl;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import tjuninfo.training.support.dao.impl.BaseDaoImpl;
import tjuninfo.training.task.dao.ISignUpStudentDao;
import tjuninfo.training.task.dto.SignUpStudentDTO;
import tjuninfo.training.task.util.Page;

import java.util.List;

@Repository
public class SignUpStudentDaoImpl extends BaseDaoImpl<SignUpStudentDTO> implements ISignUpStudentDao {
    public SignUpStudentDaoImpl() {
        super(SignUpStudentDTO.class);
    }

    @Override
    public Page getSinUpStudentList(int pageSize, int pageIndex, long classId) {

        if (classId<=0){//非法id
            return null;
        }
        StringBuffer hql =new StringBuffer(
                "SELECT si as student,r.otherCharges as otherCharges,r.trainingExpense as trainingExpense,r.scaleFeeTotal as scaleFeeTotal,r.foodTotal as foodTotal,r.dining as dining,r.hotel as hotel,r.pay as pay,r.reportTime as reportTime,r.place as place,r.number as number" +
                        " FROM Student si INNER JOIN Register r ON si.siId = r.siId AND r.classId = "+classId
        ) ;
        Page<SignUpStudentDTO> page = new Page<>();
        //這裡需要注意的一點是返回的VO對象，如果有構造函數，則無參構造函數一定要是public
//        List<SignUpStudentDTO> list = super.getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(SignUpStudentDTO.class)).list();
        Query query = getSession().createQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(SignUpStudentDTO.class)).list().size();
        query.setFirstResult((pageIndex-1)*pageSize);
        query.setMaxResults(pageSize);
        List<SignUpStudentDTO> list = query.setResultTransformer(Transformers.aliasToBean(SignUpStudentDTO.class)).list();


        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageIndex);
        page.setTotalRecords(count);
        return page;
    }

    @Override
    public Page<SignUpStudentDTO> getSinUpStudentList(int pageSize, int pageIndex, long classId, String siName,String unitName, int pay,int dining,int hotel) {
        if (classId<=0){//非法id
            return null;
        }
        StringBuffer hql = new StringBuffer("SELECT si as student,r.otherCharges as otherCharges,r.trainingExpense as trainingExpense,r.hotelPlace as hotelPlace,r.scaleFeeTotal as scaleFeeTotal,r.foodTotal as foodTotal,r.dining as dining,r.hotel as hotel,r.pay as pay,r.reportTime as reportTime,r.place as place,r.number as number" +
                " FROM Student si INNER JOIN Register r ON si.siId = r.siId AND r.classId = "+classId);
        if(!("".equals(siName))&&siName!=null){
            hql.append(" AND si.siName like "+ "'%" + siName + "%'");
        }
        if(!("".equals(unitName))&&unitName!=null){
            hql.append(" AND si.unitName like "+ "'%" + unitName + "%'");
        }
        if(pay == 1 || pay == 2 || pay == 3||pay == 4){
            //表示有支付过滤条件，其他数值则忽略此过滤条件
            hql.append(" AND r.pay = ").append(pay);
        }
        //是否就餐（1:就餐，2：不就餐）
        if(dining == 1|| dining == 2){
            hql.append(" AND r.dining = ").append(dining);
        }
        //是否住宿（0:标间，1:单间，2：不住宿）
        if(hotel == 0 || hotel == 1|| hotel == 2){
            hql.append(" AND r.hotel = ").append(hotel);
        }
        Page<SignUpStudentDTO> page = new Page<>();
        //這裡需要注意的一點是返回的VO對象，如果有構造函數，則無參構造函數一定要是public
//        List<SignUpStudentDTO> list = super.getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(SignUpStudentDTO.class)).list();

        Query query = getSession().createQuery(hql.toString());
        int count = query.setResultTransformer(Transformers.aliasToBean(SignUpStudentDTO.class)).list().size();
        query.setFirstResult((pageIndex-1)*pageSize);
        query.setMaxResults(pageSize);
        List<SignUpStudentDTO> list = query.setResultTransformer(Transformers.aliasToBean(SignUpStudentDTO.class)).list();


        page.setPageSize(pageSize);
        page.setList(list);
        page.setPageNo(pageIndex);
        page.setTotalRecords(count);
        return page;
    }
}
