package io.woo.htmltopdf.wkhtmltopdf;

import com.sun.jna.Native;
import com.sun.jna.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

class WkHtmlToPdfLoader {
    static WkHtmlToPdf load() {
        Path tmpFile;
        try {
            tmpFile = Files.createTempFile("htmlToPdfLoader", "nativeLibrary");
            try {
                try (InputStream in = WkHtmlToPdfLoader.class.getResourceAsStream(getLibraryResource())) {
                    Files.copy(in, tmpFile, StandardCopyOption.REPLACE_EXISTING);
                    return (WkHtmlToPdf)Native.loadLibrary(tmpFile.toAbsolutePath().toString(), WkHtmlToPdf.class);
                }
            } finally {
                Files.deleteIfExists(tmpFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String getLibraryResource() {
        return "/wkhtmltox/0.12.4/"
                + (Platform.isWindows() ? "" : "lib")
                + "wkhtmltox"
                + (Platform.is64Bit() ? "" : ".32")
                + (Platform.isWindows() ? ".dll" : ".so");
    }
}
