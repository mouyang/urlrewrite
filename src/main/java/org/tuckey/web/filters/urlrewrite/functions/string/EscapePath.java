package org.tuckey.web.filters.urlrewrite.functions.string;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionContext;
import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionFilterChain;
import org.tuckey.web.filters.urlrewrite.utils.Log;

/**
 * escape string as a URI path segment "as as" will return "as%20as"
 * note, encoding can be specified after colon eg, "as:UTF-16"
 */
public class EscapePath implements StringTransformStrategy {
    private static Log log = Log.getLog(EscapePath.class);

    public String transform(String subject, SubstitutionFilterChain nextFilter, SubstitutionContext ctx) {
        String encoding = "UTF-8";
        if (FIND_ENCODING_PATTERN.matcher(subject).find()) {
            encoding = subject.substring(0, subject.indexOf(':'));
            subject = subject.substring(subject.indexOf(':') + 1);
            if (!Charset.isSupported(encoding)) encoding = "UTF-8";
        }
        subject = nextFilter.substitute(subject, ctx);
        try {
            return org.tuckey.web.filters.urlrewrite.utils.URLEncoder.encodePathSegment(subject, encoding);
        } catch (UnsupportedEncodingException e) {
            log.error(e, e);
        }
        return "";
    }
}
