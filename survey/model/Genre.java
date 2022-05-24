package survey.model;
/**
 * reads and writes files based on the student survey to display different information
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public enum Genre {
	ACTION,
	ADVENTURE,
	ANIMATION,
	BIOGRAPHY,
	COMEDY, 
	CRIME,
	DRAMA,
	FAMILY,
	FANTASY,
	MYSTERY,
	HORROR,
	ROMANCE,
	DOCUMENTARY,
	MUSICAL,
	MUSIC,
	NEWS,
	HISTORY,
	REALITY_TV,
	SPORT,
	SCI_FI, 
	THRILLER, 
	WESTERN,
	WAR, 
	UNKNOWN;


	/**
	 * This function takes a string that hopefully matches ONE genre type,
	 * and will return that enum constant, multi-word genre has - not _
	 * @param g
	 * @return matching Genre, or 
	 */
	public static Genre matchGenre(String g) {
		//Genre gen = Genre.valueOf(g.toUpperCase());
		Genre gen = UNKNOWN;
		// if g contains " " or "-", replace with _, also get rid of "
		g = g.replace("-", "_").replace("\"", "");

		for(Genre x : Genre.values()) {
			if(x.toString().equalsIgnoreCase(g)) { //perfect genre match
				gen = x;
			}
		}
		if(gen == UNKNOWN) {
			System.err.println("Couldn't match string genre " + g + " with existing constants");
		}
		return gen;
	}

	public static Collection<Genre> matchGenreCSVList(String gList) {
		//gList may be ONE or more items (comma separated list)
		Collection<Genre> genreList = new ArrayList<>(); //start with empty list
		// turn string into list
		Scanner gS = new Scanner(gList);
		gS.useDelimiter(",");
		while(gS.hasNext()) {
			String next = gS.next(); // grabs a genre as a string
			Genre g = Genre.matchGenre(next.strip());
			genreList.add(g); //TODO decide if UNKNOWN should go in collection or not
		}
		return genreList;
	}

}
