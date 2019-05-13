package cn.youguang.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 客户
 */

@Entity
@Table(name = "t_khcp")
public class Khcp extends IdEntity {


    @ManyToOne
    private Kh kh;


    @ManyToOne
    private Cp cp;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date starttime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stoptime;

    private String sm;//说明


    public Kh getKh() {
        return kh;
    }

    public void setKh(Kh kh) {
        this.kh = kh;
    }

    public Cp getCp() {
        return cp;
    }

    public void setCp(Cp cp) {
        this.cp = cp;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStoptime() {
        return stoptime;
    }

    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }


    @Override
    public String toString() {
        return "Khcp{" +
                "kh=" + kh +
                ", cp=" + cp +
                ", starttime=" + starttime +
                ", stoptime=" + stoptime +
                ", sm='" + sm + '\'' +
                ", id=" + id +
                "} " + super.toString();
    }
}
