package com.sekud.id.model;

public class ModelItem {

    private String id;
    private String title;
    private String usage;
    private String status;
    private String price;
    private String advantage;
    private String deficiency;
    private String url;

    public ModelItem(String id, String title, String usage, String status, String price, String advantage, String deficiency, String url) {
        this.id = id;
        this.title = title;
        this.usage = usage;
        this.status = status;
        this.price = price;
        this.advantage = advantage;
        this.deficiency = deficiency;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsage() {
        return usage;
    }

    public String getStatus() {
        return status;
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

    public String getUrl() {
        return url;
    }
}
