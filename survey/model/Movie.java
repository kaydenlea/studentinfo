package survey.model;
/**
 * reads and writes files based on the student survey to display different information
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/
import java.time.LocalDate;
import java.util.*;

public class Movie extends Media implements Audio {
	private Collection <Genre> genreList; // an enum that we will define
	private double runningTime; 
	
	/**
	 * 
	 * @param title
	 * @param price
	 * @param publishDate
	 * @param genre : A string representing the genre enum (when we have one genre)
	 * @param runningTime
	 */
	
	public Movie(String title, double price, LocalDate publishDate, String genre,
			double runningTime) {
		this(title, price, publishDate, Genre.matchGenre(genre), runningTime);
	}
	
	public Movie(String title, double price, LocalDate publishDate, Genre genre,
			double runningTime) {
		super(title, price, publishDate);
		genreList = new ArrayList<>();
		genreList.add(genre);
		this.runningTime = runningTime;
	}
	
	public Movie(String title, double price, LocalDate publishDate, 
			Collection<Genre> genreList, double runningTime) {
		super(title, price, publishDate);
		this.genreList = genreList;
		this.runningTime = runningTime;
	}

	public void addGenre(String genre) {
		addGenre(Genre.matchGenre(genre));
		
	}
	public void addGenre(Genre genre) {
		genreList.add(genre);
	}


	public Collection<Genre> getGenre() {
		return genreList;
	}

	public void setGenre(Collection<Genre> list) {
		this.genreList = list;
	}

	@Override
	public String toString() {
		return super.toString() + " [genre=" + genreList + "]";
	}

	@Override
	public double getLength() {
		// TODO Auto-generated method stub
		return runningTime;
	}

	/**
	 * Given a movie in the file format below, turn the data into a Movie object
	 * Since the movies in this dataset don't have a price, all the movies will be 19.99
	 * titleType	primaryTitle	startYear	runtimeMinutes	genres
	 * @param line
	 * @return
	 */
	public static Movie parseLineToMovie(String line) {
		Scanner ls = new Scanner(line);
		ls.useDelimiter("\t");
		String skip = ls.next(); // word movie to start
		String title = ls.next();
		int year = ls.nextInt(); // date is just the year
		double runTime = ls.nextDouble();
		String gList = ls.next(); //all the genres
		LocalDate date = LocalDate.of(year, 1, 1);
		
		//gList may be ONE or more items (comma separated list)
		Collection<Genre> genreList = new ArrayList<>(); //start with empty list
		// turn string into list
		Scanner gS = new Scanner(gList);
		gS.useDelimiter(",");
		while(gS.hasNext()) {
			String next = gS.next(); // grabs a genre as a string
			Genre g = Genre.matchGenre(next);
			genreList.add(g); //TODO decide if UNKNOWN should go in collection or not
		}
		
		double price = 19.99;
		return new Movie(title, price, date, genreList, runTime);

	}
	
	
	
}
