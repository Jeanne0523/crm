package com.atguigu.crm.test;

import javax.servlet.http.HttpServletRequest;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class StrutsMenuTestHandler {

	@RequestMapping("/menu")
	public String strutsMenu(HttpServletRequest request) {
		System.out.println("strutsmenu...");
		
		//存放 StrutsMenu menu 的容器. 
		MenuRepository repository = new MenuRepository();
		//设置显示样式
		MenuRepository defaultRepository = (MenuRepository) request.getSession().getServletContext()
				.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
		repository.setDisplayers(defaultRepository.getDisplayers());

		//实际的按钮.
		MenuComponent mc = new MenuComponent();
		//设置标签的 name 属性.
		mc.setName("AAAA");
		//设置 title
		mc.setTitle("AAAA");
		
		MenuComponent m = new MenuComponent();
		m.setName("BBBB");
		m.setTitle("BBBB");
		m.setLocation("http://wwww.atguigu.com");
		m.setParent(mc);
		
		repository.addMenu(mc);
		request.setAttribute("repository", repository);
		
		return "menu";
	}

}
