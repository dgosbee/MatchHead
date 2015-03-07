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
package matchhead.matchphraseloader;

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

/**
 * 
 * Loads the match phrase and url data for each page from match-phrases.txt
 * 
 * @author shommel
 */
public final class MatchPhraseLoader {

    private String path;
    // private List<String> matchPhrases = new ArrayList<>(); 
    private List<WebPage> webPages = new ArrayList<>();

    public MatchPhraseLoader(String path) {
        this.path = path;
        loadMatchPhrases(path);
    }

    private void loadMatchPhrases(String path) {
        String url = null;
        Set<String> parsedMatchPhrases = null;
        WebPage webPage;
        try {
            BufferedReader br
                    = new BufferedReader(new FileReader(new File(path)));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("[BEGIN]")) {
                        url = null;
                        parsedMatchPhrases = new HashSet<>();
                        webPage = null;
                    } else if (line.startsWith("[URL]")) {
                        StringTokenizer st = new StringTokenizer(line, "]");
                        st.nextToken();//ignores
                        url = st.nextToken();
                    } else if (line.startsWith("[MP]")) {
                        StringTokenizer st = new StringTokenizer(line, "]");
                        st.nextToken();//ignores
                        String matchPhrase = st.nextToken();
                        parsedMatchPhrases.add(matchPhrase);
                    } else if (line.startsWith("[END]")) {
                        webPage = new WebPage(new URL(url), parsedMatchPhrases);
                        webPages.add(webPage);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MatchPhraseLoader.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MatchPhraseLoader.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public List<WebPage> getPages() {
        return webPages;
    }
}