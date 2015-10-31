package com.atguigu.crm.orm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Page<T> {

	private List<T> content = new ArrayList<T>();
	private long total;
	
	private int pageNo;
	private int pageSize = 5;
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}

	public Page(List<T> content, long total) {

		if (null == content) {
			throw new IllegalArgumentException("Content must not be null!");
		}

		this.content.addAll(content);
		this.total = total;
	}

	public Page(List<T> content) {
		this(content, null == content ? 0 : content.size());
	}

	public int getNumber() {
		if(pageNo < 1){
			this.pageNo = 1;
		}
		if(pageNo > getTotalPages()){
			this.pageNo = getTotalPages();
		}
		
		return pageNo;
	}

	public int getSize() {
		return pageSize;
	}

	public int getTotalPages() {
		return getSize() == 0 ? 1 : (int) Math.ceil((double) total
				/ (double) getSize());
	}

	public int getNumberOfElements() {
		return content.size();
	}

	public long getTotalElements() {
		return total;
	}

	public boolean hasPreviousPage() {
		return getNumber() > 1;
	}

	public boolean isFirstPage() {
		return !hasPreviousPage();
	}

	public boolean hasNextPage() {
		return getNumber() < getTotalPages();
	}

	public boolean isLastPage() {
		return !hasNextPage();
	}

	public Iterator<T> iterator() {
		return content.iterator();
	}

	public List<T> getContent() {
		return Collections.unmodifiableList(content);
	}

	public boolean hasContent() {
		return !content.isEmpty();
	}

}
