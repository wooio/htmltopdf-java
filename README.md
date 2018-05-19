# htmltopdf-java
This project is based on [wkhtmltopdf](https://github.com/wkhtmltopdf/wkhtmltopdf) and uses JNA to access the native C API.

## Examples

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

// Attempt to access a bad URL and learn about the failure
HtmlToPdf.create()
    .object(HtmlToPdfObject.forUrl("http://someurlwith.badtld"))
    .failure(() -> System.out.println("Failed to load PDF"))
    .convert("/path/to/saved.pdf");
```
        
You may discover more options by looking into `HtmlToPdf` and `HtmlToPdfObject` methods.
This mapping of `wkhtmltopdf` provides almost all of the features available in the C library and executable.