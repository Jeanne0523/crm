package com.atguigu.crm.dao.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.Validate;

import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.ReflectionUtils;

/**
 * Http与Servlet工具类.
 * 
 */
public class Servlets {

	public static Map<String, Object> parseFiltersToMyBatisParams(
			List<PropertyFilter> filters) {
		Map<String, Object> params = new HashMap<>();
		
		if(filters != null && filters.size() > 0){
			for(PropertyFilter filter: filters){
				String key = filter.getPropertyName();
				Object val = filter.getPropertyVal();
				
				val = ReflectionUtils.convertValue(val, filter.getPropertyType().getPropertyType());
				switch(filter.getMatchType()){
				case LIKE:
					val = "%" + val + "%";
					break;
				}
				
				params.put(key, val);
			}
		}
		
		return params;
	}
	
	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 返回的结果的 Parameter 名已去除前缀.
	 */
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if ((values == null) || (values.length == 0)) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 组合 Parameters 生成 Query String 的 Parameter 部分, 并在 paramter name 上加上prefix.
	 * 
	 * @see #getParametersStartingWith
	 */
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if ((params == null) || (params.size() == 0)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}
}
