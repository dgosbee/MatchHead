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
package matchhead.matchmaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import matchhead.webpage.WebPageable;

public final class MatchMaker implements Matchable {

    private final List<WebPageable> webPagesToSearch = new ArrayList<>();
    private final Set<WebPageable> matchResults = new HashSet<>();

    @Override
    public Set<WebPageable> match(String query) throws MatchNotFoundException {

        query = query.toLowerCase();
        performSearch(query);

        if (matchResults.isEmpty()) {
            throw new MatchNotFoundException("No results found for: \""
                    + query + "\"");
        }
        return matchResults;
    }

    private void performSearch(String query) {
        for (WebPageable page : webPagesToSearch) {            
             matchRule1(query,page);
            for(String matchPhrase : page.getMatchPhrases()){   
                matchRule2(query,matchPhrase,page);
            }
        }
    }

    /**
     * Performs a literal match. Here the incoming search query is matched
     * exactly as-is against the target match phrase.
     */
    private void matchRule1(String query, WebPageable page) {
       if(page.getMatchPhrases().contains(query)){
       this.matchResults.add(page);
       }
    }

    /**
     * Strips the query of known common words, then checks to see if the
     * stripped down query matches anything.
     */
    private void matchRule2(String query, String matchPhrase, WebPageable page) {
        String[] commonWords = {"the", "be", "to", "and", "a", "that",
            "it", "on", "as", "at", "an", "is", "-"};
        Set<String> mySet = new HashSet<>(Arrays.asList(commonWords));
        StringTokenizer tokenizer = new StringTokenizer(query);
        StringBuilder builder = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            // what
            if (!(mySet.contains(token))) {
                builder.append(token);
                builder.append(" ");
            }
        }
        query = builder.toString().trim();
        matchRule1(query,page);
    }

    @Override
    public void addPage(WebPageable page) {
        if (page == null) {
            throw new NullPointerException("null Page passed to addPage(Page)!"
                    + " Thrown by: "
                    + this.getClass().getName());
        }
        webPagesToSearch.add(page);
    }
}
