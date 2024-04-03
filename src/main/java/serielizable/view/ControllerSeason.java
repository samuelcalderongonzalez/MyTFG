package serielizable.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import serielizable.entity.Season;
import utils.AbstractController;
import utils.DateUtils;

public class ControllerSeason extends AbstractController {

	private ObservableList<Season> seasons;

	@FXML
	private Label score;
	
	@FXML
	private Label serieTitle;

	@FXML
	private Label totalVotes;

	@FXML
	private Label completedDate;

	@FXML
	private Label review;

	@FXML
	private Button btnEditSeason;

	@FXML
	private TableView<Season> tableFootage;
	@FXML
	private TableColumn<Season, String> tcTitle;
	@FXML
	private TableColumn<Season, String> tcStatus;
	@FXML
	private TableColumn<Season, String> tcPersonalScore;
	@FXML
	private TableColumn<Season, String> tcReleaseDate;
	@FXML
	private TableColumn<Season, String> tcProgress;

	@FXML
	public void initialize() {
		System.out.println(currentSerie);
		serieTitle.setText(currentSerie.getTitle());
		seasons = FXCollections.observableArrayList(currentSerie.getSeasons());
		tcTitle.setCellValueFactory(param -> param.getValue().getSPName());
		tcStatus.setCellValueFactory(param -> param.getValue().getSPStatus());
		tcPersonalScore.setCellValueFactory(param -> param.getValue().getSPPersonalScore());
		tcReleaseDate.setCellValueFactory(param -> param.getValue().getSPReleaseDate());
		tcProgress.setCellValueFactory(param -> param.getValue().getSPProgress());
		tableFootage.setItems(seasons);
		tableFootage.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				clearLabels();
				currentSeason = tableFootage.getSelectionModel().getSelectedItem();
				System.out.println(currentSeason);
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
	public void serie() {
		setViewSerie();
	}
	@FXML
	public void editSeason() {
//		setViewEditSerie();
	}
	
	@FXML
	public void handleBack() {
		setViewSerie();
	}
	
	@FXML
	public void viewSeasons() {
//		setViewSeasons();
	}

	private void pupulateLabels() {
		btnEditSeason.setVisible(true);
		score.setText(currentSeason.getScore().toString());
//		totalVotes.setText(currentSerie.getScoreVotes().toString()); TODO
		completedDate.setText(seasonRepository.completedDateExists(currentSeason)
				? DateUtils.mapDateToString(currentSeason.getCompletedDate())
				: "No se ha completado");
		review.setText(seasonRepository.reviewExists(currentSeason) ? currentSeason.getReview()
				: "No has hecho ninguna reseña sobre esta temporada");
	}

	private void clearLabels() {
		score.setText("");
		totalVotes.setText("votes");
		completedDate.setText("");
		review.setText("");
	}

	public ControllerSeason() {

	}
}
