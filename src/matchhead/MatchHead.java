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
import java.util.logging.Level;
import java.util.logging.Logger;
import matchhead.loader.FileLoadable;
import matchhead.loader.FileLoader;
import matchhead.matchmaker.MatchMaker;
import matchhead.matchmaker.MatchNotFoundException;
import matchhead.matchmaker.Matchable;
import matchhead.page.Pageable;
import matchhead.prefilter.QueryPreFilter;
import matchhead.prefilter.QueryPreFilterException;
import matchhead.prefilter.QueryPreFilterable;

public class MatchHead {

    private static String query = "encapsulation";
    
    public static void main(String[] args) throws MalformedURLException {

        /*
        TO-DO: It's bad style to hardcode path info like this. We should
        make a class that exposes this value as a constant. That way we can do 
        something like new FileLoader(Config.MATCH_PHRASE_PATH);
        
        Dain: Go ahead and set that up. I would suggest making a Constants class
        in the matchhead.cinfig package. Put one variable in there defined like
        public static String MATCH_PHRASE_PATH (that's how we define constants
        in Java.
        
        */
        FileLoadable loader = new FileLoader("src/matchhead/data/match-phrases.txt");
        List<Pageable> loadedPages = loader.getPages();

        // Run query through the pre filter
        QueryPreFilterable preFilter = new QueryPreFilter(); 
        try {
            query = preFilter.preFilter(query);
        } catch (QueryPreFilterException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        
        // Create and populate a MatchMaker
        Matchable matchMaker = new MatchMaker();
        for (Pageable page : loadedPages) {
            matchMaker.addPage(page);
        }

        // Now perform the search!
        try {
            List<Pageable> results = matchMaker.match(query);
            for (Pageable page : results) {
                System.out.println("Match Found!");
                System.out.println("Query: " + query + "\nMatched the following Page(s):");
                System.out.println(page.toString());
                System.out.println("");
            }
        } catch (MatchNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
} 
