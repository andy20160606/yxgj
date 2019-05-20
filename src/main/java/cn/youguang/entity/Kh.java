package cn.youguang.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 客户
 */

@Entity
@Table(name = "t_kh")
public class Kh extends IdEntity {
    private String khmc;

    private String cslx;  //产生类型

    private String sjhm; //手机号码

    private String loginname;


    @JsonIgnore
    private String loginpass;

    private String wybs; //唯一标识

    private String khbm; //客户编码





    public Integer getVip() {
        return vip;
    }


    private Integer vip;

    private Integer type;

    public String getKhmc() {
        return khmc;
    }

    public void setKhmc(String khmc) {
        this.khmc = khmc;
    }

    public String getCslx() {
        return cslx;
    }

    public void setCslx(String cslx) {
        this.cslx = cslx;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getLoginpass() {
        return loginpass;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }



    public Integer isVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKhbm() {
        return khbm;
    }

    public void setKhbm(String khbm) {
        this.khbm = khbm;
    }






    @Override
    public String toString() {
        return "Kh{" +
                "khmc='" + khmc + '\'' +
                ", cslx='" + cslx + '\'' +
                ", sjhm='" + sjhm + '\'' +
                ", loginname='" + loginname + '\'' +
                ", loginpass='" + loginpass + '\'' +
                ", wybs='" + wybs + '\'' +
                "} " + super.toString();
    }

    public String getWybs() {
        return wybs;
    }

    public void setWybs(String wybs) {
        this.wybs = wybs;
    }
}
