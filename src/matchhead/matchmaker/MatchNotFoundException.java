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

package matchhead.matchmaker;

/**
 * Represents a condition where a search query does not match
 * any stored match phrases in any existing pages. Exceptions of this type
 * will be thrown by the MatchMaker if a match cannot be made.
 * 
 * @author production
 */
public class MatchNotFoundException extends Exception {

    public MatchNotFoundException(String msg) {
        super(msg);
    }
}