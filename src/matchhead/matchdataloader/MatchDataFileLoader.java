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
package matchhead.matchdataloader;

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
import matchhead.page.Page;
import matchhead.page.Pageable;

/**
 * Implementation of the MatchDataFileLoadable interface.
 *
 * TO-DO: Implement better Error checking on parameters
 *
 * This implementation should not be considered stable yet!
 *
 * @author shommel
 */
public final class MatchDataFileLoader implements MatchDataFileLoadable {

    private String path;
    private List<String> matchPhrases = new ArrayList<>(); // raw file data
    private List<Pageable> pages = new ArrayList<>(); // constructed pages

    public MatchDataFileLoader(String path) {
        this.path = path;
        try {
            loadMatchPhrases(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MatchDataFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<String> loadMatchPhrases(File file) throws FileNotFoundException {
        List<String> results = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                results.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(MatchDataFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.matchPhrases = results;
        return this.matchPhrases;
    }

    @Override
    public List<Pageable> getPages() {

        this.matchPhrases.stream().forEach((line) -> {
            
            StringTokenizer st1 = new StringTokenizer(line, "@");
            String url = st1.nextToken();
            
            /*
            The page could remain null if the URL is malformed. So make sure
            to check for null throughout the remainder of this method.
            */
            Pageable page = null;
            
            try {
                page = new Page(new URL(url));
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

        return pages;
    }
}
