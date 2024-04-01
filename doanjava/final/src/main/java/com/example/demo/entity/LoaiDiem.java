package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loaidiem")
public class LoaiDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maloai;
    private String tenloai;
    private Integer heso;
    @OneToMany(mappedBy = "loaidiem", targetEntity = Diem.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diem> diems = new ArrayList<Diem>();
    public Integer getMaloai() {
        return maloai;
    }

    public void setMaloai(Integer maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
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
