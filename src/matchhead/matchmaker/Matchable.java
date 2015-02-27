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

package matchhead.matchmaker;

import java.util.List;
import matchhead.page.Pageable;

/**
 * Specifies the behavior of the system's MatchMaker. The default 
 * implementation of this interface is the MatchMaker class. Adding a page to 
 * be searched is accomplished by invoking its addPage(Page) method.
 * 
 * Match Rules:
 * 
 * 1. Searches will not be case-sensitive. Implementations are free to handle this
 *    however they feel is best: convert query to lowercase, 
 *    use case-insensitive regular expressions, etc.
 * 
 * 2. Literal matches are considered first. If the incoming query exactly matches
 *    a stored match phrase, the page in which the match has occurred will be 
 *    saved. Example: "what is a class?" matches "what is a class?" but not
 *    "what is a class"
 * 
 * 3. More match rules TBD
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author shommel
 */
public interface Matchable {
 
    /**
     * Adds a page to be searched. Pages added using this method
     * will be eligible for inclusion in search results.
     * 
     * @param page The page to add 
     */
    public void addPage(Pageable page);
   
    public List<Pageable> match(String query) throws MatchNotFoundException;
        
}