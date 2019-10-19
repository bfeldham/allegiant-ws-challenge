package com.barry.allegiant.challenge.springboot.model;

public enum SearchField {
	FIRSTNAME("first_name"), LASTNAME("last_name"), EMAIL("email"), IP("ip");
	
    private String value;

    SearchField(final String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
