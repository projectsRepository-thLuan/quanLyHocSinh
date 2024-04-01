package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lop")
public class Lop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer malop;
	private String tenlop;
	private Integer khoilop;
    @OneToMany(mappedBy = "lop", targetEntity=HocSinh.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<HocSinh> hocsinhs = new ArrayList<HocSinh>();
    @ManyToMany(mappedBy = "lops", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<GiaoVien> giaoviens= new ArrayList<GiaoVien>();
    @OneToMany(mappedBy = "lop", targetEntity = Lichlamviec.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Lichlamviec> lichlamviecs = new ArrayList<Lichlamviec>();
    public List<GiaoVien> getGiaoviens() {
		return giaoviens;
	}
	public void setGiaoviens(List<GiaoVien> giaoviens) {
		this.giaoviens = giaoviens;
	}
	public List<HocSinh> getHocsinhs() {
		return hocsinhs;
	}
	public void setHocsinhs(List<HocSinh> hocsinhs) {
		this.hocsinhs = hocsinhs;
	}
	public Lop() {}
    public Lop(String tenlop) {this.tenlop = tenlop;}
	public Integer getMalop() {
		return malop;
	}
	public void addHocsinh(HocSinh hs) {
		hocsinhs.add(hs);
		hs.setLop(this);
	}
	public void xoaLop(){
    	for(HocSinh hs : this.getHocsinhs()){
    		hs.setLop(null);
		}
    	this.setHocsinhs(null);
	}
	public void xoaGiaoVien(){
    	for(GiaoVien gv : this.getGiaoviens()){
    		gv.setLops(null);
		}
    	this.setGiaoviens(null);
	}
	public void removeHocsinh(HocSinh hs) {
		hs.setLop(null);
		//hocsinhs.remove(hs);
	}
	public void setMalop(Integer malop) {
		this.malop = malop;
	}

	public String getTenlop() {
		return tenlop;
	}

	public void setTenlop(String tenlop) {
		this.tenlop = tenlop;
	}

	public Integer getKhoilop() {
		return khoilop;
	}

	public void setKhoilop(Integer khoilop) {
		this.khoilop = khoilop;
	}

	public List<Lichlamviec> getLichlamviecs() {
		return lichlamviecs;
	}

	public void setLichlamviecs(List<Lichlamviec> lichlamviecs) {
		this.lichlamviecs = lichlamviecs;
	}
}
