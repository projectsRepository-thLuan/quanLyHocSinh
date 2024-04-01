package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="monhoc")
public class MonHoc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mamh;
	private String tenmh;
	private Integer heso;
	@OneToMany(mappedBy = "monhoc", targetEntity = GiaoVien.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<GiaoVien> giaoviens = new ArrayList<GiaoVien>();
	@OneToMany(mappedBy = "monhoc", targetEntity = Diem.class, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Diem> diems = new ArrayList<Diem>();
	public Integer getMamh() {
		return mamh;
	}
	public MonHoc() {}
	public void xoaMonHoc() {
		for (GiaoVien g : this.getGiaoviens()) {
			g.setMonhoc(null);
		}
		this.setGiaoviens(null);
	}
	public void setMamh(Integer mamh) {
		this.mamh = mamh;
	}
	public List<GiaoVien> getGiaoviens() {
		return giaoviens;
	}
	public void setGiaoviens(List<GiaoVien> giaoviens) {
		this.giaoviens = giaoviens;
	}
	public MonHoc(String tenmh) {
		super();
		this.tenmh = tenmh;
	}
	public String getTenmh() {
		return tenmh;
	}
	public void setTenmh(String tenmh) {
		this.tenmh = tenmh;
	}

	public List<Diem> getDiems() {
		return diems;
	}

	public void setDiems(List<Diem> diems) {
		this.diems = diems;
	}

	public Integer getHeso() {
		return heso;
	}

	public void setHeso(Integer heso) {
		this.heso = heso;
	}
}
