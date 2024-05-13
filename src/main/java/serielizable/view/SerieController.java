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
import serielizable.entity.Serie;
import utils.AbstractController;

public class SerieController extends AbstractController {

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

	private ImageView imageView;

	private ImageView imageViewSeason;

	@FXML
	public void initialize() {
		setButtonIcon();
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
				pupulateLabels();
			}
		});
		//Mostrar la primera serie al iniciar la vista
		currentSerie = currentSeries.get(0);
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
	public void film() {
		setViewFilm();
	}

	@FXML
	public void editSerie() {
		setViewEditSerie();
	}

	@FXML
	public void viewSeasons() {
		setViewSeason();
	}

	private void pupulateLabels() {
		btnEditSerie.setVisible(true);
		btnSeasons.setVisible(true);
		score.setText(currentSerie.getStringScore());
		totalVotes.setText(currentSerie.getStringTotalScoreVotes());
		genres.setText(currentSerie.getGenres());
		completedDate.setText(currentSerie.getCompletedDate());
		sinopsis.setText(currentSerie.getSynopsis());
		review.setText(currentSerie.getReview());
	}

	private void clearLabels() {
		score.setText("");
		totalVotes.setText("votes");
		genres.setText("");
		completedDate.setText("");
		sinopsis.setText("");
		review.setText("");
	}

	public SerieController() {

	}

	private void setButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/edit.png"));
		imageView = new ImageView(editImg);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnEditSerie.setGraphic(imageView);
		
		Image goSeason = new Image(getClass().getResourceAsStream("../../utils/seasonDetails.png"));
		imageViewSeason = new ImageView(goSeason);
		imageViewSeason.setFitHeight(50);
		imageViewSeason.setFitWidth(50);
		btnSeasons.setGraphic(imageViewSeason);
	}
}
