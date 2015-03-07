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

    private final List<WebPageable> pagesToSearch = new ArrayList<>();
    private final Set<WebPageable> matchResults = new HashSet<>();

    @Override
    public Set<WebPageable> match(String query) throws MatchNotFoundException {

        query = query.toLowerCase();
        performSearch(query);

        if (matchResults.isEmpty()) {
            throw new MatchNotFoundException("No results found for: \"" 
                    + query+"\"");
        }
        return matchResults;
    }

    private void performSearch(String query) {
        pagesToSearch.stream().forEach((page) -> {
            page.getMatchPhrases().stream().forEach((matchPhrase) -> {
                
                /*
                For now, match rules are coded as matchRule1, matchRule2, etc.
                Each new rule is added to the chain in its own method following
                that naming convention. TBD: Is this the most intuitive way to
                support rule chaining in the long term? Probably not, because 
                if there are lots of rules, we will need some kind of reference 
                chart describing what each one is for. Or maybe the javadoc comment
                on each method is enough? Will leave this as-is for now, but it 
                is something to think about.
                */
                matchRule1(query, matchPhrase, page);
                matchRule2(query, matchPhrase, page);   
            });
        });
    }

    /**
     * Performs a literal match. Here the incoming search query is matched
     * exactly as-is against the target match phrase.
     */
    private void matchRule1(String query, String matchPhrase, WebPageable page) {
        if (query.equals(matchPhrase)) {
            matchResults.add(page);
        }
    }

    /**
     * Strips the query of known common words, then checks to see if the
     * stripped down query matches anything.
     */
    private void matchRule2(String query, String matchPhrase, WebPageable page) {
        String[] commonWords = {"the", "be", "to", "and", "a", "that",
            "it", "on", "as", "at", "an", "is","-"};
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
        matchRule1(query,matchPhrase,page);
    }

    @Override
    public void addPage(WebPageable page) {
        if (page == null) {
            throw new NullPointerException("null Page passed to addPage(Page)!"
                    + " Thrown by: "
                    + this.getClass().getName());
        }
        pagesToSearch.add(page);
    }
}
