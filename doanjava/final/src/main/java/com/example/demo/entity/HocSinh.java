package com.example.demo.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "hocsinh")
public class HocSinh {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masv", nullable = false)
	private Integer masv;
	@Column(name = "ten", nullable = false)
	private String ten;
	@Column(name = "gioitinh", nullable = false)
	private String gioitinh;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hs_lop_id", nullable = true)
	private Lop lop;;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hs_chucvu_id", nullable = true)
	private ChucVu chucvu;
	@OneToMany(mappedBy = "hocsinh", targetEntity = Diem.class, cascade = CascadeType.ALL)
	private List<Diem> diems = new ArrayList<Diem>();
	private String diachi;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaysinh;
	private String image_path;
	public HocSinh() {}
	public HocSinh(String ten) {this.ten = ten;}
	public Lop getLop() {
		return lop;
	}
	public void setLop(Lop lop) {
		this.lop = lop;
	}
	public Integer getMasv() {
		return masv;
	}
	public void setMasv(Integer masv) {
		this.masv = masv;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public Date getNgaysinh() {
		return ngaysinh;
	}
	public void setNgaysinh(Date ngaysinh) {
		this.ngaysinh = ngaysinh;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public ChucVu getChucvu() {
		return chucvu;
	}

	public void setChucvu(ChucVu chucvu) {
		this.chucvu = chucvu;
	}

	public List<Diem> getDiems() {
		return diems;
	}

	public void setDiems(List<Diem> diems) {
		this.diems = diems;
	}

	public String getGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
}
