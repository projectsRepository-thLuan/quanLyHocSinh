package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lichlamviec")
public class Lichlamviec {
    @EmbeddedId
    private LichlamviecPK lichlamviecPK;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "giaovien_id", referencedColumnName = "magv", insertable = false, updatable = false)
    private GiaoVien giaovien;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "lop_id", referencedColumnName = "malop", insertable = false, updatable = false)
    private Lop lop;
    public Lichlamviec(){}
    public Lichlamviec(LichlamviecPK lichlamviecPK)
    {
        this.lichlamviecPK = lichlamviecPK;
    }
    public LichlamviecPK getLichlamviecPK() {
        return lichlamviecPK;
    }

    public void setLichlamviecPK(LichlamviecPK lichlamviecPK) {
        this.lichlamviecPK = lichlamviecPK;
    }

    public GiaoVien getGiaoVien() {
        return giaovien;
    }

    public void setGiaoVien(GiaoVien giaoVien) {
        this.giaovien = giaoVien;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }
}
