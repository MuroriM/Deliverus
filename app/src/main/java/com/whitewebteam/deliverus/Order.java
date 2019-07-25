package com.whitewebteam.deliverus;

class Order {

    static final int HEAD = 0;
    static final int BODY = 1;
    static final int TAIL = 2;

    private int type, total, deliveryFee, itemPrice, itemPcs;
    private String id, time, user, supermarket, deliverer, itemName;

    Order(int type, String id, String time, String user, String supermarket, int total,
          int deliveryFee, String deliverer, String itemName, int itemPrice, int itemPcs) {
        this.type = type;
        this.id = id;
        this.time = time;
        this.user = user;
        this.supermarket = supermarket;
        this.total = total;
        this.deliveryFee = deliveryFee;
        this.deliverer = deliverer;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemPcs = itemPcs;
    }

    int getType() {
        return type;
    }

    String getId() {
        return id;
    }

    String getTime()
    {
        return time;
    }

    String getUser() {
        return user;
    }

    String getSupermarket() {
        return supermarket;
    }

    int getTotal()
    {
        return total;
    }

    int getDeliveryFee()
    {
        return deliveryFee;
    }

    String getDeliverer() {
        return deliverer;
    }

    String getItemName() {
        return itemName;
    }

    int getItemPrice() {
        return itemPrice;
    }

    int getItemPcs() {
        return itemPcs;
    }
}
