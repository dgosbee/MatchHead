/*
 * Copyright (C) 2015 Scott A. Hommel
 *
 * This file is part of MatchHead.
 *
 * MatchHead is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MatchHead is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MatchHead.  If not, see <http://www.gnu.org/licenses/>.
 */

package matchhead.prefilter;

/**
 * Provides a concrete implementation of the pre filter. For more information,
 * see the QueryPreFilterable interface.
 *
 * @author shommel
 */
public final class QueryPreFilter implements QueryPreFilterable {

    /**
     * Provides a concrete implementation of the preFilter method. This
     * implementation throws a QueryPreFilterException if the provided query is
     * not valid.
     *
     * @param query The search query to test
     * @return The query after successfully passing this filter
     * @throws QueryPreFilterException if the query is not valid
     */
    @Override
    public String preFilter(String query) throws QueryPreFilterException {

        // Throw an exception if the query is not valid
        if (!isValid(query)) {
            throw new QueryPreFilterException("Thrown from "
                    + this.getClass().getName() + ": The search string "
                    + "is not valid: \"" + query + "\"");
        }
        return query;
    }

    /**
     * Determines whether or not the query is valid (passes the filter).
     *
     * @param query The query to test
     * @return true if valid, false if not
     */
    private boolean isValid(String query) {

        /*
         Local variable "result" defined below indicates whether or not the query 
         is valid (passes the filter). True means valid, false means not valid. This is 
         determined in two stages:
        
         1. Test for null object reference or empty string (bad query)
         2. Test for the presence of at least one word character
         
         The query is forwarded to isNullOrEmpty, which tests for a "bad condition." 
         So we must invert its returned value before storing it in result. For example,
         if isNullOrEmpty returns true, the query will NOT pass the filter, and 
         the result of isValid must be false. Similarly, if isNullOrEmpty returns false, 
         the query has not yet been ruled out, so result should be set to true. This
         is why the result is always set to the opposite using "!"
         */
        boolean result = !(isNullOrEmpty(query));
        result = hasWordChars(query);
        return result;
    }

    /**
     * Determines whether the query string is null or empty.
     *
     * @param query The query to check
     * @return true if null or empty, false if not
     */
    private boolean isNullOrEmpty(String query) {
        return query == null || query.isEmpty();
    }

    /**
     * Determines whether the query string contains any word characters.
     *
     * The regular expression used in this implementation is:
     *
     * Zero or more of anything .* 
     * followed by One or more word characters \w+ 
     * followed by Zero or more of anything .*
     *
     * @param query The query to check
     * @return true is word characters are present, false if not
     */
    private boolean hasWordChars(String query) {
        return query.matches(".*\\w+.*");
    }
}