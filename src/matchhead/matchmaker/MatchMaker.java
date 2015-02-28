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
package matchhead.matchmaker;

import java.util.ArrayList;
import java.util.List;
import matchhead.page.Pageable;

public final class MatchMaker implements Matchable {

    private final List<Pageable> pagesToSearch = new ArrayList<>();
    private final List<Pageable> matchResults = new ArrayList<>();

    @Override
    public List<Pageable> match(String query) throws MatchNotFoundException {
        
        query = query.toLowerCase();
        doMatchRules(query);
        
        if (matchResults.isEmpty()) {
            throw new MatchNotFoundException("Not results found for: " + query);
        }
        return matchResults;
    }

    private void doMatchRules(String query) {
        pagesToSearch.stream().forEach((page) -> {
            page.getMatchPhrases().stream().forEach((matchPhrase) -> {      
                matchLiteralRule(query, matchPhrase, page);
            });
        });
    }

    private void matchLiteralRule(String query, String matchPhrase, Pageable page) {
        if (query.equals(matchPhrase)) {
            matchResults.add(page);
        }
    }

    @Override
    public void addPage(Pageable page) {
        if (page == null) {
            throw new NullPointerException("null Page passed to addPage(Page)!"
                    + " Thrown by: "
                    + this.getClass().getName());
        }
        pagesToSearch.add(page);
    }
}
