package serielizable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Season")
public class Season {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "season_number")
	private Integer seasonNumber;

	@Column(name = "name")
	private String name;

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

	@Column(name = "total_episodes")
	private Integer totalEpisodes;

	@Column(name = "current_episodes")
	private Integer currentEpisodes;
	
	@ManyToOne()
	@JoinColumn(name = "serie_id")
	private Serie serie;

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

	public Integer getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(Integer seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getTotalEpisodes() {
		return totalEpisodes;
	}

	public void setTotalEpisodes(Integer totalEpisodes) {
		this.totalEpisodes = totalEpisodes;
	}

	public Integer getCurrentEpisodes() {
		return currentEpisodes;
	}

	public void setCurrentEpisodes(Integer currentEpisodes) {
		this.currentEpisodes = currentEpisodes;
	}

	public Season() {

	}

}
