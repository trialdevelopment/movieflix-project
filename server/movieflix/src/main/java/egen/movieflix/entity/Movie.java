package egen.movieflix.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
@NamedQueries ({
	@NamedQuery(name="Movie.findByName", query="SELECT m FROM Movie m WHERE m.title = :pTitle")
})
public class Movie {

	@Id
	@GeneratedValue(generator="uuid2")
	@GenericGenerator(name="uuid2", strategy="uuid2")
	private String movieID;

	@Column(unique=true)
	private String title;

	private int year;

	private String rated;

	private String released; 
	private String runtime;

	@Column(columnDefinition = "TEXT")
	private String genre;

	@Column(columnDefinition = "TEXT")
	private String director;
	@Column(columnDefinition = "TEXT")
	private String writer;
	@Column(columnDefinition = "TEXT")
	private String actor;

	@Column(columnDefinition = "TEXT")
	private String plot;

	@Column(columnDefinition = "TEXT")
	private String languages;

	@Column(columnDefinition = "TEXT")
	private String countries;

	@Column(columnDefinition = "TEXT")
	private String awards;

	@Column(columnDefinition = "TEXT")
	private String poster;

	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private IMDB imdb;

	private String type;

	private int rating;

	public String getMovieID() {
		return movieID;
	}
	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getReleased() {
		return released;
	}
	public void setReleased(String released) {
		this.released = released;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getPlot() {
		return plot;
	}
	public void setPlot(String plot) {
		this.plot = plot;
	}
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public IMDB getImdb() {
		return imdb;
	}
	public void setImdb(IMDB imdb) {
		this.imdb = imdb;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getRated() {
		return rated;
	}
	public void setRated(String rated) {
		this.rated = rated;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String languages) {
		this.languages = languages;
	}
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}