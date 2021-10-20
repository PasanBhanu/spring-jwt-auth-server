package com.softinklab.rest.action;

public enum Default implements Action{
    NULL(null);

    private final String label;

    Default(String label) {
        this.label = label;
    }

    @Override
    public String label() {
        return label;
    }
}
