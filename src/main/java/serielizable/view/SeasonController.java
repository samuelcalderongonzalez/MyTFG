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

/**
 * The season controller class
 * 
 * @author Samuel Calderón González
 *
 */
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
		seasons = FXCollections
				.observableArrayList(currentSerie.getSeasons() != null ? currentSerie.getSeasons() : null);
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
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.jpg"));
		posterImageRectangle.setFill(new ImagePattern(imgPosterDefault));
		// Shows the first season at first
		if (!seasons.isEmpty()) {
			currentSeason = seasons.get(0);
			pupulateLabels();
		}
	}

	/**
	 * Log off method
	 */
	@FXML
	public void logOff() {
		currentUser = null;
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

	/**
	 * Open the edit season view
	 */
	@FXML
	public void editSeason() {
		setViewEditSeason();
	}

	/**
	 * Go back to the previous view
	 */
	@FXML
	public void handleBack() {
		setViewSerie();
	}

	/**
	 * Set the background image
	 */
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

	/**
	 * Populate the labels
	 */
	private void pupulateLabels() {
		btnEditSeason.setVisible(true);
		score.setText(currentSeason.getStringScore());
		completedDate.setText(currentSeason.getCompletedDate());
		review.setText(currentSeason.getReview());
	}

	/**
	 * Clear the labels
	 */
	private void clearLabels() {
		score.setText("");
		totalVotes.setText("votes");
		completedDate.setText("");
		review.setText("");
	}

	public SeasonController() {

	}

	/**
	 * Set the edit button icon and resizes it
	 */
	private void setButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/edit.png"));
		imageView = new ImageView(editImg);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		btnEditSeason.setGraphic(imageView);
	}

	/**
	 * Set the back button icon and resizes it
	 */
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButtonWhite.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(40);
		imageViewBack.setFitWidth(40);
		getBackButton.setGraphic(imageViewBack);
	}
}
