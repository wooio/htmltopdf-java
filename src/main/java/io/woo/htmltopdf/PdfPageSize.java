package io.woo.htmltopdf;

public enum PdfPageSize implements WkValue {

    /** 841 x 1189 mm */
    A0,

    /** 594 x 841 mm */
    A1,

    /** 420 x 594 mm */
    A2,

    /** 297 x 420 mm */
    A3,

    /** 210 x 297 mm, 8.26 x 11.69 inches */
    A4,

    /** 148 x 210 mm */
    A5,

    /** 105 x 148 mm */
    A6,

    /** 74 x 105 mm */
    A7,

    /** 52 x 74 mm */
    A8,

    /** 37 x 52 mm */
    A9,

    /** 1000 x 1414 mm */
    B0,

    /** 707 x 1000 mm */
    B1,

    /** 500 x 707 mm */
    B2,

    /** 353 x 500 mm */
    B3,

    /** 250 x 353 mm */
    B4,

    /** 176 x 250 mm, 6.93 x 9.84 inches */
    B5,

    /** 125 x 176 mm */
    B6,

    /** 88 x 125 mm */
    B7,

    /** 62 x 88 mm */
    B8,

    /** 33 x 62 mm */
    B9,

    /** 31 x 44 mm */
    B10,

    /** 163 x 229 mm */
    C5E,

    /** 105 x 241 mm, U.S. Common 10 Envelope */
    Comm10E,

    /** 110 x 220 mm */
    DLE,

    /** 7.5 x 10 inches, 190.5 x 254 mm */
    Executive,

    /** 210 x 330 mm */
    Folio,

    /** 431.8 x 279.4 mm */
    Ledger,

    /** 8.5 x 14 inches, 215.9 x 355.6 mm */
    Legal,

    /** 8.5 x 11 inches, 215.9 x 279.4 mm */
    Letter,

    /** 279.4 x 431.8 mm */
    Tabloid;

    @Override
    public String getWkValue() {
        return name();
    }
}
