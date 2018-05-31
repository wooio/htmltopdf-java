package io.woo.htmltopdf;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HtmlToPdfTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void convertReturnsTrueWhenConversionSuccessful() throws IOException {
        File file = tempFolder.newFile();
        boolean result = HtmlToPdf.create()
                .object(HtmlToPdfObject.forHtml("<p>Test</p>"))
                .convert(file.getPath());

        assertTrue(result);
    }

    @Test
    public void convertReturnsFalseWhenConversionFailed() throws IOException {
        File file = tempFolder.newFile();
        boolean result = HtmlToPdf.create()
                .object(HtmlToPdfObject.forUrl("file:///path/that/does/not/exist"))
                .convert(file.getPath());
        assertFalse(result);
    }

    @Test
    public void convertReturnsFalseWhenNoObjectsSpecified() throws IOException {
        File file = tempFolder.newFile();
        boolean result = HtmlToPdf.create().convert(file.getPath());
        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingObjectForHtmlWithNoContentThrowsException() {
        HtmlToPdfObject.forHtml("");
    }

    @Test(expected = HtmlToPdfException.class)
    public void convertToInputStreamThrowsExceptionOnFailure() {
        HtmlToPdf.create()
                .object(HtmlToPdfObject.forUrl("file:///path/that/does/not/exist"))
                .convert();
    }
}