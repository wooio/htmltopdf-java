![Build status](https://travis-ci.org/benbarkay/htmltopdf-java.svg?branch=master)

# Overview
This project is based on [wkhtmltopdf](https://github.com/wkhtmltopdf/wkhtmltopdf), which converts HTML documents to PDF.
Access to wkhtmltopdf is performed via JNA, exposed through a Java-friendly layer.

## Maven

The project is hosted in Maven Central at the following coordinates:
 
`com.benbarkay.htmltopdf:htmltopdf:0.1.0`

## Usage

```java
// Convert amazon.com's homepage, streaming PDF bytes via an InputStream
try (InputStream pdfIn = HtmlToPdf.create()
                .object(HtmlToPdfObject.forUrl("http://amazon.com"))
                .convert()) {
    // pdfIn contains the generated PDF bytes
} catch (HtmlToPdfException e) {
    // On failure, an exception is thrown
}

// Convert github.com's homepage, saving the resulting PDF to a file
boolean success = HtmlToPdf.create()
    .object(HtmlToPdfObject.forUrl("http://github.com"))
    .convert("/path/to/saved.pdf");
```
        
You may discover more options by looking into the methods of `HtmlToPdf` and `HtmlToPdfObject`.