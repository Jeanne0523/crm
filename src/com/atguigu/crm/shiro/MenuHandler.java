package com.atguigu.crm.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atuigu.crm.entity.Authority;
import com.atuigu.crm.entity.Role;
import com.atuigu.crm.entity.User;

@Controller
public class MenuHandler {

	@RequestMapping("/menu")
	public String menuList(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		
		// 存放 StrutsMenu menu 的容器.
		MenuRepository repository = new MenuRepository();
		// 设置显示样式
		MenuRepository defaultRepository = (MenuRepository) request
				.getSession().getServletContext()
				.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
		repository.setDisplayers(defaultRepository.getDisplayers());

		// 实际的按钮.
		MenuComponent crmmenu = new MenuComponent();
		crmmenu.setName("crmmenu");
		crmmenu.setTitle("客户关系管理系统");
		
		User user = (User) request.getSession().getAttribute("user");
		Map<Long, MenuComponent> parentMenus = new HashMap<Long, MenuComponent>();
		
		if(user != null){
			Role role = user.getRole();
			for(Authority authority: role.getAuthorities()){
				MenuComponent menu = new MenuComponent();
				menu.setName(authority.getName());
				menu.setTitle(authority.getDisplayName());
				menu.setLocation(contextPath + authority.getUrl());
				
				Authority parentAuthority = authority.getParentAuthority();
				MenuComponent parentMenu = parentMenus.get(parentAuthority.getId());
				if(parentMenu == null){
					parentMenu = new MenuComponent();
					parentMenu.setTitle(parentAuthority.getDisplayName());
					parentMenu.setParent(crmmenu);
					parentMenus.put(parentAuthority.getId(), parentMenu);
				}
				
				menu.setParent(parentMenu);
			}
		}
		
		repository.addMenu(crmmenu);
		request.setAttribute("repository", repository);
		
		return "home/menu";
	}

}
