package org.tuckey.web.filters.urlrewrite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileRewriteMap implements RewriteMap {

    private Properties properties = new Properties();

    public PropertiesFileRewriteMap(InputStream is) throws IOException {
        properties.load(is);
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
