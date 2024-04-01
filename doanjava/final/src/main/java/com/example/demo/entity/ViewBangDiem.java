package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class ViewBangDiem {
    private List<KetQua> ketQuas = new ArrayList<KetQua>();
    private HocSinh hocsinh;
    private Lop lop;
    private Integer hocky;
    private float diemHK;
    public ViewBangDiem(){}
    public float tinhDiemHK(){
        float dtb = 0;
        Integer hs = 0;
        for(KetQua kq : getKetQuas())
        {
            if(kq.getDiemTB() == -1){
                return -1;
            }
            dtb += kq.getDiemTB() * kq.getMonhoc().getHeso();
            hs += kq.getMonhoc().getHeso();
        }

        return (dtb / hs);
    }
    public void tinhDiemAllMh(List<MonHoc> monHocs, List<Diem> hs_diem, List<LoaiDiem> lds){
        for(MonHoc mh:monHocs)
        {
            KetQua kq = new KetQua();
            kq.setMonhoc(mh);
            this.getKetQuas().add(kq);
        }
        for(KetQua re: this.getKetQuas())
        {
            for(Diem d : hs_diem)
            {
                if (re.getMonhoc().getMamh().equals((d.getMonhoc().getMamh()))) {
                    if (d.getLoaidiem().getTenloai().equals("DiemMieng") && d.getDiemPK().getHocky().equals(hocky))
                        re.setDiemMieng(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("Diem15p") && d.getDiemPK().getHocky().equals(hocky))
                        re.setDiem15p(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("Diem1t") && d.getDiemPK().getHocky().equals(hocky))
                        re.setDiem1t(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("DiemGK") && d.getDiemPK().getHocky().equals(hocky))
                        re.setDiemGK(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("DiemCK") && d.getDiemPK().getHocky().equals(hocky))
                        re.setDiemCK(d.getDiem());
                }
            }
            re.setDiemTB(re.tinhDiemTB((List<LoaiDiem>) lds));
        }
        this.setDiemHK(this.tinhDiemHK());
    }
    public List<KetQua> getKetQuas() {
        return ketQuas;
    }

    public void setKetQuas(List<KetQua> ketQuas) {
        this.ketQuas = ketQuas;
    }

    public HocSinh getHocsinh() {
        return hocsinh;
    }

    public void setHocsinh(HocSinh hocsinh) {
        this.hocsinh = hocsinh;
    }

    public Integer getHocky() {
        return hocky;
    }

    public void setHocky(Integer hocky) {
        this.hocky = hocky;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    public float getDiemHK() {
        return diemHK;
    }

    public void setDiemHK(float diemHK) {
        this.diemHK = diemHK;
    }
}
