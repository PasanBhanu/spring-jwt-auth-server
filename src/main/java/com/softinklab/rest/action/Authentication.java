package com.softinklab.rest.action;

public enum Authentication implements Action {
    RESEND_VERIFICATION_EMAIL("AUT_010"),
    LOGIN("AUT_005"),
    REGISTRATION_DISABLED("AUT_001"),
    SESSION_EXPIRED("AUT_006"),
    AUTHENTICATION_FAILED("AUT_007");

    private final String label;

    Authentication(String label) {
        this.label = label;
    }

    @Override
    public String label() {
        return label;
    }
}
