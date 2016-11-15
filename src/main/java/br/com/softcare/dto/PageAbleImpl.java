package br.com.softcare.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageAbleImpl implements Pageable {

	private int pageNumber;
	private int pageSize;

	public PageAbleImpl(int pageNumber,int pageSize){
		this.pageNumber = pageNumber-1;
		this.pageSize = pageSize;
	}
	
	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getOffset() {
		return pageNumber*pageSize;
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

}
