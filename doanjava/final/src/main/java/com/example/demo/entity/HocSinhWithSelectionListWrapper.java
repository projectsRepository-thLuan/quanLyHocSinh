package com.example.demo.entity;

import java.util.ArrayList;

public class HocSinhWithSelectionListWrapper {
	private ArrayList<HocSinhWithSelection> hocsinhlist;
	private Integer id_lop;
	public Integer getId_lop() {
		return id_lop;
	}
	public void setId_lop(Integer id_lop) {
		this.id_lop = id_lop;
	}
	public ArrayList<HocSinhWithSelection> getHocsinhlist() {
		return hocsinhlist;
	}
	public void setHocsinhlist(ArrayList<HocSinhWithSelection> hocsinhlist) {
		this.hocsinhlist = hocsinhlist;
	}
}
