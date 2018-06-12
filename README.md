## Overview
This project is based on [wkhtmltopdf](https://github.com/wkhtmltopdf/wkhtmltopdf), which converts HTML documents to PDF.
Access to wkhtmltopdf is performed via JNA, exposed through a Java-friendly layer.

## Get it

Gradle:
```groovy
compile 'io.woo:htmltopdf:1.0.3'
```

Maven:
```xml
<dependency>
  <groupId>io.woo</groupId>
  <artifactId>htmltopdf</artifactId>
  <version>1.0.3</version>
  <scope>compile</scope>
</dependency>
```

## Getting started

The following examples should be sufficient to get you started, however there
are many more options discoverable by looking into the methods of `HtmlToPdf` and `HtmlToPdfObject`.

#### Saving HTML as a PDF file

```java
boolean success = HtmlToPdf.create()
    .object(HtmlToPdfObject.forHtml("<p><em>Apples</em>, not oranges</p>"))
    .convert("/path/to/file.pdf");
```

#### Saving a webpage from URL as a PDF file

```java
boolean success = HtmlToPdf.create()
    .object(HtmlToPdfObject.forUrl("https://github.com/wooio/htmltopdf-java"))
    .convert("/path/to/file.pdf");
```

#### Saving multiple objects as a PDF file

```java
boolean success = HtmlToPdf.create()
    .object(HtmlToPdfObject.forUrl("https://github.com/wooio/htmltopdf-java"))
    .object(HtmlToPdfObject.forHtml("<p>This is the second object...</p>"))
    // ...
    .convert("/path/to/file.pdf");
```

#### Converting to InputStream (instead of saving as file)

Converting to an InputStream would be useful if you intend on returning the resulting PDF document 
as an HTTP response or adding it as an email attachment

```java
HtmlToPdf htmlToPdf = HtmlToPdf.create()
    // ...
    .object(HtmlToPdfObject.forUrl("https://github.com/wooio/htmltopdf-java"));

try (InputStream in = htmlToPdf.convert()) {
    // "in" has PDF bytes loaded
} catch (HtmlToPdfException e) {
    // HtmlToPdfException is a RuntimeException, thus you are not required to
    // catch it in this scope. It is thrown when the conversion fails
    // for any reason.
}
```

## Troubleshooting

#### Missing native dependencies
If you get the following exception:
```
java.lang.UnsatisfiedLinkError: Unable to load library '/tmp/io.woo.htmltopdf/wkhtmltox/0.12.4/libwkhtmltox.so': Native library (tmp/io.woo.htmltopdf/wkhtmltox/0.12.4/libwkhtmltox.so) not found in resource path
```
Then that likely means that one of the native dependencies of wkhtmltopdf is not met.
It might be worth checking that the following packages are installed:

- libc6 (or glibc)
- libx11
- libxext
- libxrender
- libstdc++
- libssl1.0
- freetype
- fontconfig

