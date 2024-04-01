package com.example.demo.support;

public class Cons {
    public static int LoaiMon_1_week = 24;
    public static int LoaiMon_2_week = 24;
    public static int LoaiMon_3_week = 24;
    public static int LoaiMon_1_lop = 6;
    public static int LoaiMon_2_lop = 4;
    public static int LoaiMon_3_lop = 2;
    public static int getLoaiMon_week(String loaimon)
    {
        if(loaimon.equals("Toan") == true || loaimon.equals("Van") || loaimon.equals("Anh")) {
            return LoaiMon_1_week;
        }else if(loaimon.equals("Ly") || loaimon.equals("Hoa"))
        {
            return LoaiMon_2_week;
        }else{
            return LoaiMon_3_week;
        }
    }
    public static int getLoaiMon_lop(String loaimon)
    {
        if(loaimon.equals("Toan") == true || loaimon.equals("Van") || loaimon.equals("Anh")) {
            return LoaiMon_1_lop;
        }else if(loaimon.equals("Ly") || loaimon.equals("Hoa"))
        {
            return LoaiMon_2_lop;
        }else{
            return LoaiMon_3_lop;
        }
    }
}
