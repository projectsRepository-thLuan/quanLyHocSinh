package com.example.demo.entity;

public class GiaoVienWithSelection {
	private Boolean selected;
	private Lop lop;
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public GiaoVienWithSelection(Boolean selected, Lop lop) {
		super();
		this.selected = selected;
		this.lop = lop;
	}
	public GiaoVienWithSelection() {}
	public Lop getLop() {
		return lop;
	}
	public void setLop(Lop lop) {
		this.lop = lop;
	}
	
}
