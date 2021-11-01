package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

class HzppHtmlHelper {

    private HzppHtmlHelper() {
    }

    static String getHtml(String path) {
        URL resource = HzppHtmlHelper.class.getClassLoader().getResource(path);
        if (resource == null) {
            throw new RuntimeException("Unable to resolve resource: " + path);
        }
        try {
            return Files.readString(Path.of(resource.toURI()), Charset.forName("Cp1250"));
        }
        catch (URISyntaxException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
