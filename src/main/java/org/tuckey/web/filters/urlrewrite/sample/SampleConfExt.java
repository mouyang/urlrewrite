package org.tuckey.web.filters.urlrewrite.sample;

import org.tuckey.web.filters.urlrewrite.Conf;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;


public class SampleConfExt extends Conf {

    public SampleConfExt() {
        // do something
    }

    protected synchronized void loadDom(InputStream inputStream) {
        // do something
        super.loadDom(inputStream);
    }

    protected void processConfDoc(Document doc) throws IllegalArgumentException, IOException {
        // do something else
        super.processConfDoc(doc);
    }
}
