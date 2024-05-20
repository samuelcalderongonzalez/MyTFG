package serielizable.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import serielizable.entity.Season;
import utils.AbstractController;

public class SeasonController extends AbstractController {

	private ObservableList<Season> seasons;

	@FXML
	private Label score;
	
	@FXML
	private Label serieTitle;

	@FXML
	private Label totalVotes;

	@FXML
	private TextField completedDate;

	@FXML
	private TextArea review;

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
	private Rectangle posterImageRectangle;
	
	@FXML
	private Rectangle backgroundImage;
	
	@FXML
	public void initialize() {
		setPosterImg();
		setBackgroundImage();
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
	public void exit() {
		Platform.exit();
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
	
	@FXML
	public void editSeason() {
		setViewEditSeason();
	}
	
	@FXML
	public void handleBack() {
		setViewSerie();
	}
	
	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}
	
	/**
	 * This method fills the poster with the serie image
	 */
	private void setPosterImg() {
		// Create a default image
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.jpg"));
		// If the current serie has an url:
		if (currentSerie.getImageLink() != null) {
			// Create an image with the url
			Image imgPoster = new Image(currentSerie.getImageLink());
			// If the image is valid:
			if (!imgPoster.isError()) {
				// Assign the image
				posterImageRectangle.setFill(new ImagePattern(imgPoster));
			}
			// Assign the default image
			else {
				posterImageRectangle.setFill(new ImagePattern(imgPosterDefault));
			}
		}
		// Assign the default image
		else {
			posterImageRectangle.setFill(new ImagePattern(imgPosterDefault));
		}
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

	public SeasonController() {

	}
	
	private void setButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/edit.png"));
		imageView = new ImageView(editImg);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnEditSeason.setGraphic(imageView);
	}
	
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButtonWhite.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(40);
		imageViewBack.setFitWidth(40);
		getBackButton.setGraphic(imageViewBack);
	}
}
