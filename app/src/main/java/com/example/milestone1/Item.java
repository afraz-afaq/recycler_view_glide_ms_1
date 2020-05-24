package com.example.milestone1;

public class Item {

    private String mName;
    private String mEmail;
    private String mImage;

    public Item(String mName, String mEmail, String mImage) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mImage = mImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}

