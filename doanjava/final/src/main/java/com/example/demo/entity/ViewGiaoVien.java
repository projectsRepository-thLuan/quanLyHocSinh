package com.example.demo.entity;

import java.util.List;

public class ViewGiaoVien {
	private Integer id_gv;
	private List<Lop> lops;
	private MonHoc monhoc;
	public List<Lop> getLops() {
		return lops;
	}
	public void setLops(List<Lop> lops) {
		this.lops = lops;
	}
	public MonHoc getMonhoc() {
		return monhoc;
	}
	public void setMonhoc(MonHoc monhoc) {
		this.monhoc = monhoc;
	}
	public Integer getId_gv() {
		return id_gv;
	}
	public void setId_gv(Integer id_gv) {
		this.id_gv = id_gv;
	}
	
}
