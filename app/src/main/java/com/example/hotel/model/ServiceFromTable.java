package com.example.hotel.model;

public class ServiceFromTable {
    int id;
    int userId;
    int roomId;
    int totalPrice;

    public ServiceFromTable(int id, int roomId, int userId, int totalPrice) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
    }

    public ServiceFromTable(int roomId,int userId, int totalPrice) {
        this.userId = userId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
