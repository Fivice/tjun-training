package tjuninfo.training.task.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.dto.AnnualDataDto;
import tjuninfo.training.task.entity.*;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.IdGen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *证件制作控制器
 * @author
 * @date 2018年10月8日
 */
@Controller
@RequestMapping("/documentMaking")
public class DocumentMakingController extends BaseController{

	@Autowired
	private StudentCardService studentCardService;
    @Autowired
    private TeachRecoverService teachRecoverService;


	/**证件制作页面**/
	@RequestMapping("/view")
	public String view(Model model){
		return "documentMaking/documentMakingList2";
	}


	/**
	 * 生成流水号
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/flowCode")
	@ResponseBody
	public Object list(Model model) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//终止号
        String terminateNumber=request.getParameter("terminateNumber").trim();
        //起始号
        String startingNumber=request.getParameter("startingNumber").trim();
        String typeName=request.getParameter("typeName").trim();
        Integer sNumber=Integer.parseInt(startingNumber);
        int len=Integer.parseInt(terminateNumber)-Integer.parseInt(startingNumber);
        //学生流水号集合
		List<StudentCard> studentCards = new ArrayList<StudentCard>();
        //教师流水号集合
        List<TeacherCard> teacherCards = new ArrayList<TeacherCard>();
		for (int i = 0; i <len ; i++) {
			//流水号
			String number= autoGenericCode(sNumber.toString(),4)+String.valueOf(((int)(Math.random()*(9999-1000+1))+1000));
            if(typeName.equals("学员证")){
                List<StudentCard> studentCardList=studentCardService.findListBy(number);
                if(studentCardList.size()>0){
                    studentCards.add(studentCardList.get(0));
                }else{
                    StudentCard studentCard=new StudentCard();
                    studentCard.setNumber(number);
                    studentCardService.save(studentCard);
                    studentCardService.get(studentCard.getId());
                    studentCards.add(studentCard);
                }
            }else if(typeName.equals("教师证")){
                List<TeacherCard> teacherCardList=teachRecoverService.findListBy(number);
                if(teacherCardList.size()>0){
                    teacherCards.add(teacherCardList.get(0));
                }else{
                    TeacherCard teacherCard=new TeacherCard();
                    teacherCard.setNumber(number);
                    teachRecoverService.save(teacherCard);
                    teachRecoverService.get(teacherCard.getId());
                    teacherCards.add(teacherCard);
                }
            }

			sNumber++;
		}

        if(typeName.equals("学员证")) {
            return studentCards;
        }else{
            return teacherCards;
        }
	}

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     * @param code
     * @return
     */
    private String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code));

        return result;
    }

}
