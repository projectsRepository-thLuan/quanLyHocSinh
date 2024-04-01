package com.example.demo.entity;

import java.util.List;

public class KetQua {
    private MonHoc monhoc;
    private float diemMieng;
    private float diem15p;
    private float diem1t;
    private float diemGK;
    private float diemCK;
    private float diemTB;
    public KetQua(){
        this.diemMieng = -1;
        this.diemMieng = -1;
        this.diem15p = -1;
        this.diem1t = -1;
        this.diemGK = -1;
        this.diemCK = -1;
        this.setDiemTB(-1);
    }
    public float tinhDiemTB(List<LoaiDiem> ld){
        if(this.getDiemMieng() != -1 && this.getDiem15p() != -1 && this.getDiem1t() != -1 && this.getDiemGK() != -1 && this.getDiemCK() != -1)
        {
            float tong_diem = 0;
            Integer tong_hs = 0;
            for(LoaiDiem l : ld){
                tong_hs += l.getHeso();
                if(l.getTenloai().equals("DiemMieng"))
                    tong_diem += this.getDiemMieng()*l.getHeso();
                else if(l.getTenloai().equals("Diem15p"))
                    tong_diem += this.getDiem15p()*l.getHeso();
                else if(l.getTenloai().equals("Diem1t"))
                    tong_diem += this.getDiem1t()*l.getHeso();
                else if(l.getTenloai().equals("DiemGK"))
                    tong_diem += this.getDiemGK()*l.getHeso();
                else if(l.getTenloai().equals("DiemCK"))
                    tong_diem += this.getDiemCK()*l.getHeso();
            }

            float dtb = (tong_diem) / tong_hs;
            return dtb;
        }
        return -1;
    }
    public MonHoc getMonhoc() {
        return monhoc;
    }

    public void setMonhoc(MonHoc monhoc) {
        this.monhoc = monhoc;
    }

    public float getDiemMieng() {
        return diemMieng;
    }

    public void setDiemMieng(float diemMieng) {
        this.diemMieng = diemMieng;
    }

    public float getDiem15p() {
        return diem15p;
    }

    public void setDiem15p(float diem15p) {
        this.diem15p = diem15p;
    }

    public float getDiem1t() {
        return diem1t;
    }

    public void setDiem1t(float diem1t) {
        this.diem1t = diem1t;
    }

    public float getDiemGK() {
        return diemGK;
    }

    public void setDiemGK(float diemGK) {
        this.diemGK = diemGK;
    }

    public float getDiemCK() {
        return diemCK;
    }

    public void setDiemCK(float diemCK) {
        this.diemCK = diemCK;
    }

    public float getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(float diemTB) {
        this.diemTB = diemTB;
    }
}
