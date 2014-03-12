package server;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by dronloko 15.02.14.
 */

public class PageGenerator {
    private static final String HTML_DIR = "static/templates";
    private static final Configuration CFG = new Configuration();

    public static String getPage (String file_name, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + file_name);
            template.process(data, stream);
        } catch ( IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }
}