package org.tuckey.web.filters.urlrewrite;

import java.io.IOException;
import java.io.InputStream;

public class RewriteMapFactory {
    public static RewriteMap create(String type, String source) throws IllegalArgumentException, IOException {
        // TODO currently only grabs source from classpath.  eventually grab 
        // from other source types (like the internets)
        InputStream is = RewriteMapFactory.class.getClass().getResourceAsStream(source);

        // TODO currently only supports .properties.  eventually process other 
        // types of output (for example json or a database)
        if ("".equals(type) || "properties".equals(type)) {
            return new PropertiesFileRewriteMap(is);
        }
        throw new IllegalArgumentException(String.format("cannot process type='%s'", type));
    }
}
