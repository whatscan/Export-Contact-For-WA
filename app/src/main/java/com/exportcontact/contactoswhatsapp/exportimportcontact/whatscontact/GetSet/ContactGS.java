package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet;

public class ContactGS {
    String name;
    String number;

    public ContactGS(String str, String str2) {
        this.name = str;
        this.number = str2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String str) {
        this.number = str;
    }
}
