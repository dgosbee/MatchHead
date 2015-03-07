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
package matchhead.matchphrasedataloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import matchhead.webpage.WebPage;
import matchhead.webpage.WebPageable;

/**
 * Implementation of the MatchPhraseDataFileLoadable interface.
 *
 * TO-DO: Implement better Error checking on parameters
 *
 * This implementation should not be considered stable yet!
 *
 * @author shommel
 */
public final class MatchPhraseDataFileLoader implements MatchPhraseDataFileLoadable {

    private String path;
    private List<String> matchPhrases = new ArrayList<>(); // raw file data
    private List<WebPageable> pages = new ArrayList<>(); // constructed pages

    public MatchPhraseDataFileLoader(String path) {
        this.path = path;
        try {
            readLinesFromInputFile(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MatchPhraseDataFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Reads all lines from match-phrases.txt. Stores each line as an entry in a
     * List<String>, then returns the list.
     */
    private List<String> readLinesFromInputFile(File file) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(MatchPhraseDataFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.matchPhrases = lines;
        return this.matchPhrases;
    }

    @Override
    public List<WebPageable> getPages() {

        parseMatchPhrases();

        return pages;
    }

    /*
     Parse entries from each line.
     Construct WebPage objects and return as a list.
     */
    private void parseMatchPhrases() {

        this.matchPhrases.stream().forEach((line) -> {

            // Parse before and after @ sign
            StringTokenizer st1 = new StringTokenizer(line, "@");
            String url = st1.nextToken();

            /*
             The page could remain null if the URL is malformed. So make sure
             to check for null throughout the remainder of this method.
             */
            WebPageable page = null;

            try {
                page = new WebPage(new URL(url));
            } catch (MalformedURLException ex) {
                System.out.println(ex.getMessage());
            }
            String allPhrases = st1.nextToken();
            StringTokenizer st2 = new StringTokenizer(allPhrases, ",");
            while (st2.hasMoreTokens() && (page != null)) {
                page.addMatchPhrase(st2.nextToken());
            }

            if (page != null) {
                pages.add(page);
            }
        });
    }
}
