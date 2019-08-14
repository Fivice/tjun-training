package tjuninfo.training.task.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import tjuninfo.training.support.service.impl.BaseServiceImpl;
import tjuninfo.training.task.dao.IRoleMenuDao;
import tjuninfo.training.task.dto.RoleMenuDTO;
import tjuninfo.training.task.entity.RoleMenu;
import tjuninfo.training.task.enums.MenuTypeEnum;
import tjuninfo.training.task.service.IRoleMenuService;
import tjuninfo.training.task.vo.RoleMenuVO;
import tjuninfo.training.task.vo.RoleMenuVo2;

/**
 * 类名称：RoleMenuServiceImpl   
 * 类描述：RoleMenu / 角色目录关联表 业务逻辑层接口实现    
 * @author Administrator
 *
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu> implements IRoleMenuService {
	private IRoleMenuDao roleMenuDao;
	@Resource
	public void setIRoleMenuDao(IRoleMenuDao roleMenuDao) {
		this.roleMenuDao = roleMenuDao;
		this.dao = roleMenuDao; 
	}
	
	@Override
	public List<RoleMenuVO> listByUserId(Integer userId) {
		List<RoleMenuDTO> meunList = roleMenuDao.findMeunByUserId(userId);
		List<RoleMenuVO> menus = new ArrayList<>();
		List<RoleMenuDTO> parentMenus =new ArrayList<>();//一级目录
		List<RoleMenuDTO> childMenus =new ArrayList<>();//二级目录
		List<RoleMenuDTO> thirdMenus =new ArrayList<>();//sa目录
		List<RoleMenuDTO> parentAllMenus =new ArrayList<>();//一级目录
		List<RoleMenuDTO> childAllMenus =new ArrayList<>();//二级目录
//		List<RoleMenuDTO> handleMenus =new ArrayList<>();//查询操作目录
		//  查询一级目
		for(RoleMenuDTO menu : meunList) {
			if(MenuTypeEnum.FIRST_MENU.getType() == menu.getMenuType()) {
				parentAllMenus.add(menu);
				 parentMenus = menuDereplication(parentAllMenus);
			}else if(MenuTypeEnum.SECOND_MENU.getType() == menu.getMenuType()) {
				childAllMenus.add(menu);
				childMenus = menuDereplication(childAllMenus);
			}
			/*else if(MenuTypeEnum.HANDLE_MENU.getType() == menu.getMenuType()) {
				handleMenus.add(menu);
			}*/
		}
		// 获取根级权限的子级权限
			for (RoleMenuDTO parentMenu : parentMenus) {
				recursionMenu(menus, childMenus, parentMenu);
					}
		return menus;
	}
		/**
		 * 递归得到每个权限的子级权限
		 * @param menus 处理后的目录列表
		 * @param childMenus  二级目录列表
		 * @param parentMenu 当前一级目录
		 */
		private void recursionMenu(List<RoleMenuVO> menus, List<RoleMenuDTO> childMenus, RoleMenuDTO parentMenu) {
			List<RoleMenuDTO> childMenuList = new ArrayList<>();
			for (RoleMenuDTO menu : childMenus) {
				if (parentMenu.getMenuId() == menu.getParentId()) {
					childMenuList.add(menu);
				}
			}
			RoleMenuVO parentMenuVo = new RoleMenuVO();
			BeanUtils.copyProperties(parentMenu, parentMenuVo);
			parentMenuVo.setChildMenus(childMenuList);
			menus.add(parentMenuVo);
		}

		/**
		 * 权限去重 （由于数据库中DISTINCT关键词版本报错）
		 * @param sourceRoleMenuVOs 原权限
		 * @return
		 */
		private List<RoleMenuDTO> menuDereplication(List<RoleMenuDTO> sourceRoleMenuVOs) {
			List<RoleMenuDTO> roleMenuDTOs = new ArrayList<>();
			for (RoleMenuDTO roleMenuDTO : sourceRoleMenuVOs) {
				if (!roleMenuDTOs.contains(roleMenuDTO)) {
					roleMenuDTOs.add(roleMenuDTO);
				}
			}
			return roleMenuDTOs;
		}
		//保存roleMenu对象
		@Override
		public void add(RoleMenu roleMenu) {
			// TODO Auto-generated method stub
			roleMenuDao.save(roleMenu);
		}
		
		@Override
		public void doDel(Long roleId) {
			roleMenuDao.doDel(roleId);
		
		}

		@Override
		public List<RoleMenuDTO> listByUserId1(Integer userId) {
			// TODO Auto-generated method stub
			return roleMenuDao.findMeunByUserId1(userId);
		}

		@Override
		public List<RoleMenuVo2> findMenuIdByRoleId(Long roleId) {
			return roleMenuDao.findMenuIdByRoleId(roleId);
		}
		
}
