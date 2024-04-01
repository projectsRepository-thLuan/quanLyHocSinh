package com.example.demo.entity;


import java.util.ArrayList;
import java.util.List;

public class ViewThongKe {
    private List<ViewBangDiem> hs_hk1 = new ArrayList<ViewBangDiem>();
    private List<ViewBangDiem> hs_hk2 = new ArrayList<ViewBangDiem>();
    private Integer lop_id;
    private String thoidiem;
    private String loaihocsinh;

    public List<KetQuaThongKe> thongke()
    {
        List<KetQuaThongKe> l_kqtk = new ArrayList<>();
        if (this.getThoidiem().equals("hk1"))
        {
            if(this.getLoaihocsinh().equals("yeu"))
            {
                for(ViewBangDiem vhs : this.hs_hk1)
                {
                    if (vhs.getDiemHK() < 5 && vhs.getDiemHK() != -1)
                    {
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vhs.getHocsinh());
                        kqtk.setDiem(vhs.getDiemHK());
                        l_kqtk.add(kqtk);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("tb"))
            {
                for(ViewBangDiem vhs : hs_hk1)
                {
                    if ((vhs.getDiemHK() < 6.5 && vhs.getDiemHK() >= 5) && vhs.getDiemHK() != -1)
                    {
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vhs.getHocsinh());
                        kqtk.setDiem(vhs.getDiemHK());
                        l_kqtk.add(kqtk);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("kha"))
            {
                for(ViewBangDiem vhs : hs_hk1)
                {
                    if ((vhs.getDiemHK() < 8 && vhs.getDiemHK() >= 6.5) && vhs.getDiemHK() != -1)
                    {
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vhs.getHocsinh());
                        kqtk.setDiem(vhs.getDiemHK());
                        l_kqtk.add(kqtk);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("gioi"))
            {
                for(ViewBangDiem vhs : hs_hk1)
                {
                    if (vhs.getDiemHK() >= 8 && vhs.getDiemHK() != -1)
                    {
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vhs.getHocsinh());
                        kqtk.setDiem(vhs.getDiemHK());
                        l_kqtk.add(kqtk);
                    }
                }
            }
        }


        if (this.getThoidiem().equals("hk2"))
        {
            if(this.getLoaihocsinh().equals("yeu"))
            {
                for(ViewBangDiem vhs : hs_hk2)
                {
                    if (vhs.getDiemHK() < 5 && vhs.getDiemHK() != -1)
                    {
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vhs.getHocsinh());
                        kqtk.setDiem(vhs.getDiemHK());
                        l_kqtk.add(kqtk);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("tb"))
            {
                for(ViewBangDiem vhs : hs_hk2)
                {
                    if ((vhs.getDiemHK() < 6.5 && vhs.getDiemHK() >= 5) && vhs.getDiemHK() != -1)
                    {
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vhs.getHocsinh());
                        kqtk.setDiem(vhs.getDiemHK());
                        l_kqtk.add(kqtk);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("kha"))
            {
                for(ViewBangDiem vhs : hs_hk2)
                {
                    if ((vhs.getDiemHK() < 8 && vhs.getDiemHK() >= 6.5) && vhs.getDiemHK() != -1)
                    {
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vhs.getHocsinh());
                        kqtk.setDiem(vhs.getDiemHK());
                        l_kqtk.add(kqtk);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("gioi"))
            {
                for(ViewBangDiem vhs : hs_hk2)
                {
                    if (vhs.getDiemHK() >= 8 && vhs.getDiemHK() != -1)
                    {
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vhs.getHocsinh());
                        kqtk.setDiem(vhs.getDiemHK());
                        l_kqtk.add(kqtk);
                    }
                }
            }
        }


        if (this.getThoidiem().equals("all"))
        {
            boolean flag = false;
            List<KetQuaThongKe> tmp = new ArrayList<>();
            for(ViewBangDiem vbs_1: hs_hk1)
            {
                if(flag == true)
                    break;
                for(ViewBangDiem vbs_2 : hs_hk2)
                {
                    if(vbs_1.getHocsinh().getMasv().equals(vbs_2.getHocsinh().getMasv()))
                    {
                        if(vbs_1.getDiemHK() == -1 || vbs_2.getDiemHK() == -1)
                        {
                            flag = true;
                            break;
                        }
                        KetQuaThongKe kqtk = new KetQuaThongKe();
                        kqtk.setHocsinh(vbs_1.getHocsinh());

                        kqtk.setDiem(((vbs_1.getDiemHK() + (vbs_2.getDiemHK() * 2)) / 3));
                        tmp.add(kqtk);

                    }
                }
            }
            if(this.getLoaihocsinh().equals("yeu") && flag == false)
            {
                for(KetQuaThongKe kq : tmp){
                    if (kq.getDiem() < 5)
                    {
                        l_kqtk.add(kq);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("tb") && flag == false)
            {
                for(KetQuaThongKe kq : tmp){
                    if (kq.getDiem() < 6.5 && kq.getDiem() >= 5)
                    {
                        l_kqtk.add(kq);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("kha") && flag == false)
            {
                for(KetQuaThongKe kq : tmp){
                    if (kq.getDiem() < 8 && kq.getDiem() >= 6.5)
                    {
                        l_kqtk.add(kq);
                    }
                }
            }
            if (this.getLoaihocsinh().equals("gioi") && flag == false)
            {
                for(KetQuaThongKe kq : tmp){
                    if (kq.getDiem() >= 8)
                    {
                        l_kqtk.add(kq);
                    }
                }
            }
        }
        return l_kqtk;
    }

    public List<ViewBangDiem> getHs_hk1() {
        return hs_hk1;
    }

    public void setHs_hk1(List<ViewBangDiem> hs_hk1) {
        this.hs_hk1 = hs_hk1;
    }

    public List<ViewBangDiem> getHs_hk2() {
        return hs_hk2;
    }

    public void setHs_hk2(List<ViewBangDiem> hs_hk2) {
        this.hs_hk2 = hs_hk2;
    }

    public String getThoidiem() {
        return thoidiem;
    }

    public void setThoidiem(String thoidiem) {
        this.thoidiem = thoidiem;
    }

    public String getLoaihocsinh() {
        return loaihocsinh;
    }

    public void setLoaihocsinh(String loaihocsinh) {
        this.loaihocsinh = loaihocsinh;
    }

    public Integer getLop_id() {
        return lop_id;
    }

    public void setLop_id(Integer lop_id) {
        this.lop_id = lop_id;
    }
}
