/*
 * Copyright (C) 2015 Scott A. Hommel
 *
 * This file is part of MatchHead.
 *
 * SearchProto is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SearchProto is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SearchProto.  If not, see <http://www.gnu.org/licenses/>.
 */

package matchhead.prefilter;

/**
 * Specifies the method(s) that define a "pre filter" for the search engine. 
 * 
 * @author shommel
 */
public interface QueryPreFilterable {

    /**
     * This method specifies a "pre filter" through which all user queries
     * must pass before they will be allowed into the rest of the system. 
     * Implementations of this method must provide the following checks:
     * 
     * Test for null object reference
     * Test for empty String
     * Test for the presence of at least one word character
     * 
     * To successfully pass through this filter, the query must contain at least 
     * one word character. If it does not (or if the query is empty or null), 
     * a QueryPreFilterException will be thrown.
     * 
     * @param query The search query to test
     * @return The search query, but only if it passes all tests
     * @throws QueryPreFilterException if the query fails to pass this filter
     */
    public String preFilter(String query) throws QueryPreFilterException;
        
}