package org.tuckey.web.filters.urlrewrite.functions.string;

import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionContext;
import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionFilterChain;

public class Trim implements StringTransformStrategy {
    public String transform(String subject, SubstitutionFilterChain nextFilter, SubstitutionContext ctx) {
        if (subject == null) {
            return null;
        }
        return nextFilter.substitute(subject, ctx).trim();
    }
}
