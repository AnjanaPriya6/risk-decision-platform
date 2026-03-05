package model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ChannelType {
    WEB,
    MOBILE,
    API;

    @JsonCreator
    public static ChannelType fromValue(String value) {
        return ChannelType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
