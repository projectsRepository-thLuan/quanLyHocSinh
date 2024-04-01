package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class DiemPK implements Serializable {
    @NotNull
    @Column(name = "hocsinh_id")
    private Integer hsId;
    @NotNull
    @Column(name = "monhoc_id")
    private Integer mhId;
    @NotNull
    @Column(name = "loaidiem_id")
    private Integer loaidiemId;
    @NotNull
    @Column(name = "hocky")
    private Integer hocky;
    public DiemPK(){}
    public DiemPK(Integer hsId,Integer mhId,Integer loaidiemId,Integer hocky){
        this.setHsId(hsId);
        this.setMhId(mhId);
        this.setLoaidiemId(loaidiemId);
        this.setHocky(hocky);
    }
    public boolean equals(Object object) {
        if (object instanceof DiemPK) {
            DiemPK pk = (DiemPK)object;
            return getLoaidiemId().equals(pk.getLoaidiemId()) && getMhId().equals(pk.getMhId()) && getHsId().equals(pk.getHsId()) && getHocky().equals(pk.getHocky());
        } else {
            return false;
        }
    }
    public int hashCode() {
        return (int)(getLoaidiemId() + getMhId() + getHsId() + getHocky());
    }

    public Integer getHocky() {
        return hocky;
    }

    public void setHocky(Integer hocky) {
        this.hocky = hocky;
    }


    public Integer getHsId() {
        return hsId;
    }

    public void setHsId(Integer hsId) {
        this.hsId = hsId;
    }

    public Integer getMhId() {
        return mhId;
    }

    public void setMhId(Integer mhId) {
        this.mhId = mhId;
    }

    public Integer getLoaidiemId() {
        return loaidiemId;
    }

    public void setLoaidiemId(Integer loaidiemId) {
        this.loaidiemId = loaidiemId;
    }
}
