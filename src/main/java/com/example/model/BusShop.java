package com.example.model;

import java.time.LocalDateTime;

public class BusShop {
    private int tenantId;
    private int gptqty;
    private int vipgptqty;
    private int orderqty;
    private LocalDateTime updateTime;

    // Getters and Setters

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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
