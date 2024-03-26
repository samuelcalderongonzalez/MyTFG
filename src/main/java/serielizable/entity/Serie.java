package serielizable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "Serie")
public class Serie {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "title")
	private String title;

	@Column(name = "status")
	private String status; // TODO Pendiente de enum

	@Column(name = "review")
	private String review;

	@Column(name = "score")
	private Double score;

	@Column(name = "personal_score")
	private Double personalScore;

	@Column(name = "release_date")
	private Date releaseDate;

	@Column(name = "completed_date")
	private Date completedDate;

	@Column(name = "last_update_date")
	private Date lastUpdateDate;

	@Column(name = "genres")
	private String genres; // TODO darle una vuelta a esto

	@Column(name = "total_episodes")
	private Integer totalEpisodes;

	@Column(name = "current_episodes")
	private Integer currentEpisodes;

	@Column(name = "duration_per_episode")
	private Integer duration;
	
	@Column(name = "synopsis")
	private String synopsis;

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public void setTotalEpisodes(Integer totalEpisodes) {
		this.totalEpisodes = totalEpisodes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getPersonalScore() {
		return personalScore;
	}

	public void setPersonalScore(Double personalScore) {
		this.personalScore = personalScore;
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

	public Integer getTotalEpisodes() {
		return totalEpisodes;
	}

	public void setEpisodes(Integer totalEpisodes) {
		this.totalEpisodes = totalEpisodes;
	}

	public Integer getCurrentEpisodes() {
		return currentEpisodes;
	}

	public void setCurrentEpisodes(Integer currentEpisodes) {
		this.currentEpisodes = currentEpisodes;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Serie(Integer id, Integer userId, String title, String type, String status, String review, Double score,
			Double personalScore, Date releaseDate, Date completedDate, Date lastUpdateDate, String genres,
			Integer totalEpisodes, Integer currentEpisodes, Integer duration, String synopsis) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.status = status;
		this.review = review;
		this.score = score;
		this.personalScore = personalScore;
		this.releaseDate = releaseDate;
		this.completedDate = completedDate;
		this.lastUpdateDate = lastUpdateDate;
		this.genres = genres;
		this.totalEpisodes = totalEpisodes;
		this.currentEpisodes = currentEpisodes;
		this.duration = duration;
		this.synopsis = synopsis;
	}

	public Serie() {

	}

	public SimpleStringProperty getSPTitle() {
		return new SimpleStringProperty(title);
	}

	public SimpleStringProperty getSPStatus() {
		return new SimpleStringProperty(status);
	}

	public SimpleStringProperty getSPProgress() {
		if (currentEpisodes > totalEpisodes)
			setCurrentEpisodes(totalEpisodes);
		return new SimpleStringProperty(currentEpisodes.toString() + "/" + totalEpisodes.toString());
	}
	public void addGenre(String genre) {
		if(this.genres == null) {
			genres = genre;
		} else {
			genres = genres + ", " + genre;
		}
	}
}
