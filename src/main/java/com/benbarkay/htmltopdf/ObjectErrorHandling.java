package com.benbarkay.htmltopdf;

public enum ObjectErrorHandling implements WkValue {
    /** Aborts the conversion process. */
    ABORT("abort"),

    /** Skips the erroneous object, adding other successful objects. */
    SKIP("skip"),

    /** Ignores the errors and attempts to add the object to the final output. */
    IGNORE("ignore");

    private final String wkValue;

    ObjectErrorHandling(String wkValue) {
        this.wkValue = wkValue;
    }

    @Override
    public String getWkValue() {
        return wkValue;
    }
}
