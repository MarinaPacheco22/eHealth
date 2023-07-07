package com.tfg.eHealth.utils;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public boolean isOrPredicate() {
        return operation.contains("/");
    }
}