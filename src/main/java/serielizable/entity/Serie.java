package serielizable.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;
import utils.Constants;
import utils.DateUtils;

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
	private Integer personalScore;

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

	@Column(name = "total_seasons")
	private Integer countSeasons;

	@Column(name = "synopsis")
	private String synopsis;

	@Column(name = "total_score_votes")
	private Integer totalScoreVotes;

	public Integer getTotalScoreVotes() {
		return totalScoreVotes;
	}

	public String getStringTotalScoreVotes() {
		return totalScoreVotes.toString() + " votos";
	}

	public void setTotalScoreVotes(Integer totalScoreVotes) {
		this.totalScoreVotes = totalScoreVotes;
	}

	@OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
	private List<Season> seasons;

	public List<Season> getSeasons() {
		return seasons;
	}

	public void addSeason(Season season) {
		seasons.add(season);
	}

	public Integer getCountSeasons() {
		return countSeasons;
	}

	public void setCountSeasons(Integer countSeasons) {
		this.countSeasons = countSeasons;
	}

	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}

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
		if (score > 0)
			return score.toString();
		else
			return Constants.NO_SCORE;
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

	public void setPersonalScore(String personalScore) {
		if (personalScore != null) {
			if (!personalScore.contains("-"))
				this.personalScore = Integer.parseInt(personalScore);
			else
				this.personalScore = null;
		}
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

	public Serie() {
		this.seasons = new ArrayList<Season>();
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

	public SimpleStringProperty getSPReleaseDate() {
		return new SimpleStringProperty(DateUtils.mapDateToString(releaseDate));
	}

	public SimpleStringProperty getSPTotalSeasons() {
		return new SimpleStringProperty(countSeasons.toString());
	}

	public SimpleStringProperty getSPScore() {
		return new SimpleStringProperty(score.toString());
	}

	public void addGenre(String genre) {
		if (this.genres == null) {
			genres = genre;
		} else {
			genres = genres + ", " + genre;
		}
	}

	public SimpleStringProperty getSPPersonalScore() {
		if (personalScore != null)
			return new SimpleStringProperty(personalScore.toString());
		else
			return new SimpleStringProperty("-");
	}

	public SimpleStringProperty getSPCountSeasons() {
		return new SimpleStringProperty(countSeasons.toString());
	}

	public String getStringPersonalScore() {
		if (personalScore != null)
			return personalScore.toString();
		else
			return "-";
	}
}
