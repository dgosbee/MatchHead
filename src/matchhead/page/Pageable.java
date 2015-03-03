/*
 * Copyright (C) 2015 Scott A. Hommel
 *
 * This file is part of MatchHead .
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
package matchhead.page;

import java.net.URL;
import java.util.List;

/**
 * A page (as in, "web page") has a URL and a list of match phrases. Match
 * phrases are String objects, and represent the kind of phrase that an end-user
 * would be likely to search for. New match phrases can be added to the page
 * using the addMatchPhrase(String) method. The URL of the page itself must be
 * set in the constructor of the implementing class.
 *
 * Populating each page with specific match phrases is the responsibility of the
 * website owner/admin on which the search engine is running. There are no
 * particular rules to follow when adding a match phrase to a page. The
 * MatchMaker (a separate part of the system) will be responsible for defining
 * match rules, such as filtering out common words, replacing certain words with
 * synonyms, and deciding how much of a pattern will trigger a match. Match
 * phrases do not need to be literally present in the text of a given page.
 *
 * Example:
 *
 * Pageable page = new Page(new
 * URL("http://docs.oracle.com/javase/tutorial/java/concepts/object.html"));
 * page.addMatchPhrase("what is an object?");
 * page.addMatchPhrase("object-oriented technology"); page.addMatchPhrase("state
 * and behavior"); page.addMatchPhrase("object state");
 * page.addMatchPhrase("object behavior"); page.addMatchPhrase("what are
 * fields?"); page.addMatchPhrase("what are methods?");
 * page.addMatchPhrase("data encapsulation");
 * page.addMatchPhrase("encapsulation"); page.addMatchPhrase("information
 * hiding"); page.addMatchPhrase("object communication");
 *
 * @author shommel
 */
public interface Pageable {

    /**
     * Gets the list of match phrases for this page.
     *
     * @return the list of match phrases
     */
    public List<String> getMatchPhrases();

    /**
     * Adds a match phrase to the page.
     *
     * @param matchPhrase the match phrase to add
     */
    public void addMatchPhrase(String matchPhrase);

    /**
     * Adds the list of match phrases.
     * 
     * @param matchPhrases 
     */
    public void setMatchPhrases(List<String> matchPhrases);

    /**
     *
     * Gets the URL of this page.
     *
     * @return the URL of this page, as a String
     */
    public URL getURL();
}
