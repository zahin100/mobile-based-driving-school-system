package com.example.fyp;

public class Licence {

    private int licenceID;
    private String type;
    private double price;

    private static final Licence instance = new Licence();

    public static Licence getInstance(){
        return instance;

    }

    public Licence(){
        super();
    }

    public int getLicenceID() {
        return licenceID;
    }

    public void setLicenceID(int licenceID) {
        this.licenceID = licenceID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
