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

package matchhead.page;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The Page class is an implementation of the Pageable interface. 
 * 
 * @author shommel
 */
public final class Page implements Pageable {
  
    private final URL url;
    private List<String> matchPhrases;

    /**
     * Creates a new Page for the given URL. A java.net.URL object is required 
     * at the time of creation (instead of a String) because it enforces 
     * certain rules, ensuring that the page will not contain a malformed URL.
     * 
     * @param url 
     */
    public Page(URL url) {
        this.url = url;
        this.matchPhrases = new ArrayList<>();
    }

    @Override
    public URL getURL(){
        return this.url;
    }
    
    @Override
    public final List<String> getMatchPhrases() {
        return matchPhrases;
    }

    public void setMatchPhrases(List<String> matchPhrases) {
        this.matchPhrases = matchPhrases;
    }
    
    @Override
    public final void addMatchPhrase(String matchPhrase) {
        if (matchPhrase != null && !matchPhrase.equals("")) {
            this.matchPhrases.add(matchPhrase);
        }
    }

    @Override
    public final String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("URL: ");
        sb.append(url.toString());
        sb.append("\n");
        sb.append("TOTAL STORED MATCH PHRASES: ");
        sb.append(matchPhrases.size());
        
        int count = 0;
        for (String matchPhrase : this.matchPhrases) {
            sb.append("\n");
            sb.append("\t").append(++count);
            sb.append(": ");
            sb.append("\"");
            sb.append(matchPhrase);
            sb.append("\"");
        }
        
        return sb.toString();
    }
}