package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import serielizable.entity.Serie;
import utils.AbstractController;
import utils.DateUtils;

public class ControllerSerie extends AbstractController {

	private ObservableList<Serie> series;

	@FXML
	private Label score;

	@FXML
	private Label totalVotes;

	@FXML
	private Label genres;

	@FXML
	private Label completedDate;

	@FXML
	private Label sinopsis;

	@FXML
	private Label review;

	@FXML
	private Button btnEditSerie;
	
	@FXML
	private Button btnSeasons;

	@FXML
	private TableView<Serie> tableFootage;
	@FXML
	private TableColumn<Serie, String> tcTitle;
	@FXML
	private TableColumn<Serie, String> tcStatus;
	@FXML
	private TableColumn<Serie, String> tcPersonalScore;
	@FXML
	private TableColumn<Serie, String> tcReleaseDate;
	@FXML
	private TableColumn<Serie, String> tcSeasons;

	@FXML
	public void initialize() {
		currentSeries = serieRepository.getAllByUserId(currentUser.getId());
		series = FXCollections.observableArrayList(currentSeries);
		tcTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
		tcStatus.setCellValueFactory(param -> param.getValue().getSPStatus());
		tcPersonalScore.setCellValueFactory(param -> param.getValue().getSPPersonalScore());
		tcReleaseDate.setCellValueFactory(param -> param.getValue().getSPReleaseDate());
		tcSeasons.setCellValueFactory(param -> param.getValue().getSPCountSeasons());
		tableFootage.setItems(series);
		tableFootage.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				clearLabels();
				currentSerie = tableFootage.getSelectionModel().getSelectedItem();
				System.out.println(currentSerie);
				pupulateLabels();
			}
		});

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
	public void film() {
		setViewFilm();
	}
	@FXML
	public void editSerie() {
//		setViewEditSerie();
	}
	
	@FXML
	public void viewSeasons() {
		System.out.println(currentSerie);
		setViewSeason();
	}

	private void pupulateLabels() {
		btnEditSerie.setVisible(true);
		btnSeasons.setVisible(true);
		score.setText(currentSerie.getScore().toString());
//		totalVotes.setText(currentSerie.getScoreVotes().toString()); TODO
		genres.setText(currentSerie.getGenres());
		completedDate.setText(serieRepository.completedDateExists(currentSerie)
				? DateUtils.mapDateToString(currentSerie.getCompletedDate())
				: "No se ha completado");
		sinopsis.setText(currentSerie.getSynopsis());
		review.setText(serieRepository.reviewExists(currentSerie) ? currentSerie.getReview()
				: "No has hecho ninguna reseña sobre esta película");
	}

	private void clearLabels() {
		score.setText("");
		totalVotes.setText("votes");
		genres.setText("");
		completedDate.setText("");
		sinopsis.setText("");
		review.setText("");
	}

	public ControllerSerie() {

	}
}
