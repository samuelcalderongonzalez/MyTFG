package serielizable.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import utils.AbstractController;
import utils.DateUtils;

/**
 * The edit serie controller class
 * 
 * @author Samuel Calderón González
 *
 */
public class EditSerieController extends AbstractController {

	@FXML
	private Label title;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private ComboBox<String> cbPersonalScore;

	@FXML
	private TextArea tfReview;

	private ImageView imageViewBack;
	@FXML
	private Button btFavorite;
	@FXML
	private Button getBackButton;

	private ImageView imageFavoriteBtn;

	@FXML
	private Rectangle posterImageRec;

	@FXML
	private Rectangle backgroundImage;

	Image favoriteImg = new Image(getClass().getResourceAsStream("../../utils/favorite.png"));
	Image noFavoriteImg = new Image(getClass().getResourceAsStream("../../utils/noFavorite.png"));

	public void initialize() {
		setBackgroundImage();
		setFavoriteImage();
		setPosterImg();
		setBackButtonIcon();
		populateFields();
	}

	/**
	 * Log off method
	 */
	@FXML
	public void logOff() {
		currentUser = null;
		currentFilm = null;
		currentSerie = null;
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
		currentSerie = null;
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
	 * Go back to the previous view
	 */
	@FXML
	public void handleBack() {
		currentSerie = null;
		setViewSerie();
	}

	/**
	 * Populate the field and combo boxes
	 */
	private void populateFields() {
		cbStatus.getItems().addAll("Completada", "Pendiente", "Abandonada", "En curso");
		cbPersonalScore.getItems().addAll("-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		title.setText(currentSerie.getTitle());
		cbStatus.getSelectionModel().select(currentSerie.getStatus());
		cbPersonalScore.getSelectionModel().select(currentSerie.getStringPersonalScore());
		tfReview.setText(currentSerie.getReview() != null ? currentSerie.getReview() : "");

	}

	/**
	 * Delete the current serie from the list
	 */
	@FXML
	private void deleteSerie() {
		serieRepository.deleteSerie(currentSerie);
		handleBack();

	}

	/**
	 * Save the serie with the current info
	 */
	@FXML
	private void saveSerie() {
		currentSerie.setStatus(cbStatus.getSelectionModel().getSelectedItem());
		currentSerie.setReview(tfReview.getText());
		if (cbPersonalScore.getSelectionModel().getSelectedItem() != null)
			currentSerie.setPersonalScore(cbPersonalScore.getSelectionModel().getSelectedItem());
		// If status is completed, the completedDate is currentDate
		currentSerie.setCompletedDate(
				cbStatus.getSelectionModel().getSelectedItem().equals("Completada") ? DateUtils.getCurrentDate()
						: null);
		// Sets lastUpdateDate to current date
		currentSerie.setLastUpdateDate(DateUtils.getCurrentDate());
		// Saves the serie and go back
		serieRepository.updateSerie(currentSerie);
		handleBack();
	}

	/**
	 * Set back button icon and resizes it
	 */
	private void setBackButtonIcon() {
		Image editImg = new Image(getClass().getResourceAsStream("../../utils/backButtonWhite.png"));
		imageViewBack = new ImageView(editImg);
		imageViewBack.setFitHeight(50);
		imageViewBack.setFitWidth(50);
		getBackButton.setGraphic(imageViewBack);
	}

	/**
	 * Set the poster image of the serie. If the image can't be accessed, puts a
	 * default image
	 */
	private void setPosterImg() {
		Image imgPosterDefault = new Image(getClass().getResourceAsStream("../../utils/posterImageDefault.jpg"));
		if (currentSerie.getImageLink() != null) {
			Image imgPoster = new Image(currentSerie.getImageLink());
			if (!imgPoster.isError()) {
				posterImageRec.setFill(new ImagePattern(imgPoster));
			} else {
				posterImageRec.setFill(new ImagePattern(imgPosterDefault));
			}
		} else {
			posterImageRec.setFill(new ImagePattern(imgPosterDefault));
		}
	}

	/**
	 * Set the favorite image based on the favorite's serie field
	 */
	private void setFavoriteImage() {
		if (currentSerie.isFavorite()) {
			imageFavoriteBtn = new ImageView(favoriteImg);
			resizeFavoriteImage();
		} else {
			imageFavoriteBtn = new ImageView(noFavoriteImg);
			resizeFavoriteImage();
		}

	}

	/**
	 * Resize the favorite image
	 */
	private void resizeFavoriteImage() {
		imageFavoriteBtn.setFitHeight(125);
		imageFavoriteBtn.setFitWidth(125);
		btFavorite.setGraphic(imageFavoriteBtn);
	}

	/*
	 * Toggles the favorite serie image and field
	 */
	@FXML
	private void toggleFavorite() {
		currentSerie.setFavorite(!currentSerie.isFavorite());
		setFavoriteImage();
	}

	/**
	 * Set the background image
	 */
	private void setBackgroundImage() {
		Image imgBackground = new Image(getClass().getResourceAsStream("../../utils/backgroundImage.jpg"));
		backgroundImage.setFill(new ImagePattern(imgBackground));

	}

}
