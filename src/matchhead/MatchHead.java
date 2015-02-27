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
package matchhead;

import java.net.MalformedURLException;
import java.util.List;
import matchhead.loader.FileLoadable;
import matchhead.loader.FileLoader;
import matchhead.matchmaker.MatchMaker;
import matchhead.matchmaker.MatchNotFoundException;
import matchhead.matchmaker.Matchable;
import matchhead.page.Pageable;

public class MatchHead {

    private static String QUERY = "encapsulation";
    
    public static void main(String[] args) throws MalformedURLException {

        // Load the pages to search from the file defined by args[0]
        FileLoadable loader = new FileLoader("src/matchhead/data/match-phrases.txt");
        List<Pageable> loadedPages = loader.getPages();

        // Create and populate a MatchMaker
        Matchable matchMaker = new MatchMaker();
        for (Pageable page : loadedPages) {
            matchMaker.addPage(page);
        }

        // Now perform the search!
        try {
            List<Pageable> results = matchMaker.match(QUERY);
            for (Pageable page : results) {
                System.out.println("Match Found!");
                System.out.println("Query: " + QUERY + "\nMatched the following Page(s):");
                System.out.println(page.toString());
                System.out.println("");
            }
        } catch (MatchNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
} 
