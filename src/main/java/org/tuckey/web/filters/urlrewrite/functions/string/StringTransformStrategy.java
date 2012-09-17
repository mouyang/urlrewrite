package org.tuckey.web.filters.urlrewrite.functions.string;

import java.util.regex.Pattern;

import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionContext;
import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionFilterChain;

public interface StringTransformStrategy {
    public static final Pattern FIND_COLON_PATTERN = Pattern.compile("(?<!\\\\):");
    public static final Pattern FIND_ENCODING_PATTERN = Pattern.compile("^[0-9a-zA-Z-]+:");

    public String transform(String subject, SubstitutionFilterChain nextFilter, SubstitutionContext ctx);
}
