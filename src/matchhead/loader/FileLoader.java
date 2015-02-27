/*
 * Copyright (C) 2015 Scott A. Hommel
 *
 * This file is part of SearchProto.
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
 * Implementation of the FileLoadable interface.
 *
 * TO-DO: Implement better Error checking on parameters 
 * 
 * This implementation should not be considered stable yet!
 * 
 * @author shommel
 */
public final class FileLoader implements FileLoadable {

    private String path;
    private List<String> matchPhrases = new ArrayList<>(); // raw file data
    private List<Pageable> pages = new ArrayList<>(); // constructed pages

    public FileLoader(String path) {
        this.path = path;
        try {
            loadMatchPhrases(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.matchPhrases = results;
        return this.matchPhrases;
    }

    @Override
    public List<Pageable> getPages() {

        // Convert each line of the file to a Page object
        for (String line : this.matchPhrases) {
            StringTokenizer st1 = new StringTokenizer(line, "@");
            String url = st1.nextToken();
            Pageable page = null;
            try {
                page = new Page(new URL(url));
            } catch (MalformedURLException ex) {
                Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            String allPhrases = st1.nextToken();
            StringTokenizer st2 = new StringTokenizer(allPhrases, ",");
            while (st2.hasMoreTokens()) {
                page.addMatchPhrase(st2.nextToken());
            }
            pages.add(page);
        }
        return pages;
    }
}
