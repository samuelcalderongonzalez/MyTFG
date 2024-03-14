package serielizable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "Footage")
public class Footage  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "type")
	private String type; // TODO Pendiente de enum
	
	@Column(name = "status")
	private String status; // TODO Pendiente de enum

	@Column(name = "review")
	private String review;
	
	@Column(name = "score")
	private int score;
	
	@Column(name = "release_date")
	private Date releaseDate;
	
	@Column(name = "completed_date")
	private Date completedDate;
	
	@Column(name = "last_update_date")
	private Date lastUpdateDate;
	
	@Column(name = "genres")
	private String genres; //TODO darle una vuelta a esto
	
	@Column(name = "episodes")
	private Integer episodes;
	
	@Column(name = "duration")
	private int duration;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public int getEpisodes() {
		return episodes;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Footage(int userId, String title, String type, String status, String review, int score,
			Date releaseDate, Date completedDate, Date lastUpdateDate, String genres, int episodes, int duration) {
		this.userId = userId;
		this.title = title;
		this.type = type;
		this.status = status;
		this.review = review;
		this.score = score;
		this.releaseDate = releaseDate;
		this.completedDate = completedDate;
		this.lastUpdateDate = lastUpdateDate;
		this.genres = genres;
		this.episodes = episodes;
		this.duration = duration;
	}
	public Footage() {
		
	}
	
	public SimpleStringProperty getSPTitle() {
		return new SimpleStringProperty(title);
	}
	
	public SimpleStringProperty getSPStatus() {
		return new SimpleStringProperty(status);
	}
	
	public SimpleStringProperty getSPProgress() {
		return new SimpleStringProperty(episodes.toString());
	}
	
	
}
