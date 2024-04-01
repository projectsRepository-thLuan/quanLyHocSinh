package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chucvu")
public class ChucVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer macv;
    private String tencv;
    @OneToMany(mappedBy = "chucvu", targetEntity = GiaoVien.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GiaoVien> giaoviens = new ArrayList<GiaoVien>();
    @OneToMany(mappedBy = "chucvu", targetEntity = HocSinh.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HocSinh> hocsinhs = new ArrayList<HocSinh>();
    public Integer getMacv() {
        return macv;
    }

    public void setMacv(Integer macv) {
        this.macv = macv;
    }

    public String getTencv() {
        return tencv;
    }

    public void setTencv(String tencv) {
        this.tencv = tencv;
    }

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
}
