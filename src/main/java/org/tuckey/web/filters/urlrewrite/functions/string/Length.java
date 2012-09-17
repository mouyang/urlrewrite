package org.tuckey.web.filters.urlrewrite.functions.string;

import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionContext;
import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionFilterChain;

public class Length implements StringTransformStrategy {
    public String transform(String subject, SubstitutionFilterChain nextFilter, SubstitutionContext ctx) {
        if (subject == null) {
            return "0";
        }
        return String.valueOf(nextFilter.substitute(subject, ctx).length());
    }
}
