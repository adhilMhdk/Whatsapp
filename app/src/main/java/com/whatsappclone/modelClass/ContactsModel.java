package com.whatsappclone.modelClass;

public class ContactsModel {
    String phone,name;

    public ContactsModel(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public ContactsModel() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
