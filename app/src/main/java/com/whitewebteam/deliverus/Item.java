package com.whitewebteam.deliverus;

class Item
{
    private int id, price;
    private String name, thumbnailURL;

    void setId(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    void setName(String name)
    {
        this.name = name;
    }

    String getName()
    {
        return name;
    }

    void setPrice(int price)
    {
        this.price = price;
    }

    int getPrice()
    {
        return price;
    }

    void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    String getThumbnailURL() {
        return thumbnailURL;
    }
}
