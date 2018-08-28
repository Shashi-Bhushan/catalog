/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Shashi Bhushan
 */
package in.shabhushan.catalog.commerce.hybris;

import java.util.Map;

/**
 * Provides information about a user's session.
 */
public interface SessionInfo {
    /**
     * Get the session's user id.
     */
    public abstract String getUserId();

    /**
     * Get the session user's password.
     * @deprecated Do not use this method. Implementations that do not store a user's password (e.g. OAuth) will return
     * {@code null} here.
     */
    @Deprecated
    public abstract String getPassword();

    /**
     * Get session cookies.
     */
    public abstract Map<String, String> getCookieInfo();

    /**
     * Set new session cookies.
     */
    public abstract void setCookieInfo(Map<String, String> cookieInfo);

    /**
     * Is anonymous user or session.
     */
    public abstract boolean isAnonymous();
}
