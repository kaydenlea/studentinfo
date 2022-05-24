/**
 * jkoe - coffee
 */
package survey.model;
/**
 * reads and writes files based on the student survey to display different information
 * klea@usc.edu
 * ITP 265, Spring 2022
 * [coffee] Class Section
 **/
import java.time.LocalDate;
import java.util.Objects;

public abstract class Media implements Comparable<Media> { //helps guide the hierarchy
	
	protected String title;
	protected double price;
	private LocalDate publishDate;
	
	public Media(String title, double price, LocalDate publishDate) {
		this.title = title;
		this.price = price;
		this.publishDate = publishDate;
	}

	
	@Override
	public int compareTo(Media other) {
		//Natural ordering -- all media to be alphabetical by title
		int diff = this.title.compareTo(other.title);
		if(diff == 0) { // titles are the same, look futher...
			double pDiff = this.price - other.price;
			if(pDiff > 0) diff = 1;
			else if(pDiff < 0) { diff = -1; }
			else { // titles and price are the same, look at date
				diff = this.publishDate.compareTo(other.publishDate);
			}
		}
	
		return diff;
		
	}
	



	public String getTitle() {
		return  title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public LocalDate getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [title=" + title + ", price=" + price + ", publishDate=" + publishDate + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(price, publishDate, title);
	}
	@Override
	public boolean equals(Object obj) {
		//TODO come back and explore the equals method
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
		//if(!(obj instanceof Media))
			return false;
		Media other = (Media) obj;
		return Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(publishDate, other.publishDate) && Objects.equals(title, other.title);
	}

	

}
