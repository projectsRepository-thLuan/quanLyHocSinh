package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class LichlamviecPK implements Serializable {

    @NotNull
    @Column(name = "giaovien_id")
    private Integer gvId;
    @NotNull
    @Column(name = "lop_id")
    private Integer lopId;
    @NotNull
    @Column(name = "thu")
    private Integer thu;
    @NotNull
    @Column(name = "tiet")
    private Integer tiet;

    public LichlamviecPK() {
    }

    public LichlamviecPK(Integer gvId, Integer lopId, Integer thu, Integer tiet)
    {
        this.setGvId(gvId);
        this.setLopId(lopId);
        this.setThu(thu);
        this.setTiet(tiet);
    }

    public boolean equals(Object object) {
        if (object instanceof LichlamviecPK) {
            LichlamviecPK pk = (LichlamviecPK) object;
            return getGvId().equals(pk.getGvId()) && getLopId().equals(pk.getLopId()) && getThu().equals(pk.getThu()) && getTiet().equals(pk.getTiet());
        } else {
            return false;
        }
    }
    public int hashCode() {
        return (int)(getGvId() + getLopId() + getThu() + getTiet());
    }

    public Integer getGvId() {
        return gvId;
    }

    public void setGvId(Integer gvId) {
        this.gvId = gvId;
    }

    public Integer getLopId() {
        return lopId;
    }

    public void setLopId(Integer lopId) {
        this.lopId = lopId;
    }

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
