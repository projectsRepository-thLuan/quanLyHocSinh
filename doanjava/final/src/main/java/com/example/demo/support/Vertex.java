package com.example.demo.support;

import com.example.demo.entity.GiaoVien;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private GiaoVien giaovien;
    private Map<Integer, GiaoVien> near = new HashMap<Integer, GiaoVien>();

    public GiaoVien getGiaovien() {
        return giaovien;
    }

    public void setGiaovien(GiaoVien giaovien) {
        this.giaovien = giaovien;
    }

    public Map<Integer, GiaoVien> getNear() {
        return near;
    }

    public void setNear(Map<Integer, GiaoVien> near) {
        this.near = near;
    }
}
