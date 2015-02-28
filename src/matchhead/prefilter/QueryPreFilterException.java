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
 * Represents a condition in which a particular String fails to 
 * pass the PreFilter.  
 * 
 * @author shommel
 */
public class QueryPreFilterException extends Exception {
  
    /**
     * Creates a new exception with the specified message.
     * 
     * @param msg The message stored inside this exception.
     */
    public QueryPreFilterException(String msg) {
        super(msg);
    }
}