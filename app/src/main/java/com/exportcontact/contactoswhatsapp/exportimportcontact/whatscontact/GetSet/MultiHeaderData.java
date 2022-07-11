package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.GetSet;

public class MultiHeaderData {
    private String banner_color;
    private String banner_text;
    private String f444id;
    private String image_url;
    private String is_banner;
    private String last_modified_date;
    private String link;
    private String redirection;
    private String text_color;
    private String type;
    private String user;

    public MultiHeaderData(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.f444id = str;
        this.type = str2;
        this.user = str3;
        this.is_banner = str4;
        this.banner_text = str5;
        this.image_url = str6;
        this.link = str7;
        this.redirection = str8;
        this.banner_color = str9;
        this.text_color = str10;
        this.last_modified_date = str11;
    }

    public String getId() {
        return this.f444id;
    }

    public void setId(String str) {
        this.f444id = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String str) {
        this.user = str;
    }

    public String getIs_banner() {
        return this.is_banner;
    }

    public void setIs_banner(String str) {
        this.is_banner = str;
    }

    public String getBanner_text() {
        return this.banner_text;
    }

    public void setBanner_text(String str) {
        this.banner_text = str;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String str) {
        this.image_url = str;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public String getRedirection() {
        return this.redirection;
    }

    public void setRedirection(String str) {
        this.redirection = str;
    }

    public String getBanner_color() {
        return this.banner_color;
    }

    public void setBanner_color(String str) {
        this.banner_color = str;
    }

    public String getText_color() {
        return this.text_color;
    }

    public void setText_color(String str) {
        this.text_color = str;
    }

    public String getLast_modified_date() {
        return this.last_modified_date;
    }

    public void setLast_modified_date(String str) {
        this.last_modified_date = str;
    }
}
