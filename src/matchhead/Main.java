/*
 * Copyright (C) 2015 Scott A. Hommel
 *
 * This file is part of Main.
 *
 * Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Main.  If not, see <http://www.gnu.org/licenses/>.
 */
package matchhead;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import matchhead.matchphraseloader.MatchPhraseLoader;
import matchhead.matchmaker.MatchMaker;
import matchhead.matchmaker.MatchNotFoundException;
import matchhead.outputformatter.ConsoleOutputFormatter;
import matchhead.outputformatter.HTMLOutputFormatter;
import matchhead.prefilter.QueryPreFilter;
import matchhead.prefilter.QueryPreFilterException;
import matchhead.webpage.WebPage;

public final class Main {

    // Simulate incoming query from web browser
    private static String query = "implementation";

    private static MatchPhraseLoader loader;
    private static List<WebPage> loadedPages;
    private static QueryPreFilter preFilter;
    private static MatchMaker matchMaker;
    private static HTMLOutputFormatter htmlOut;
    private static ConsoleOutputFormatter consoleOut; // ONLY USED IN DEVELOPMENT

    public static void main(String[] args) throws MalformedURLException {
        initResources();
        preFilter();
        search();
        closeResources();
    }

    private static void initResources() {

        loader = new MatchPhraseLoader("src/matchhead/matchdata/match-phrases.txt");
        loadedPages = loader.getPages();
        preFilter = new QueryPreFilter();
        matchMaker = new MatchMaker();
        consoleOut = new ConsoleOutputFormatter();

        try {
            htmlOut = new HTMLOutputFormatter("src/matchhead/output/results.html");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }

        loadedPages.stream().forEach((page) -> {
            matchMaker.addPage(page);
        });
    }

    private static void preFilter() {
        try {
            query = preFilter.preFilter(query);
        } catch (QueryPreFilterException ex) {
            System.out.println(ex.getMessage());
            System.exit(0); // probably change this later
        }
    }

    private static void search() {
        try {

            Set<WebPage> results = matchMaker.match(query);

            results.stream().forEach((page) -> {

                // Print results to console
                consoleOut.formatOutput("Match Found!");
                consoleOut.formatOutput("Query: " + query + "\nMatched the following Page(s):");
                consoleOut.formatOutput(page.toString());
                consoleOut.formatOutput("");

                // Print results to HTML
                htmlOut.formatOutput("<H3>Match Found!</H3>");
                htmlOut.formatOutput("<UL>");
                htmlOut.formatOutput("<LI>Query: " + query);
                htmlOut.formatOutput("<LI>");
                htmlOut.formatOutput("<A HREF=\""
                        + page.getURL() + "\">" + page.getURL() + "</A>");
                htmlOut.formatOutput("</UL>");

            });

        } catch (MatchNotFoundException ex) {
            consoleOut.formatOutput(ex.getMessage());
            htmlOut.formatOutput("<H1>Match Not Found</H1>");
            htmlOut.formatOutput("<P>" + ex.getMessage() + "</P>");
        }
    }

    private static void closeResources() {
        htmlOut.cleanupResources();
    }
}
