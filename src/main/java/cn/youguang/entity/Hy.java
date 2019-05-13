package cn.youguang.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 *
 *
 * 行业
 */


@Table
@Entity(name = "t_hy")
public class Hy extends IdEntity{

    private String hymc;

    private String hyxx;//行业信息

    private String hybq;//行业标签

    private Integer status; //0代表 下架 1代表上架

    private String hyfl;//行业分类 1 还是 2



    @JsonIgnore
    @ManyToMany(fetch= FetchType.LAZY,mappedBy="hys")
    private List<Cp> cps;


    public String getHymc() {
        return hymc;
    }

    public void setHymc(String hymc) {
        this.hymc = hymc;
    }

    public String getHyxx() {
        return hyxx;
    }

    public void setHyxx(String hyxx) {
        this.hyxx = hyxx;
    }

    public String getHybq() {
        return hybq;
    }

    public void setHybq(String hybq) {
        this.hybq = hybq;
    }

    public List<Cp> getCps() {
        return cps;
    }

    public void setCps(List<Cp> cps) {
        this.cps = cps;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHyfl() {
        return hyfl;
    }

    public void setHyfl(String hyfl) {
        this.hyfl = hyfl;
    }

    @Override
    public String toString() {
        return "Hy{" +
                "hymc='" + hymc + '\'' +
                ", hyxx='" + hyxx + '\'' +
                ", hybq='" + hybq + '\'' +
                ", cps=" + cps +
                ", id=" + id +
                '}';
    }
}
