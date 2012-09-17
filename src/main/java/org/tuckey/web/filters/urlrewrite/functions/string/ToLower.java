package org.tuckey.web.filters.urlrewrite.functions.string;

import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionContext;
import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionFilterChain;

public class ToLower implements StringTransformStrategy {
    public String transform(String subject, SubstitutionFilterChain nextFilter, SubstitutionContext ctx) {
        return subject == null ? null : nextFilter.substitute(subject, ctx).toLowerCase();
    }
}
