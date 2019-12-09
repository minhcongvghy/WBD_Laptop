package com.codegym.wbdlaptop.message.request;

public class SearchProductByNameForm {
    private String name;

    public SearchProductByNameForm() {
    }

    public SearchProductByNameForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
