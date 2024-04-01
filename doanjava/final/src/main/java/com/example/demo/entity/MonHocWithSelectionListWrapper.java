package com.example.demo.entity;

import java.util.List;

public class MonHocWithSelectionListWrapper {
	private List<MonHocWithSelection> list_monhoc;
	private Integer id_gv;
	public Integer getId_gv() {
		return id_gv;
	}

	public void setId_gv(Integer id_gv) {
		this.id_gv = id_gv;
	}

	public List<MonHocWithSelection> getList_monhoc() {
		return list_monhoc;
	}

	public void setList_monhoc(List<MonHocWithSelection> list_monhoc) {
		this.list_monhoc = list_monhoc;
	}

}
