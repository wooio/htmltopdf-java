package com.benbarkay.htmltopdf;

public enum PdfColorMode implements WkValue {
    COLOR("Color"),
    GRAYSCALE("Grayscale");

    private final String wkValue;

    PdfColorMode(String wkValue) {
        this.wkValue = wkValue;
    }

    @Override
    public String getWkValue() {
        return wkValue;
    }
}
