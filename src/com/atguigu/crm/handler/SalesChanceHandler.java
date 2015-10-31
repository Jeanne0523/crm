package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SalesChanceService;
import com.atuigu.crm.entity.SalesChance;
import com.atuigu.crm.entity.User;

@RequestMapping("/chance")
@Controller
public class SalesChanceHandler extends BaseHandler<SalesChance>{

	public SalesChanceService getChanceService(){
		return (SalesChanceService) service;
	}
	
	/**
	 * RESTFUL 风格的 URL:
	 * 添加: 添加一个新的对象, 所以没有 id
	 * 1. 显示表单: chance GET         (input)
	 * 2. 完成添加: chance POST        (save)
	 * 
	 * 删除: 有 id
	 * chance/id DELETE    (delete?id=xxxx)
	 * 
	 * 修改: 有 id
	 * 1. 显示表单: chance/id GET . 需要携带一个参数(隐藏域, type 类型为 UPDATE 即可), 以区分查询某一个对象.  (edit?id=xxx)
	 * 2. 完成修改: chance/id PUT  (update?id=xxxx)
	 * 
	 * 查询: 
	 * 1. 查询单个的对象, 有 id: chance/id GET  (details?id=xxx)
	 * 2. 获取集合对象: chances GET (list)
	 */
	
	@RequestMapping(value="/chance/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		getChanceService().delete(id);
		return "redirect:/chance/list";
	}
	
	@ModelAttribute
	public void getChance(@RequestParam(value="id",required=false) Integer id, Map<String, Object> map){
		if(id != null){
			map.put("chance", getChanceService().get(id));
		}
	}
	
	/**
	 * 修改之前需要使用 @ModelAttribute 注解的方法来从数据库中先获取对象. 如何保证 @ModelAttribute 方法被执行呢 ?
	 * 通过判断 请求参数 id 是否为 null. 但若这样, 则 url 中携带的 id 就没有作用了.
	 * 注意: 在 restful 风格的 url 中, 只有修改时时携带隐藏域的 id 的. 而 删除、获取 是不通过请求参数携带 id. 所以通过
	 * 判断请求参数 id 是否为 null, 可以保证 @ModelAttribute 方法只被执行一次.
	 * 
	 * 
	 */
	@RequestMapping(value="/chance/{id}", method=RequestMethod.PUT)
	public String update(RedirectAttributes attributes, @ModelAttribute("chance") SalesChance chance){
		getChanceService().update(chance);
		attributes.addFlashAttribute("message", "修改成功!");
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/chance/{id}",method=RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Map<String, Object> map){
		map.put("chance", getChanceService().get(id));
		return "chance/input";
	}
	
	@RequestMapping(value="/chance", method=RequestMethod.POST)
	public String save(RedirectAttributes attributes, SalesChance chance){
		getChanceService().save(chance);
		attributes.addFlashAttribute("message", "添加成功!");
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/chance",method=RequestMethod.GET)
	public String input(Map<String, Object> map, HttpSession session){
		SalesChance chance = new SalesChance();
		chance.setCreateBy((User)session.getAttribute("user"));
		map.put("chance", chance);
		return "chance/input";
	}
	
	@RequestMapping("/list")
	public String list2(@RequestParam(value="page",required=false,defaultValue="1") String pageNoStr,
			HttpServletRequest request){
		super.list(pageNoStr, request);
		//5. 返回结果.
		return "chance/list";
	}
	
	//@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false,defaultValue="1") String pageNoStr,
			Map<String, Object> map){
		int pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		Page<SalesChance> page = new Page<>(pageNo);
		page = getChanceService().getPage(page);
		map.put("page", page);
		
		return "chance/list";
	}
}
