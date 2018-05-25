package com.e3mall.search.common.pojo;

import java.io.Serializable;
import java.util.List;


public class DataGridResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer total;
	
	private List rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "DataGridResult [total=" + total + ", rows=" + rows + "]";
	}
	
	
}
