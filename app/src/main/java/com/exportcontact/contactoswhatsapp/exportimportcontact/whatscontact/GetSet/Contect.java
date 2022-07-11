package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet;

import java.io.Serializable;

public class Contect implements Serializable {
    public String contect;


    public String f450id;

    public Contect() {
    }

    public Contect(String str) {
        this.contect = str;
    }

    public Contect(String str, String str2) {
        this.f450id = str;
        this.contect = str2;
    }

    public String getId() {
        return this.f450id;
    }

    public void setId(String str) {
        this.f450id = str;
    }

    public String getContect() {
        return this.contect;
    }

    public void setContect(String str) {
        this.contect = str;
    }
}
