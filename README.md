# MatchHead

matchhead:  This is just a package for main.

matchhead.matchdata:  The input data lives here.

matchhead.matchdataloader:  This packages is for loading the input data, aka the mechanism for simply getting the information from match-phrases.txt into the system.

matchhead.matchmaker:  A "MatchMaker" is an object that performs the search. It takes the user's query, sifts through all known web pages, and "matches" it against the key phrases.  So all the search logic would be in there. Unless we define some kind of logic package or something with pluggable rules (Not yet though).

matchhead.matchmaker.MatchNotFoundException:  The match maker will throw this if the user's query doesnt trigger a match anywhere in the system.

matchhead.outputformatter:  Classes for sending output to the console, or html. More things will be added to this later. Like maybe there's a need to send the output to a database. Or to XML. In such case we would make additional output formatters.

matchhead.page:  Represents a Page in the system (think web page).

matchhead.prefilter:  Code for "pre filtering" the user's query. Checks for null, empty string, and ensure at least one word character is in the query.

matchhead.prefilter.QueryPreFilterException:  Indicates the user's query is bogus.
