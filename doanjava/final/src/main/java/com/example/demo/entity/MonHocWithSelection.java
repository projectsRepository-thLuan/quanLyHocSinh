package com.example.demo.entity;

public class MonHocWithSelection {
	private Boolean selected;
	private MonHoc monhoc;
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public MonHocWithSelection(Boolean selected, MonHoc monhoc) {
		super();
		this.selected = selected;
		this.setMonhoc(monhoc);
	}
	public MonHocWithSelection() {}
	public MonHoc getMonhoc() {
		return monhoc;
	}
	public void setMonhoc(MonHoc monhoc) {
		this.monhoc = monhoc;
	}
}
