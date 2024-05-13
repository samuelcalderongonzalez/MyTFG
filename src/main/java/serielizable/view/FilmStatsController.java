package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import serielizable.entity.Film;
import serielizable.repository.FilmRepository;
import utils.AbstractController;

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

	
	private int[] score = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	@FXML
	public void initialize() {
		updateCurrentFilms();
		getData();
		populatePieChart();
		populateBarChart();

	}

	private void updateCurrentFilms() {
		FilmRepository filmRepository = new FilmRepository();
		currentFilms = filmRepository.getAllByUserId(currentUser.getId());
	}

	private void populatePieChart() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Completadas", getPercentage(completedCount, totalFilmsCount)),
				new PieChart.Data("Pendientes", getPercentage(pendingCount, totalFilmsCount)),
				new PieChart.Data("Abandonadas", getPercentage(droppedCount, totalFilmsCount)));
		pieChart.setData(pieChartData);
	}

	private void populateBarChart() {
		xaxis.setLabel("Mis notas");
		yaxis.setLabel("Películas totales");

		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Puntuación");
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
		// Adding series to the barchart
		barChart.getData().add(series);
	}

	@FXML
	public void logOff() {
		currentUser = null;
		setViewLogin();
	}

	@FXML
	public void search() {
		setViewSearch();
	}

	@FXML
	public void serie() {
		setViewSerie();
	}

	public FilmStatsController() {

	}

	private double getPercentage(int element, int total) {
		return Double.valueOf(element) * 100 / Double.valueOf(total);
	}

	private void getData() {
		for (Film film : currentFilms) {
			totalFilmsCount++;
			getStatusCounts(film);
			getScoresCount(film);
			getFavoritesCount(film);
			getReviewedCount(film);
			getStatsFields(film);
		}
		nFilmsRatedCount = totalFilmsCount - score[0];
		nFilmsRated.setText(String.valueOf(nFilmsRatedCount));
		nFilms.setText(String.valueOf(totalFilmsCount));
		nFilmsFavorite.setText(String.valueOf(favoritesCount));
		nFilmsReviewed.setText(String.valueOf(reviewedCount));
		longestFilm.setText(longest.printDuration());
		shortestFilm.setText(shortest.printDuration());

	}

	private void getStatsFields(Film film) {
		//TODO valorar si añadir que esté en completed
		if(longest == null)
			longest = film;
		else if(longest.getDuration() < film.getDuration())
			longest = film;
		if(shortest == null)
			shortest = film;
		else if(shortest.getDuration() > film.getDuration())
			shortest = film;

	}

//	private void getFromQuery() {
//		longest = ;
//		shortest = ;
//		newest = ;
//		oldest = ;
//		lastest = ;
//		lastUpdated = ;
//		
//	}

	private void getFavoritesCount(Film film) {
		if (film.isFavorite())
			favoritesCount++;
	}

	private void getReviewedCount(Film film) {
		if (film.isReviewed())
			reviewedCount++;
	}

	private void getStatusCounts(Film film) {
		if (film.getStatus().equals("Completada"))
			completedCount++;
		if (film.getStatus().equals("Pendiente"))
			pendingCount++;
		if (film.getStatus().equals("Abandonada"))
			droppedCount++;
	}

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

}
