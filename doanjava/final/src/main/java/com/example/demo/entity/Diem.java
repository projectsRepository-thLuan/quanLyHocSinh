package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "diem")
public class Diem{
    @EmbeddedId
    private DiemPK diemPK;
    private float diem;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "loaidiem_id", referencedColumnName = "maloai", insertable = false, updatable = false)
    private LoaiDiem loaidiem;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "hocsinh_id", referencedColumnName = "masv", insertable = false, updatable = false)
    private HocSinh hocsinh;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "monhoc_id", referencedColumnName = "mamh", insertable = false, updatable = false)
    private MonHoc monhoc;
    public Diem(){

    }
    public Diem(DiemPK diemPK, float diem){
        this.diemPK = diemPK;
        this.diem = diem;
    }
    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }


    public DiemPK getDiemPK() {
        return diemPK;
    }

    public void setDiemPK(DiemPK diemPK) {
        this.diemPK = diemPK;
    }

    public LoaiDiem getLoaidiem() {
        return loaidiem;
    }

    public void setLoaidiem(LoaiDiem loaidiem) {
        this.loaidiem = loaidiem;
        this.getDiemPK().setLoaidiemId(loaidiem.getMaloai());
    }

    public HocSinh getHocsinh() {
        return hocsinh;
    }

    public void setHocsinh(HocSinh hocsinh) {
        this.hocsinh = hocsinh;
        this.getDiemPK().setHsId(hocsinh.getMasv());
    }

    public MonHoc getMonhoc() {
        return monhoc;
    }

    public void setMonhoc(MonHoc monhoc) {
        this.monhoc = monhoc;
        this.getDiemPK().setMhId(monhoc.getMamh());
    }
}
