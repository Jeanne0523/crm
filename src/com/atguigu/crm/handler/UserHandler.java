package com.atguigu.crm.handler;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.service.UserService;
import com.atuigu.crm.entity.User;

@RequestMapping("/user")
@Controller
public class UserHandler {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@RequestMapping("/shiro-login")
	public String shiroLogin(@RequestParam("username") String username, 
			@RequestParam("password") String password, HttpSession session,
			RedirectAttributes attributes, Locale locale){
		Subject currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (AuthenticationException ae) {
            	attributes.addFlashAttribute("message", 
        				messageSource.getMessage("error.login", null, locale));
        		return "redirect:/index";
            }
        }
		
		System.out.println("登陆成功: [" + currentUser.getPrincipal() + "]");
		session.setAttribute("user", currentUser.getPrincipal());
		return "redirect:/success";
	}
	
	/**
	 * 若希望在重定向的情况下, 依旧可以在页面上得到在 handler 方法中添加的属性, 则:
	 * 1. 使用 RedirectAttributes#addFlashAttribute 添加键值对.
	 * 2. 重定向的目标结果必须经过 SpringMVC! 而不能直接重定向到目标资源. 
	 * 
	 * 在 SpringMVC 中如何来使用国际化资源文件
	 * 1. 在 SpringMVC 的配置文件中配置 ResourceBundleMessageSource bean
	 * 2. 在 Handler 中注入 ResourceBundleMessageSource Bean
	 * 3. 在 Handler 的方法中调用 ResourceBundleMessageSource#getMessage 方法. 
	 */
	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, 
			@RequestParam("password") String password, HttpSession session,
			RedirectAttributes attributes, Locale locale){
		User user = userService.login(username, password);
		if(user != null){
			session.setAttribute("user", user);
			return "redirect:/success";
		}
		
		attributes.addFlashAttribute("message", 
				messageSource.getMessage("error.login", null, locale));
		return "redirect:/index";
	}
	
}
