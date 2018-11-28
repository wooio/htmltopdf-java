package io.woo.htmltopdf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import io.woo.htmltopdf.wkhtmltopdf.WkHtmlToPdf;

public class HtmlToPdf {

    /**
     * Creates a new {@code HtmlToPdf} instance.
     */
    public static HtmlToPdf create() {
        return new HtmlToPdf();
    }

    private final Map<String,String> settings;
    private final List<HtmlToPdfObject> objects;
    private final List<Consumer<String>> warningCallbacks;
    private final List<Consumer<String>> errorCallbacks;
    private final List<Consumer<HtmlToPdfProgress>> progressChangedCallbacks;
    private final List<Consumer<Boolean>> finishedCallbacks;

    private HtmlToPdf() {
        this.objects = new ArrayList<>();
        this.settings = new HashMap<>();
        warningCallbacks = new ArrayList<>();
        errorCallbacks = new ArrayList<>();
        progressChangedCallbacks = new ArrayList<>();
        finishedCallbacks = new ArrayList<>();
    }

    /**
     * Disable the intelligent shrinking strategy used by WebKit that makes the pixel/dpi ratio none constant
     */
    public HtmlToPdf disableSmartShrinking(boolean disableSmartShrinking) {
        return setting("disable-smart-shrinking", disableSmartShrinking);
    }

    /**
     * Zoom factor. Default is 1. This argument is passed to wkhtmltopdf as <code>--zoom</code> argument
     */
    public HtmlToPdf zoom(float zoomSize) {
        return setting("zoom", zoomSize);
    }

    /**
     * The paper size of the output document.
     */
    public HtmlToPdf pageSize(PdfPageSize pageSize) {
        return setting("size.pageSize", pageSize);
    }

    /**
     * The orientation of the output document.
     */
    public HtmlToPdf orientation(PdfOrientation orientation) {
        return setting("orientation", orientation);
    }

    /**
     * The color mode of the output document.
     */
    public HtmlToPdf colorMode(PdfColorMode colorMode) {
        return setting("colorMode", colorMode);
    }

    /**
     * The DPI of the output document.
     */
    public HtmlToPdf dpi(int dpi) {
        return setting("dpi", dpi);
    }

    /**
     * Whether or not to collate copies.
     */
    public HtmlToPdf collate(boolean collate) {
        return setting("collate", collate);
    }

    /**
     * Whether or not a table of contents should be generated. This is the table of contents
     * in the sidebar.
     */
    public HtmlToPdf outline(boolean outline) {
        return setting("outline", outline);
    }

    /**
     * The maximum depth of the outline.
     */
    public HtmlToPdf outlineDepth(int outlineDepth) {
        return setting("outlineDepth", outlineDepth);
    }

    /**
     * The title of the PDF document.
     */
    public HtmlToPdf documentTitle(String title) {
        return setting("documentTitle", title);
    }

    /**
     * Whether or not loss-less compression should be used.
     */
    public HtmlToPdf compression(boolean compression) {
        return setting("useCompression", compression);
    }

    /**
     * The size of the top margin (CSS value, e.g. "5in", "15px" etc.)
     */
    public HtmlToPdf marginTop(String marginTop) {
        return setting("margin.top", marginTop);
    }

    /**
     * The size of the bottom margin (CSS value, e.g. "5in", "15px" etc.)
     */
    public HtmlToPdf marginBottom(String marginBottom) {
        return setting("margin.bottom", marginBottom);
    }

    /**
     * The size of the left margin (CSS value, e.g. "5in", "15px" etc.)
     */
    public HtmlToPdf marginLeft(String marginLeft) {
        return setting("margin.left", marginLeft);
    }

    /**
     * The size of the right margin (CSS value, e.g. "5in", "15px" etc.)
     */
    public HtmlToPdf marginRight(String marginRight) {
        return setting("margin.right", marginRight);
    }

    /**
     * The maximum DPI to use for images.
     */
    public HtmlToPdf imageDpi(int imageDpi) {
        return setting("imageDPI", imageDpi);
    }

    /**
     * JPEG compression factor (1-100)
     */
    public HtmlToPdf imageQuality(int quality) {
        return setting("imageQuality", quality);
    }

    /**
     * The cookie jar to use when loading and storing cookies.
     */
    public HtmlToPdf cookieJar(String cookieJar) {
        return setting("load.cookieJar", cookieJar);
    }

    /**
     * Adds a consumer for warning messages produced during the conversion process.
     */
    public HtmlToPdf warning(Consumer<String> warningConsumer) {
        warningCallbacks.add(warningConsumer);
        return this;
    }

    /**
     * Adds a consumer for error messages produced during the conversion process.
     */
    public HtmlToPdf error(Consumer<String> errorConsumer) {
        errorCallbacks.add(errorConsumer);
        return this;
    }

    /**
     * Adds a consumer for conversion progress updates.
     */
    public HtmlToPdf progress(Consumer<HtmlToPdfProgress> progressChangeConsumer) {
        progressChangedCallbacks.add(progressChangeConsumer);
        return this;
    }

    /**
     * Adds a consumer for the conversion's finish state. When the conversion is finished,
     * this consumer will be called with a {@code Boolean} value denoting whether the
     * conversion was successful ({@code true}) or a failure ({@code false}).
     */
    public HtmlToPdf finished(Consumer<Boolean> finishConsumer) {
        finishedCallbacks.add(finishConsumer);
        return this;
    }

    /**
     * Adds a runnable to run if the conversion was successful.
     */
    public HtmlToPdf success(Runnable successRunnable) {
        return finished(success -> {
            if (success) {
                successRunnable.run();
            }
        });
    }

    /**
     * Adds a runnable to run if the conversion failed.
     */
    public HtmlToPdf failure(Runnable failureRunnable) {
        return finished(success -> {
            if (!success) {
                failureRunnable.run();
            }
        });
    }

    /**
     * Adds an object to be converted.
     */
    public HtmlToPdf object(HtmlToPdfObject object) {
        objects.add(object);
        return this;
    }

    /**
     * Performs the conversion, saving the result PDF to the specified path.
     * @return {@code true} if the conversion process completed successfully,
     *         or {@code false} otherwise.
     */
    public boolean convert(String path) {
        if (objects.isEmpty()) {
            return false;
        }
        Map<String,String> settings = new HashMap<>(this.settings);
        settings.put("out", path);
        return withConverter(settings, (c, wkHtmlToPdf) -> wkHtmlToPdf.wkhtmltopdf_convert(c) == 1);
    }

    /**
     * Performs the conversion, returning an {@code InputStream} with the
     * bytes of the resulting PDF.
     * @throws HtmlToPdfException if conversion failed
     */
    public InputStream convert() {
        Map<String,String> settings = new HashMap<>(this.settings);
        settings.remove("out");
        return withConverter(settings, (c, wkHtmlToPdf) -> {
            List<String> log = new ArrayList<>();
            warning(w -> log.add("Warning: " + w));
            error(e -> log.add("Error: " + e));
            PointerByReference out = new PointerByReference();
            if (wkHtmlToPdf.wkhtmltopdf_convert(c) == 1) {
                long size = wkHtmlToPdf.wkhtmltopdf_get_output(c, out);
                byte[] pdfBytes = new byte[(int) size];
                out.getValue().read(0, pdfBytes, 0, pdfBytes.length);
                return new ByteArrayInputStream(pdfBytes);
            } else {
                throw new HtmlToPdfException("Conversion returned with failure. Log:\n"
                    + log.stream().collect(Collectors.joining("\n")));
            }
        });
    }

    private HtmlToPdf setting(String name, Object value) {
        return setting(name, value.toString());
    }

    private HtmlToPdf setting(String name, WkValue value) {
        return setting(name, value.getWkValue());
    }

    private HtmlToPdf setting(String name, String value) {
        settings.put(name, value);
        return this;
    }

    public HtmlToPdf setting(Map<String,String> settings) {
        this.settings = settings;
        return this;
    }

    private <T> T withConverter(Map<String,String> settings, BiFunction<Pointer, WkHtmlToPdf, T> consumer) {
        return WkHtmlToPdf.withInstance(wkHtmlToPdf -> {
            Pointer globalSettings = wkHtmlToPdf.wkhtmltopdf_create_global_settings();
            settings.forEach((k, v) -> wkHtmlToPdf.wkhtmltopdf_set_global_setting(globalSettings, k, v));
            Pointer converter = wkHtmlToPdf.wkhtmltopdf_create_converter(globalSettings);
            wkHtmlToPdf.wkhtmltopdf_set_warning_callback(converter, (c, s) -> warningCallbacks.forEach(wc -> wc.accept(s)));
            wkHtmlToPdf.wkhtmltopdf_set_error_callback(converter, (c,s) -> errorCallbacks.forEach(ec -> ec.accept(s)));
            wkHtmlToPdf.wkhtmltopdf_set_progress_changed_callback(converter, (c, phaseProgress) -> {
                int phase = wkHtmlToPdf.wkhtmltopdf_current_phase(c);
                int totalPhases = wkHtmlToPdf.wkhtmltopdf_phase_count(c);
                String phaseDesc = wkHtmlToPdf.wkhtmltopdf_phase_description(c, phase);
                HtmlToPdfProgress progress = new HtmlToPdfProgress(
                        phase,
                        phaseDesc,
                        totalPhases,
                        phaseProgress);
                progressChangedCallbacks.forEach(pc -> pc.accept(progress));
            });
            wkHtmlToPdf.wkhtmltopdf_set_finished_callback(converter, (c, i) -> finishedCallbacks.forEach(fc -> fc.accept(i==1)));
            try {
                objects.forEach((object) -> {
                    Pointer objectSettings = wkHtmlToPdf.wkhtmltopdf_create_object_settings();
                    object.getSettings().forEach((k, v) -> wkHtmlToPdf.wkhtmltopdf_set_object_setting(objectSettings, k, v));
                    wkHtmlToPdf.wkhtmltopdf_add_object(converter, objectSettings, object.getHtmlData());
                });
                return consumer.apply(converter, wkHtmlToPdf);
            } finally {
                wkHtmlToPdf.wkhtmltopdf_destroy_converter(converter);
            }
        });
    }
}
