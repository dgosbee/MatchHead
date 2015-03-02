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

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import matchhead.matchdataloader.MatchDataFileLoadable;
import matchhead.matchdataloader.MatchDataFileLoader;
import matchhead.matchmaker.MatchMaker;
import matchhead.matchmaker.MatchNotFoundException;
import matchhead.matchmaker.Matchable;
import matchhead.outputformatter.ConsoleOutputFormatable;
import matchhead.outputformatter.ConsoleOutputFormatter;
import matchhead.outputformatter.HTMLOutputFormatable;
import matchhead.outputformatter.HTMLOutputFormatter;
import matchhead.page.Pageable;
import matchhead.prefilter.QueryPreFilter;
import matchhead.prefilter.QueryPreFilterException;
import matchhead.prefilter.QueryPreFilterable;

public class MatchHead {

    private static String query = "encapsulation";
    private static MatchDataFileLoadable loader;
    private static List<Pageable> loadedPages;
    private static QueryPreFilterable preFilter;
    private static Matchable matchMaker;
    private static ConsoleOutputFormatable consoleOut;
    private static HTMLOutputFormatable htmlOut;

    /**
     * The application's main entry point. This entry point will change
     * after the code is moved to a Servlet. In the final deployment 
     * scenario, a main method will not exist. The various private 
     * processing methods defined here will be pushed out to some other section
     * of the framework. Details are still TBD.
     */
    public static void main(String[] args) throws MalformedURLException {
        init();
        runPreFilter();
        runSearch();
        cleanup();
    }

    /**
     * Initializes the objects declared at the top of this file.
     */
    private static void init() {

        // Read match phrase data from match-phrases.txt
        loader = new MatchDataFileLoader("src/matchhead/matchdata/match-phrases.txt");
        loadedPages = loader.getPages();
        preFilter = new QueryPreFilter();
        matchMaker = new MatchMaker();
        consoleOut = new ConsoleOutputFormatter();
        
        // Write results to results.html
        try {
            htmlOut = new HTMLOutputFormatter("src/matchhead/output/results.html");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }

        //. Populate the match maker
        for (Pageable page : loadedPages) {
            matchMaker.addPage(page);
        }
    }

    /**
     * Runs the incoming query through the pre filter.
     */
    private static void runPreFilter() {
        try {
            query = preFilter.preFilter(query);
        } catch (QueryPreFilterException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    /**
     * Runs the search by passing the user's query to the match maker.
     */
    private static void runSearch() {
        try {
            List<Pageable> results = matchMaker.match(query);

            results.stream().forEach((page) -> {
                
                // Print results to the console
                consoleOut.formatOutput("Match Found!");
                consoleOut.formatOutput("Query: " + query + "\nMatched the following Page(s):");
                consoleOut.formatOutput(page.toString());
                consoleOut.formatOutput("");
                
                // Print results to HTML
                
                htmlOut.formatOutput("<H3>Match Found!</H3>");
                htmlOut.formatOutput("<UL>");
                htmlOut.formatOutput("<LI>Query: " + query);
                htmlOut.formatOutput("<LI>");
                htmlOut.formatOutput("<A HREF=\""+
                        page.getURL()+"\">"+page.getURL()+"</A>");
                htmlOut.formatOutput("</UL>");
               
                
            });

        } catch (MatchNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Cleans up any resources, such as closing I/O streams.
     */
     private static void cleanup(){
         htmlOut.close();
     }
}