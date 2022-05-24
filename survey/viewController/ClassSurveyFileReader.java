package survey.viewController;
/**
 * reads and writes files based on the student survey to display different information
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/
import survey.model.Genre;
import survey.model.Movie;
import survey.model.Person;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class ClassSurveyFileReader {
    private static final String FILE_NAME = "src/survey.resources/Spring2022_classSurvey.tsv";

    public static Collection<Person> readSurvey() { //reads in the data from teh survey
        // TODO Auto-generated method stub
        Collection<Person> people = new ArrayList<>(); //creates array list for the people
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             Scanner scanner = new Scanner(fis)) {
            if (scanner.hasNext()) {
                String header = scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine(); //parses people to the array list
                    Person p = parseStringIntoPerson(line);
                    people.add(p);
                }
            }


        } catch (FileNotFoundException e) {
            System.err.println("File not found, problem saving data... " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Some other IO Problem happened while saving data... " + e);
        } // files are closed automatically during try-with-resource
        return people;

    }

    private static Person parseStringIntoPerson(String line) {
        Person person = null; //gets the people from the line
        //one line of data from survey data
		try {
			Scanner ls = new Scanner(line);
			ls.useDelimiter("\t");
			String ts = ls.next(); //timestamp
			String email = ls.next();
			String name = ls.next();
			String pass = ls.next();
			String bdayStr = ls.next();
			String candy = ls.next();
			String title = ls.next();
			double price = ls.nextDouble();
			String publishStr = ls.next();
			String genreString = ls.next();
			double runTime = ls.nextDouble();
			Collection<Genre> genreColl = Genre.matchGenreCSVList(genreString);

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			LocalDate bday = LocalDate.parse(bdayStr, dateFormatter);
			LocalDate publish = LocalDate.parse(publishStr, dateFormatter);
			Movie movie = new Movie(title, price, publish, genreColl, runTime);
			person = new Person(email, name, pass, bday, candy, movie);
		}
		catch(Exception e){
			System.err.println("Error reading line of file: " + line + "\nerror; " + e);
		}
        return person;
//make movie and person objects
    }

}
