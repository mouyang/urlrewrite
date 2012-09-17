package org.tuckey.web.filters.urlrewrite.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.tuckey.web.filters.urlrewrite.RewriteMap;
import org.tuckey.web.filters.urlrewrite.substitution.ChainedSubstitutionFilters;
import org.tuckey.web.filters.urlrewrite.substitution.FunctionReplacer;
import org.tuckey.web.filters.urlrewrite.substitution.SubstitutionContext;


public class FunctionReplacerTest extends TestCase {

    private final static String UNICODE_VALUE = "\u0131"; // Turkish dotless i
    private final static String UTF16ESCAPED_UNICODE_VALUE = "%FE%FF%01%31";

    public void setUp() {
        Log.setLevel("DEBUG");
    }

    public void testDefaultEscape() throws UnsupportedEncodingException {
        assertEquals("a%20b%20c%20:%20other%20%2f%20path",
                FunctionReplacer.replace("${escapePath:UTF-8:a b c : other / path}"));
        assertEquals("a+b+c+%3A+other+%2F+path",
                FunctionReplacer.replace("${escape:UTF-8:a b c : other / path}"));
        assertEquals("a+b c/",
                FunctionReplacer.replace("${unescapePath:UTF-8:a+b c%2F}"));
        assertEquals("a b c/",
                FunctionReplacer.replace("${unescape:UTF-8:a+b c%2F}"));
        assertEquals("a+b+c%FE%FF%00%3Aotherstr",
                FunctionReplacer.replace("${escape:UTF-16:a b c:otherstr}"));
        assertEquals("a+b+c%3Aotherstr",
                FunctionReplacer.replace("${escape:utf8:a b c:otherstr}"));
        assertEquals("a+b+c",
                FunctionReplacer.replace("${escape:UTF-16:a b c}"));
        assertEquals("a b c",
                FunctionReplacer.replace("${unescape:UTF-16:a+b+c}"));
        assertEquals(java.net.URLEncoder.encode(UNICODE_VALUE, "UTF-8"),
                FunctionReplacer.replace("${escape:unknown:" + UNICODE_VALUE + "}"));
    }

    public void testEncodingEscape() {
        assertEquals(UTF16ESCAPED_UNICODE_VALUE,
                FunctionReplacer.replace("${escape:UTF-16:" + UNICODE_VALUE + "}"));
    }

    public void testDefaultUnescape() throws java.io.UnsupportedEncodingException {
        String testString = "unknown:" + UNICODE_VALUE;
        assertEquals(testString, FunctionReplacer.replace(
                "${unescape:" + java.net.URLEncoder.encode(testString, "UTF-8") + "}"));
    }

    public void testEncodingUnescape() {
        assertEquals(UNICODE_VALUE, FunctionReplacer.replace(
                "${unescape:UTF-16:" + UTF16ESCAPED_UNICODE_VALUE + "}"));
    }

    public void testSimple1() throws InvocationTargetException, IOException, ServletException {
        assertTrue(FunctionReplacer.containsFunction("a${lower:HEllo}b"));
        assertEquals("ahellob", FunctionReplacer.replace("a${lower:HElLO}b"));
    }

    public void testSimple2() throws InvocationTargetException, IOException, ServletException {
        assertTrue(FunctionReplacer.containsFunction("a${upper:HEllo}b"));
        assertEquals("aHELLOb", FunctionReplacer.replace("a${upper:hellO}b"));
    }

    public void testSimple3() throws InvocationTargetException, IOException, ServletException {
        assertTrue(FunctionReplacer.containsFunction("a${replace:a b c: :_}b"));
        assertEquals("aa_b_cb", FunctionReplacer.replace("a${replace:a b c: :_}b"));
    }

    public void testSimple4() throws InvocationTargetException, IOException, ServletException {
        assertTrue(FunctionReplacer.containsFunction("a${replaceFirst:a b c: :_}b"));
        assertEquals("aa_b cb", FunctionReplacer.replace("a${replaceFirst:a b c: :_}b"));
    }

    public void testSimple5() throws InvocationTargetException, IOException, ServletException {
        assertTrue(FunctionReplacer.containsFunction("a${escape:a b c} b"));
        assertEquals("aa+b+c b", FunctionReplacer.replace("a${escape:a b c} b"));
    }

    public void testSimple6() throws InvocationTargetException, IOException, ServletException {
        assertTrue(FunctionReplacer.containsFunction("a${trim: b } b"));
        assertEquals("ab b", FunctionReplacer.replace("a${trim: b } b"));
    }

    public void testSimple7() throws InvocationTargetException, IOException, ServletException {
        assertTrue(FunctionReplacer.containsFunction("a${length:asdf} b"));
        assertEquals("a4 b", FunctionReplacer.replace("a${length:asdf} b"));
    }

    public void testRecursive() throws InvocationTargetException, IOException, ServletException {
        assertTrue(FunctionReplacer.containsFunction("a${upper:${lower:fOObAR}} b"));
        assertEquals("aFOOBAR b", FunctionReplacer.replace("a${upper:${lower:fOObAR}} b"));
    }

    public void testRewriteMap() throws InvocationTargetException, IOException, ServletException {
        final String subjectOfReplacement = "a${rewriteMap:constantMap:key} b";
        Map<String, RewriteMap> rewriteMaps = new HashMap<String, RewriteMap>();
        rewriteMaps.put("constantMap", new RewriteMap() {
            public String getValue(String key) {
                return "Newvalue";
            }
        });

        SubstitutionContext ctx = new SubstitutionContext(null, null, null, null, rewriteMaps);

        assertTrue(FunctionReplacer.containsFunction(subjectOfReplacement));
        assertEquals(
              "aNewvalue b"
            , new FunctionReplacer().substitute(
                subjectOfReplacement, 
                ctx, 
                new ChainedSubstitutionFilters(Collections.EMPTY_LIST)
            )
        );
    }

    public void testRewriteMapDefaultValue() throws InvocationTargetException, IOException, ServletException {
        final String subjectOfReplacement = "a${rewriteMap:nullMap:oldValue:default} b";
        Map<String, RewriteMap> rewriteMaps = new HashMap<String, RewriteMap>();
        rewriteMaps.put("nullMap", new RewriteMap() {
            public String getValue(String key) {
                return null;
            }
        });

        SubstitutionContext ctx = new SubstitutionContext(null, null, null, null, rewriteMaps);

        assertTrue(FunctionReplacer.containsFunction(subjectOfReplacement));
        assertEquals(
              "adefault b"
            , new FunctionReplacer().substitute(
                subjectOfReplacement, 
                ctx, 
                new ChainedSubstitutionFilters(Collections.EMPTY_LIST)
            )
        );
    }
}
