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
import serielizable.entity.Season;
import utils.AbstractController;

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
	private Button getBackButton;

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
	
	private ImageView imageView;
	
	private ImageView imageViewBack;
	
	@FXML
	public void initialize() {
		setBackButtonIcon();
		setButtonIcon();
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
				pupulateLabels();
			}
		});
		//Mostrar la primera season al iniciar la vista
		currentSeason = seasons.get(0);
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
	public void serie() {
		setViewSerie();
	}
	
	@FXML
	public void editSeason() {
		setViewEditSeason();
	}
	
	@FXML
	public void handleBack() {
		setViewSerie();
	}

	private void pupulateLabels() {
		btnEditSeason.setVisible(true);
		score.setText(currentSeason.getStringScore());
		completedDate.setText(currentSeason.getCompletedDate());
		review.setText(currentSeason.getReview());
	}

	private void clearLabels() {
		score.setText("");
		totalVotes.setText("votes");
		completedDate.setText("");
		review.setText("");
	}

	public ControllerSeason() {

	}
	
	private void setButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/edit.png"));
		imageView = new ImageView(editImg);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnEditSeason.setGraphic(imageView);
	}
	
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButton.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(40);
		imageViewBack.setFitWidth(40);
		getBackButton.setGraphic(imageViewBack);
	}
}
