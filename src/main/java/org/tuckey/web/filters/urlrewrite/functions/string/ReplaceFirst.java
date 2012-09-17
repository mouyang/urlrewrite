package org.tuckey.web.filters.urlrewrite.functions.string;

import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionContext;
import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionFilterChain;

public class ReplaceFirst implements StringTransformStrategy {
    public String transform(String subject, SubstitutionFilterChain nextFilter, SubstitutionContext ctx) {
        String replace = "";
        String with = "";
        if (FIND_COLON_PATTERN.matcher(subject).find()) {
            replace = subject.substring(subject.indexOf(':') + 1);
            subject = subject.substring(0, subject.indexOf(':'));
            if (FIND_COLON_PATTERN.matcher(replace).find()) {
                with = replace.substring(replace.indexOf(':') + 1);
                replace = replace.substring(0, replace.indexOf(':'));
            }
        }
        subject = nextFilter.substitute(subject, ctx);
        return subject.replaceFirst(replace, with);
    }
}
