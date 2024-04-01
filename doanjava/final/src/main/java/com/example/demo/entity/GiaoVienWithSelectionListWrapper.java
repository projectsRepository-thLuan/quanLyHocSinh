package com.example.demo.entity;

import java.util.List;

public class GiaoVienWithSelectionListWrapper {
	private List<GiaoVienWithSelection> list_lop;
	private Integer id_gv;
	public Integer getId_gv() {
		return id_gv;
	}

	public void setId_gv(Integer id_gv) {
		this.id_gv = id_gv;
	}

	public List<GiaoVienWithSelection> getList_lop() {
		return list_lop;
	}

	public void setList_lop(List<GiaoVienWithSelection> list_lop) {
		this.list_lop = list_lop;
	}
}
