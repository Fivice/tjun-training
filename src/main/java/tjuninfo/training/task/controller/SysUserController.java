package tjuninfo.training.task.controller;

import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tjuninfo.training.support.BTView;
import tjuninfo.training.support.controller.BaseController;
import tjuninfo.training.task.constant.CommonReturnCode;
import tjuninfo.training.task.dto.RoleMenuDTO;
import tjuninfo.training.task.entity.Department;
import tjuninfo.training.task.entity.SysRole;
import tjuninfo.training.task.entity.SysUser;
import tjuninfo.training.task.entity.UserRole;
import tjuninfo.training.task.result.CmsResult;
import tjuninfo.training.task.service.*;
import tjuninfo.training.task.util.CommonRegExUtils;
import tjuninfo.training.task.util.DateUtil;
import tjuninfo.training.task.util.Pages;
import tjuninfo.training.task.util.StringUtils;
import tjuninfo.training.task.vo.RoleMenuVO;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户控制器
 * @author shenxianyan
 * @date 2018年5月24日
 */
@Controller
@RequestMapping("/sysuser")
public class SysUserController extends BaseController{

	@Resource
	private ISysUserService userService;
	@Resource
	private ISysRoleService roleService;
	@Resource
	private IMenuService menuService;
	@Resource
	private IRoleMenuService roleMenuService;
	@Resource
	private ISysRoleService sysRoleService;
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private DepartmentService departmentService;
	@Resource
    private ISysUserService iSysUserService;

	/**首页**/
    @RequestMapping("/index")
    public String toIndex(Model model){
        SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
    	model.addAttribute("user",userInfo.getUserName());
		UserRole role = userRoleService.getUserRoleByUserId(userInfo.getUserId());
		boolean roleId = true;
		if(role.getRoleId()!=1&&role.getRoleId()!=3){
			roleId = false;
		}
		model.addAttribute("roleId",roleId);

		String day=	"";
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyMMdd");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		File file = new File("/upload/userOperate/tjun-training/");
		File [] files = file.listFiles();
		String [] names = file.list();
		if(names != null){
			for(File a:files){
				String fName = a.getName();
				if(fName.substring(0,6).equals(str)){
					day=fName;
				}
			}
		}
		model.addAttribute("day",day);
		return "user/index";
    }

	/**访问用户页面**/
	@RequestMapping("/user")
	public String toUser(Model model){
		//获取区域
		List<Department>  list = departmentService.getBySjreaId(0);//查询最上级区域，最上级代码默认为0
		//查找所有机房
		List<Department> sj = new ArrayList<>();
		sj = departmentService.list();
		model.addAttribute("sjAera",sj);
		return "user/user";
	}

	/**
	 * 分页查询用户
	 * @param user
	 * @param btView
	 * @throws IOException
	 */
	@RequestMapping(value = "/findUser")
	@ResponseBody
	public Object findUser(SysUser user,BTView<SysUser> btView) throws IOException{
		String loginAccount = request.getParameter("loginAccount").trim();
		String userName = request.getParameter("userName").trim();
		String departmentName =  request.getParameter("departmentName").trim();
		int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber").trim());
		Pages pages = userService.getUserList(pageSize,pageNumber,loginAccount,userName,departmentName);
		List<SysUser> sysUserList =pages.getResult();
		List<Map<String,Object>> mapList = new ArrayList<>();
		Map<String,Object> map1 = new HashMap<>();
		SysRole sysRole = null;

		for(SysUser sysUser : sysUserList){

			Long roleId = userRoleService.getRoleIdByUserId(sysUser.getUserId()).get(0).getRoleId();
			sysRole = sysRoleService.get(roleId);
			Map<String,Object> map = new HashMap<>();
			map.put("sysUser",sysUser);
			map.put("sysRole",sysRole);
			mapList.add(map);
		}
		map1.put("rows",  mapList);
		map1.put("total", pages.getTotalResults());
		return map1;
	}

	/**
	 * GET 创建用户页面
	 * @return
	 */
	@GetMapping(value = "/add/view")
	public String getInsertPage(Model model) {
		//获取区域
		List<Department>  list = departmentService.getBySjreaId(0);//查询最上级区域，最上级代码默认为0
		//查找所有机房
		List<Department> sj = new ArrayList<>();
		sj = departmentService.list();
		model.addAttribute("sjAera",sj);
		//查询所有角色
		List<SysRole> sysRole = sysRoleService.list();
		model.addAttribute("role", sysRole);
		return "/user/user_add";
	}

	/**
	 * POST 创建用户
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/add")
	public void insert(SysUser user) throws IOException {
		result.setSuccess(false);
		Long count = userService.getCountByProerties(new String[]{"loginAccount"}, new String[]{user.getLoginAccount()});
		if(count>0){
			result.setMessage("账号已存在");
			super.writeJSON(result);
			return;
		}
		try{
			//注册时间
			user.setRegisterTime(DateUtil.getDateTime(new Date()));
			//Sha256Hash加密
			user.setLoginPass(new Sha256Hash(user.getLoginPass()).toHex());
			user.setDepartment(departmentService.get(user.getDepartment().getAreaId()));
			userService.save(user);
			//角色分配
			Long roleId= user.getRole();
			if(roleId!=null&&!roleId.equals("")) {
				UserRole ur = new UserRole();
				ur.setRoleId(roleId);
				ur.setUserId(user.getUserId());
				userRoleService.save(ur);
			}
			session.setAttribute(USER_SESSION, user);
			result.setMessage("注册成功");
			result.setSuccess(true);
		}catch(Exception e){
			result.setMessage("注册失败");
		}
		super.writeJSON(result);
	}


	/**
	 * DELETE 删除用户
	 * @return
	 */
	@DeleteMapping(value = "/{userId}/delete")
	@ResponseBody
	public Object delete(@PathVariable("userId") Integer userId) {
		userService.deleteByPK(userId);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam(value="userIds",required=false)String userIds) {

		String [] result = userIds.split(",");
		for(int i = 0;i<result.length;i++){
			Integer userId= Integer.valueOf(result[i]);
			SysUser user =  userService.get(userId);
			user.setState(2);
			userService.save(user);
		}
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	/**
	 * GET 更新用户页面
	 * @return
	 */
	@GetMapping(value = "/{userId}/edit")
	public String getUpdatePage(Model model, @PathVariable("userId") Integer userId) {
		// 用户信息
		SysUser user = userService.get(userId);
		model.addAttribute("user", user);

		//获取原区域信息
		if(user.getDepartment()!=null&&!user.getDepartment().equals("")){
			Department d2= user.getDepartment();
			Department d= new Department();
			if(d2.getSjareaId()!=1) {
				d = departmentService.get(d2.getSjareaId());
				model.addAttribute("d", d);//上级区域
				model.addAttribute("d2", d2);//下级区域
			}else {
				model.addAttribute("d", d2);//上级区域
				model.addAttribute("d2", d);//下级区域
			}
		}

		//获取区域
		List<Department>  list = departmentService.getBySjreaId(0);//查询最上级区域，最上级代码默认为0
		//查找所有机房
		List<Department> sj = new ArrayList<>();
		sj = departmentService.list();
		model.addAttribute("sjAera",sj);
		return "user/user_update";
	}

	@PutMapping(value = "/{userId}/updatRoles")
	@ResponseBody
	public Object getUpdateRolesPage(Model model, @PathVariable("userId") Integer userId) {
		// 用户信息
		//	String roles = request.getParameter("selectRoles");
		String roleId = request.getParameter("roleId");
		userService.updateRoles(userId,roleId);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/**
	 * 角色分配
	 * @param model
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/{userId}/assignRoles")
	public String assignRoles(Model model, @PathVariable("userId") Integer userId) {
		SysUser user = userService.get(userId);
		model.addAttribute("user", user);

		List<SysRole> sysRole = sysRoleService.list();
		model.addAttribute("sysRole", sysRole);

		UserRole userRole = userRoleService.getUserRoleByUserId(userId);
		model.addAttribute("userRole", userRole);

		return "user/assign_roles";
	}

	/**
	 * PUT 根据url管理员ID来指定更新对象,并根据传过来的管理员信息来更新管理员信息
	 * @return
	 */
	@PutMapping(value = "/{userId}")
	@ResponseBody
	public Object update(SysUser user, @PathVariable("userId") Integer userId,
						 @RequestParam(required = false, value = "roleId") String[] roleIds) {
		// 更新用户及角色记录
			user.setDepartment(departmentService.get(user.getDepartment().getAreaId()));
		userService.update(user);
		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		userInfo=userService.get(userInfo.getUserId());
		session.setAttribute(USER_SESSION, userInfo);


		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}

	/**
	 * 用户登录
	 * @param user
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public void login(SysUser user,boolean rememberMe) throws IOException{
		result.setSuccess(false);
		//用户登录
		SysUser userInfo = userService.getByProerties(new String[]{"loginAccount"}, new String[]{user.getLoginAccount()},null);
		if(userInfo==null){
			result.setMessage("用户名错误");
			super.writeJSON(result);
			return;
		}
		if(!userInfo.getLoginPass().equals(new Sha256Hash(user.getLoginPass()).toHex())){
			result.setMessage("密码错误");
			super.writeJSON(result);
			return;
		}
		if(userInfo.getState().equals(0)){
			result.setMessage("该用户已被冻结！");
			super.writeJSON(result);
			return;
		}
		//存入session
		Subject subject = SecurityUtils.getSubject();
		//记得传入明文密码
		subject.login(new UsernamePasswordToken(userInfo.getLoginAccount(), user.getLoginPass(), rememberMe));
		session.setAttribute(USER_SESSION, userInfo);
		result.setMessage("登录成功");
		result.setSuccess(true);
		//记住密码
        if (rememberMe){
            Cookie loginAccount = new Cookie("loginAccount",user.getLoginAccount());
            loginAccount.setMaxAge(60*60);
            Cookie passWord = new Cookie("passWord",user.getLoginPass());
            passWord.setMaxAge(60*60);
        }
		super.writeJSON(result);
	}

	/**
	 * 用户注册
	 * @param user
	 * @throws IOException
	 */
	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
	public void register(SysUser user) throws IOException{
		result.setSuccess(false);
		Long count = userService.getCountByProerties(new String[]{"loginAccount"}, new String[]{user.getLoginAccount()});
		if(count>0){
			result.setMessage("账号已存在");
			super.writeJSON(result);
			return;
		}
		try{
			//注册时间
			user.setRegisterTime(DateUtil.getDateTime(new Date()));
			//Sha256Hash加密
			user.setLoginPass(new Sha256Hash(user.getLoginPass()).toHex());
			user.setState(1);
			user.setRole(1L);
			user.setUserName("未填写");
			user.setUserType("一般");
			user.setEmail("");
			user.setSupType("一般");
			//默认为注册用户
//			SysRole role = roleService.getByProerties(new String[]{"roleKey"},new String[]{"ROLE_USER"},null);
//			user.getRoles().add(role);
			userService.save(user);

            SysUser newUser = userService.getByProerties(new String[]{"loginAccount"}, new String[]{user.getLoginAccount()},null);
            UserRole userRole = new UserRole();
            userRole.setRoleId(1L);
            userRole.setUserId(newUser.getUserId());
            userRoleService.save(userRole);

			//存入session
//			Subject subject = SecurityUtils.getSubject();
//			subject.login(new UsernamePasswordToken(user.getLoginAccount(), "111111"));
//			subject.login(new UsernamePasswordToken(user.getLoginAccount(), user.getLoginPass()));
			session.setAttribute(USER_SESSION, user);
			result.setMessage("注册成功");
			result.setSuccess(true);
		}catch(Exception e){
			result.setMessage("注册失败");
		}
		super.writeJSON(result);
	}


	/**
	 * 判断用户账号是否已存在
	 * @throws IOException
	 */
	@RequestMapping(value = "/getUserNameCount", method = { RequestMethod.POST, RequestMethod.GET })
	public void getUserNameCount(String loginAccount) throws IOException{
		result.setSuccess(false);
		if(StringUtils.isBlank(loginAccount)){
			result.setMessage("账号不能为空");
			super.writeJSON(result);
			return;
		}
		Long count = userService.getCountByProerties(new String[]{"loginAccount"}, new String[]{loginAccount});
		if(count>0){
			result.setMessage("账号已存在");
		}else{
			result.setSuccess(true);
			result.setMessage("该账号可用");
		}
		super.writeJSON(result);
	}

	/**
	 * 登录首页
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/view")
	public String list(Model model) {
		// 管理员信息
		SysUser user = getUser();
		model.addAttribute("user", user);

//		SysUser user1 = userService.get(user.getUserId());
//		Department  department = departmentService.getByAreaId(Integer.parseInt(user1.getEmployee().getAreaId()));
//		String departmentName = department.getAreaName();
//		while(department.getSjareaId()!=0)
//		{
//			department = departmentService.getByAreaId(department.getSjareaId());
//			departmentName = department.getAreaName()+" > "+departmentName;
//		}
//		model.addAttribute("departmentName", departmentName);
		// 系统目录
		List<RoleMenuVO> menus = roleMenuService.listByUserId(user.getUserId());
		List<RoleMenuDTO> thirdMenu = roleMenuService.listByUserId1(user.getUserId());
		model.addAttribute("thirdMenu",thirdMenu);
		model.addAttribute("menus", menus);

		SysUser userInfo = (SysUser) session.getAttribute(USER_SESSION);
		int userId = userInfo.getUserId();
		UserRole userRole = userRoleService.getUserRoleByUserId(userId);
		SysRole sysRole = sysRoleService.get(userRole.getRoleId());
		model.addAttribute("sysRole", sysRole);
		return "main";
	}

	// 登出
	@RequestMapping("/logout")
	public void logout() throws IOException {
		//退出权限验证
		SecurityUtils.getSubject().logout();
		//销毁session
		session.invalidate();
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	/**
	 * 修改密码页面
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/editPwd")
	public String edit(Model model) {
		SysUser user1 = getUser();

		model.addAttribute("user", user1);
		return "/user/editPassword";
	}

	/**
	 * 查询用户密码是否正确
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/validPwd")
	@ResponseBody
	public Object validPwd(SysUser sysUser,String oldpass) {
		Map<String, Object> map = new HashMap<String, Object>();
		sysUser = getUser();
		if(sysUser.getLoginPass().equals(new Sha256Hash(oldpass).toHex())) {
			map.put("valid",true);
		}else {
			map.put("valid",false);
		}


		return map;
	}
	/**
	 * 修改密码
	 * @throws IOException
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object savePass(SysUser sysUser) {
		String npassword = request.getParameter("loginPass");
		sysUser = getUser();
		sysUser.setLoginPass(new Sha256Hash(npassword).toHex());
		userService.update(sysUser);
		return new CmsResult(CommonReturnCode.SUCCESS, 1);
	}
	/**
	 * 个人信息页面
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/userInfo")
	public String userInfo(Model model) {
		SysUser user = getUser();
		List<Department> departments = departmentService.list();
		model.addAttribute("departments",departments);
		model.addAttribute("user", user);
		return "/user/userInfo";
	}
	/**
	 * 更新登录用户个人信息
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/updateUserInfo")
    @ResponseBody
	public Object updateUserInfo(Model model) {
        JSONObject jsonObject = new JSONObject();
		String username = request.getParameter("username");
		String loginAccount = request.getParameter("loginAccount");
		String telephone = request.getParameter("telephone");
		String email = request.getParameter("email");
		String department = request.getParameter("department");
		String sex = request.getParameter("sex");
        SysUser user = getUser();
        boolean infoStatus = true;

        if (!CommonRegExUtils.StrNotNull(loginAccount)){//验证账户名不为空
            infoStatus = false;
        }
        if (CommonRegExUtils.StrNotNull(username)){//用户名不为空:为空则不保存，保留之前用户面
            user.setUserName(username);
        }

        if (!CommonRegExUtils.StrNotNull(telephone)||CommonRegExUtils.isPhone(telephone)){//电话号码验证:电话号码为空或者符合号码规则则保存
            user.setTelephone(telephone);
        }else{
            infoStatus = false;
        }
        if (!CommonRegExUtils.StrNotNull(email)||CommonRegExUtils.isEmail(email)){//email 验证：email为空或者符合号码规则则保存
            user.setEmail(email);
        }else{
            infoStatus = false;
        }

        if (CommonRegExUtils.StrNotNull(department)){//部门id不为空则修改否则不做修改
            Department dp = departmentService.getByAreaId(Integer.parseInt(department));
            if (dp != null){//查询到部门了，否则不做修改
                user.setDepartment(dp);
            }
        }
        if (("1").equals(sex)||("2").equals(sex)){//满足条件则修改，否则不修改
            user.setSex(Integer.parseInt(sex));
        }

        if (infoStatus){//前面信息检查无误则更新信息
            try{
                iSysUserService.update(user);
            }catch (Exception e){
                infoStatus = false;
                e.printStackTrace();
            }
        }
        jsonObject.put("message",infoStatus?CommonReturnCode.SUCCESS:CommonReturnCode.FAILED);
        return jsonObject;
	}

}
