package com.solarize.solarizeWebBackend.materialCatalog;

public enum MetricEnum {
    UNIT("unit"),
    METER("meter"),
    CENTIMETER("centimeter");

    public String metric;

    MetricEnum(String metric) {
        this.metric = metric;
    }
}
