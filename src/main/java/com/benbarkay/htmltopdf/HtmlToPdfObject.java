package com.benbarkay.htmltopdf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HtmlToPdfObject {

    /**
     * Creates a new {@code HtmlToPdfObject} using the specified HTML as content.
     * @param html  The HTML code to convert to PDF.
     * @return  The created {@code HtmlToPdfObject} instance.
     */
    public static HtmlToPdfObject forHtml(String html) {
        if (html == null || html.isEmpty() || html.startsWith("\0")) {
            throw new IllegalArgumentException("No content specified for object.");
        }
        return new HtmlToPdfObject(html);
    }

    /**
     * Creates a new {@code HtmlToPdfObject} for the specified URL. The content will be
     * obtained from the specified URL during the conversion process.
     * @param url   The URL to obtain HTML content from.
     * @return  The created {@code HtmlToPdfObject} instance.
     */
    public static HtmlToPdfObject forUrl(String url) {
        HtmlToPdfObject object = new HtmlToPdfObject(null);
        object.settings.put("page", url);
        return object;
    }

    private final Map<String, String> settings;
    private final String htmlData;

    private HtmlToPdfObject(String htmlData) {
        this.settings = new HashMap<>();
        this.htmlData = htmlData;
    }

    /**
     * Whether or not to show the page's background.
     */
    public HtmlToPdfObject showBackground(boolean background) {
        return setting("web.background", background);
    }

    /**
     * Whether or not to load images.
     */
    public HtmlToPdfObject loadImages(boolean load) {
        return setting("web.loadImages", load);
    }

    /**
     * Whether or not to enable javascript.
     */
    public HtmlToPdfObject enableJavascript(boolean enable) {
        return setting("web.enableJavascript", enable);
    }

    /**
     * Whether or not to enable Intelligent Shrinking. Intelligent Shrinking will
     * attempt to fit more content into pages if enabled.
     */
    public HtmlToPdfObject enableIntelligentShrinking(boolean enable) {
        return setting("web.enableIntelligentShrinking", enable);
    }

    /**
     * The minimum font size allowed.
     */
    public HtmlToPdfObject minimumFontSize(int size) {
        return setting("web.minimumFontSize", size);
    }

    /**
     * Whether or not to use "print" media type (instead of "screen" media type) for CSS styles.
     */
    public HtmlToPdfObject usePrintMediaType(boolean use) {
        return setting("web.printMediaType", use);
    }

    /**
     * The character encoding to use when not specified by the webpage (e.g. "utf-8")
     */
    public HtmlToPdfObject defaultEncoding(String encoding) {
        return setting("web.defaultEncoding", encoding);
    }

    /**
     * A stylesheet to apply for the conversion.
     * @param urlOrPath The URL or path to the stylesheet to apply.
     */
    public HtmlToPdfObject userStylesheet(String urlOrPath) {
        return setting("web.userStyleSheet", urlOrPath);
    }

    /**
     * The auth username to use when requesting the webpage.
     */
    public HtmlToPdfObject authUsername(String username) {
        return setting("load.username", username);
    }

    /**
     * The auth password to use when requesting the webpage.
     */
    public HtmlToPdfObject authPassword(String password) {
        return setting("load.password", password);
    }

    /**
     * Delay, in milliseconds, to allow between the time when the page has been
     * loaded and the time of conversion to PDF. This might be needed to allow
     * javascript to load and display content.
     */
    public HtmlToPdfObject javascriptDelay(int delayMs) {
        return setting("load.jsdelay", delayMs);
    }

    /**
     * Amount of zoom to use when converting.
     */
    public HtmlToPdfObject zoomFactor(float factor) {
        return setting("load.zoomFactor", factor);
    }

    /**
     * Whether or not to block the webpage from accessing local file access.
     */
    public HtmlToPdfObject blockLocalFileAccess(boolean block) {
        return setting("load.blockLocalFileAccess", block);
    }

    /**
     * Whether or not to stop slow running javascript.
     */
    public HtmlToPdfObject stopSlowScript(boolean stop) {
        return setting("load.stopSlowScript", stop);
    }

    /**
     * Whether or not to debug javascript warnings and errors. If enabled,
     * warnings and errors from javascript will be added to {@link HtmlToPdf}
     * warning callback.
     * @see HtmlToPdf#warning(Consumer)
     */
    public HtmlToPdfObject debugJavascriptWarningsAndErrors(boolean debug) {
        return setting("load.debugJavascript", debug);
    }

    /**
     * Specifies the way in which errors are handled for this object.
     */
    public HtmlToPdfObject handleErrors(ObjectErrorHandling errorHandling) {
        return setting("load.loadErrorHandling", errorHandling);
    }

    /**
     * The font size of the custom header to add.
     */
    public HtmlToPdfObject headerFontSize(int size) {
        return setting("header.fontSize", size);
    }

    /**
     * The font name of the custom header to add.
     */
    public HtmlToPdfObject headerFontName(String fontName) {
        return setting("header.fontName", fontName);
    }

    /**
     * Whether or not to add a line beneath the custom header.
     */
    public HtmlToPdfObject headerLine(boolean line) {
        return setting("header.line", line);
    }

    /**
     * The amount of spacing between the header and the content.
     */
    public HtmlToPdfObject headerSpacing(int spacing) {
        return setting("header.spacing", spacing);
    }

    /**
     * URL for an HTML document to use for the header.
     */
    public HtmlToPdfObject headerHtmlUrl(String url) {
        return setting("header.htmlUrl", url);
    }

    /**
     * Text to write in the left part of the header.
     */
    public HtmlToPdfObject headerLeft(String text) {
        return setting("header.left", text);
    }

    /**
     * Text to write in the center part of the header.
     */
    public HtmlToPdfObject headerCenter(String text) {
        return setting("header.center", text);
    }

    /**
     * Text to write in the right part of the header.
     */
    public HtmlToPdfObject headerRight(String text) {
        return setting("header.right", text);
    }

    /**
     * Whether or not to use dotted lines for the table of contents.
     */
    public HtmlToPdfObject tableOfContentsDottedLines(boolean dottedLines) {
        return setting("toc.useDottedLines", dottedLines);
    }

    /**
     * The caption text to use for the table of contents.
     */
    public HtmlToPdfObject tableOfContentsCaptionText(String captionText) {
        return setting("toc.captionText", captionText);
    }

    /**
     * Whether or not the table of contents should link to the content in the PDF document.
     */
    public HtmlToPdfObject tableOfContentsForwardLinks(boolean forward) {
        return setting("toc.forwardLinks", forward);
    }

    /**
     * Whether or not content should link back to the table of contents.
     */
    public HtmlToPdfObject tableOfContentsBackLinks(boolean backLinks) {
        return setting("toc.backLinks", backLinks);
    }

    /**
     * The indentation to use for the table of contents. This string is a size
     * such as used in CSS ("5px", "2em" etc.)
     */
    public HtmlToPdfObject tableOfContentsIndentation(String indentation) {
        return setting("toc.indentation", indentation);
    }

    /**
     * The scale-down per indentation of the table of contents. For instance, a value of
     * {@code 0.8} will scale the font down by 20% for every level in the table of contents.
     */
    public HtmlToPdfObject tableOfContentsIndentationFontScaleDown(float scale) {
        return setting("toc.fontScale", scale);
    }

    /**
     * Whether or not sections from the document should be included in the outline of the
     * table of contents.
     */
    public HtmlToPdfObject tableOfContentsIncludeSections(boolean include) {
        return setting("includeInOutline", include);
    }

    /**
     * Whether or not to keep external links.
     */
    public HtmlToPdfObject useExternalLinks(boolean use) {
        return setting("useExternalLinks", use);
    }

    /**
     * Whether or not to convert links within the webpage to PDF references.
     */
    public HtmlToPdfObject convertInternalLinksToPdfReferences(boolean convert) {
        return setting("useLocalLinks", convert);
    }

    /**
     * Whether or not to turn HTML forms into PDF forms.
     */
    public HtmlToPdfObject produceForms(boolean produce) {
        return setting("produceForms", produce);
    }

    /**
     * Whether or not to include page count in the header, footer, and table of contents.
     */
    public HtmlToPdfObject pageCount(boolean pageCount) {
        return setting("pagesCount", pageCount);
    }

    private HtmlToPdfObject setting(String name, Object value) {
        return setting(name, value.toString());
    }

    private HtmlToPdfObject setting(String name, WkValue value) {
        return setting(name, value.getWkValue());
    }

    private HtmlToPdfObject setting(String name, String value) {
        settings.put(name, value);
        return this;
    }

    Map<String, String> getSettings() {
        return settings;
    }

    String getHtmlData() {
        return htmlData;
    }
}
