package com.solarize.solarizeWebBackend.material_catalog;

public enum MetricEnum {
    UNIT("unit"),
    METER("meter"),
    CENTIMETER("centimeter");

    public String metric;

    MetricEnum(String metric) {
        this.metric = metric;
    }
}
