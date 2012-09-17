package org.tuckey.web.filters.urlrewrite.substitution;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.tuckey.web.filters.urlrewrite.ConditionMatch;
import org.tuckey.web.filters.urlrewrite.RewriteMap;
import org.tuckey.web.filters.urlrewrite.utils.StringMatchingMatcher;

public class SubstitutionContext {


    private HttpServletRequest hsRequest;
    private StringMatchingMatcher matcher;
    private ConditionMatch lastConditionMatch;
    private String replacePattern;
    private Map<String, RewriteMap> rewriteMaps;

    public SubstitutionContext(HttpServletRequest hsRequest,
                               StringMatchingMatcher matcher, ConditionMatch lastConditionMatch,
                               String replacePattern) {
        this(hsRequest, matcher, lastConditionMatch, replacePattern, new HashMap<String, RewriteMap>());
    }

    public SubstitutionContext(HttpServletRequest hsRequest,
            StringMatchingMatcher matcher, ConditionMatch lastConditionMatch,
            String replacePattern, Map<String, RewriteMap> rewriteMaps) {
        super();
        this.hsRequest = hsRequest;
        this.matcher = matcher;
        this.lastConditionMatch = lastConditionMatch;
        this.replacePattern = replacePattern;
        this.rewriteMaps = rewriteMaps;
    }

    public HttpServletRequest getHsRequest() {
        return hsRequest;
    }

    public StringMatchingMatcher getMatcher() {
        return matcher;
    }

    public ConditionMatch getLastConditionMatch() {
        return lastConditionMatch;
    }

    public String getReplacePattern() {
        return replacePattern;
    }

    public Map<String, RewriteMap> getRewriteMaps() {
        return rewriteMaps;
    }
}
