package com.atguigu.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.atuigu.crm.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, 
	JpaSpecificationExecutor<Product>{

}
