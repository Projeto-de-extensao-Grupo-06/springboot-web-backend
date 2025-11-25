package com.solarize.solarizeWebBackend.modules.material;

public enum MetricEnum {
    UNIT("unit"),
    METER("meter"),
    CENTIMETER("centimeter");

    public final String metric;

    MetricEnum(String metric) {
        this.metric = metric;
    }
}
