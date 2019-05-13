package cn.youguang.dto;

public class HdllDto {

    private Long hdId;
    private String hdmc;  //产品名称
    private Long var1;  //上月次数
    private Long var2;  //本月次数
    private Long var3;//

    public Long getHdId() {
        return hdId;
    }

    public void setHdId(Long hdId) {
        this.hdId = hdId;
    }

    public String getHdmc() {
        return hdmc;
    }

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }


    public Long getVar1() {
        return var1;
    }

    public void setVar1(Long var1) {
        this.var1 = var1;
    }

    public Long getVar2() {
        return var2;
    }

    public void setVar2(Long var2) {
        this.var2 = var2;
    }

    public Long getVar3() {
        return var3;
    }

    public void setVar3(Long var3) {
        this.var3 = var3;
    }
}
