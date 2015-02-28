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
package matchhead.loader;

import java.util.List;
import matchhead.page.Pageable;

/**
 * This interface specifies a mechanism for loading URL/MatchPhrase pairs
 * from a plain text file on the file system. This is the most basic system possible,
 * but may also be the most appropriate for small websites. 
 * 
 * @author shommel
 */

public interface FileLoadable {
   
   /**
    * Gets the list of pages to search. The Pageable objects stored in this List
    * will be constructed dynamically at runtime after parsing the URL/match phrase
    * text file.
    * 
    * TO-DO: Specify any exceptions that should be thrown. Are there any?
    * 
    * @return The list of pages to search
    */
    public List<Pageable> getPages();
    
}
