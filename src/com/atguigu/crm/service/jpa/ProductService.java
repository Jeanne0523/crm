package com.atguigu.crm.service.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.repository.ProductRepository;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Page<Product> getPage(int pageNo, int pageSize, Map<String, Object> params){
		PageRequest pageable = new PageRequest(pageNo - 1, pageSize);
		Specification<Product> specification = buildSpecification(params);
		Page<Product> page = productRepository.findAll(specification, pageable);
		return page;
		
		// 不带查询条件的分页
		// return productRepository.findAll(pageable);
	}

	//前台页面传入的查询参数
	private Specification<Product> buildSpecification(Map<String, Object> params) {
		//1. 把 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		
		//2. 把 PropertyFilter 的集合转为 Specification 对象。
		Specification<Product> specification = parseFiltersToSpecification(filters);
		
		return specification;
	}

	private Specification<Product> parseFiltersToSpecification(
			final List<PropertyFilter> filters) {
		Specification<Product> specification = new Specification<Product>() {
			/**
			 * @param root: 查询的 "根", 通过调用 get 方法可以导航到其他的属性.
			 * @param query
			 * @param builder: 工厂类. 可以获取代表查询条件的 Predicate 对象
			 * @return: 代表 JPA Criteria 查询的一个条件. 
			 */
			@Override
			public Predicate toPredicate(Root<Product> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				
				if(filters != null && filters.size() > 0){
					for(PropertyFilter filter: filters){
						//该属性可能是直接属性: name, 也可能是级联属性: manager.name
						String propertyName = filter.getPropertyName();
						Object propertyVal = filter.getPropertyVal();
						propertyVal = ReflectionUtils.convertValue(propertyVal, filter.getPropertyType().getPropertyType());
						
						String [] propertyNames = propertyName.split("\\.");
						Path path = root.get(propertyNames[0]);
						if(propertyNames.length > 1){
							for(int i = 1; i < propertyNames.length; i++){
								path = path.get(propertyNames[i]);
							}
						}
						
						//查询条件
						Predicate predicate = null;
						
						switch(filter.getMatchType()){
						case EQ:
							predicate = builder.equal(path, propertyVal);
							break;
						case GE:
							predicate = builder.ge(path, (Number)propertyVal);
							break;
						case GT:
							predicate = builder.gt(path, (Number)propertyVal);
							break;
						case LT:
							predicate = builder.lt(path, (Number)propertyVal);
							break;
						case LE:
							predicate = builder.le(path, (Number)propertyVal);
							break;
						case NULL:
							predicate = builder.isNull(path);
							break;
						case NOTNULL:
							predicate = builder.isNotNull(path);
							break;
						case LIKE:
							predicate = builder.like(path, "%" + propertyVal + "%");
							break;
						}
						
						if(predicate != null){
							predicates.add(predicate);
						}
					}
				}
				
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
		return specification;
	}
	
	public static void main(String[] args) {
		String str = "name";
		String [] strs = str.split("\\.");
		
		System.out.println(strs.length);
	}
}
