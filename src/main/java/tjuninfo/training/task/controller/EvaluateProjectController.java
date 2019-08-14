package tjuninfo.training.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.entity.EvaluateProject;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.EvaluateProjectService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/*
评价项目维护
 */
@Controller
@RequestMapping(value = "/evaluate")
public class EvaluateProjectController extends BaseController {

    @Resource
    private EvaluateProjectService evaluateProjectservice;

    /*
     * 访问页面
     */
    @RequestMapping("/view")
    public String toEvaluateProject() {
        return "evaluateProject/evaluateProject_list";
    }

    /*
       查找数据表
     */
    @RequestMapping(value = "/findTable")
    @ResponseBody
    public void findTable(Model model, BTView<EvaluateProject> btView)throws IOException {
        String lc = request.getParameter("largeClass");
        Integer largeClass = Integer.parseInt(lc);
        List<EvaluateProject> evaluateProjectList = evaluateProjectservice.findEvaluateProjectList(btView,largeClass);
        btView.setRows(evaluateProjectList);
        btView.setTotal(btView.getTotal());
        super.writeJSON(btView);
    }


    /*
     * GET 添加页面
     */
    @RequestMapping(value = "/add/view")
    public String getInsertPage() {
        return "evaluateProject/evaluateProject_add";
    }

    /*
     * POST 保存信息
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object insert(EvaluateProject evaluateProject) {
        evaluateProjectservice.saveOrUpdate(evaluateProject);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }


    /*
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids", required = false) String ids) {
        String[] result = ids.split(",");
        for (int i = 0; i < result.length; i++) {
            int id = Integer.parseInt(result[i]);
            evaluateProjectservice.deleteByNid(id);
        }
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }


    /*
     * GET 更新页面
     * @GetMapping(value = "/{id}/edit")
     */
    @GetMapping(value = "/{id}/edit")
    public String getUpdatePage(Model model, @PathVariable("id")int id) {
        EvaluateProject evaluateProject = evaluateProjectservice.get(id);
        model.addAttribute("evaluateProject", evaluateProject);
        return "evaluateProject/evaluateProject_update";
    }

    /*
     * PUT 根据Id来更新对象
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public Object update(EvaluateProject evaluateProject, @PathVariable("id") int id) {
        evaluateProjectservice.update(evaluateProject);
        return new CmsResult(CommonReturnCode.SUCCESS, 1);
    }

}
