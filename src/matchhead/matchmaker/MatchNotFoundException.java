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

public final class MatchNotFoundException extends Exception {

    public MatchNotFoundException(String msg) {
        super(msg);
    }
}