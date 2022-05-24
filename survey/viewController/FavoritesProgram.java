package survey.viewController;
/**
 * reads and writes files based on the student survey to display different information
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import jdk.jshell.spi.ExecutionControl;
import survey.model.Genre;
import survey.model.Movie;
import survey.model.Person;

public class FavoritesProgram {
	private UI ui;
	private Map<String, Person> userMap;
	private Map<String, Integer> candyMap;
	private Map<Month, List<Person>>  bdayMap;
	private Map<Genre, Set<Movie>> movieMap;
	private Map<Integer, Integer>  dayMap;
	// private Map<Genre, Map<String, List<Movie>>> movieMapMap; //bonus only

	public FavoritesProgram() {
		ui = new UIConsole();
		userMap = new HashMap<>();
		candyMap = new HashMap<>();
		bdayMap = new HashMap<>();
		movieMap = new HashMap<>();
		//movieMapMap = new HashMap<>();
		initMaps();
	}


	private void initMaps() { //initmaps calls all the methods that use the data from classsurveyfilereader
		Collection<Person> data = ClassSurveyFileReader.readSurvey();
		createUserMap(data);
		createCandyMap(data);
		createBdayMap(data);
		createMovieMap(data);
	}

	private void createUserMap(Collection<Person> data){ //creates email map from people
		int index = 0;
		for (Person person: data){ //iterates through eveery person to create key value pair
			userMap.put(person.getEmail(), person);
		}
		for (String key: userMap.keySet()){ //goes through map and prints each person's email and index
			ui.print(index + ": " + userMap.get(key).getEmail());
			index++;
		}

	}

	private void createCandyMap(Collection<Person> data){ //goes through data and adds every persons favorite candy
		for (Person person:data){
			if (candyMap.containsKey(person.getFavCandy())){ //if the candy exists already, it will add to the count
				candyMap.put(person.getFavCandy(), (candyMap.get(person.getFavCandy())+1));

			}
			else{//if it doesnt exist a new key value pair with one will be made
				candyMap.put(person.getFavCandy(), 1);
			}
		}
		for (String key: candyMap.keySet()){ //prints every key and count value for each candy that is greater than 3
			if (candyMap.get(key)>=3) {
				ui.print(key + ": " + candyMap.get(key));
			}
		}

	}

	private void createBdayMap(Collection<Person> data){ //creates bdaymap

		for (Month month: Month.values()){ //goes through each month
			ArrayList<Person> personList = new ArrayList<>(); //creates an array list to hold people
			for (Person person :data){//gets birthday month from each person
				Month birthMonth = person.getBirthday().getMonth();
				if(month.equals(birthMonth)) { //adds person if month match based on first for loop
					personList.add(person);
				}
				bdayMap.put(month, personList);//puts the month and then the whole list of people who have birthdays in that month to the map
			}
		}
		String monthInput = ui.inputWord("Input month name"); //asks user for month name
		for(Month key: bdayMap.keySet()){ //iterates through map to print everyone born in that month
			if (Month.valueOf(monthInput.toUpperCase()).equals(key)){
				ui.print(key + ": "+ bdayMap.get(key));
			}
		}

	}

	private void createMovieMap(Collection<Person> data){

		for (Genre genre: Genre.values()){// goes through all the genres
			Set<Movie> movieSet = new HashSet<>(); //creates set for movies
			for (Person person:data){//goes through every person
				Movie movie = person.getFavMovie();//gets movie from each person
				for (Genre g : movie.getGenre()){
					if (g.equals(genre)){//finds genre that matches and adds to the set
						movieSet.add(movie);
						movieMap.put(genre, movieSet); //adds the set and the genre as key value pair
					}
				}

			}
		}
		//asks user for input for what genre they want to see and prints all the movies in that genre
		String genreInput = ui.inputWord("Input genre").toUpperCase();
		for(Genre key: movieMap.keySet()){
			if(Genre.matchGenre(genreInput).equals(key)){
				ui.print(key+ ": " + movieMap.get(key));
			}
		}

	}


	public void orderBirthdaysFor(Month month) {
		List<Person> plist = bdayMap.get(month); // change object name to match the name of your map
		Comparator<Person> comp = (Person p1 , Person  p2) -> p1.getBirthday().getDayOfYear() - p2.getBirthday().getDayOfYear();
		Collections.sort(plist,comp) ;
	}

	private void monthFileCreator() {//goes through every month and creates a txt file for it
		for(Month month: Month.values()) {
			try {
				File myObj = new File(month.toString() + ".txt");
				if (myObj.createNewFile()) {
					System.out.println("File created: " + myObj.getName());
				} else {
					System.out.println("File already exists.");
				}
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}

	private void monthFileEditor(){//adds to each of the month files
		for (Month key:bdayMap.keySet()){
			orderBirthdaysFor(key);
			String USER_FILE = key.toString() + ".txt";
			try ( FileOutputStream fos = new FileOutputStream(USER_FILE);
			PrintWriter out = new PrintWriter(fos)){
				for (Person person: bdayMap.get(key)){//adds the day they were born in that month to the file
					out.println(person.getBirthday().getDayOfMonth() +":"+ person.getName());
				}
			} catch (FileNotFoundException e) {
			System.err.println("File not found, problem saving data... " + USER_FILE);
		} catch (IOException e) {
			System.err.println("Some other IO Problem happened while saving data... " + e);
		} // files are closed automatically during try-with-resource

		System.out.println("Saved users to: " + USER_FILE);

		}
	}

	private void commonBirthdayReader(){//reads in the birthday files
		for(Month month: Month.values()) {
			String FILE_NAME = month.toString() + ".txt";
			dayMap = new HashMap<>(); //creates a map for each month
			try (FileInputStream fis = new FileInputStream(FILE_NAME);
				 Scanner scanner = new Scanner(fis)) {
				if (scanner.hasNext()) {
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();//gets the day from each person
						int day = parseDay(line);
						if (dayMap.containsKey(day)){ //adds to a map for the days of that month
							dayMap.put(day, (dayMap.get(day)+1));

						}
						else{
							dayMap.put(day, 1);
						}
					}
				}
			} catch (FileNotFoundException e) {
				System.err.println("File not found, problem saving data... " + FILE_NAME);
			} catch (IOException e) {
				System.err.println("Some other IO Problem happened while saving data... " + e);
			} // files are closed automatically during try-with-resource
			for (int key: dayMap.keySet()){
				if (dayMap.get(key)>1) { //for every day map that has more than one person with the same day, it will print
					ui.print(dayMap.get(key) + " people have birthdays on " + month.toString() + " " + key);
				}
			}
		}

	}
	private static int parseDay(String line){ //used to get the day from the txt file
		Scanner sc = new Scanner(line);
		sc.useDelimiter(":");
		int day = sc.nextInt();
		return day;
	}

	private void run() {//runs methods below
		monthFileCreator();
		monthFileEditor();
		commonBirthdayReader();
	}

	public static void main(String[] args) {

		FavoritesProgram fp = new FavoritesProgram();
		fp.run();

	}



}
