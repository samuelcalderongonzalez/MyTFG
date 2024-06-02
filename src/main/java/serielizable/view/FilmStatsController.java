package serielizable.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import serielizable.entity.Film;
import serielizable.repository.FilmRepository;
import utils.AbstractController;
import utils.Constants;

/**
 * The film stats controller class
 * 
 * @author Samuel Calderón González
 *
 */
public class FilmStatsController extends AbstractController {

	@FXML
	private PieChart pieChart;

	@FXML
	private BarChart<String, Number> barChart;

	@FXML
	private CategoryAxis xaxis;

	@FXML
	private NumberAxis yaxis;

	@FXML
	private Label nFilmsRated;

	@FXML
	private Label nFilms;

	@FXML
	private Label nFilmsFavorite;

	@FXML
	private Label nFilmsReviewed;

	@FXML
	private Label totalTimeInvested;

	@FXML
	private Label longestFilm;
	@FXML
	private Label shortestFilm;
	@FXML
	private Label newestFilm;
	@FXML
	private Label oldestFilm;
	@FXML
	private Label lastestFilm;
	@FXML
	private Label lastUpdatedFilm;

	private int completedCount = 0;
	private int pendingCount = 0;
	private int droppedCount = 0;
	private int totalFilmsCount = 0;

	private int nFilmsRatedCount = 0;

	private int favoritesCount = 0;

	private int reviewedCount = 0;

	private Film longest;

	private Film shortest;

	private Film newest;

	private Film oldest;

	private Film lastest;

	private Film lastUpdated;

	private int totalMinutes = 0;

	private int[] score = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	@FXML
	private Rectangle backgroundImage;

	@FXML
	public void initialize() {
		setBackgroundImage();
		updateCurrentFilms();
		getData();
		populatePieChart();
		populateBarChart();

	}

	/**
	 * Update the currentFilms list
	 */
	private void updateCurrentFilms() {
		FilmRepository filmRepository = new FilmRepository();
		currentFilms = filmRepository.getAllByUserId(currentUser.getId());
	}

	/**
	 * Populate the pie chart with the status and their percentages
	 */
	private void populatePieChart() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Completadas (" + completedCount + ")",
						getPercentage(completedCount, totalFilmsCount)),
				new PieChart.Data("Pendientes (" + pendingCount + ")", getPercentage(pendingCount, totalFilmsCount)),
				new PieChart.Data("Abandonadas (" + droppedCount + ")", getPercentage(droppedCount, totalFilmsCount)));
		pieChart.setData(pieChartData);

	}

	/**
	 * Populate the bar chart with the scores
	 */
	private void populateBarChart() {
		xaxis.setLabel("Puntuaciones");
		yaxis.setLabel("Películas totales");

		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Puntuaciones");
		series.getData().add(new XYChart.Data<>("10", score[10]));
		series.getData().add(new XYChart.Data<>("9", score[9]));
		series.getData().add(new XYChart.Data<>("8", score[8]));
		series.getData().add(new XYChart.Data<>("7", score[7]));
		series.getData().add(new XYChart.Data<>("6", score[6]));
		series.getData().add(new XYChart.Data<>("5", score[5]));
		series.getData().add(new XYChart.Data<>("4", score[4]));
		series.getData().add(new XYChart.Data<>("3", score[3]));
		series.getData().add(new XYChart.Data<>("2", score[2]));
		series.getData().add(new XYChart.Data<>("1", score[1]));
		series.getData().add(new XYChart.Data<>("Sin nota", score[0]));
		// Adding series to the bar chart
		barChart.getData().add(series);

	}

	/**
	 * Log off method
	 */
	@FXML
	public void logOff() {
		currentUser = null;
		currentFilm = null;
		currentSerie = null;
		setViewLogin();
	}

	/**
	 * Close the app
	 */
	@FXML
	public void exit() {
		Platform.exit();
	}

	/**
	 * Open the search view
	 */
	@FXML
	public void search() {
		currentSerie = null;
		setViewSearch();
	}

	/**
	 * Open the serie view
	 */
	@FXML
	public void serie() {
		setViewSerie();
	}

	/**
	 * Open the film view
	 */
	@FXML
	public void film() {
		setViewFilm();
	}

	/**
	 * Open the film stats view
	 */
	@FXML
	public void filmStats() {
		setViewFilmStats();
	}

	/**
	 * Open the serie stats view
	 */
	@FXML
	public void serieStats() {
		setViewSerieStats();
	}

	public FilmStatsController() {

	}

	/**
	 * Convert the into a percentage based in the total
	 * 
	 * @param element
	 * @param total
	 * @return the percentage
	 */
	private double getPercentage(int element, int total) {
		return Double.valueOf(element) * 100 / Double.valueOf(total);
	}

	/**
	 * Populate all the data field and display them
	 */
	private void getData() {
		for (Film film : currentFilms) {
			totalFilmsCount++;
			getStatusCounts(film);
			getScoresCount(film);
			getFavoritesCount(film);
			getReviewedCount(film);
			getStatsFields(film);
			sumDuration(film);
		}
		nFilmsRatedCount = totalFilmsCount - score[0];
		nFilmsRated.setText(String.valueOf(nFilmsRatedCount));
		nFilms.setText(String.valueOf(totalFilmsCount));
		nFilmsFavorite.setText(String.valueOf(favoritesCount));
		nFilmsReviewed.setText(String.valueOf(reviewedCount));
		longestFilm.setText((longest != null ? longest.printDuration() : Constants.NOT_AVAILABLE));
		shortestFilm.setText(shortest != null ? shortest.printDuration() : Constants.NOT_AVAILABLE);
		newestFilm.setText(newest != null ? newest.printReleaseDate() : Constants.NOT_AVAILABLE);
		oldestFilm.setText(oldest != null ? oldest.printReleaseDate() : Constants.NOT_AVAILABLE);
		lastestFilm.setText(lastest != null ? lastest.printCompletedDate() : Constants.NOT_AVAILABLE);
		lastUpdatedFilm.setText(lastUpdated != null ? lastUpdated.printLastUpdateDate() : Constants.NOT_AVAILABLE);
		totalTimeInvested.setText(printTotalMinutes(totalMinutes));

	}

	/**
	 * Sum the film duration if the status is completed
	 * 
	 * @param film
	 */
	private void sumDuration(Film film) {
		if (film.getDuration() != null & film.getStatus().equals("Completada"))
			totalMinutes += film.getDuration();
	}

	/**
	 * Compare certain films in order to fill the stats fields
	 * 
	 * @param film
	 */
	private void getStatsFields(Film film) {
		// Compare and saves the longest and shortest film
		if (film.getDuration() != null) {
			if (longest == null)
				longest = film;
			else if (longest.getDuration() < film.getDuration())
				longest = film;
			if (shortest == null)
				shortest = film;
			else if (shortest.getDuration() > film.getDuration())
				shortest = film;
		}
		// Compare and saves the newest and oldest film
		if (film.getReleaseDate() != null) {
			if (newest == null)
				newest = film;
			else if (newest.getReleaseDate().before(film.getReleaseDate()))
				newest = film;
			if (oldest == null)
				oldest = film;
			else if (oldest.getReleaseDate().after(film.getReleaseDate()))
				oldest = film;
		}
		// Compare and save the lastest completed film
		if (film.getCompletedDateDate() != null) {
			if (lastest == null)
				lastest = film;
			else if (lastest.getCompletedDateDate().before(film.getCompletedDateDate()))
				lastest = film;
		}
		// Compare and save the lastest updated film
		if (film.getLastUpdateDate() != null) {
			if (lastUpdated == null)
				lastUpdated = film;
			else if (lastUpdated.getLastUpdateDate().before(film.getLastUpdateDate()))
				lastUpdated = film;
		}

	}

	/**
	 * Count the number of film favorites
	 * 
	 * @param film
	 */
	private void getFavoritesCount(Film film) {
		if (film.isFavorite())
			favoritesCount++;
	}

	/**
	 * Count the number of film reviewed
	 * 
	 * @param film
	 */
	private void getReviewedCount(Film film) {
		if (film.isReviewed())
			reviewedCount++;
	}

	/**
	 * Count the number of film completed, pending and dropped
	 * 
	 * @param film
	 */
	private void getStatusCounts(Film film) {
		if (film.getStatus().equals("Completada"))
			completedCount++;
		if (film.getStatus().equals("Pendiente"))
			pendingCount++;
		if (film.getStatus().equals("Abandonada"))
			droppedCount++;
	}

	/**
	 * Count how many time each score has been submitted
	 * 
	 * @param film
	 */
	private void getScoresCount(Film film) {
		if (film.getPersonalScore() != null) {
			switch (film.getPersonalScore()) {
			case 1:
				score[1]++;
				break;
			case 2:
				score[2]++;
				break;
			case 3:
				score[3]++;
				break;
			case 4:
				score[4]++;
				break;
			case 5:
				score[5]++;
				break;
			case 6:
				score[6]++;
				break;
			case 7:
				score[7]++;
				break;
			case 8:
				score[8]++;
				break;
			case 9:
				score[9]++;
				break;
			case 10:
				score[10]++;
				break;
			}
		} else {
			score[0]++;
		}
	}

	/**
	 * Prints the total time watching film based on the film runtime. Print it in a
	 * determinate format
	 * 
	 * @param duration
	 * @return duration in the correct format
	 */
	private String printTotalMinutes(int duration) {
		int newDuration = duration;
		Integer hour = 0;
		while (newDuration - 60 >= 0) {
			hour++;
			newDuration = newDuration - 60;
		}
		String time = hour > 0 ? hour.toString() + "h " + newDuration + "min" : newDuration + "min";
		return "Has pasado un total de " + time + " viendo películas.";
	}

	/**
	 * Set the background image
	 */
	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

}
