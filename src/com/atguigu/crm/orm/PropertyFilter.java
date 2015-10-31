package com.atguigu.crm.orm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PropertyFilter {
	public enum MatchType {
		LIKE, GT, LT, GE, LE, EQ, NULL, NOTNULL;
	}

	// "S"
	public enum PropertyType {

		I(Integer.class), S(String.class), D(Date.class), F(Float.class);

		private Class<?> propertyType;

		private PropertyType(Class<?> propertyType) {
			this.propertyType = propertyType;
		}

		public Class<?> getPropertyType() {
			return propertyType;
		}
	}

	private String propertyName;
	private Object propertyVal;

	private MatchType matchType;
	private PropertyType propertyType;

	public PropertyFilter(String propertyName, Object propertyVal,
			MatchType matchType, PropertyType propertyType) {
		this.propertyName = propertyName;
		this.propertyVal = propertyVal;
		this.matchType = matchType;
		this.propertyType = propertyType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	//LIKES_loginName, EQI_age
	public static List<PropertyFilter> parseParamsToFilters(Map<String, Object> params) {
		List<PropertyFilter> filters = new ArrayList<>();
		
		if(params != null && params.size() > 0){
			for(Map.Entry<String, Object> param: params.entrySet()){
				String key = param.getKey(); //LIKES_loginName
				Object propertyVal = param.getValue();
				
				if(propertyVal == null || propertyVal.toString().trim().equals("")){
					continue;
				}
				
				String str = StringUtils.substringBefore(key, "_");
				String matchTypeCode = str.substring(0, str.length() - 1); //LIKE
				String propertyTypeCode = str.substring(str.length() - 1); //S
				String propertyName = StringUtils.substringAfter(key, "_"); //loginName
				
				MatchType matchType = Enum.valueOf(MatchType.class, matchTypeCode);
				PropertyType propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode);
				
				PropertyFilter filter = new PropertyFilter(propertyName, propertyVal, matchType, propertyType);
				filters.add(filter);
			}
		}
		
		return filters;
	}

	@Override
	public String toString() {
		return "PropertyFilter [propertyName=" + propertyName
				+ ", propertyVal=" + propertyVal + ", matchType=" + matchType
				+ ", propertyType=" + propertyType + "]";
	}
}
