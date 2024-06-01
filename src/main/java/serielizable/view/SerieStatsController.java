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
import serielizable.entity.Season;
import serielizable.entity.Serie;
import serielizable.repository.SerieRepository;
import utils.AbstractController;
import utils.Constants;

public class SerieStatsController extends AbstractController {

	@FXML
	private PieChart pieChart;

	@FXML
	private BarChart<String, Number> barChart;

	@FXML
	private CategoryAxis xaxis;

	@FXML
	private NumberAxis yaxis;

	@FXML
	private Label nSeriesRated;

	@FXML
	private Label nSeries;

	@FXML
	private Label nSeriesFavorite;

	@FXML
	private Label nSeriesReviewed;
	
	@FXML
	private Label totalTimeInvested;

	@FXML
	private Label longestSerie;
	@FXML
	private Label shortestSerie;
	@FXML
	private Label newestSerie;
	@FXML
	private Label oldestSerie;
	@FXML
	private Label lastestSerie;
	@FXML
	private Label lastUpdatedSerie;

	private int completedCount = 0;
	private int onGoingCount = 0;
	private int pendingCount = 0;
	private int droppedCount = 0;
	private int totalSeriesCount = 0;

	private int nSeriesRatedCount = 0;

	private int favoritesCount = 0;

	private int reviewedCount = 0;
	
	private int totalEpisodesViewed = 0;

	private Serie longest;

	private Serie shortest;

	private Serie newest;

	private Serie oldest;

	private Serie lastest;

	private Serie lastUpdated;
	
	private int totalSeasons = 0;

	private int[] score = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	@FXML
	private Rectangle backgroundImage;

	@FXML
	public void initialize() {
		setBackgroundImage();
		updateCurrentSeries();
		getData();
		populatePieChart();
		populateBarChart();

	}

	private void updateCurrentSeries() {
		SerieRepository serieRepository = new SerieRepository();
		currentSeries = serieRepository.getAllByUserId(currentUser.getId());
	}

	private void populatePieChart() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Completadas (" + completedCount +")", getPercentage(completedCount, totalSeriesCount)),
				new PieChart.Data("Pendientes (" + pendingCount + ")", getPercentage(pendingCount, totalSeriesCount)),
				new PieChart.Data("En curso (" + onGoingCount + ")", getPercentage(onGoingCount, totalSeriesCount)),
				new PieChart.Data("Abandonadas (" + droppedCount + ")", getPercentage(droppedCount, totalSeriesCount)));
		pieChart.setData(pieChartData);

	}

	private void populateBarChart() {
		xaxis.setLabel("Puntuaciones");
		yaxis.setLabel("Series totales");

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
		// Adding series to the barchart
		barChart.getData().add(series);
		
		
	}

	@FXML
	public void logOff() {
		currentUser = null;
		currentFilm = null;
		currentSerie = null;
		setViewLogin();
	}

	@FXML
	public void exit() {
		Platform.exit();
	}

	@FXML
	public void search() {
		currentSerie = null;
		setViewSearch();
	}

	@FXML
	public void serie() {
		setViewSerie();
	}

	@FXML
	public void film() {
		setViewFilm();
	}

	@FXML
	public void filmStats() {
		setViewFilmStats();
	}

	@FXML
	public void serieStats() {
		setViewSerieStats();
	}

	public SerieStatsController() {

	}

	private double getPercentage(int element, int total) {
		return Double.valueOf(element) * 100 / Double.valueOf(total);
	}

	private void getData() {
		for (Serie serie : currentSeries) {
			for (Season s : serie.getSeasons()) {
				totalEpisodesViewed += s.getCurrentEpisodes();
			}
			totalSeriesCount++;
			getStatusCounts(serie);
			getScoresCount(serie);
			getFavoritesCount(serie);
			getReviewedCount(serie);
			getStatsFields(serie);
			sumDuration(serie);
		}
		nSeriesRatedCount = totalSeriesCount - score[0];
		nSeriesRated.setText(String.valueOf(nSeriesRatedCount));
		nSeries.setText(String.valueOf(totalSeriesCount));
		nSeriesFavorite.setText(String.valueOf(favoritesCount));
		nSeriesReviewed.setText(String.valueOf(reviewedCount));
		longestSerie.setText((longest != null ? longest.printDuration() : Constants.NOT_AVAILABLE));
		shortestSerie.setText(shortest != null ? shortest.printDuration() : Constants.NOT_AVAILABLE);
		newestSerie.setText(newest != null ? newest.printReleaseDate() : Constants.NOT_AVAILABLE);
		oldestSerie.setText(oldest != null ? oldest.printReleaseDate() : Constants.NOT_AVAILABLE);
		lastestSerie.setText(lastest != null ? lastest.printCompletedDate() : Constants.NOT_AVAILABLE);
		lastUpdatedSerie.setText(lastUpdated != null ? lastUpdated.printLastUpdateDate() : Constants.NOT_AVAILABLE);
		totalTimeInvested.setText(printTotalSeasons(totalSeasons));

	}

	private void sumDuration(Serie serie) {
		if(serie.getCountSeasons() != null & serie.getStatus().equals("Completada"))
			totalSeasons += serie.getCountSeasons();
	}

	private void getStatsFields(Serie serie) {
		if (serie.getCountSeasons() != null) {
			if (longest == null)
				longest = serie;
			else if (longest.getCountSeasons() < serie.getCountSeasons())
				longest = serie;
			if (shortest == null)
				shortest = serie;
			else if (shortest.getCountSeasons() > serie.getCountSeasons())
				shortest = serie;
		}
		if (serie.getReleaseDate() != null) {
			if (newest == null)
				newest = serie;
			else if (newest.getReleaseDate().before(serie.getReleaseDate()))
				newest = serie;
			if (oldest == null)
				oldest = serie;
			else if (oldest.getReleaseDate().after(serie.getReleaseDate()))
				oldest = serie;
		}
		if (serie.getCompletedDateDate() != null) {
			if (lastest == null)
				lastest = serie;
			else if (lastest.getCompletedDateDate().before(serie.getCompletedDateDate()))
				lastest = serie;
		}
		if (serie.getLastUpdateDate() != null) {
			if (lastUpdated == null)
				lastUpdated = serie;
			else if (lastUpdated.getLastUpdateDate().before(serie.getLastUpdateDate()))
				lastUpdated = serie;
		}

	}

	private void getFavoritesCount(Serie serie) {
		if (serie.isFavorite())
			favoritesCount++;
	}

	private void getReviewedCount(Serie serie) {
		if (serie.isReviewed())
			reviewedCount++;
	}

	private void getStatusCounts(Serie serie) {
		if (serie.getStatus().equals("Completada"))
			completedCount++;
		if (serie.getStatus().equals("En curso"))
			completedCount++;
		if (serie.getStatus().equals("Pendiente"))
			pendingCount++;
		if (serie.getStatus().equals("Abandonada"))
			droppedCount++;
	}

	private void getScoresCount(Serie serie) {
		if (serie.getPersonalScore() != null) {
			switch (serie.getPersonalScore()) {
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
	
	private String printTotalSeasons(int duration) {
		return "Has visto un total de " + totalSeasons + " temporadas y " + totalEpisodesViewed + " capítulos.";
	}
	
	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

}
