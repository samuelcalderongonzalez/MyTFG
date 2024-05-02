package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import serielizable.entity.Film;
import utils.AbstractController;

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
	
	private ImageView imageView;

	@FXML
	public void initialize() {
		setButtonIcon();
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
		//Mostrar la primera película al iniciar la vista
		currentFilm = currentFilms.get(0);
		pupulateLabels();

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

	@FXML
	public void editFilm() {
		setViewEditFilm();
	}

	private void pupulateLabels() {
		btEditFilm.setVisible(true);
		score.setText(currentFilm.getStringScore());
		totalVotes.setText(currentFilm.getStringTotalScoreVotes());
		genres.setText(currentFilm.getGenres());
		completedDate.setText(currentFilm.getCompletedDate());
		sinopsis.setText(currentFilm.getSynopsis());
		review.setText(currentFilm.getReview());
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
	
	private void setButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/edit.png"));
		imageView = new ImageView(editImg);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btEditFilm.setGraphic(imageView);
	}
}
