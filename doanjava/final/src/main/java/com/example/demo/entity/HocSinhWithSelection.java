package com.example.demo.entity;

public class HocSinhWithSelection {
	private Boolean selected;
	private HocSinh hocsinh;

	public HocSinhWithSelection() {
	}

	public HocSinhWithSelection(Boolean selected, HocSinh hocsinh) {
	    super();
	    this.selected = selected;
	    this.setHocsinh(hocsinh);
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public HocSinh getHocsinh() {
		return hocsinh;
	}

	public void setHocsinh(HocSinh hocsinh) {
		this.hocsinh = hocsinh;
	}

}
