package com.example.model;

public class BusShopWeeklyTaskLog {

    private int tenantId;
    private int gptqty;
    private int vipgptqty;
    private int orderqty;

    // 添加构造函数
    public BusShopWeeklyTaskLog(int tenantId, int gptqty, int vipgptqty, int orderqty) {
        this.tenantId = tenantId;
        this.gptqty = gptqty;
        this.vipgptqty = vipgptqty;
        this.orderqty = orderqty;
    }

    // Getter 和 Setter 方法
    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getGptqty() {
        return gptqty;
    }

    public void setGptqty(int gptqty) {
        this.gptqty = gptqty;
    }

    public int getVipgptqty() {
        return vipgptqty;
    }

    public void setVipgptqty(int vipgptqty) {
        this.vipgptqty = vipgptqty;
    }

    public int getOrderqty() {
        return orderqty;
    }

    public void setOrderqty(int orderqty) {
        this.orderqty = orderqty;
    }
}
