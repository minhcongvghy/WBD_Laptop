package com.codegym.wbdlaptop.message.request;

public class SearchProductByLineAndName {
    private Long lineId;
    private String name;

    public SearchProductByLineAndName() {
    }

    public SearchProductByLineAndName(Long lineId, String name) {
        this.lineId = lineId;
        this.name = name;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
