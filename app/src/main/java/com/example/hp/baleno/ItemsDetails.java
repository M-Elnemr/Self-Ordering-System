package com.example.hp.baleno;

public class ItemsDetails {

    private int item_image;
    private String item_nameE;
    private String item_nameA;
    private int itemID;
    private String item_des;
    private String item_type;
    private double item_price, item_priceS, item_priceL;

    public ItemsDetails (int item_image, String item_nameE, String item_nameA, int itemID,String item_des, String item_type, double item_price, double item_priceS, double item_priceL){
        setItem_image(item_image);
        setItem_nameE(item_nameE);
        setItem_nameA(item_nameA);
        setItemID(itemID);
        setItem_des(item_des);
        setItem_type(item_type);
        setItem_price(item_price);
        setItem_priceL(item_priceL);
        setItem_priceS(item_priceS);
    }

    public double getItem_priceS() {
        return item_priceS;
    }

    public void setItem_priceS(double item_priceS) {
        this.item_priceS = item_priceS;
    }

    public double getItem_priceL() {
        return item_priceL;
    }

    public void setItem_priceL(double item_priceL) {
        this.item_priceL = item_priceL;
    }

    public int getItem_image() {
        return item_image;
    }

    public void setItem_image(int item_image) {
        this.item_image = item_image;
    }

    public String getItem_nameE() {
        return item_nameE;
    }

    public void setItem_nameE(String item_nameE) {
        this.item_nameE = item_nameE;
    }

    public String getItem_nameA() {
        return item_nameA;
    }

    public void setItem_nameA(String item_nameA) {
        this.item_nameA = item_nameA;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItem_des() {
        return item_des;
    }

    public void setItem_des(String item_des) {
        this.item_des = item_des;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }
}
