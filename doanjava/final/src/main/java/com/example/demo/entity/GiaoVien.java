package com.example.demo.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "giaovien")
public class GiaoVien {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer magv;
	private String hoten;
	private String diachi;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaysinh;
	private String image_path;
	private String gioitinh;
	private String username;
	private String password;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "giaovien_lop", 
		joinColumns = @JoinColumn(name = "giaovien_id", referencedColumnName = "magv"), 
		inverseJoinColumns = @JoinColumn(name = "lop_id", referencedColumnName = "malop"))
	private List<Lop> lops = new ArrayList<Lop>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gv_mh_id", nullable = true)
	private MonHoc monhoc;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "gv_cv_id", nullable = true)
	private  ChucVu chucvu;
	@ManyToMany
	@JoinTable(
			name = "giaovien_role",
			joinColumns = {@JoinColumn(name="giaovien_id")},
			inverseJoinColumns = {@JoinColumn(name="role_id")}
	)
	private List<Role> roles = new ArrayList<Role>();
	@OneToMany(mappedBy = "giaovien", targetEntity = Lichlamviec.class ,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Lichlamviec> lichlamviecs = new ArrayList<Lichlamviec>();
	public void addLop(Lop lop) {
		lops.add(lop);
		lop.getGiaoviens().add(this);
	}
	public void xoaLop(Lop lop) {
		lops.remove(lop);
		lop.getGiaoviens().remove(this);
	}
	public void addMonHoc(MonHoc mh) {
		monhoc = null;
		monhoc = mh;
		monhoc.getGiaoviens().add(this);
	}
	public void xoaMonHoc(MonHoc mh) {
		mh.getGiaoviens().remove(this);
		this.setMonhoc(null);
	}
	public void xoaChucVu(ChucVu cv){
		cv.getGiaoviens().remove(this);
		this.setChucvu(null);
	}
	public String hashPassword(String password)
	{
		return Md5Hashing.Encrypt(password);
	}
	public MonHoc getMonhoc() {
		return monhoc;
	}
	public void setMonhoc(MonHoc monhoc) {
		this.monhoc = monhoc;
	}
	public List<Lop> getLops() {
		return lops;
	}
	public void setLops(List<Lop> lops) {
		this.lops = lops;
	}
	@DateTimeFormat(pattern="dd-MMM-YYYY")
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
	public String getHoten() {
		return hoten;
	}
	public void setHoten(String hoten) {
		this.hoten = hoten;
	}
	public Integer getMagv() {
		return magv;
	}
	public void setMagv(Integer magv) {
		this.magv = magv;
	}

	public ChucVu getChucvu() {
		return chucvu;
	}

	public void setChucvu(ChucVu chucvu) {
		this.chucvu = chucvu;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public String getGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
	}

	public List<Lichlamviec> getLichlamviecs() {
		return lichlamviecs;
	}

	public void setLichlamviecs(List<Lichlamviec> lichlamviecs) {
		this.lichlamviecs = lichlamviecs;
	}
}
