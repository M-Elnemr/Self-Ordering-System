package com.example.hp.baleno;

public class Items {

    private String namesE;
    private String namesA;
    private int pics;

    public Items (String namesE, String namesA, int pics){
        setNamesE(namesE);
        setNamesA(namesA);
        setPics(pics);
    }

    public String getNamesE() {
        return namesE;
    }

    public void setNamesE(String namesE) {
        this.namesE = namesE;
    }

    public String getNamesA() {
        return namesA;
    }

    public void setNamesA(String namesA) {
        this.namesA = namesA;
    }

    public int getPics() {
        return pics;
    }

    public void setPics(int pics) {
        this.pics = pics;
    }
}
