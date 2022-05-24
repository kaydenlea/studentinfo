package survey.model;
/**
 * reads and writes files based on the student survey to display different information
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/
import java.time.LocalDate;
import java.util.Objects;

public class Person extends User {
	private LocalDate birthday;
	private String favCandy;
	private Movie favMovie;

	public Person(String email, String name, String password, LocalDate birthday, String favCandy, Movie favMovie) {
		super(email, name, password);
		this.birthday = birthday;
		this.favCandy = favCandy;
		this.favMovie = favMovie;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getFavCandy() {
		return favCandy;
	}

	public void setFavCandy(String favCandy) {
		this.favCandy = favCandy;
	}

	public Movie getFavMovie() {
		return favMovie;
	}

	public void setFavMovie(Movie favMovie) {
		this.favMovie = favMovie;
	}

	@Override
	public String toString() {
		return super.getName() + " Person [birthday=" + birthday + ", favCandy=" + favCandy + ", favMovie=" + favMovie.getTitle();
	}

	
	
}
