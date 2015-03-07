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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import matchhead.webpage.WebPage;
import matchhead.webpage.WebPageable;

/**
 * Implementation of the MatchPhraseDataFileLoadable interface.
 *
 * 
 * 1. open the match-phrases.txt file for reading
 * 2. read each line from the file into memory
 * 3. parse out URL text (up to @ sign) 
 * 4. grab the entire keyword list: "object,class,blah,blah"
 * 5. parse the key phrases from comma-separated list above
 * 6. create Java objects with this data
 * 7. create WebPage objects; store in list of web pages
 * 
 * TO-DO: Implement better Error checking on parameters
 *
 * This implementation should not be considered stable yet!
 *
 * @author shommel
 */
public final class MatchPhraseDataFileLoader implements MatchPhraseDataFileLoadable {

    private String path;
    // private List<String> matchPhrases = new ArrayList<>(); 
    private List<WebPageable> webPages = new ArrayList<>(); 

    public MatchPhraseDataFileLoader(String path) {
        this.path = path;
        List<String> linesRead = null;
        try {
            linesRead = readLinesFromInputFile(new File(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MatchPhraseDataFileLoader
                    .class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        if(linesRead!=null){
            parseMatchPhrases(linesRead);
        }else{
            System.out.println("Problem parsing lines from match-phrases.txt!");
            System.exit(0);
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
            // Read each line from the text file
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            // The entire file has been read by this point
            
        } catch (IOException ex) {
            Logger.getLogger(MatchPhraseDataFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }

    @Override
    public List<WebPageable> getPages() {
        return webPages;
    }
    
    private void parseMatchPhrases(List<String> lines) {
  
        lines.stream().forEach((line) -> {
   
            StringTokenizer st1 = new StringTokenizer(line, "@");
            URL url = null;
  
            try {
                url = new URL(st1.nextToken()); // Everything before @
            } catch (MalformedURLException ex) {
                Logger.getLogger(MatchPhraseDataFileLoader
                        .class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            
            String everythingElse = st1.nextToken(); // Everything else after @
            Set<String> parsedMatchPhrases = new HashSet<>();
            StringTokenizer st2 = new StringTokenizer(everythingElse,",");
 
            while(st2.hasMoreTokens()){            
                parsedMatchPhrases.add(st2.nextToken());
            }
         
            WebPageable webPage = new WebPage(url,parsedMatchPhrases);
            this.webPages.add(webPage);      
        });
    }
}
