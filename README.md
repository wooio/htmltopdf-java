# htmltopdf-java
This project is based on wkhtmltopdf and uses JNA to access the native C API.

## Usage

A simple example (creating a PDF of the amazon.com homepage):

    HtmlToPdf.create()
        .object(HtmlToPdfObject.forUrl("http://amazon.com"))
        .convert("/path/to/amazon.pdf");
        
You may discover more options by looking into `HtmlToPdf` and `HtmlToPdfObject` methods.