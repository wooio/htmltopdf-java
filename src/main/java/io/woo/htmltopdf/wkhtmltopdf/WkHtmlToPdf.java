package io.woo.htmltopdf.wkhtmltopdf;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

public interface WkHtmlToPdf extends Library {
    WkHtmlToPdf INSTANCE = WkHtmlToPdfLoader.load();

    interface wkhtmltopdf_str_callback extends Callback {
        void callback(Pointer converter, String str);
    }
    interface wkhtmltopdf_void_callback extends Callback {
        void callback(Pointer converter);
    }
    interface wkhtmltopdf_int_callback extends Callback {
        void callback(Pointer converter, int i);
    }

    int wkhtmltopdf_init(int useGraphics);
    int wkhtmltopdf_deinit();
    int wkhtmltopdf_extended_qt();

    String wkhtmltopdf_version();

    Pointer wkhtmltopdf_create_global_settings();
    int wkhtmltopdf_set_global_setting(Pointer globalSettings, String name, String value);
    int wkhtmltopdf_get_global_setting(Pointer globalSettings, String name, Memory memory, int memorySize);
    void wkhtmltopdf_destroy_global_settings(Pointer pointer);

    Pointer wkhtmltopdf_create_object_settings();
    int wkhtmltopdf_set_object_setting(Pointer objectSettings, String name, String value);
    int wkhtmltopdf_get_object_setting(Pointer objectSettings, String name, Memory memory, int memorySize);
    void wkhtmltopdf_destroy_object_settings(Pointer pointer);

    Pointer wkhtmltopdf_create_converter(Pointer globalSettings);
    void wkhtmltopdf_set_warning_callback(Pointer converter, wkhtmltopdf_str_callback cb);
    void wkhtmltopdf_set_error_callback(Pointer converter, wkhtmltopdf_str_callback cb);
    void wkhtmltopdf_set_phase_changed_callback(Pointer converter, wkhtmltopdf_void_callback cb);
    void wkhtmltopdf_set_progress_changed_callback(Pointer converter, wkhtmltopdf_int_callback cb);
    void wkhtmltopdf_set_finished_callback(Pointer converter, wkhtmltopdf_int_callback cb);
    void wkhtmltopdf_add_object(Pointer converter, Pointer objectSettings, String data);
    int wkhtmltopdf_current_phase(Pointer converter);
    int wkhtmltopdf_phase_count(Pointer converter);
    String wkhtmltopdf_phase_description(Pointer converter, int phase);
    String wkhtmltopdf_progress_string(Pointer converter);
    int wkhtmltopdf_http_error_code(Pointer converter);
    int wkhtmltopdf_convert(Pointer converter);
    long wkhtmltopdf_get_output(Pointer converter, PointerByReference out);
    void wkhtmltopdf_destroy_converter(Pointer converter);
}
