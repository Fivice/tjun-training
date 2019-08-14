package tjuninfo.training.task.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tjuninfo.training.task.entity.MessageHistory;
import tjuninfo.training.task.entity.SysUser;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageHistoryVO implements Serializable {
    //private ClassInfo classInfo;
    private MessageHistory messageHistory;
    private SysUser sysUser;
}
