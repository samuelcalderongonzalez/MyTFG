package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import serielizable.entity.Film;
import utils.AbstractController;
import utils.DateUtils;

public class ControllerFilm extends AbstractController {

	private ObservableList<Film> films;

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
	private Button btEditFilm;

	@FXML
	private TableView<Film> tableFootage;
	@FXML
	private TableColumn<Film, String> tcTitle;
	@FXML
	private TableColumn<Film, String> tcStatus;
	@FXML
	private TableColumn<Film, String> tcPersonalScore;
	@FXML
	private TableColumn<Film, String> tcReleaseDate;
	@FXML
	private TableColumn<Film, String> tcDuration;

	@FXML
	public void initialize() {
		currentFilms = filmRepository.getAllByUserId(currentUser.getId());
		films = FXCollections.observableArrayList(currentFilms);
		tcTitle.setCellValueFactory(param -> param.getValue().getSPTitle());
		tcStatus.setCellValueFactory(param -> param.getValue().getSPStatus());
		tcPersonalScore.setCellValueFactory(param -> param.getValue().getSPPersonalScore());
		tcReleaseDate.setCellValueFactory(param -> param.getValue().getSPReleaseDate());
		tcDuration.setCellValueFactory(param -> param.getValue().getSPDuration());
		tableFootage.setItems(films);
		tableFootage.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				clearLabels();
				currentFilm = tableFootage.getSelectionModel().getSelectedItem();
				System.out.println(currentFilm);
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

	public void editFilm() {
		setViewEditFilm();
	}

	private void pupulateLabels() {
		btEditFilm.setVisible(true);
		score.setText(currentFilm.getScore().toString());
//		totalVotes.setText(currentFilm.getScoreVotes().toString()); TODO
		genres.setText(currentFilm.getGenres()); 
		completedDate.setText(filmRepository.completedDateExists(currentFilm) ? DateUtils.mapDateToString(currentFilm.getCompletedDate()) : "No se ha completado");
		sinopsis.setText(currentFilm.getSynopsis());
		review.setText(filmRepository.reviewExists(currentFilm) ? currentFilm.getReview()
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

	public ControllerFilm() {

	}
}
