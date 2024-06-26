package serielizable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;
import utils.Constants;
import utils.DateUtils;

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
	private String status;

	@Column(name = "review")
	private String review;

	@Column(name = "score")
	private Double score;

	@Column(name = "personal_score")
	private Integer personalScore;

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
		if (review != null)
			return review;
		else
			return " ";
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Double getScore() {
		return score;
	}

	public String getStringScore() {
		if (score != null) {
			if (score > 0)
				return score.toString();
			else
				return Constants.NOT_AVAILABLE;
		} else
			return Constants.NOT_AVAILABLE;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getPersonalScore() {
		return personalScore;
	}

	public void setPersonalScore(Integer personalScore) {
		this.personalScore = personalScore;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCompletedDate() {
		if (completedDate != null)
			return DateUtils.mapDateToString(completedDate);
		else
			return Constants.NO_COMPLETE_DATE;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Integer getTotalEpisodes() {
		return totalEpisodes;
	}

	public String getStringTotalEpisodes() {
		return totalEpisodes.toString();
	}

	public void setTotalEpisodes(Integer totalEpisodes) {
		this.totalEpisodes = totalEpisodes;
	}

	public Integer getCurrentEpisodes() {
		if(currentEpisodes != null)
			return currentEpisodes;
		else
			return 0;
	}

	public String getStringCurrentEpisodes() {
		if (currentEpisodes != null)
			return currentEpisodes.toString();
		else
			return "0";
	}

	public void setCurrentEpisodes(Integer currentEpisodes) {
		this.currentEpisodes = currentEpisodes;
	}

	public void setCurrentEpisodes(String currentEpisodes) {
		this.currentEpisodes = Integer.parseInt(currentEpisodes);
	}

	public Season() {

	}

	public SimpleStringProperty getSPName() {
		return new SimpleStringProperty(name);
	}

	public SimpleStringProperty getSPStatus() {
		return new SimpleStringProperty(status);
	}

	public SimpleStringProperty getSPProgress() {
		Integer provCurrentEpisodes = 0;
		if (currentEpisodes != null)
			provCurrentEpisodes = currentEpisodes;
		if (provCurrentEpisodes > totalEpisodes)
			setCurrentEpisodes(totalEpisodes);
		return new SimpleStringProperty(provCurrentEpisodes.toString() + "/" + totalEpisodes.toString());
	}

	public SimpleStringProperty getSPReleaseDate() {
		return new SimpleStringProperty(DateUtils.mapDateToString(releaseDate));
	}

	public SimpleStringProperty getSPScore() {
		return new SimpleStringProperty(score.toString());
	}

	public SimpleStringProperty getSPPersonalScore() {
		if (personalScore != null)
			return new SimpleStringProperty(personalScore.toString());
		else
			return new SimpleStringProperty("-");
	}

	public void setPersonalScore(String personalScore) {
		if (!personalScore.contains("-"))
			this.personalScore = Integer.parseInt(personalScore);
		else
			this.personalScore = null;
	}

	public String getStringPersonalScore() {
		if (personalScore != null)
			return personalScore.toString();
		else
			return "-";
	}

}
