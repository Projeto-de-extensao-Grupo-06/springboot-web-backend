package com.solarize.solarizeWebBackend.modules.address;

public enum TypeEnum {
    RESIDENTIAL("residential"),
    COMMERCIAL("commercial"),
    BUILDING("building"),
    OTHER("other");

    public String value;

    TypeEnum(String value) {
        this.value = value;

    }
}
