package com.example.demo.support;

public class Ngaylamviec {

    private Integer thu;
    private Integer tiet;
    public Ngaylamviec(Integer thu, Integer tiet)
    {
        this.thu = thu;
        this.tiet = tiet;
    }
    public Ngaylamviec(){}
    public Integer getThu() {
        return thu;
    }

    public void setThu(Integer thu) {
        this.thu = thu;
    }

    public Integer getTiet() {
        return tiet;
    }

    public void setTiet(Integer tiet) {
        this.tiet = tiet;
    }
}
