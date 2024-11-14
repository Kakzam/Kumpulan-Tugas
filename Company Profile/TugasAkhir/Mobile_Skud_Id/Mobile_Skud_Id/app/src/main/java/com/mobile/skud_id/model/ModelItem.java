package com.mobile.skud_id.model;

public class ModelItem {

    private String id;
    private String title;
    private String usage;
    private String price;
    private String advantage;
    private String deficiency;
    private String image;
    private String check_new;

    public ModelItem(String id, String title, String usage, String price, String advantage, String deficiency, String image, String check_new) {
        this.id = id;
        this.title = title;
        this.usage = usage;
        this.price = price;
        this.advantage = advantage;
        this.deficiency = deficiency;
        this.image = image;
        this.check_new = check_new;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getUsage() {
        return usage;
    }

    public String getPrice() {
        return price;
    }

    public String getAdvantage() {
        return advantage;
    }

    public String getDeficiency() {
        return deficiency;
    }

    public String getCheck_new() {
        return check_new;
    }
}
