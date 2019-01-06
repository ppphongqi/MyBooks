package com.pty.book.pojo;

import java.util.List;

public class BookBean {
	private String total;
	private List<Book> rows;
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<Book> getRows() {
		return rows;
	}
	public void setRows(List<Book> rows) {
		this.rows = rows;
	}
	
}
