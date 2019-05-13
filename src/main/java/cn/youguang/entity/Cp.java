package cn.youguang.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;


/**
 * 产品
 */
@Entity
@Table(name = "t_cp")
public class Cp extends IdEntity {


    private String cpmc;

    private String cpxx;//产品信息

    private String cplb;//产品类别

    private String cpbh;//产品编号

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sjsj;  //上架时间

    private Integer cpzt; //产品状态 0 下架 1 上架

    private Double cpyj; //产品原价

    private Double cpxj;  //产品现价


    private String cpzst; //产品展示图 对应文件名


    private String cpxqt1; //产品详情图1 对应文件名

    private String cpxqt2; //产品详情图2 对应文件名

    private String cpxqt3;//产品详情图3

    private String cptct;//产品套餐图

    private String zsdz;//真实地址


    @ManyToMany
    private List<Hy> hys;


    @ManyToMany
    private List<Zt> zts;


    private String rd;  //热度

    private String gms;  //购买度


    private String wxlbht; //微信列表横图


    private String czdz;  //操作地址 获取操作地址url（ps:小工具优惠券核销等）


    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getCpxx() {
        return cpxx;
    }

    public void setCpxx(String cpxx) {
        this.cpxx = cpxx;
    }

    public String getCplb() {
        return cplb;
    }

    public void setCplb(String cplb) {
        this.cplb = cplb;
    }

    public Date getSjsj() {
        return sjsj;
    }

    public void setSjsj(Date sjsj) {
        this.sjsj = sjsj;
    }

    public Integer getCpzt() {
        return cpzt;
    }

    public void setCpzt(Integer cpzt) {
        this.cpzt = cpzt;
    }

    public Double getCpyj() {
        return cpyj;
    }

    public void setCpyj(Double cpyj) {
        this.cpyj = cpyj;
    }

    public Double getCpxj() {
        return cpxj;
    }

    public void setCpxj(Double cpxj) {
        this.cpxj = cpxj;
    }


    public List<Hy> getHys() {
        return hys;
    }

    public void setHys(List<Hy> hys) {
        this.hys = hys;
    }

    public String getCpzst() {
        return cpzst;
    }

    public void setCpzst(String cpzst) {
        this.cpzst = cpzst;
    }

    public String getCpxqt1() {
        return cpxqt1;
    }

    public void setCpxqt1(String cpxqt1) {
        this.cpxqt1 = cpxqt1;
    }

    public String getCpxqt2() {
        return cpxqt2;
    }

    public void setCpxqt2(String cpxqt2) {
        this.cpxqt2 = cpxqt2;
    }


    public List<Zt> getZts() {
        return zts;
    }

    public void setZts(List<Zt> zts) {
        this.zts = zts;
    }


    public String getCpbh() {
        return cpbh;
    }

    public void setCpbh(String cpbh) {
        this.cpbh = cpbh;
    }


    public String getCpxqt3() {
        return cpxqt3;
    }

    public void setCpxqt3(String cpxqt3) {
        this.cpxqt3 = cpxqt3;
    }

    public String getCptct() {
        return cptct;
    }

    public void setCptct(String cptct) {
        this.cptct = cptct;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getGms() {
        return gms;
    }

    public void setGms(String gms) {
        this.gms = gms;
    }


    public String getZsdz() {
        return zsdz;
    }

    public void setZsdz(String zsdz) {
        this.zsdz = zsdz;
    }


    public String getWxlbht() {
        return wxlbht;
    }

    public void setWxlbht(String wxlbht) {
        this.wxlbht = wxlbht;
    }


    public String getCzdz() {
        return czdz;
    }

    public void setCzdz(String czdz) {
        this.czdz = czdz;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Cp cp = (Cp) obj;
        if (this == cp) {
            return true;
        } else {
            return (this.id.equals(cp.id));
        }

    }
}
