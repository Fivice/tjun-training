package tjuninfo.training.task.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报到登记表对应的学生
 * @author win7
 *
 */
public class RegisterVo implements Serializable{
	private static final long serialVersionUID = 1L;
//	登记表中班级对应的学生数
	private BigDecimal studentCount;

	public BigDecimal getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(BigDecimal studentCount) {
		this.studentCount = studentCount;
	}
}
