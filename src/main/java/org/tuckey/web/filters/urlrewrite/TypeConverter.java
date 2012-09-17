/**
 * Copyright (c) 2005-2007, Paul Tuckey
 * All rights reserved.
 * ====================================================================
 * Licensed under the BSD License. Text as follows.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   - Neither the name tuckey.org nor the names of its contributors
 *     may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package org.tuckey.web.filters.urlrewrite;

import org.tuckey.web.filters.urlrewrite.utils.BidirectionalMap;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

/**
 * Assists with the setting of variable type names for condition types and rule to variables.
 *
 * @author Paul Tuckey
 * @version $Revision: 1 $ $Date: 2006-08-01 21:40:28 +1200 (Tue, 01 Aug 2006) $
 */
public class TypeConverter {

    /**
     * Type of condition ie, header, port etc.
     */
    protected short type;

    /**
     * Error message from the regular expression compilation.
     */
    protected String error = null;

    // Type statics
    public static final short TYPE_TIME = 4;
    public static final short TYPE_TIME_YEAR = 5;
    public static final short TYPE_TIME_MONTH = 6;
    public static final short TYPE_TIME_DAY_OF_MONTH = 7;
    public static final short TYPE_TIME_DAY_OF_WEEK = 8;
    public static final short TYPE_TIME_AMPM = 9;
    public static final short TYPE_TIME_HOUR_OF_DAY = 10;
    public static final short TYPE_TIME_MINUTE = 11;
    public static final short TYPE_TIME_SECOND = 12;
    public static final short TYPE_TIME_MILLISECOND = 13;
    public static final short TYPE_ATTRIBUTE = 14;
    public static final short TYPE_AUTH_TYPE = 15;
    public static final short TYPE_CHARACTER_ENCODING = 16;
    public static final short TYPE_CONTENT_LENGTH = 17;
    public static final short TYPE_CONTENT_TYPE = 18;
    public static final short TYPE_CONTEXT_PATH = 19;
    public static final short TYPE_COOKIE = 20;
    public static final short TYPE_HEADER = 1;
    public static final short TYPE_LOCAL_PORT = 39;
    public static final short TYPE_METHOD = 21;
    public static final short TYPE_PARAMETER = 22;
    public static final short TYPE_PATH_INFO = 23;
    public static final short TYPE_PATH_TRANSLATED = 24;
    public static final short TYPE_PROTOCOL = 25;
    public static final short TYPE_QUERY_STRING = 26;
    public static final short TYPE_REMOTE_ADDR = 27;
    public static final short TYPE_REMOTE_HOST = 28;
    public static final short TYPE_REMOTE_USER = 29;
    public static final short TYPE_REQUESTED_SESSION_ID = 30;
    public static final short TYPE_REQUEST_URI = 31;
    public static final short TYPE_REQUEST_URL = 32;
    public static final short TYPE_SESSION_ATTRIBUTE = 33;
    public static final short TYPE_SESSION_IS_NEW = 34;
    public static final short TYPE_SERVER_PORT = 35;
    public static final short TYPE_SERVER_NAME = 36;
    public static final short TYPE_SCHEME = 37;
    public static final short TYPE_USER_IN_ROLE = 38;
    public static final short TYPE_EXCEPTION = 40;
    public static final short TYPE_REQUESTED_SESSION_ID_FROM_COOKIE = 41;
    public static final short TYPE_REQUESTED_SESSION_ID_FROM_URL = 42;
    public static final short TYPE_REQUESTED_SESSION_ID_VALID = 43;
    public static final short TYPE_REQUEST_FILENAME = 44;
    public static final short TYPE_SERVLET_CONTEXT = 45;

    private static BidirectionalMap<Short, String> typeMap = new BidirectionalMap<Short, String>();
    static {
        typeMap.addPair(TYPE_TIME, "time");
        typeMap.addPair(TYPE_TIME_YEAR, "year");
        typeMap.addPair(TYPE_TIME_MONTH, "month");
        typeMap.addPair(TYPE_TIME_DAY_OF_MONTH, "dayofmonth");
        typeMap.addPair(TYPE_TIME_DAY_OF_WEEK, "dayofweek");
        typeMap.addPair(TYPE_TIME_AMPM, "ampm");
        typeMap.addPair(TYPE_TIME_HOUR_OF_DAY, "hourofday");
        typeMap.addPair(TYPE_TIME_MINUTE, "minute");
        typeMap.addPair(TYPE_TIME_SECOND, "second");
        typeMap.addPair(TYPE_TIME_MILLISECOND, "millisecond");
        typeMap.addPair(TYPE_ATTRIBUTE, "attribute");
        typeMap.addPair(TYPE_AUTH_TYPE, "auth-type");
        typeMap.addPair(TYPE_CHARACTER_ENCODING, "character-encoding");
        typeMap.addPair(TYPE_CONTENT_LENGTH, "content-length");
        typeMap.addPair(TYPE_CONTENT_TYPE, "content-type");
        typeMap.addPair(TYPE_CONTEXT_PATH, "context-path");
        typeMap.addPair(TYPE_COOKIE, "cookie");
        typeMap.addPair(TYPE_HEADER, "header");
        typeMap.addPair(TYPE_LOCAL_PORT, "local-port");
        typeMap.addPair(TYPE_METHOD, "method");
        typeMap.addPair(TYPE_PARAMETER, "parameter");
        typeMap.addPair(TYPE_PATH_INFO, "path-info");
        typeMap.addPair(TYPE_PATH_TRANSLATED, "path-translated");
        typeMap.addPair(TYPE_PROTOCOL, "protocol");
        typeMap.addPair(TYPE_QUERY_STRING, "query-string");
        typeMap.addPair(TYPE_REMOTE_ADDR, "remote-addr");
        typeMap.addPair(TYPE_REMOTE_HOST, "remote-host");
        typeMap.addPair(TYPE_REMOTE_USER, "remote-user");
        typeMap.addPair(TYPE_REQUESTED_SESSION_ID, "requested-session-id");
        typeMap.addPair(TYPE_REQUESTED_SESSION_ID_FROM_COOKIE, "requested-session-id-from-cookie");
        typeMap.addPair(TYPE_REQUESTED_SESSION_ID_FROM_URL, "requested-session-id-from-url");
        typeMap.addPair(TYPE_REQUESTED_SESSION_ID_VALID, "requested-session-id-valid");
        typeMap.addPair(TYPE_REQUEST_URI, "request-uri");
        typeMap.addPair(TYPE_REQUEST_URL, "request-url");
        typeMap.addPair(TYPE_SESSION_ATTRIBUTE, "session-attribute");
        typeMap.addPair(TYPE_SESSION_IS_NEW, "session-isnew");
        typeMap.addPair(TYPE_SERVER_PORT, "port");
        typeMap.addPair(TYPE_SERVER_NAME, "server-name");
        typeMap.addPair(TYPE_SCHEME, "scheme");
        typeMap.addPair(TYPE_USER_IN_ROLE, "user-in-role");
        typeMap.addPair(TYPE_EXCEPTION, "exception");
        typeMap.addPair(TYPE_REQUEST_FILENAME, "request-filename");
        typeMap.addPair(TYPE_SERVLET_CONTEXT, "context");
    }

    /**
     * Will get the type code ie, method, port, header etc.
     *
     * @return String
     */
    public String getType() {
        String returnValue = typeMap.getValue(type);
        return returnValue != null ? returnValue : "";

    }

    /**
     * Will set the type.
     *
     * @param strType the type
     */
    public void setType(final String strType) {
        Short typeValue = typeMap.getKey(strType);
        if (typeValue != null) {
            this.type = typeValue;
        } else {
            if (StringUtils.isBlank(strType)) {
                this.type = TYPE_HEADER;
            } else if ("param".equals(strType)) {
                this.type = TYPE_PARAMETER;
            } else {
                setError("Operator " + strType + " is not valid");
            }
        }
    }


    /**
     * Will get the description of the error.
     *
     * @return String
     */
    public final String getError() {
        return error;
    }

    protected void setError(String error) {
        this.error = error;
    }

    public int getTypeShort() {
        return type;
    }
}
