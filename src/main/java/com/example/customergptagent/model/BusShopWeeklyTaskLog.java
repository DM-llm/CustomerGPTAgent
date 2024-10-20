package com.example.customergptagent.model;

import java.time.LocalDateTime;

public class BusShopWeeklyTaskLog {
    private int id;
    private int shopId;
    private int gptqty;
    private int vipgptqty;
    private int orderqty;
    private LocalDateTime createTime;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
